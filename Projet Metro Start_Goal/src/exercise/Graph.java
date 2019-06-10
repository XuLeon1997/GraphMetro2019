package exercise;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;


/**
 * This class is an Abstract Graph. 
 * Types of Vertices (V) and Edges (E) are not defined by default. 
 * The only constraints on theses types are that Vertices must be Comparable ie. 
 * there must be a way to order vertices with one another. This is done through the use of the Comparable interface.
 *  
 * 
 * @author slefebvr
 *
 * @param <String>
 * @param <E>
 */
public  class Graph<V extends Comparable<V>> {
	
	protected int nbVertex; // number of vertice
	protected int nbEdges; // number of edge
	public final Map<String, List<Edge<String>>> adjacency = new TreeMap<>();
	
	public Graph(int nbVertices, int nbEdges) {
		this.nbVertex= nbVertices;
		this.nbEdges = nbEdges;
	}
	
	
	/**
	 * Initializes an empty graph
	 */
	public Graph() {
		this(0,0);
	}
	
	/**
	 * Returns the order of the graph (i.e. the number of vertices) 
	 */
	public int order() {
		return nbVertex;
	}
	
	/**
	 * Returns the size of the graph (i.e. the number of edges) 
	 */
	public int size() {
		return nbEdges;
	}


	public void addEdge(String s, String t, double distance_travel) {
		nbEdges++;		
		if(!adjacency.containsKey(s))
			addVertex(s);
		if(!adjacency.containsKey(t))
			addVertex(t);
		
		List<Edge<String>> se = adjacency.get(s);
		if(!this.containsEdge(s, t)) se.add(new Edge<>(s,t,distance_travel));
		List<Edge<String>> te = adjacency.get(t);
		if(!this.containsEdge(t, s)) te.add(new Edge<>(t,s,distance_travel));

	}
	/**
	 * Adds a vertex to the graph. On success n is increased
	 * @param v
	 */
	public void addVertex(String v) {
		nbVertex++;
		adjacency.put(v, new LinkedList<>());
		
	}
	/**
	 * 
	 * @return the list of the graph vertices
	 */
	public List<String> vertices() {
		List<String> out = new ArrayList<>();
		out.addAll(adjacency.keySet());
		
		return out;
	}

	public boolean containsEdge(String from, String to) {
		for(Edge<String> edge : this.adjacency.get(from))
			if(edge.to().equals(to)) return true;
		return false;
	}
	
	public List<Edge<String>> inEdges(String v) {
		List<Edge<String>> out = new LinkedList<>();
		
		for(String source: vertices())
			for(Edge<String> e: outEdges(source))
				if(e.to().equals(v))
					out.add(e);
		return out;
	}
	public List<Edge<String>> outEdges(String vertice) {
		
		return adjacency.get(vertice);
	}
	/** 		[Corentin-Cel
	 * Return the list of v's neighbors
	 * @param u
	 */
	
	public List<String> inNeighbors(V u) {
		
		List<String> out = new LinkedList<>();
		for(Edge<String> e: inEdges((String) u))
			out.add(e.from());
		
		return out;

	}
	public List<String> outNeighbors(String v) {
		
		List<String> out = new LinkedList<>();
		for(Edge<String> e: outEdges(v))
			out.add(e.to());
		
		return out;
	}
		
	/**
	 * Returns the inbound degree of node v
	 * in case of undirected graph should return the same as outDegree
	 * @param v
	 * @return inbound degree of vertex v
	 */
	public int inDegree(String v) {
		
		return inNeighbors((V) v).size();
	}		
	public int outDegree(String v) {
		return adjacency.get(v).size();
	}	
	
	
	/**
	 * Returns true if the graph is connected
	 * can be more efficient. 
	 * @return
	 */
	public boolean isConnected() {
		return cc(this)==1;
	}
	
	private static<V extends Comparable<V>> void assign(V u, V root, Graph<V> g, Map<V, V> components) {
		if(!components.containsKey(u)) {
			components.put(u, root);
			List<V> in = (List<V>) g.inNeighbors(u);
			for(V v: in)
				assign(v, root,g,components);	
		}
	}
	
	/**
	 * Counts and returns the number of connected components
	 * Kosaraju's algorithm		[Corentin-Cel
	 * @param g
	 * @returna
	 */
	public static<V extends Comparable<V>> int cc(Graph<V> g) {
		
		List<V> vertices = (List<V>) g.vertices(), 
				dfsOut= new ArrayList<>();
		Map<V, V> components = new TreeMap<>();
		dfsCc(g, vertices.get(0), dfsOut);
		
		for(V v : dfsOut)
			assign(v,v,g,components);
		
		Set<V> s = new HashSet<>();
		s.addAll(components.values());
		return s.size();
		
	}
	
	
	
	private static <V extends Comparable<V>> V findMin(List<V> l) {
		V min = l.get(0);
		for(V elem: l) {
			min = elem.compareTo(min) < 0 ? elem : min;
		}
		return min;
	}
	
	private static<V extends Comparable<V>>  void dfsCc(Graph<V> g, V start, List<V> out) {
		out.add(start);
		List<V> neighbors = (List<V>) g.outNeighbors((String) start);
		while(neighbors.size() > 0) {
			V v = findMin(neighbors);
			neighbors.remove(v);
			if(!out.contains(v)) {
				dfsCc(g,v,out);
			}
 		}
	}
	
	/**
	 * Returns the list of vertices ordered by DFS visit 
	 * 
	 * @param g
	 * @return
	 */
	public static<V extends Comparable<V>>  List<V> dfs(Graph<V> g,V bfsStartNode, V bfsGoalNode) {
		List<V> out = new LinkedList<>();
		dfs(g, bfsStartNode, bfsGoalNode, out);
//		for(V current: vertices) {
//			if(!out.contains(current))
//				dfs(g,current, out);
//		}
		
		return out;
	}
	private static<V extends Comparable<V>>  boolean dfs(Graph<V> g, V start, V goal, List<V> out) {
		out.add(start);
		List<V> neighbors = (List<V>) g.outNeighbors((String) start);
		boolean flag=true;
		while(neighbors.size() > 0 && flag) {
			V v = findMin(neighbors);
			neighbors.remove(v);
			if(v.equals(goal)) {
				out.add(v);
//				System.out.println("Destination trouvé : "+v);
				flag=false;
			}else if(!out.contains(v)){
				flag=dfs(g,v,goal,out);
			}else if(out.contains(v)){
//				System.out.println("Chemin déjà par couru : "+ v);
			}else {
				return flag=false;
			}
 		}
		return flag;
	}
	

	/**
	 * Iterative function for bfs 
	 * @param g
	 * @return
	 */
	private static Map<String, Boolean> vis = new HashMap<>();
	private static Map<String, String> prev = new HashMap<>();
	
    public static<V extends Comparable<V>> List<String> bfsShortestPath(Graph<V> G, String startPoint, String goalPoint){
    	vis.clear();
    	prev.clear();
    	List<String> directions =new LinkedList<>();
    	Queue<String> q = new LinkedList<>();
    	String current = startPoint;
    	q.add(current);
    	vis.put(current,true);
    	while (!q.isEmpty())
    	{
    		current=q.remove();
    		if (current.equals(goalPoint))
    		{
    			break;
    		}
    		else
    		{
    			for(int y=0;y<G.adjacency.get(current).size();y++) 
    			{
    				String adj =G.adjacency.get(current).get(y).to();
    				if (!vis.containsKey(adj))
    				{
    					q.add(adj);
//    					System.out.println(G.adjacency.get(current).get(y).to());
    					vis.put(adj, true);
    					prev.put(adj,current);
    				}
    	 			
    	 			
    	 			
    			}
    		}	
    	}
    	if (!current.equals(goalPoint))
    	{
    		System.out.println("Start : " +startPoint+" 	Goal : "+ goalPoint);
    		System.out.println(vis.size());
    		System.out.println("on ne peut pas atteindre la destination : current = " + current + " / Goal Point : "+goalPoint);
    		System.out.println("current voisin = " + G.adjacency.get(current).size());
    		System.out.println("current voisin 0 = " +G.adjacency.get(current).get(0).to());
    		System.out.println("current voisin deja vu = " + vis.containsKey(G.adjacency.get(current).get(0).to()));

    		
    	}
    	
    	for (String stop = goalPoint; stop!=null; stop=prev.get(stop))
    	{
    		directions.add(stop);
    	}
    	 Collections.reverse(directions);
    	 return directions;
    	
    	
//    	List<String> BFS =new ArrayList<>(0);
//    	String x= startPoint;
//    	System.out.println("départ" + x);
//    	BFS.add(x);
//		for(int y=0;y<G.adjacency.get(x).size();y++) {
// 			BFS.add(G.adjacency.get(x).get(y).to());
// 			System.out.println(G.adjacency.get(x).get(y).to());
//		}
//		boolean flagEnd=true;
//    	do {
//    		boolean flag=true;
//    		do {
//    			x=BFS.get(0);
//    			BFS.remove(0);
//
//    			
//    			if(x.equals(goalPoint)) {
//    				vis.put(x,true);
//			
//    					
//    				
//    				flagEnd=false;
//    				flag=false;
//    				System.out.println("Trouvé");
//    			}
//    			else if (!vu.contains(x)){
//    				vis.put(x,true);
//    				
//    				flag=false;
//    			}else if (BFS.isEmpty()) {
//    				flag=false;
//    			}
//    			
//    			
//    		}while(flag);
//    		if(G.adjacency.containsKey(x)) {
//	    		for(int y=0;y<G.adjacency.get(x).size();y++) {
//	    			if (!vu.contains(G.adjacency.get(x).get(y).to())) BFS.add(G.adjacency.get(x).get(y).to());
//	    			System.out.println(G.adjacency.get(x).get(y).to());
//	    		}
//    		}
//    	}while(!BFS.isEmpty() && flagEnd);
//        return (ArrayList<V>) vu;
    }


    
	// TODO: Implement this function, parametrized by the type of vertex V, to do
	// some sample processing
	// The function accepts a start vertex of type V for a BFS traversal, and the
	// filename from which to load
	// the graph.
	// It then proceeds to load the graph and print fome information about it.
	public static <V extends Comparable<V>>  void processDigraph(V bfsStartNode, V bfsGoalNode, String filename) {
		System.out.println("Reading graph from file: " + filename);

		// TODO: call GraphFactory.createDiGraphFromTextFileAuto and assignt to a
		// generic Graph type
		// An unchecked type cast is needed.
		Graph<V>  g = (Graph<V> ) GraphFactory.createGraphFromTextFileAuto(filename);

		System.out.println("Graph structure: (node -> [<out_neighgbor>])");
		for (String  vertex : g.vertices()) {
			System.out.print(vertex.toString() + " -> ");
			// TODO: iterate over the neighbors of each vertex v and print them out.
			for (String  neighbour : g.outNeighbors(vertex)) {
				System.out.print(neighbour.toString() + " ");
			}
			System.out.println();
		}

		System.out.println();

		// TODO: Print the order of the graph, generically.
		//System.out.println("Order: " + g.order() );

		// TODO: Print the size of the graph, generically.
		//System.out.println("Size: " + g.size() );

//		// TODO: Print the connectedness of the graph, generically.
//		System.out.println("Connected: " + g.isConnected() );
//
//		// TODO: Execute a DFS of the graph, generically, and print the resulting list
//		// of nodes, generically.
//		List<V>  dfsList =  dfs(g, bfsStartNode, bfsGoalNode);
//		System.out.println("DFS result:");
//		// TODO: Iterate and print each node in the DFS result list
//		for (V  dfsNode : dfsList) {
//			System.out.print(dfsNode.toString() + " ");
//		}
//		System.out.println();

		// TODO: Execute a BFS of the graph starting at bfsStartNode, generically, and
		// print the resulting list of nodes, generically.
		List<String>  bfsList = bfsShortestPath(g , (String)bfsStartNode, (String) bfsGoalNode);
		System.out.println("BFS result:");
		// TODO: Iterate and print each node in the DFS result list
		System.out.println(bfsList.toString());
		//*********************
		//CALCUL DISTANCE TOTAL
		//*********************
		
		double distanceTot=0;
		String currentPoint=null;
		for(String vertice : bfsList) {
			if(currentPoint != null) {
				for(int i =0;i<g.adjacency.get(currentPoint).size();i++) {
					if (g.adjacency.get(currentPoint).get(i).to()==vertice) {
						distanceTot+=g.adjacency.get(currentPoint).get(i).distanceTravel();
						break;
					}
				}
				
			}
			currentPoint=vertice;
		}
		System.out.println("Travel total distance : "+distanceTot+"Km");
		
		
		
		System.out.println("\n------------------------------\n");
		
	
	
		
		//*********************
		//CALCUL DIAMETER
		//*********************
//		System.out.println("Calcul Diameter ...");
//		System.out.println("Diameter : " + g.diameter());
		
		
		
		
		DijkstraTreeSet.dijkstra((Graph<String>) g,(String) bfsStartNode,(String)bfsGoalNode);
	}
	
	public int diameter() {
	    int[] arrayShortestPaths=new int[this.adjacency.size()];
	    int i=0;
	    for (String entree : this.adjacency.keySet()){
		    String keyE = entree;
		    arrayShortestPaths[i]=0;//Integer.MAX_VALUE
		    for (String sortie:this.adjacency.keySet()){
			    String keyS = sortie;
			    if(keyE!=keyS) {
				    int pathDistance = Graph.bfsShortestPath(this, keyE, keyS).size();
				    if (arrayShortestPaths[i]<pathDistance)arrayShortestPaths[i]=pathDistance;
			    }
			}
			i++;
		}
	    
		//System.out.println(Arrays.toString(arrayShortestPaths));
		int diameter=0;
		for (int ShortestPath :arrayShortestPaths ) {
			if (ShortestPath > diameter) diameter=ShortestPath;
	    }
		return diameter;
	}
	
	public static void main(String[] args) {
		
		// Calls processDigraph for Integer
		processDigraph("Jacques-Bonsergent Metro 5", "Porte de Vanves Metro 13", "final3.txt");

	}


}
