import java.util.ArrayList;


public class Node {

	private int demand_supply ;
	private int node_number ;
	private ArrayList<Arc> connections ;
	
	
	public Node (int dem_sup, int n_number)
	{
		demand_supply = dem_sup ;
		node_number = n_number ;
		
		connections = new ArrayList<Arc>() ;
	}
	
	public void insert_arc (Arc new_arc)
	{		
		connections.add(new_arc) ;
	}
	
	public int get_demand_supply ()
	{
		return demand_supply ;
	}
	
	public int get_node_number ()
	{
		return node_number ;
	}
	
	public Arc get_arc (int in_vertex)
	{
		int cont = 0 ;
		while (connections.get(cont).get_in_vertex() !=  in_vertex)
		{
			if(connections.get(cont) == null)
				return null ;
			cont++ ;
		}
		return connections.get(cont);
	}
}
