package com.talkdesk.tdx.nlp.transcription;

public class StatefulTranscription {

    private String id;
    private String text;
    private String state;

    private StatefulTranscription(){
        // Required for deserialization
    }

    public StatefulTranscription(Transcription transcription, TranscriptionState state) {
        validate(transcription, state);
        this.id = transcription.getId();
        this.text = transcription.getText();
        this.state = state.getState();

    }

    private void validate(Transcription transcription, TranscriptionState state) {
        if (!transcription.getId().equals(state.getId())) {
            throw new RuntimeException(
                String.format("Transcription and state IDs do not match. Transcription ID: %s, State ID: %s",
                    transcription.getId(), state.getId()));
        }
    }

    public String getText(){
        return text;
    }

    public String getState(){
        return state;
    }

    public String getId(){
        return id;
    }
}
