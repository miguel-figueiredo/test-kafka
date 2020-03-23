package com.talkdesk.tdx.nlp.transcription;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.SynchronousQueue;
import javax.enterprise.context.*;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TranscriptionProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionProcessor.class);

    private SynchronousQueue<TranscriptionState> states = new SynchronousQueue<>();

    @Incoming("transcriptions")
    @Outgoing("generated-transcription-state")
    public KafkaMessage<String, TranscriptionState> processTranscription(Transcription transcription) {
        String id = transcription.getId();
        LOGGER.info("Processing transcription : {} - {}",
            transcription.getId(), transcription.getText());
        try {
            TranscriptionState state = states.take();
            return createMessage(transcription, state);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private KafkaMessage<String, TranscriptionState> createMessage(Transcription transcription, TranscriptionState state) {
        TranscriptionState newState = new TranscriptionState(transcription.getId(),
            state.getState() + "\n" +transcription.getText());
        LOGGER.info("Sending message {}", newState);
        return KafkaMessage.of(transcription.getId(), newState);
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
