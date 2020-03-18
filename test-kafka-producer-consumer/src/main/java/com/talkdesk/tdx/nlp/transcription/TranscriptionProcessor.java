package com.talkdesk.tdx.nlp.transcription;

import javax.enterprise.context.*;
import javax.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TranscriptionProcessor {
    private static final Logger LOGGER  = LoggerFactory.getLogger(TranscriptionProcessor.class);

    @Incoming("transcriptions")
    public void process(Transcription transcription) {
        LOGGER.info("Processing transcription: {}", transcription.getText());
    }
}
