package exercise;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

// A  factory class for graphs
public class GraphFactory {

	public static Graph<String>  createGraphFromTextFileAuto(String filename) {
		
		Graph<String> gString = new Graph<>(); // construct it

		try (Scanner scan = new Scanner(FileSystems.getDefault().getPath(filename))) {
			
			while (scan.hasNextLine()) {
				// read the line then contain from and to vertex and the distance between the vertex
				String[] ligne=scan.nextLine().split(",");
				String fromVertex = ligne[0]; // read the from vertex
				String toVertex = ligne[1]; // read the to vertex
				double distance_travel = Double.parseDouble(ligne[2]);// read the distance in km
				gString.addEdge(fromVertex, toVertex, distance_travel); // add Edge in the graph 
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return gString;
	}
}