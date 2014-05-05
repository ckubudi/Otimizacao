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
}
