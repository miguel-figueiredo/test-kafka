package com.talkdesk.tdx.nlp.transcription;

import java.util.*;
import javax.json.bind.JsonbBuilder;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

public class ObjectSerializer implements Serializer<Object> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, Object o) {
        return serialize(o);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Object data) {
        return serialize(data);
    }

    private byte[] serialize(Object o) {
        return JsonbBuilder.create().toJson(o).getBytes();
    }

    @Override
    public void close() {

    }
}
