package com.talkdesk.tdx.nlp.price;

import io.smallrye.reactive.messaging.annotations.Channel;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.SseElementType;

/**
 * A simple resource retrieving the in-memory "my-data-stream" and sending the items as server-sent events.
 */
@Path("/prices")
public class PriceResource {

    @Inject
    // @Channel is a qualifier to inject a channel into a regular CDI bean.
    // It’s used to bridge the imperative and reactive worlds, or retrieve channels managed by Reactive Messaging.
    @Channel("my-data-stream")
    Publisher<Double> prices;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("text/plain")
    public Publisher<Double> stream() {
        return prices;
    }
}