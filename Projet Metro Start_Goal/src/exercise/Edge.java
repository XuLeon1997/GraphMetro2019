package exercise;

// A class representing a directed edge (with no weight) parametrized on the type of vertices it connects (V)
public class Edge<V> {
	private final V source, destination;
	private final int time_travel;
	
	public Edge(V s, V d, int time_travel) {
		this.source = s;
		this.destination = d;
		this.time_travel = time_travel;
	}
	public V from() {return source;}
	public V to() {return destination;}
	public int timeTravel() {return time_travel;}
	
}