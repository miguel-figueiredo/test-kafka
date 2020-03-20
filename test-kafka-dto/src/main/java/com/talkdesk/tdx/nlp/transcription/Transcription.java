package com.talkdesk.tdx.nlp.transcription;

import java.io.*;

public class Transcription implements Serializable {
    String id;
    String text;

    public Transcription(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
