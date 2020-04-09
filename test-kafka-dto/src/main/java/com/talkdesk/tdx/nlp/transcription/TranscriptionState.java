package com.talkdesk.tdx.nlp.transcription;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TranscriptionState {
    private String id;
    private String state;

    private TranscriptionState() {
        // Used for deserialization
    }

    public TranscriptionState(String id) {
        this.id = id;
        this.state = "";
    }

    public TranscriptionState(String id, String state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    @JsonIgnore
    public boolean isValid() {
        String[] stateArray = state.trim().split(" ");
        if (stateArray.length < 2) {
            return true;
        }

        for (int i = 1; i < stateArray.length; i++) {
            if (Integer.valueOf(stateArray[i]) != Integer.valueOf(stateArray[i - 1]) + 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "TranscriptionState{" +
            "id='" + id + '\'' +
            ", state='" + state + '\'' +
            '}';
    }
}
