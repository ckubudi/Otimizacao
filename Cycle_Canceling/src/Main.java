import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
    	FileProcessor fp = new FileProcessor("instancias_trab1/nossoexemplo2.net");
    	Graph g = fp.processFile();
    	System.out.println("old\n" + g);
    	g.setMaxCostFlow() ;
    	BellmanFord bell = new BellmanFord() ;
    	bell.bellmanFord(g.resGraph, 4
    			) ;
    	//while(bell.bellmanFord(g.resGraph, source))
    	
    	//System.out.println("new\n" + g);
    	//System.out.println(g.flowOptCondition());
    	
    	
    }
}
