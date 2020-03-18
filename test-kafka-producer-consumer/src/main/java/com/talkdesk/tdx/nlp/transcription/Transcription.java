package com.talkdesk.tdx.nlp.transcription;

import io.vertx.core.json.JsonObject;
import java.io.*;

public class Transcription {
    Long id;
    String text;

    public Transcription() {
    }

    public Transcription(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
