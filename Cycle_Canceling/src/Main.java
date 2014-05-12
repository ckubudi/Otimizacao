import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
    	FileProcessor fp = new FileProcessor("instancias_trab1/transp1.net");
    	Graph g = fp.processFile();
    	BellmanFord bell = new BellmanFord() ;   	
    	long maximumTime = 1000 * 60 *5 ;
    	   	
    	long startTime = System.currentTimeMillis() ;
    	g.setMaxCostFlow() ;  	
    	while(!(bell.bellmanFord(g.resGraph, g.supplyNodes.get(1).getId())))
    	{
    		g.updateGraph(bell) ;
    		if(System.currentTimeMillis() - startTime >= maximumTime)
    		{
    			System.out.println("Excedeed 5 minutes!") ;
    			System.exit(1) ;
    		}
    			
    	}
    	
    	long minCostFlow = g.minCostFlow() ;    	
    	long elapsedTime = System.currentTimeMillis() - startTime ;
    	System.out.println("minCostFlow: " + minCostFlow + " found in " + elapsedTime/1000. + " seconds");
    	
    }
}
