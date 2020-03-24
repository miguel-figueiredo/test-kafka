package com.talkdesk.tdx.nlp.transcription;

import org.apache.kafka.streams.kstream.ValueJoiner;

public class TranscriptionStateJoiner implements ValueJoiner<Transcription, TranscriptionState, String> {
    @Override
    public String apply(Transcription transcription, TranscriptionState transcriptionState) {
        return transcription.getId().equals(transcriptionState.getId()) ?
            transcription + " - " + transcriptionState : null;
    }
}
