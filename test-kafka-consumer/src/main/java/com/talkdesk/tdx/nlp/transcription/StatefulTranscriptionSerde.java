package com.talkdesk.tdx.nlp.transcription;

import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;
import java.util.*;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class StatefulTranscriptionSerde implements Serde<StatefulTranscription> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<StatefulTranscription> serializer() {
        return new ObjectMapperSerializer<>();
    }

    @Override
    public Deserializer<StatefulTranscription> deserializer() {
        return new StatefulTranscriptionDeserializer();
    }
}
