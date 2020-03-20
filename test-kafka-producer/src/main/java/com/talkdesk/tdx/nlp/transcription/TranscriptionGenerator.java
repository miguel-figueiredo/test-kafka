package com.talkdesk.tdx.nlp.transcription;


import com.github.javafaker.Faker;
import io.reactivex.Flowable;
import io.reactivex.flowables.ConnectableFlowable;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.*;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TranscriptionGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionGenerator.class);

    Flowable<String> publisher = Flowable.interval(1, TimeUnit.SECONDS).map(tick -> getId()).share();

    @Outgoing("generated-transcription")
    public Flowable<KafkaMessage<String, Transcription>> generateTranscription() {
        return publisher.map(id -> getTranscription(id));
    }

    @Outgoing("generated-transcription-state")
    public Flowable<KafkaMessage<String, TranscriptionState>> generateTranscriptionState() {
        return publisher.map(id -> getTranscriptionState(id));
    }

    private KafkaMessage<String, Transcription> getTranscription(String id) {
        Transcription transcription = new Transcription(id, getSentence());
        return KafkaMessage.of(transcription.getId(), transcription);
    }

    private KafkaMessage<String, TranscriptionState> getTranscriptionState(String id) {
        TranscriptionState transcription = new TranscriptionState(id);
        return KafkaMessage.of(transcription.getId(), transcription);
    }

    private static String getId() {
        String id = new Faker().dune().planet();
        LOGGER.info("Generated ID: {}", id);
        return id;
    }

    private String getSentence() {
        return new Faker().dune().saying();
    }
}
