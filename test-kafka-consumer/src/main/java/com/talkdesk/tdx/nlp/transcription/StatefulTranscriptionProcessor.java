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
public class StatefulTranscriptionProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatefulTranscriptionProcessor.class);

    @Incoming("stateful-transcriptions")
    public CompletionStage<Void> processTranscription(KafkaMessage<String, StatefulTranscription> message) {
        return CompletableFuture.runAsync(() -> {
            StatefulTranscription transcription = message.getPayload();
            String key = message.getKey();
            LOGGER.info("Processing stateful transcription from partition {}: {} - {} - {}",
                message.getPartition(), transcription.getId(), transcription.getText(), transcription.getState());

            // TODO: generate new state
        });
    }
}
