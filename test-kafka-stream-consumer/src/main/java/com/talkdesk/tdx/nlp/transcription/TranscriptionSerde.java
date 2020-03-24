package com.talkdesk.tdx.nlp.transcription;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JacksonUtils;
import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;
import java.io.*;
import java.util.*;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class TranscriptionSerde implements Serde<Transcription> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<Transcription> serializer() {
        return new ObjectMapperSerializer<>();
    }

    @Override
    public Deserializer<Transcription> deserializer() {
        return new TranscriptionDeserializer();
    }
}
