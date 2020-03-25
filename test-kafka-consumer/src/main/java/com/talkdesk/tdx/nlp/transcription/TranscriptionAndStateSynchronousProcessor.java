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
public class TranscriptionAndStateSynchronousProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionAndStateSynchronousProcessor.class);

    private SynchronousQueue<TranscriptionState> states = new SynchronousQueue<>();

    //@Incoming("transcriptions")
    public CompletionStage<Void> processTranscription(KafkaMessage<String, Transcription> message) {
        return CompletableFuture.runAsync(() -> {
            //sleep();
            Transcription transcription = message.getPayload();
            String key = message.getKey();
            LOGGER.info("Processing transcription from partition {}: {} - {}",
                message.getPartition(), transcription.getId(), transcription.getText());
            //waitForState(transcription);
        });
    }

    private void waitForState(Transcription transcription) {
        try {
            TranscriptionState state = states.take();
            sendMessage(transcription, state);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(Transcription transcription, TranscriptionState state) {
        LOGGER.info("Transcription and state received.");
    }

    //@Incoming("transcription-states")
    public CompletionStage<Void> processTranscriptionState(KafkaMessage<String, TranscriptionState> message) {
        return CompletableFuture.runAsync(() -> {
            //  sleep();
            TranscriptionState transcriptionState = message.getPayload();
            String key = message.getKey();
            LOGGER.info("Processing transcription state from partition {}: {} - {}",
                message.getPartition(), transcriptionState.getId(), transcriptionState.getState());
            //waitForTranscription(transcriptionState);
        });
    }

    private void waitForTranscription(TranscriptionState transcriptionState) {
        try {
            states.put(transcriptionState);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sleep() {
        try {
            Thread.sleep((long) (Math.random() * 2000));
        } catch (InterruptedException e) {
            LOGGER.error("Sleep was interrupted", e);
        }
    }
}
