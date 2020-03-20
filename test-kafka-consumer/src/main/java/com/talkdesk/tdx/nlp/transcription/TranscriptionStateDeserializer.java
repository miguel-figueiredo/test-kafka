package com.talkdesk.tdx.nlp.transcription;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class TranscriptionStateDeserializer extends ObjectMapperDeserializer<TranscriptionState> {

    public TranscriptionStateDeserializer() {
        super(TranscriptionState.class);
    }
}
