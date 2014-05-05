import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {

		try {
			read_file() ;
		} catch (IOException e) {
			e.printStackTrace();
		}

		//teste ford
		/*Graph G = new Graph(6, 10, 1, 1, 0, 0, 0, 0, 20) ;
		G.insert_arc(new Arc(1, 2, 0, 0, 16,0)) ;
		G.insert_arc(new Arc(1, 3, 0, 0, 13,0)) ;
		G.insert_arc(new Arc(2, 3, 0, 0, 10,0)) ;
		G.insert_arc(new Arc(2, 4, 0, 0, 12,0)) ;
		G.insert_arc(new Arc(3, 2, 0, 0, 4,0)) ;
		G.insert_arc(new Arc(3, 5, 0, 0, 14,0)) ;
		G.insert_arc(new Arc(4, 3, 0, 0, 9,0)) ;
		G.insert_arc(new Arc(4, 6, 0, 0, 20,0)) ;
		G.insert_arc(new Arc(5, 4, 0, 0, 7,0)) ;
		G.insert_arc(new Arc(5, 6, 0, 0, 4,0)) ;
		
		Graph residual_G = new Graph(6, 10, 1, 1, 0, 0, 0, 0, 20) ;
		residual_G.insert_arc(new Arc(1, 2, 0, 0, 16,0)) ;
		residual_G.insert_arc(new Arc(1, 3, 0, 0, 13,0)) ;
		residual_G.insert_arc(new Arc(2, 3, 0, 0, 10,0)) ;
		residual_G.insert_arc(new Arc(2, 4, 0, 0, 12,0)) ;
		residual_G.insert_arc(new Arc(3, 2, 0, 0, 4,0)) ;
		residual_G.insert_arc(new Arc(3, 5, 0, 0, 14,0)) ;
		residual_G.insert_arc(new Arc(4, 3, 0, 0, 9,0)) ;
		residual_G.insert_arc(new Arc(4, 6, 0, 0, 20,0)) ;
		residual_G.insert_arc(new Arc(5, 4, 0, 0, 7,0)) ;
		residual_G.insert_arc(new Arc(5, 6, 0, 0, 4,0)) ;
		
		residual_G.create_residual_graph() ;
		
		Ford_Fulkerson f = new Ford_Fulkerson() ;*/
		//int max = f.main_ford(G, 1, 6, residual_G) ;
		
		//System.out.println(max) ;

	}
	
	public static Graph read_graph_information (BufferedReader reader) throws IOException
	{
		int random_seed = 0;
		int number_nodes = -1;
		int number_source_nodes = -1;
		int number_sink_nodes = -1 ;
		int number_arcs = -1 ;
		int min_arc_cost = -1 ;
		int max_arc_cost = -1 ;
		int tot_supply = -1 ;
		int min_arc_cap = -1 ;
		int max_arc_cap = -1 ;
		
		
		String line = null;
		//while ((line = reader.readLine()) != null) 
		line = reader.readLine() ;
		//while (line.charAt(0) != 'n') 
		while(!(line.contains("Minimum cost flow")))
		{
			if(line.contains("Random seed"))
			{
				String seed_value = line.replaceAll( "[^\\d]", "" );
				random_seed = Integer.parseInt(seed_value) ;
				System.out.println(seed_value) ;
			}
					
			else if(line.contains("Number of nodes"))
			{
				String n_nodes_value = line.replaceAll( "[^\\d]", "" );
				number_nodes = Integer.parseInt(n_nodes_value) ;
				System.out.println(n_nodes_value) ;
			}
				
			else if(line.contains("Source nodes"))
			{
				String source_nodes_value = line.replaceAll( "[^\\d]", "" );
				number_source_nodes = Integer.parseInt(source_nodes_value) ;
				System.out.println(source_nodes_value) ;
			}
				
			else if(line.contains("Sink nodes"))
			{
				String sink_nodes_value = line.replaceAll( "[^\\d]", "" );
				number_sink_nodes = Integer.parseInt(sink_nodes_value) ;
				System.out.println(sink_nodes_value) ;
			}
				
			else if(line.contains("Number of arcs"))
			{
				String n_arcs_value = line.replaceAll( "[^\\d]", "" );
				number_arcs = Integer.parseInt(n_arcs_value) ;
				System.out.println(n_arcs_value) ;
			}
				
			else if(line.contains("Minimum arc cost"))
			{
				String min_arc_value = line.replaceAll( "[^\\d]", "" );
				min_arc_cost = Integer.parseInt(min_arc_value) ;
				System.out.println(min_arc_value) ;
			}
				
			else if(line.contains("Maximum arc cost"))
			{
				String max_arc_value = line.replaceAll( "[^\\d]", "" );
				max_arc_cost = Integer.parseInt(max_arc_value) ;
				System.out.println(max_arc_value) ;
			}
				
			else if(line.contains("Total supply"))
			{
				String supply_value = line.replaceAll( "[^\\d]", "" );
				tot_supply = Integer.parseInt(supply_value) ;
				System.out.println(supply_value) ;
			}
			
			else if(line.contains("Minimum arc capacity:"))
			{
				String min_cap_value = line.replaceAll( "[^\\d]", "" );
				min_arc_cap = Integer.parseInt(min_cap_value) ;
				System.out.println(min_cap_value) ;
			}
			
			else if(line.contains("Maximum arc capacity:"))
			{
				String max_cap_value = line.replaceAll( "[^\\d]", "" );
				max_arc_cap = Integer.parseInt(max_cap_value) ;
				System.out.println(max_cap_value) ;
			}
			
			
			line = reader.readLine() ;
		}
		
		System.out.println(number_nodes) ;
		System.out.println(number_arcs) ;
		System.out.println(number_source_nodes) ;
		System.out.println(number_sink_nodes) ;
		System.out.println(min_arc_cost) ;
		System.out.println(max_arc_cost) ;
		System.out.println(tot_supply) ;
		System.out.println(min_arc_cap) ;
		System.out.println(max_arc_cap) ;
		
		if( (number_nodes == -1) || (number_arcs == -1) || (number_source_nodes == -1) || (number_sink_nodes == -1) ||
				(min_arc_cost == -1) || (max_arc_cost) == -1 || (tot_supply == -1) || (min_arc_cap == -1) || (max_arc_cap == -1))
		{
			System.out.println("Invalid reading of file") ;
			System.exit(1) ;
		}		
		
		Graph graph = new Graph(number_nodes, number_arcs, number_source_nodes, number_sink_nodes, min_arc_cost, max_arc_cost, tot_supply,
				min_arc_cap, max_arc_cap) ;

		return graph ;
		
	}
	
	
	@SuppressWarnings("null")
	public static Graph read_arc_information (BufferedReader reader, Graph graph) throws IOException
	{
		
		int in_vertex = -1 ;
		int out_vertex = -1 ;
		int min_capacity = -1;
		int max_capacity = -1;
		int cost = -1;
		String line = null ;
		int qtty_source_nodes = 0 ;
		int node_number = 0;
		int node_demand_supply = 0 ;
		Node new_node ;

		line = reader.readLine() ;
		do
		{
			if(line.charAt(0) == 'n')
			{
				int cont = 0 ;
				String[] sink_source_infos = line.split(" "); /* split each number and "n" into a different line */
				for (String sink_source_info : sink_source_infos)
				{
					if(!(sink_source_info.matches("[^\\d]")))
					{
						if(cont == 0)
						{
							node_number = Integer.parseInt(sink_source_info) ;							
							System.out.println(sink_source_info) ;
						}
						else if (cont == 1)
						{
							node_demand_supply = Integer.parseInt(sink_source_info) ;						
							System.out.println(sink_source_info) ;
						}
						cont++ ;	
					}
				}
					
				new_node = new Node(node_demand_supply, node_number) ;
				
				if(qtty_source_nodes < graph.get_number_source_nodes())
					graph.add_list_source_nodes(new_node) ;
				else
					graph.add_list_sink_nodes(new_node) ;
				
				qtty_source_nodes++ ;
				System.out.println("qtd") ;
				System.out.println(qtty_source_nodes) ;
			}
			
			line = reader.readLine() ;
		} while(line.charAt(0) != 'a') ;				
		
		do
		{
			if(line.charAt(0) == 'a')
			{
				int cont = 0;
				String[] arc_infos = line.split(" "); /* split each number and "a" into a different line */
				for (String arc_info : arc_infos)
				{
					if(!(arc_info.matches("[^\\d]")))	/* get every line, but the one that contains "a" */
					{
						if (cont == 0)
						{
							out_vertex = Integer.parseInt(arc_info) ;	/* out_vertex */
							System.out.println(arc_info) ;
						}
						else if (cont == 1)
						{
							in_vertex = Integer.parseInt(arc_info) ; 	/* in vertex */
							System.out.println(arc_info) ;
						}
						else if (cont == 2)
						{
							min_capacity = Integer.parseInt(arc_info) ; 	/* min arc capacity */
							System.out.println(arc_info) ;
						}
						else if (cont == 3)
						{
							max_capacity = Integer.parseInt(arc_info) ;	/* max arc capacity */
							System.out.println(arc_info) ;
						}
						else if (cont == 4)
						{
							cost = Integer.parseInt(arc_info) ;	/* arc cost */
							System.out.println(arc_info) ;
						}

						cont++ ;
					}
					
				}
					
				Arc new_arc = new Arc(out_vertex, in_vertex, 0, min_capacity, max_capacity, cost) ;				
				graph.get_node(out_vertex).insert_arc(new_arc) ;	
				//System.out.println(line) ;
					
			}			
		} while((line = reader.readLine()) != null) ;
			
	return graph ;	
	}
		
	
	
	public static void read_file () throws IOException
	{
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader("netg/big1.net"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Graph graph = read_graph_information(reader) ;
		
		graph = read_arc_information (reader, graph) ;
	}
}
