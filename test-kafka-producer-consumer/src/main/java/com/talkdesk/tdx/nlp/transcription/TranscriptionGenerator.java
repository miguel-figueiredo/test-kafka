package com.talkdesk.tdx.nlp.transcription;


import io.reactivex.Flowable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.*;
import javax.json.bind.JsonbBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class TranscriptionGenerator {
    private Random random = new Random();

    @Outgoing("generated-transcription")
    public Flowable<Transcription> generate() {
        return Flowable.interval(5, TimeUnit.SECONDS).map(tick -> createTranscription());
    }

    private String createMessage() {
        return JsonbBuilder.create().toJson(createTranscription());
    }

    private Transcription createTranscription() {
        return new Transcription(random.nextLong(), RandomStringUtils.randomAscii(10 ));
    }
}
