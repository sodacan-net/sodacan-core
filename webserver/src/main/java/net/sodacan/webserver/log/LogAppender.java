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


import java.io.Serializable;
import java.util.Arrays;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.sodacan.SodacanException;
import net.sodacan.webserver.sse.PushToBrowser;

@Plugin(
		  name = "LogAppender", 
		  category = Core.CATEGORY_NAME, 
		  elementType = Appender.ELEMENT_TYPE)
public class LogAppender extends AbstractAppender {

	protected LogAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions,
			Property[] properties) {
		super(name, filter, layout, ignoreExceptions, properties);
	}

	 @PluginFactory
	    public static LogAppender createAppender( @PluginAttribute("name") String name, 
	    										 @PluginElement("Filter") Filter filter) {
	        return new LogAppender(name, filter, null, false, null);
	    }

	@Override
	public void append(LogEvent event) {
	    try {
//			LogBroadcaster.sendMessage(event.getMessage().getFormattedMessage());
		    ObjectMapper mapper = new ObjectMapper();
	
		    // create three JSON objects
		    ObjectNode ln = mapper.createObjectNode();
		    ln.put("name","log");
		    ln.put("value", event.getMessage().getFormattedMessage());
		    
		    ArrayNode arrayNode = mapper.createArrayNode();
		    // add JSON users to array
		    arrayNode.addAll(Arrays.asList(ln));
			PushToBrowser.sendMessage(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode));
		} catch (JsonProcessingException e) {
			throw new SodacanException("Error appending message");
		}
	}

}
