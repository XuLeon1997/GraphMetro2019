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
		HashSet<String> unvisitedVertices = new HashSet<>();

		// Initializes the unvisited set with all the vertices
		for (String vertice : g.adjacency.keySet()) {
			unvisitedVertices.add(vertice);
		}

		// Creates a new empty HashMap object to store the distance to each vertex
		Map<String, Double> distances = new HashMap<>();

		// Initializes all the distances to "infinity" (use the value
		// Integer.MAX_VALUE for "infinity")
		for (String vertice : g.adjacency.keySet()) {
			distances.put(vertice, Double.MAX_VALUE);
		}

		// Initializes tree set
		// override the comparator to do the sorting based keys
		// Question 1: Why does PairComparator *not* need a generic type parameter?
		TreeSet<SimpleEntry<Double, String>> treeSet = new TreeSet<SimpleEntry<Double, String>>(new PairComparator());

		// Initialises for the sourceVertex
		// Sets the distance for the sourceVertex (in distance) to 0
		distances.put(sourceVertex, (double) 0);
		
		// Creates a new pair (class SimpleEntry) of (distance, vertex) for the sourceVertex
		//
		// Add generic type parameter
		SimpleEntry<Double, String> p0 = new SimpleEntry<Double, String>((double) 0, sourceVertex);
		// Adds the pair to the treeSet
		
		treeSet.add(p0);

		// while tree set is not empty
		while (!treeSet.isEmpty()) {
			// Finds and removes the (distance, vertex) pair (class SimpleEntry) with the minimum distance
			// in the treeSet
			SimpleEntry<Double, String> extractedPair = (SimpleEntry<Double, String>) treeSet.pollFirst();

			// Get the vertex from the (distance, vertex) pair (it is in the pair value)
			String extractedVertex = extractedPair.getValue();
			// Only if the extracted vertex is in the unvisited set do the rest ...
			if (unvisitedVertices.contains(extractedVertex)) {
				// Removes the extracted vertex from the unvisited set (i.e. mark it as
				// visited)
				unvisitedVertices.remove(extractedVertex);

				// Takes all the adjacent vertices
				List<Edge<String>> list =g.adjacency.get(extractedVertex);
				
				// iterate over every neighbor/adjacent vertex
				for (int i = 0; i < list.size(); i++) {

					// Gets the corresponding Edge object
					Edge<String> edge = list.get(i);

					// Gets the Edge destination vertex
					String destination = edge.to();
//					System.out.println("		destination :	"+destination+"		unvisitedVertices :		"+unvisitedVertices.contains(destination));
					// Only if the destination vertex is in the unvisited set, do the rest
					
					if (unvisitedVertices.contains(destination)) {
						// Gets the current distance of the destination vertex
						double currentDistance = distances.get(destination);

						// Calculates the new distance via extractedVertex and the edge.weight
						double newDistance =  distances.get(extractedVertex) + edge.distanceTravel();

						// If the newDistance is less than the currentDistance, update
						if (newDistance < currentDistance) {
							// Creates a new pair (SimpleEntry object) for (newDistance, destination)
							SimpleEntry<Double, String> p = new SimpleEntry<Double, String>(newDistance, destination);
							// Adds the pair object to the treeSet
							treeSet.add(p);
							distances.put(destination, newDistance);
						}
					}
				}
			}
		}
		printDijkstra(g, distances, sourceVertex);
		printDijkstraDistance(g, distances, sourceVertex, destinationVertex);
	}
	private static void printDijkstra(Graph<String> g, Map<String, Double> distances, String sourceVertex) {
		int i = 0;
		boolean parcouru = true;
		for (String vertice : g.adjacency.keySet()) {
			i++;
			if (distances.get(vertice) == Double.MAX_VALUE) {
				
				parcouru = false;
				System.out.println( "	Source Vertex: " + sourceVertex + " to vertex " + vertice + " distance: infini");
			} else {
				System.out.println(i++ +" :	Source Vertex: " + sourceVertex + " to vertex " + vertice + " distance: " + distances.get(vertice) +"Km");
			}
		}
		System.out.print("Tout parcouru : "+parcouru);
		if(!parcouru)System.out.println("		<sur"+ i+" stations calculee>");
	}
	private static void printDijkstraDistance(Graph<String> g, Map<String, Double> distances, String sourceVertex, String destinationVertex) {

		System.out.println("	Source Vertex: " + sourceVertex + " to vertex " + destinationVertex + " distance: "+distances.get(destinationVertex)+"Km");
		List<String>  bfsList = Graph.bfsShortestPath(g , sourceVertex, destinationVertex);
		for(String parcourVertice:bfsList) {
			System.out.println(parcourVertice+ "		distance:	"+distances.get(parcourVertice)+"Km");
		}
		
	}
	
	static class PairComparator implements Comparator<SimpleEntry<Double, String>> {

		@Override
		public int compare(SimpleEntry<Double,String> o1, SimpleEntry<Double, String> o2) {
			// sort using distance values
			Double key1 =  (o1).getKey();
			Double key2 =  (o2).getKey();
			if((key1 - key2)<0)return  -1;
			else if((key1 - key2)==0.0)return  0;
			else if((key1 - key2)>0)return  1;
			else return 0;
			
		}
	}
}
