package com.talkdesk.tdx.nlp.transcription;

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

    @Override
    public String toString() {
        return "TranscriptionState{" +
            "id='" + id + '\'' +
            ", state='" + state + '\'' +
            '}';
    }
}
