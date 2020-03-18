package com.talkdesk.tdx.nlp.transcription;

import java.io.*;
import java.util.*;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectDeserializer implements Deserializer<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        return deserialize(bytes);
    }

    @Override
    public Object deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(data);
    }

    private Object deserialize(byte[] bytes) {
        // For some reason when using SerializationUtils the exception ClassNotFoundException is thrown
        try(ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(bytes))){
            return is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize object", e);
        }
    }

    @Override
    public void close() {

    }
}
