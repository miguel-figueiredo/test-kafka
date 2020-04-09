package com.talkdesk.tdx.nlp.transcription;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TranscriptionStateTest {

    @ParameterizedTest
    @ValueSource(strings = { "1", " 1 2 ", "2 3 4" })
    void validStates(String state) {
        final TranscriptionState testObj = new TranscriptionState("ID", state);

        assertTrue(testObj.isValid());
    }

    @ParameterizedTest
    @ValueSource(strings = { "2 1", "1 3", "1 2 5" })
    void inValidStates(String state) {
        final TranscriptionState testObj = new TranscriptionState("ID", state);

        assertFalse(testObj.isValid());
    }
}