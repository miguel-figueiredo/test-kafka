package com.talkdesk.tdx.nlp.transcription;

import java.util.*;
import javax.json.bind.JsonbBuilder;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

public class TranscriptionDeserializer implements Deserializer<Transcription> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Transcription deserialize(String s, byte[] bytes) {
        return deserialize(bytes);
    }

    @Override
    public Transcription deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(data);
    }

    private Transcription deserialize(byte[] bytes) {
        return JsonbBuilder.create().fromJson(new String(bytes), Transcription.class);
    }

    @Override
    public void close() {

    }
}
