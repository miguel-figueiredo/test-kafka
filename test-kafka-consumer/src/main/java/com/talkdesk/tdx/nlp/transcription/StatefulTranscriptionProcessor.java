package com.talkdesk.tdx.nlp.transcription;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.SynchronousQueue;
import javax.enterprise.context.*;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class StatefulTranscriptionProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatefulTranscriptionProcessor.class);

    @Inject
    @Channel("transcription-states")
    Emitter<KafkaMessage<String, TranscriptionState>> emmiEmitter;

    @Incoming("stateful-transcriptions")
    public CompletableFuture<Void> processTranscription(StatefulTranscription statefulTranscription) {
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("Processing stateful transcription from partition {}", statefulTranscription);

            TranscriptionState state = new TranscriptionState(statefulTranscription.getId(),
                statefulTranscription.getState() + " " + statefulTranscription.getText());

            LOGGER.info("State: {}", state.getState());

            if (state.isValid()) {
                sleep();
                LOGGER.info("Sending state transcription: {}", state);
                emmiEmitter.send(KafkaMessage.of(statefulTranscription.getId(), state));
            }
        });
    }

    private void sleep() {
        try {
            Thread.sleep(getInterval());
        } catch (InterruptedException e) {
            LOGGER.error("Sleep was interrupted", e);
        }
    }

    private int getInterval() {
        String interval = System.getenv("PROCESSING_INTERVAL");
        return Integer.parseInt(interval == null ? "0" : interval);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error("Sleep was interrupted", e);
        }
    }
}
