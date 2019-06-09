package exercise;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class DijkstraTreeSet {
	public static void dijkstra(Graph<String> g,String sourceVertex, String destinationVertex) {
		// Creates a new empty HashSet object to store the unvisited vertices
		//
		// TODO: Add generic type parameter. Can be found from usage (see line 91)
		HashSet<String> unvisitedVertices = new HashSet<>();

		// Initializes the unvisited set with all the vertices
		for (String vertice : g.adjacency.keySet()) {
			unvisitedVertices.add(vertice);
		}

		// Creates a new empty HashMap object to store the distance to each vertex
		// (key=vertex, value=distance)
		//
		// TODO: Add generic type parameter. Can be found from usage (see line 103)
		Map<String, Double> distances = new HashMap<>();

		// Initializes all the distances to "infinity" (use the value
		// Integer.MAX_VALUE for "infinity")
		for (String vertice : g.adjacency.keySet()) {
			distances.put(vertice, Double.MAX_VALUE);
		}

		// Initializes tree set
		// override the comparator to do the sorting based keys
		//
		// TODO: Add generic type parameter. Can be found from usage (see line 123)
		// Question 1: Why does PairComparator *not* need a generic type parameter?
		TreeSet<SimpleEntry<Double, String>> treeSet = new TreeSet<SimpleEntry<Double, String>>(new PairComparator());

		// Initialises for the sourceVertex
		// Sets the distance for the sourceVertex (in distance) to 0
		distances.put(sourceVertex, (double) 0);
		
		// Creates a new pair (class SimpleEntry) of (distance, vertex) for the sourceVertex
		//
		// TODO: Add generic type parameter. Can be found from constructor parameters (see line 120)
		SimpleEntry<Double, String> p0 = new SimpleEntry<Double, String>((double) 0, sourceVertex);
		System.out.println("p0 :	"+p0.toString());
		// Adds the pair to the treeSet
		
		treeSet.add(p0);

		// while tree set is not empty
		while (!treeSet.isEmpty()) {
			// Finds and removes the (distance, vertex) pair (class SimpleEntry) with the minimum distance
			// in the treeSet
			//
			// TODO: Add generic type parameter and remove explicit cast. Can be found from usage (see line 123)
			SimpleEntry<Double, String> extractedPair = (SimpleEntry<Double, String>) treeSet.pollFirst();

			// Get the vertex from the (distance, vertex) pair (it is in the pair value)
			//
			// TODO: Remove the explicit type cast if not required
			String extractedVertex = extractedPair.getValue();
//			System.out.println("	treeSet before loop:	" +treeSet);
//			System.out.println("	extractedVertex :	" +extractedVertex);
			// Only if the extracted vertex is in the unvisited set do the rest ...
			if (unvisitedVertices.contains(extractedVertex)) {
				// Removes the extracted vertex from the unvisited set (i.e. mark it as
				// visited)
				unvisitedVertices.remove(extractedVertex);

				// Takes all the adjacent vertices
				//
				// TODO: Add generic type parameter. Can be found from declaration (see line 40)
				List<Edge<String>> list =g.adjacency.get(extractedVertex);
				
				// iterate over every neighbor/adjacent vertex
				for (int i = 0; i < list.size(); i++) {

					// Gets the corresponding Edge object
					//
					// TODO: Remove the explicit type cast if not required
					Edge<String> edge = list.get(i);

					// Gets the Edge destination vertex
					String destination = edge.to();
//					System.out.println("		destination :	"+destination+"		unvisitedVertices :		"+unvisitedVertices.contains(destination));
					// Only if the destination vertex is in the unvisited set, do the rest
					
					if (unvisitedVertices.contains(destination)) {
						// Gets the current distance of the destination vertex
						//
						// TODO: Remove the explicit type cast if not required
						double currentDistance = distances.get(destination);

						// Calculates the new distance via extractedVertex and the edge.weight
						//
						// TODO: Remove the explicit type cast if not required
						double newDistance =  distances.get(extractedVertex) + edge.distanceTravel();

						// If the newDistance is less than the currentDistance, update
						if (newDistance < currentDistance) {
							// Creates a new pair (SimpleEntry object) for (newDistance, destination)
							//
							// TODO: Add generic type parameter. Can be found from usage (see line 180)
							
							SimpleEntry<Double, String> p = new SimpleEntry<Double, String>(newDistance, destination);
						
//							// Adds the pair object to the treeSet

							
//							System.out.println("				treeSet before add:	" +treeSet);
//							System.out.println("				p:	"+p);
							
							if(!treeSet.add(p)) {
								System.out.println(false);
								System.out.println("destination :	" +destination);
							}
//							System.out.println("				treeSet after  add:	" +treeSet);
							
//							Double currentDistance2 = distances.get(destination);
//							SimpleEntry<Double, String> p2 = new SimpleEntry<Double, String>(distances.get(destination), destination);
//							treeSet.add(p);
							// Updates the distance HashMap for the destination vertex to the
							// newDistance
							distances.put(destination, newDistance);
						}
					}
					
				}
			}
		}

		
		
		
		// print Shortest Path Tree
		printDijkstra(g, distances, sourceVertex);
		printDijkstraDistance(g, distances, sourceVertex, destinationVertex);
	}
	
	
	// TODO: Add generic type parameter to HashMap. Can be found from usage (see line 98)
	private static void printDijkstra(Graph<String> g, Map<String, Double> distances, String sourceVertex) {
		//System.out.println("Dijkstra Algorithm: (Adjacency List + TreeSet)");
		int i=1;
		int j=1;
		boolean parcouru= true;
		for (String vertice : g.adjacency.keySet()) {
			
			if(distances.get(vertice)==Double.MAX_VALUE){
				System.out.println(j++ +"	Source Vertex: " + sourceVertex + " to vertex " + vertice + " distance: infini");
				parcouru=false;
			}else {
//				System.out.println(i++ +" :	Source Vertex: " + sourceVertex + " to vertex " + vertice + " distance: " + distances.get(vertice) +"Km");
			}
		}
		System.out.println("Tout parcouru : "+parcouru);
		
		//System.out.println(Double.MAX_VALUE);
	}
	private static void printDijkstraDistance(Graph<String> g, Map<String, Double> distances, String sourceVertex, String destinationVertex) {
		//System.out.println("Dijkstra Algorithm: (Adjacency List + TreeSet)");

		System.out.println("	Source Vertex: " + sourceVertex + " to vertex " + destinationVertex + " distance: "+distances.get(destinationVertex)+"Km");
		List<String>  bfsList = Graph.bfsShortestPath(g , sourceVertex, destinationVertex);
		for(String parcourVertice:bfsList) {
			System.out.println(parcourVertice+ " distance: "+distances.get(parcourVertice)+"Km");
		}
	}
	
	
	// TODO: Add Generic types to "Comparator". The type being compared can be found by looking at the usage of this class (see line 111)
	static class PairComparator implements Comparator<SimpleEntry<Double, String>> {

		// Implements the compare method. o1 and o2 are really instances of
		// java.util.AbstractMap.SimpleEntry where the key and the value are both of type int. Returns the
		// equivalent of o1.key - o2.key to implement an ascending value ordering.
		//
		// TODO: Change "Object" type to reflect the more specific type used. See class declaration for type hint (line 195)
		@Override
		public int compare(SimpleEntry<Double,String> o1, SimpleEntry<Double, String> o2) {
			// sort using distance values

			// TODO: Remove explicit type casts
			Double key1 =  (o1).getKey();
			// TODO: Remove explicit type casts
			Double key2 =  (o2).getKey();
			if((key1 - key2)<0)return  -1;
			else if((key1 - key2)==0.0)return  0;
			else if((key1 - key2)>0)return  1;
			else return 0;
			
		}
	}
}
