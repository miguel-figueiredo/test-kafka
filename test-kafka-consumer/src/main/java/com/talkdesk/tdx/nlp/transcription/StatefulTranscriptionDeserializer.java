package com.talkdesk.tdx.nlp.transcription;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class StatefulTranscriptionDeserializer extends ObjectMapperDeserializer<StatefulTranscription> {

    public StatefulTranscriptionDeserializer() {
        super(StatefulTranscription.class);
    }
}
