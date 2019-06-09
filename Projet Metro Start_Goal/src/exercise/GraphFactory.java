package exercise;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

// A  factory class for graphs
public class GraphFactory {

	public static Graph<String>  createDiGraphFromTextFileAuto(String filename) {
		Graph<String> gString = new Graph<>(); // construct it

		try (Scanner scan = new Scanner(FileSystems.getDefault().getPath(filename))) {
			while (scan.hasNextLine()) {
				
				String[] ligne=scan.nextLine().split(",");
				String u = ligne[0]; // read the from vertex
				String v = ligne[1]; // read the to vertex
				double distance_travel = Double.parseDouble(ligne[2]);
				gString.addEdge(u, v, distance_travel);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return gString;
	}
}