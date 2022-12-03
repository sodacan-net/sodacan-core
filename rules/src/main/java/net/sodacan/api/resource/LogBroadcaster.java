package net.sodacan.api.resource;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;

@Singleton
@Path("broadcast")
public class LogBroadcaster {
    private static Sse sse;
    private static SseBroadcaster broadcaster;
 
    public LogBroadcaster(@Context final Sse sse) {
        LogBroadcaster.sse = sse;
        broadcaster = sse.newBroadcaster();
    }
 
    public static void sendMessage(String message) {
    	if (sse==null) return;
        final OutboundSseEvent event = sse.newEventBuilder()
                .name("message")
                .mediaType(MediaType.TEXT_PLAIN_TYPE)
                .data(String.class, message)
                .build();
     
            broadcaster.broadcast(event);
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String broadcastMessage(String message) {
    	sendMessage(message);
        return "Message '" + message + "' has been broadcast.";
    }

    /**
     * Register a new web client
     * @param eventSink
     */
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void listenToBroadcast(@Context SseEventSink eventSink) {
        broadcaster.register(eventSink);
    }
}
