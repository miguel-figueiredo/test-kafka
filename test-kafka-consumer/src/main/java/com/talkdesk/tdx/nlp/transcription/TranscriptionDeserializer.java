package com.talkdesk.tdx.nlp.transcription;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class TranscriptionDeserializer extends ObjectMapperDeserializer<Transcription> {

    public TranscriptionDeserializer() {
        super(Transcription.class);
    }
}
