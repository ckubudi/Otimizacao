import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
    	FileProcessor fp = new FileProcessor("instancias_trab1/nossoexemplo2.net");
    	Graph g = fp.processFile();
    	System.out.println("old\n" + g);
    	BellmanFord bell = new BellmanFord() ;
    	
    	g.setMaxCostFlow() ;
    	System.out.println("new\n" + g);
    	while(!(bell.bellmanFord(g.resGraph, g.supplyNodes.get(0).getId())))
    	{
    		g.updateGraph(bell) ;
    		System.out.println("bell\n" + g);
    	}
    	
    	int minCostFlow = g.minCostFlow() ;
    	System.out.println("minCostFlow: " + minCostFlow);
    	//System.out.println("new\n" + g);
    	//System.out.println(g.flowOptCondition());
    	
    	
    	System.out.println("final\n" + g);
    }
}
