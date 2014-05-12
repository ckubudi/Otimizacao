import java.io.IOException;


public class Main {
	public static void main(String[] args) throws IOException {
		FileProcessor fp = new FileProcessor("instancias_trab1/big1.net");
		Graph g = fp.processFile();
		
		g.shortestPathAlgorithm () ;
		//g.shortestPathAlgorithmCapacityScaling() ;		 
    	
	}
}
