import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
    	FileProcessor fp = new FileProcessor("instancias_trab1/nossoexemplo.net");
    	Graph g = fp.processFile();
    	Vertex s ;
    	System.out.println("old\n" + g);
    	s = g.setMaxCostFlow() ;
    	System.out.println("s: " + s.getId()) ;
    	BellmanFord bell = new BellmanFord() ;
    	
    	g.supplyNodes.get(0) ;
    	bell.bellmanFord(g.resGraph,0) ;
    	//while(bell.bellmanFord(g.resGraph, source))
    	
    	//System.out.println("new\n" + g);
    	//System.out.println(g.flowOptCondition());
    	
    	
    }
}
