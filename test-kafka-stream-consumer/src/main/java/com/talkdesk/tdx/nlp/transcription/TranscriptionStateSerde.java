package com.talkdesk.tdx.nlp.transcription;

import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;
import java.util.*;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class TranscriptionStateSerde implements Serde<TranscriptionState> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<TranscriptionState> serializer() {
        return new ObjectMapperSerializer<>();
    }

    @Override
    public Deserializer<TranscriptionState> deserializer() {
        return new TranscriptionStateDeserializer();
    }
}
