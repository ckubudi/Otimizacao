import java.util.ArrayList;
import java.util.Arrays;

public class BellmanFord {
	
    public static int INF = Integer.MAX_VALUE;
           
    private int bottleneck ;
    private int[] distance ;
    private int[] predecessor ;
    private Edge[] edgeTo ;
    private ArrayList<Edge> cycle ;
    
    public BellmanFord()
    {
    }
    
    private void setCycle (Graph G, Edge e, int[] predecessor)
    {
    	
    	int pred, goal;
		cycle = new ArrayList<Edge>() ;
		boolean[] checked = new boolean[G.numberVertices];
        Arrays.fill(checked, false);
        
        checked[e.origem.getId()] = true ;
        checked[e.destino.getId()] = true ;
        
        cycle.add(e) ;
        
        pred = predecessor[e.origem.getId()] ;

        goal = e.origem.getId() ;
        
        while(checked[pred] != true)
        {
        	cycle.add(edgeTo[goal]) ;
        	checked[pred] = true ;
        	goal = pred ;
        	pred = predecessor[pred] ;
        }
        cycle.add(edgeTo[goal]) ;

    }
    
    private void setBottleneck ( ArrayList<Edge> cycleList)
    {
    	int i = 1;
    	Edge e = cycleList.get(cycleList.size()-i);
    	Vertex firstVertex = e.getOrigem() ;
    	
    	bottleneck = e.capacity ;
    	
    	while(e.getDestino() != firstVertex)
    	{
    		bottleneck = Math.min(bottleneck, e.capacity) ;   	
    		i++ ;
    		e = cycleList.get(cycleList.size()-i);	
    	}
    	
    	bottleneck = Math.min(bottleneck, e.capacity) ; 
    	
    	//System.out.println("bottleneck: " + bottleneck);
    }
    
    public boolean bellmanFord(Graph G, int source)
    {
    	distance = new int[G.numberVertices];
    	predecessor = new int[G.numberVertices];
    	edgeTo = new Edge[G.numberVertices] ;
    	boolean changed = false ;
        Arrays.fill(distance, INF);
        Arrays.fill(predecessor, -1) ;
        Arrays.fill(edgeTo, null) ;
        distance[source] = 0;

        for (int i = 0; i < G.numberVertices-1; i++)
        {
        	for(Edge e : G.edges)
        	{
        			if (distance[e.origem.getId()] == INF) continue ;                               

        			int newDistance = distance[e.origem.getId()] + e.cost;
        		
        			if (newDistance < distance[e.destino.getId()])
        			{
        				if(e.capacity > 0)
        				{
        					distance[e.destino.getId()] = newDistance;
        					predecessor[e.destino.getId()] = e.origem.getId() ;
        					edgeTo[e.destino.getId()] = e ;
        					changed = true ;
        				}
        			}
        	
        	if(changed == false)
        		break ;
        	}
        }
         
        for (Edge e : G.edges)
        {      
        	if (distance[e.origem.getId()] != INF && (distance[e.destino.getId()] > distance[e.origem.getId()] + e.cost) && (e.capacity > 0))
        	{	
        		//Negative edge weight cycles detected!      		
        		setCycle (G, e, predecessor) ;
        		setBottleneck (cycle) ;

        		return false;
        	}
        }                
        
        return true ;
    }

	public int[] getPredecessor() {
		return predecessor;
	}

	public int[] getDistance() {
		return distance;
	}
	
	public int getBottleneck ()
	{
		return bottleneck ;
	}

	public ArrayList<Edge> getCycle() {
		return cycle;
	}

}