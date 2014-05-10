import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
    	FileProcessor fp = new FileProcessor("instancias_trab1/nossoexemplo2.net");
    	Graph g = fp.processFile();
    	System.out.println("old\n" + g);
    	g.buildResidualGraph() ;
    	g.shortestPathAlgorithm () ;
    	System.out.println("new\n" + g);
    	
    	int minCostFlow = g.minCostFlow() ;
    	System.out.println("minCostFlow: " + minCostFlow);
    	//System.out.println("new\n" + g);
    	
    	
    	//System.out.println("final\n" + g);
    }
}
