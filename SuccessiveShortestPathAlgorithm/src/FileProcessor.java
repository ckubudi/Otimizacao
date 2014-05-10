import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class FileProcessor {
	
	File file ;
	
	public FileProcessor(String path) {
		file = new File(path);
	}
	
	public Graph processFile() throws IOException
	{
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line ;
		String[] split ;
		Graph graph = null ;
		
		while ( (line = buffer.readLine()) != null) 
		{
			split = line.split(" ");
			switch (split[0])
			{
			case ("a"):
				//aresta
				graph.addEdge(
							graph.getVertex(Integer.parseInt(split[1])-1), 
							graph.getVertex(Integer.parseInt(split[2])-1), 
							Integer.parseInt(split[3]), 
							Integer.parseInt(split[4]), 
							Integer.parseInt(split[5]) ) ;
				break;
			case ("n"):
				//vertice
				Vertex v = graph.getVertex(Integer.parseInt(split[1])-1);
				int sup_dem = Integer.parseInt(split[2]) ;
				if(sup_dem < 0)
					graph.demandNodes.add(v);
				else 
					graph.supplyNodes.add(v) ;
				
				v.setSupplyDemand(sup_dem);
				break;
			case("p"):
				//problem - cria todos vertices
				graph = new Graph(Integer.parseInt(split[2]));
				break ;
			default:
				continue;
			}
		}
		
		buffer.close();
		
		return graph ;
	}
}
