package test.net.sodacan.utility;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import net.sodacan.utility.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGraph {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testCleanDAG() {
		 
		    // define the array with nodes
		    ArrayList<String> list = new ArrayList<String>(Arrays.asList("E","B","C","A","D"));
		 
		    // defining the edges for nodes
		    Graph charGraph = new Graph(list);
		    charGraph.addEdge("A","B");
		    charGraph.addEdge("A","C");
		    charGraph.addEdge("A","D");
		    charGraph.addEdge("B","D");
		    charGraph.addEdge("D","E");
		    charGraph.addEdge("C","D");
		    charGraph.addEdge("D","B");
		    String r = charGraph.isCyclic();
		    if(r!=null) {
	            System.out.println("Graph contains cycle at: " + r);
		    } else {
			    List<String> sortedList = charGraph.topologicalSort();
			    System.out.println(sortedList);
		    }
		 }

	@Test
	public void testCycle() {
		    // define the array with nodes
		    ArrayList<String> list = new ArrayList<String>(Arrays.asList("A","B","C","D","E"));
		 
		    // defining the edges for nodes
		    Graph charGraph = new Graph(list);
		    charGraph.addEdge("B","A");
		    charGraph.addEdge("A","B");
		    charGraph.addEdge("A","C");
		    charGraph.addEdge("A","D");
		    charGraph.addEdge("B","D");
		    charGraph.addEdge("C","E");
		    charGraph.addEdge("D","E");
		    String r = charGraph.isCyclic();
		    if(r!=null) {
	            System.out.println("Graph contains cycle at: " + r);
		    } else {
			    List<String> sortedList = charGraph.topologicalSort();
			    System.out.println(sortedList);
		    }
	}
	
	@Test
	public void testMissingNode() {
		    // define the array with nodes
		    ArrayList<String> list = new ArrayList<String>(Arrays.asList("A","B","C","D","E"));
		 
		    // defining the edges for nodes
		    Graph charGraph = new Graph(list);
		    charGraph.addEdge("A","B");
		    try {
		    charGraph.addEdge("B","X");	// This should fail
		    } catch (Throwable e) {
		    	System.out.println("Error - expected");
		    }
		    charGraph.addEdge("A","C");
		    charGraph.addEdge("A","D");
		    charGraph.addEdge("B","D");
		    charGraph.addEdge("C","E");
		    charGraph.addEdge("D","E");
		    String r = charGraph.isCyclic();
		    if(r!=null) {
	            System.out.println("Graph contains cycle at: " + r);
		    } else {
			    List<String> sortedList = charGraph.topologicalSort();
			    System.out.println(sortedList);
		    }
	}

}
