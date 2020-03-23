package com.talkdesk.tdx.nlp.transcription;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.SynchronousQueue;
import javax.enterprise.context.*;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TranscriptionProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionProcessor.class);

    private SynchronousQueue<TranscriptionState> states = new SynchronousQueue<>();

    @Incoming("transcriptions")
    public CompletionStage<Void> processTranscription(KafkaMessage<String, Transcription> message) {
        return CompletableFuture.runAsync(() -> {
            //sleep();
            Transcription transcription = message.getPayload();
            String key = message.getKey();
            LOGGER.info("Processing transcription from partition {}: {} - {}",
                message.getPartition(), transcription.getId(), transcription.getText());
            try {
                TranscriptionState state = states.take();
                sendMessage(transcription, state);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void sendMessage(Transcription transcription, TranscriptionState state) {
        LOGGER.info("Sending message {} {}", transcription, state);
    }

    @Incoming("transcription-states")
    public CompletionStage<Void> processTranscriptionState(KafkaMessage<String, TranscriptionState> message) {
        return CompletableFuture.runAsync(() -> {
            //  sleep();
            TranscriptionState transcriptionState = message.getPayload();
            String key = message.getKey();
            LOGGER.info("Processing transcription state from partition {}: {} - {}",
                message.getPartition(), transcriptionState.getId(), transcriptionState.getState());
            try {
                states.put(transcriptionState);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void sleep() {
        try {
            Thread.sleep((long) (Math.random() * 2000));
        } catch (InterruptedException e) {
            LOGGER.error("Sleep was interrupted", e);
        }
    }
}
