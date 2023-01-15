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
package net.sodacan.api.kafka.admin;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeLogDirsResult;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.requests.DescribeLogDirsResponse.LogDirInfo;

public class BrokerAdmin extends Admin {

	public BrokerAdmin() {
		super();
	}

	public List<Integer> listBrokerIds() throws InterruptedException, ExecutionException {
		List<Integer> brokerIds = new LinkedList<Integer>();
		// Get information about the brokers that are running
		DescribeClusterResult dcr = getAdminClient().describeCluster();
		KafkaFuture<Collection<Node>> nodes = dcr.nodes();
		for (Node node : nodes.get()) {
			brokerIds.add(node.id());
		}
		return brokerIds;
	}

	public List<String> listBrokers() throws InterruptedException, ExecutionException {
		List<String> brokers = new LinkedList<String>();
		// Get information about the brokers that are running
		DescribeClusterResult dcr = getAdminClient().describeCluster();
		KafkaFuture<Collection<Node>> nodes = dcr.nodes();
		for (Node node : nodes.get()) {
			brokers.add("Broker: " + node.id() + " on host: " + node);
		}
		return brokers;
	}
	
	public String getNodeInfo( int brokerId) throws InterruptedException, ExecutionException {
		// Get information about the brokers that are running
		DescribeClusterResult dcr = getAdminClient().describeCluster();
		KafkaFuture<Collection<Node>> nodes = dcr.nodes();
		for (Node node : nodes.get()) {
			if (node.id()==brokerId) {
				return node.toString();
			}
		}
		return "[Broker not found or may be off-line]";
	}
	
	public String describeBroker(int brokerId ) throws InterruptedException, ExecutionException {
		StringBuffer sb = new StringBuffer();
		 // Get the logs info about each of these brokers
		 DescribeLogDirsResult dldr = getAdminClient().describeLogDirs(Arrays.asList(brokerId));
		 for (Entry<Integer, Map<String, LogDirInfo>> logDir : dldr.all().get().entrySet()) {
			 sb.append("Broker: ");
			 sb.append(logDir.getKey());
			 sb.append(" on ");
			 sb.append(getNodeInfo(brokerId) );
			 sb.append('\n');
			 for (Entry<String,LogDirInfo> info : logDir.getValue().entrySet()) {
				sb.append(info.getKey());
				sb.append(": ");
				sb.append(info.getValue());
				 sb.append('\n');
			 }
		 }
		return sb.toString();
	}
}
