package net.sodacan.grammer;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
 
/*
* Graph class holds nodes and their edges. 
* The class also contains the logic to sort
*/
 
public class Graph{
    // a list to hold all the edges for each node/ vertex
    private Map<String, ArrayList<String>> edges;
    // a list of hold all the nodes
    private Collection<String> nodes;
    // a list to indicate nodes that were visited/ traversed
    private List<String> nodeVisited;
    // a list to hold all the edges
    private ArrayList<String> edgeList;
         
    // a public constructor to set the nodes and intialize edge list
    public Graph(Collection<String> nodes){
        this.nodes = nodes;
        edges = new HashMap<>();
        nodeVisited = new ArrayList<>();
    }
 // This function is a variation of DFSUtil() in
    // https://www.geeksforgeeks.org/archives/18212
    private String isCyclicUtil(String i, Set<String> visited, Set<String> recStack)
    {
//    	System.out.println("isCyclicUtil: " + i + " visited: " + visited + " stack: " + recStack);
        // Mark the current node as visited and
        // part of recursion stack
        if (recStack.contains(i))
            return i;
 
        if (visited.contains(i))
            return null;
             
        visited.add(i);
 
        recStack.add(i);
        
        List<String> children = edges.get(i);
        if (children==null) { 
            return null;
        }
        for (String c: children) {
        	String r = isCyclicUtil(c, visited, recStack);
            if (r!=null)
                return r;
        }
        recStack.remove(i);
        return null;
    }
 
    // Returns true if the graph contains a
    // cycle, else false.
    // This function is a variation of DFS() in
    // https://www.geeksforgeeks.org/archives/18212
    public String isCyclic()
    {
        // Mark all the vertices as not visited and not part of recursion stack
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();
                  
        // Call the recursive helper function to
        // detect cycle in different DFS trees
        for (String s : edges.keySet()) {
        	String r = isCyclicUtil(s, visited, recStack);
            if (r!=null) {
                return r;
            }
        }
        return null;
    }
      
    // method to add edge to a node. i.e. adding edges for given nodes.
    public void addEdge(String x, String y){
    	if (!nodes.contains(x)) {
    		throw new RuntimeException("Node " + x + " not found when adding edge");
    	}
    	if (!nodes.contains(y)) {
    		throw new RuntimeException("Node " + y + " not found when adding edge");
    	}
        // If the node (key) and edge (value) are being added for first time
        if(!edges.containsKey(x)){
            edgeList = new ArrayList<String>();
            edges.put(x,edgeList);
        } else {
            // if the node already has edges added
            edgeList = edges.get(x);
        }
        edgeList.add(y);
    }
     
    // method containing the logic to sort the given nodes recursively
    public List<String> topologicalSort(){
        Stack<String> stack = new Stack<>();
        // iterate through all the nodes and their neighbors if not already visited.
        for (String s : nodes){
            if(!nodeVisited.contains(s)){
                sort(s, stack);
            }
        }
        List<String> list = new ArrayList<String>();
        // print all the elements in the stack in reverse order
        while(!stack.empty()){
        	list.add(stack.pop());
        }
        return list;
    }
     
    // Iterate through all the nodes and neighbors
    // Push the visited items to stack
    public void sort(String ch, Stack<String> stack){
        // add the visited node to list, so we don't repeat this node again
        nodeVisited.add(ch);
        // the leaf nodes wouldn't have neighbors. A check added to avoid null pointer
        if(edges.get(ch)!=null){
            // get all the neighbor nodes , by referring its edges
            for (String neighborNode : edges.get(ch)) {
                if(!nodeVisited.contains(neighborNode)){
                    sort(neighborNode,stack);
                }
            }
        }
        // push the latest node on to the stack
        stack.push(ch);
    }
}
