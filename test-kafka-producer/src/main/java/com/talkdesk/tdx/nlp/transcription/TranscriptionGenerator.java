package com.talkdesk.tdx.nlp.transcription;


import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class TranscriptionGenerator {

    @Outgoing("generated-transcription")
    public Flowable<KafkaMessage<String, Transcription>> generate() {
        return Flowable.interval(1, TimeUnit.SECONDS)
            .map(tick -> getMessage());
    }

    private KafkaMessage<String, Transcription> getMessage() {
        Transcription transcription = createTranscription();
        return KafkaMessage.of(transcription.getId(), transcription);
    }

    private Transcription createTranscription() {
        return new Transcription(getText(), getText());
    }

    private String getText() {
        return RandomStringUtils.randomAscii(10);
    }
}
