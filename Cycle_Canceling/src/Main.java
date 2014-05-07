import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
    	FileProcessor fp = new FileProcessor("instancias_trab1/nossoexemplo2.net");
    	Graph g = fp.processFile();
    	Vertex s ;
    	System.out.println("old\n" + g);
    	s = g.setMaxCostFlow() ;
    	System.out.println("s: " + s.getId()) ;
    	BellmanFord bell = new BellmanFord() ;
    	
    	g.supplyNodes.get(0) ;
    	//bell.bellmanFord(g.resGraph,0) ;
    	//bell.getBottleneck() ;
    	while(!(bell.bellmanFord(g.resGraph, 0)))
    	{
    		System.out.println("update bell") ;
    		g.updateGraph(bell) ;
    		
    		System.out.println("Original:");
    		System.out.println(g);
    		System.out.println("Residual:");
    		System.out.println(g.resGraph);
    	}
    	
    	int minCostFlow = g.minCostFlow() ;
    	System.out.println("minCostFlow: " + minCostFlow);
    	//System.out.println("new\n" + g);
    	//System.out.println(g.flowOptCondition());
    	
    	
    }
}
