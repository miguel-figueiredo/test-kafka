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
public class StatefulTranscriptionProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatefulTranscriptionProcessor.class);

    @Incoming("stateful-transcriptions")
    @Outgoing("transcription-states")
    public KafkaMessage<String, TranscriptionState> processTranscription(StatefulTranscription statefulTranscription) {
        LOGGER.info("Processing stateful transcription from partition {}", statefulTranscription);

        TranscriptionState state = new TranscriptionState(statefulTranscription.getId(),
            statefulTranscription.getState() + " " + statefulTranscription.getText());

        return KafkaMessage.of(statefulTranscription.getId(), state);
    }
}
