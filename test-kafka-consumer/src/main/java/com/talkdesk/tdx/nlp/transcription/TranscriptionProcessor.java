package com.talkdesk.tdx.nlp.transcription;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.*;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TranscriptionProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionProcessor.class);

    @Incoming("transcriptions")
    public CompletionStage<Void> process(KafkaMessage<String, Transcription> message) {
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep((long)(Math.random() * 2000));
            } catch (InterruptedException e) {
                LOGGER.error("Sleep was interrupted", e);
            }
            LOGGER.info("Processing transcription from partition {}: {}",
                message.getPartition(), message.getPayload().getText());
        });
    }
}
