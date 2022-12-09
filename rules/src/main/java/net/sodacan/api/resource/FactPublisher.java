package net.sodacan.api.resource;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
import net.sodacan.rules.RulesException;
import net.sodacan.rules.State;
import net.sodacan.rules.TimerWorker;

@Singleton
@Path("subscribe")
public class FactPublisher {
    private static Sse sse;
    private static SseBroadcaster broadcaster;
    private static ObjectMapper mapper = new ObjectMapper();
 
    public FactPublisher(@Context final Sse sse) {
        FactPublisher.sse = sse;
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

    public static void broadcastState(State state) {
    	if (sse==null) return;
    	try {
			// build a JSON structure    	
			ObjectNode topNode = mapper.createObjectNode();
			// create three JSON objects
			ObjectNode node = mapper.createObjectNode();
			node.put("name",state.getName());
			node.put("value", state.getValue());
			node.put("level", state.getLevel());
			topNode.put("type", "state");
			topNode.set("state",node);
			FactPublisher.sendMessage(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(topNode));
		} catch (JsonProcessingException e) {
			throw new RulesException("Error formatting json string",e);
		}
    }
    /**
     * Broadcast TimeWorker
     * @param tw
     * @param iud insert, update, or delete
     */
    public static void broadcastTimerWorker(TimerWorker tw, String iud) {
    	if (sse==null) return;
    	try {
			// build a JSON structure    	
			ObjectNode topNode = mapper.createObjectNode();
			// create three JSON objects
			ObjectNode node = mapper.createObjectNode();
			node.put("state",tw.getState());
			node.put("toValue", tw.getToValue());
			node.put("time", tw.getTime());
			topNode.put("type", "timerWorker");
			topNode.put("iud", iud);
			topNode.set("tw",node);
			FactPublisher.sendMessage(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(topNode));
		} catch (JsonProcessingException e) {
			throw new RulesException("Error formatting json string of TimeWorker",e);
		}
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
