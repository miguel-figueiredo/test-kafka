package com.talkdesk.tdx.nlp.transcription;


import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.*;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TranscriptionGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionGenerator.class);

    Flowable<String> publisher = Flowable.interval(getInterval(), TimeUnit.MILLISECONDS).map(tick -> getId()).share();

    List<String> ids = Arrays.asList("A", "B", "C", "D");

    Map<String, Long> generatedTextById = new ConcurrentHashMap<>();

    public TranscriptionGenerator() {
        for(String id : ids){
            generatedTextById.put(id, 0L);
        }
    }

    @Outgoing("generated-transcription")
    public Flowable<KafkaMessage<String, Transcription>> generateTranscription() {
        return publisher.map(id -> getTranscription(id));
    }

    @Outgoing("generated-transcription-state")
    public Flowable<KafkaMessage<String, TranscriptionState>> generateTranscriptionState() {
        return publisher.filter(id -> generatedTextById.get(id) == 1L).map(id -> getTranscriptionState(id));
    }

    private KafkaMessage<String, Transcription> getTranscription(String id) {
        Transcription transcription = new Transcription(id, getText(id));
        LOGGER.info("Generating transcription: {}", transcription);
        return KafkaMessage.of(transcription.getId(), transcription);
    }

    private KafkaMessage<String, TranscriptionState> getTranscriptionState(String id) {
        TranscriptionState transcriptionState = new TranscriptionState(id);
        LOGGER.info("Generating transcription state: {}", transcriptionState);
        return KafkaMessage.of(transcriptionState.getId(), transcriptionState);
    }

    private String getId() {
        return ids.get((int) (Math.random() * 10 % ids.size()));
    }

    private String getText(String id){
        return generatedTextById.compute(id, (s, l) -> ++l).toString();
    }

    private int getInterval() {
        String interval = System.getenv("INTERVAL");
        return Integer.parseInt(interval == null ? "1000" : interval);
    }
}
