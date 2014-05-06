import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
    	FileProcessor fp = new FileProcessor("instancias_trab1/nossoexemplo.net");
    	Graph g = fp.processFile();
    	g.setMaxCostFlow() ;
    	System.out.println(g);
    	//System.out.println(g.flowOptCondition());
    	
    	
    }
}
