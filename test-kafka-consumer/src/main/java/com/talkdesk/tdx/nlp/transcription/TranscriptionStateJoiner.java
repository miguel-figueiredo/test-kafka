package com.talkdesk.tdx.nlp.transcription;

import org.apache.kafka.streams.kstream.ValueJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranscriptionStateJoiner implements ValueJoiner<Transcription, TranscriptionState, TranscriptionState> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionStateJoiner.class);

    @Override
    public TranscriptionState apply(Transcription transcription, TranscriptionState transcriptionState) {
        LOGGER.info("Joining: {} {}", transcription, transcriptionState);
        return transcription.getId().equals(transcriptionState.getId()) ?
            new TranscriptionState(transcription.getId(),
                transcriptionState.getState() + " " + transcription.getText()) : null;
    }
}
