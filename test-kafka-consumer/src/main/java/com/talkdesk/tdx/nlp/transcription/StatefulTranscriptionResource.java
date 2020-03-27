package com.talkdesk.tdx.nlp.transcription;

import io.smallrye.reactive.messaging.annotations.Channel;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

@Path("/transcriptions")
public class StatefulTranscriptionResource {
    @Inject
    @Channel("resource-stateful-transcriptions")
    Publisher<StatefulTranscription> transcriptions;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Publisher<StatefulTranscription> stream() {
        return transcriptions;
    }
}
