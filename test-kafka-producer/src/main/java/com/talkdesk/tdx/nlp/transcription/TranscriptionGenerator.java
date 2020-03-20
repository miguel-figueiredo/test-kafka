package com.talkdesk.tdx.nlp.transcription;


import com.github.javafaker.Faker;
import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.*;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class TranscriptionGenerator {

    @Outgoing("generated-transcription")
    public Flowable<KafkaMessage<String, Transcription>> generateTranscription() {
        return Flowable.interval(1, TimeUnit.SECONDS)
            .map(tick -> getTranscription());
    }

    @Outgoing("generated-transcription-state")
    public Flowable<KafkaMessage<String, Transcription>> generateTranscriptionState() {
        return Flowable.interval(1, TimeUnit.SECONDS)
            .map(tick -> getTranscriptionState());
    }

    private KafkaMessage<String, Transcription> getTranscription() {
        Transcription transcription = createTranscription();
        return KafkaMessage.of(transcription.getId(), transcription);
    }

    private KafkaMessage<String, Transcription> getTranscriptionState() {
        Transcription transcription = createTranscription();
        return KafkaMessage.of(transcription.getId(), transcription);
    }

    private Transcription createTranscription() {
        return new Transcription(getId(), getSentence());
    }

    private String getId() {
        return new Faker().dune().planet();
    }

    private String getSentence() {
        return new Faker().dune().saying();
    }
}
