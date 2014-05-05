
public class Arc {
	
	private int out_vertex ;
	private int in_vertex ;
	private int flow ;
	private int min_capacity ;
	private int max_capacity ;
	private int residual_capacity ;
	private int cost ;
	
	public Arc (int o_vertex, int i_vertex, int arc_flow, int min_cap, int max_cap, int arc_cost)
	{
		flow = arc_flow ;
		out_vertex = o_vertex ;
		in_vertex = i_vertex ;
		min_capacity = min_cap ;
		max_capacity = max_cap ;
		cost = arc_cost ;
		residual_capacity = max_capacity ;
	}
	
	public int get_out_vertex ()
	{
		return out_vertex ;
	}
	
	public int get_in_vertex ()
	{
		return in_vertex ;
	}
	
	public int get_flow ()
	{
		return flow ;
	}
	
	public void add_flow (int new_flow)
	{
		flow = flow + new_flow ;
	}
	
	public void sub_flow (int new_flow)
	{
		flow = flow - new_flow ;
	}
	
	public int get_minimum_capacity ()
	{
		return min_capacity ;
	}
	
	public int get_maximum_capacity ()
	{
		return max_capacity ;
	}
	
	public int get_cost ()
	{
		return cost ;
	}
	
	public int get_residual_capacity ()
	{
		return residual_capacity ;
	}
	
	public void set_residual_capacity (int res_cap)
	{
		residual_capacity = res_cap ;
	}

}
