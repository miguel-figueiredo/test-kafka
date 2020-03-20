package com.talkdesk.tdx.nlp.transcription;

import java.util.*;

public class TranscriptionState {
    private String id;
    List<String> state;

    public TranscriptionState(String id) {
        this.id = id;
        this.state = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<String> getState() {
        return state;
    }
}
