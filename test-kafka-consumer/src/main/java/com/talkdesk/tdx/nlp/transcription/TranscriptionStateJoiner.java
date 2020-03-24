package com.talkdesk.tdx.nlp.transcription;

import org.apache.kafka.streams.kstream.ValueJoiner;

public class TranscriptionStateJoiner implements ValueJoiner<Transcription, TranscriptionState, TranscriptionState> {
    @Override
    public TranscriptionState apply(Transcription transcription, TranscriptionState transcriptionState) {
        return transcription.getId().equals(transcriptionState.getId()) ?
            new TranscriptionState(transcription.getId()) : null;
    }
}
