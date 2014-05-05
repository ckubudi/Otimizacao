import java.util.ArrayList;


public class Graph {

	private int number_nodes ;
	private int number_arcs ;
	private int number_source_nodes ;
	private int number_sink_nodes ;
	private int minimum_arc_cost ;
	private int maximum_arc_cost ;
	private int minimum_arc_capacity ;
	private int maximum_arc_capacity ;
	private int total_supply ;
	private ArrayList<Node> _graph ;
	private ArrayList<Node> list_sink_nodes ;
	private ArrayList<Node> list_source_nodes ;

	public Graph (int n_nodes, int n_arcs, int n_source_nodes, int n_sink_nodes, int min_arc_cost, int max_arc_cost, int tot_supply,
			int min_arc_cap, int max_arc_cap)
	{
		number_nodes = n_nodes;
		number_arcs = n_arcs;
		number_source_nodes = n_source_nodes ;
		number_sink_nodes = n_sink_nodes;
		minimum_arc_cost = min_arc_cost;
		maximum_arc_cost = max_arc_cost;
		total_supply = tot_supply;
		minimum_arc_capacity = min_arc_cap ;
		maximum_arc_capacity = max_arc_cap ;
		
		_graph = new ArrayList<Node>();
		list_sink_nodes = new ArrayList<Node>() ;
		list_source_nodes = new ArrayList<Node>() ;
		
		for(int i = 0; i < number_nodes; i++)
		{
		    _graph.add(new Node(0, i));
		}		


	}
	
	/*public Graph ()
	{
	}

	
	public void initialize_graph_connections ()
	{
		System.out.println("entrou") ;
		for (int out_vertex = 0; out_vertex < number_nodes; out_vertex++)
        {
           for (int in_vertex = 0; in_vertex < number_nodes; in_vertex++)
           {
        	   System.out.println(out_vertex) ;
               graph_connections[out_vertex][in_vertex] = 0;
           }
        }
	}*/
	
	/*public void create_residual_graph ()
	{
		for(int i = 0; i < number_nodes; i++)
		{
			int j = 0 ;
			while (j < _graph.get(i).size())
			{
				int new_arc_cost = - _graph.get(i).get(j).get_cost() ;
				int new_arc_min_cap = _graph.get(i).get(j).get_minimum_capacity() ;
				int new_arc_max_cap = _graph.get(i).get(j).get_maximum_capacity() ;
				int new_arc_in_vertex = _graph.get(i).get(j).get_out_vertex() ;
				int new_arc_out_vertex = _graph.get(i).get(j).get_in_vertex() ;
				
				Arc new_arc = new Arc(new_arc_out_vertex, new_arc_in_vertex, 0, new_arc_min_cap, new_arc_max_cap, new_arc_cost) ;
				new_arc.set_residual_capacity(new_arc_max_cap - _graph.get(i).get(j).get_residual_capacity()) ;
				
				_graph.get(new_arc_out_vertex-1).add(new_arc) ;
				
				j++ ;
			}
		}
	}*/
	
	/*public void add_graph_connections (int out_vertex, int in_vertex, )
	{
		for (int out_vertex = 0; out_vertex < number_nodes; out_vertex++)
        {
           for (int in_vertex = 0; in_vertex < number_nodes; in_vertex++)
           {
               graph_connections[out_vertex][in_vertex] = 0;
           }
        }
	}*/
	
	public int get_number_nodes ()
	{
		return number_nodes ;
	}
	
	public int get_number_arcs ()
	{
		return number_arcs ;
	}
	
	public int get_number_source_nodes ()
	{
		return number_source_nodes ;
	}
	
	public int get_number_sink_nodes ()
	{
		return number_sink_nodes ;
	}
	
	public int get_minimum_arc_cost ()
	{
		return minimum_arc_cost ;
	}
	
	public int get_maximum_arc_cost ()
	{
		return maximum_arc_cost ;
	}
	
	public int get_total_supply ()
	{
		return total_supply ;
	}
	
	public int get_minimum_arc_capacity ()
	{
		return minimum_arc_capacity ;
	}
	
	public int get_maximum_arc_capacity ()
	{
		return maximum_arc_capacity ;
	}
	
	public Node get_node (int vertex)
	{
		return _graph.get(vertex-1) ;
	}
	
	public void add_list_sink_nodes(Node new_node)
	{
		list_sink_nodes.add(new_node) ;
	}
	
	public void add_list_source_nodes(Node new_node)
	{
		list_source_nodes.add(new_node) ;
	}
	
	/*public void set_list_sink_nodes(ArrayList<Node> sink_list)
	{
		for(int i = 0; i < sink_list.size(); i++)
		{
			list_sink_nodes.add(sink_list.get(i)) ;
		}
	}
	
	public void set_list_source_nodes(ArrayList<Node> source_list)
	{
		for(int i = 0; i < source_list.size(); i++)
		{
			list_source_nodes.add(source_list.get(i)) ;
		}
	}*/
	
	public boolean is_source_node(int vertex)
	{
		return list_source_nodes.contains(vertex) ;
	}
	
	
	public boolean is_sink_node(int vertex)
	{
		return list_sink_nodes.contains(vertex) ;
	}

	
	/*public Arc get_arc (int in_vertex, int out_vertex)
	{	
		int cont = 0 ;
		while (_graph.get(in_vertex-1).get(cont).get_out_vertex() != out_vertex)
		{
			if(_graph.get(in_vertex-1).get(cont) == null)
				return null ;
			cont++ ;
		}
		return _graph.get(in_vertex-1).get(cont) ;

	}*/
	
	public void print_graph ()
	{
		for(int i = 0; i < number_nodes; i++)
		{
			int j = 0 ;
			System.out.println(_graph.get(i).get_node_number());
			while(_graph.get(i).get_arc(j) != null)
			{
				System.out.println("arc to" + _graph.get(i).get_arc(j).get_in_vertex());
				j++ ;
			}
		}	
	}



}
