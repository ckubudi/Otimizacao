import java.io.IOException;


public class Main {
	public static void main(String[] args) throws IOException {
		FileProcessor fp = new FileProcessor("instancias_trab1/cap2.net");
		Graph g = fp.processFile();
		System.out.println("Raw Graph\n" + g);
		g.shortestPathAlgorithm () ;
    	System.out.println("MinCostFlow Graph\n" + g);
    	System.out.println("MinCostFlow Residual Graph\n" + g.resGraph);

    	int minCostFlow = g.minCostFlow() ;
    	System.out.println("MinCostFlow value: " + minCostFlow);	 
    	
	}
}
