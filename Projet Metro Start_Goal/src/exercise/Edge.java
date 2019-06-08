package exercise;

// A class representing a directed edge (with no weight) parametrized on the type of vertices it connects (V)
public class Edge<V> {
	private final V source, destination;
	private final double distance_travel;
	
	public Edge(V s, V d, double time_travel) {
		this.source = s;
		this.destination = d;
		this.distance_travel = time_travel;
	}
	public V from() {return source;}
	public V to() {return destination;}
	public double distanceTravel() {return distance_travel;}
	
	
}