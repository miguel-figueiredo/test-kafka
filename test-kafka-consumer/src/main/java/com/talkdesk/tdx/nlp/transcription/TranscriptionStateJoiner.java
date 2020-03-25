package com.talkdesk.tdx.nlp.transcription;

import org.apache.kafka.streams.kstream.ValueJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranscriptionStateJoiner implements ValueJoiner<Transcription, TranscriptionState, StatefulTranscription> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptionStateJoiner.class);

    @Override
    public StatefulTranscription apply(Transcription transcription, TranscriptionState transcriptionState) {
        LOGGER.info("Joining: {} {}", transcription, transcriptionState);
        return transcription.getId().equals(transcriptionState.getId()) ?
            new StatefulTranscription(transcription, transcriptionState) : null;
    }
}
