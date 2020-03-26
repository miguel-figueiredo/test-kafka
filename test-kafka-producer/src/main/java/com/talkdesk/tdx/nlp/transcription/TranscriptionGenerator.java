package com.talkdesk.tdx.nlp.transcription;


import com.github.javafaker.Faker;
import io.quarkus.runtime.StartupEvent;
import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.*;
import javax.enterprise.event.Observes;
import org.apache.commons.lang3.RandomUtils;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TranscriptionGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionGenerator.class);

    Flowable<String> publisher = Flowable.interval(getInterval(), TimeUnit.MILLISECONDS).map(tick -> getId()).share();

    Map<String, Long> generatedIds = new ConcurrentHashMap<>();

    void onStart(@Observes StartupEvent ev) {
        publisher.subscribe(i -> generatedIds.putIfAbsent(i, 0L));
    }

    @Outgoing("generated-transcription")
    public Flowable<KafkaMessage<String, Transcription>> generateTranscription() {
        return publisher.map(id -> getTranscription(id));
    }

    @Outgoing("generated-transcription-state")
    public Flowable<KafkaMessage<String, TranscriptionState>> generateTranscriptionState() {
        return publisher.filter(id -> !generatedIds.containsKey(id)).map(id -> getTranscriptionState(id));
    }

    private KafkaMessage<String, Transcription> getTranscription(String id) {
        Transcription transcription = new Transcription(id, getSentence(id));
        LOGGER.info("Generating transcription: {}", transcription);
        return KafkaMessage.of(transcription.getId(), transcription);
    }

    private KafkaMessage<String, TranscriptionState> getTranscriptionState(String id) {
        TranscriptionState transcriptionState = new TranscriptionState(id);
        LOGGER.info("Generating transcription state: {}", transcriptionState);
        return KafkaMessage.of(transcriptionState.getId(), transcriptionState);
    }

    private String getId() {
        String id = new Faker().dune().planet();
        LOGGER.info("Generated ID: {}", id);
        return id;
    }

    private String getSentence(String id){
        Long sentence = generatedIds.computeIfPresent(id, (s, l) -> ++l);
        return sentence == null ? "1" : sentence.toString();
    }

    private String getSentence() {
        return new Faker().dune().saying();
    }

    private int getInterval() {
        return Integer.parseInt(System.getenv("INTERVAL"));
    }
}
