package com.talkdesk.tdx.nlp.transcription.serialization;

import com.talkdesk.tdx.nlp.transcription.TranscriptionState;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class TranscriptionStateDeserializer extends ObjectMapperDeserializer<TranscriptionState> {

    public TranscriptionStateDeserializer() {
        super(TranscriptionState.class);
    }
}
