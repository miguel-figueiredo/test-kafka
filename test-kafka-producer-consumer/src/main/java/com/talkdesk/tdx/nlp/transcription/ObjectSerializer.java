package com.talkdesk.tdx.nlp.transcription;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

public class ObjectSerializer implements Serializer<Serializable> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, Serializable o) {
        return serialize(o);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Serializable data) {
        return serialize(data);
    }

    private byte[] serialize(Serializable o) {
        return SerializationUtils.serialize(o);
    }

    @Override
    public void close() {

    }
}
