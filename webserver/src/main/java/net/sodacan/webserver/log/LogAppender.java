/*
 * Copyright 2023 John M Churin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sodacan.webserver.log;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.sodacan.SodacanException;
import net.sodacan.webserver.sse.PushToBrowser;

public class LogAppender {
	private final static Logger logger = LoggerFactory.getLogger(LogAppender.class);

//	
//	@Override
//	public void close() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean requiresLayout() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	protected void append(LoggingEvent event) {
//	    try {
////			LogBroadcaster.sendMessage(event.getMessage().getFormattedMessage());
//		    ObjectMapper mapper = new ObjectMapper();
//	
//		    // create three JSON objects
//		    ObjectNode ln = mapper.createObjectNode();
//		    ln.put("name","log");
//		    ln.put("value", event.getMessage().toString());
//		    
//		    ArrayNode arrayNode = mapper.createArrayNode();
//		    // add JSON users to array
//		    arrayNode.addAll(Arrays.asList(ln));
//			PushToBrowser.sendMessage(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode));
//		} catch (JsonProcessingException e) {
//			throw new SodacanException("Error appending message");
//		}
//	}

}
