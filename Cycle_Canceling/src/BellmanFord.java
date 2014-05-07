import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class BellmanFord {
	
    public static int INF = Integer.MAX_VALUE;
           
    public BellmanFord()
    {
    	
    }
    
    private ArrayList<Edge> getCycle (Graph G, Edge e, int[] predecessor)
    {
    	
    	int pred, goal;
		ArrayList<Edge> listChecked = new ArrayList<Edge>() ;
		boolean[] checked = new boolean[G.numberVertices];
        Arrays.fill(checked, false);
        
        checked[e.origem.getId()] = true ;
        checked[e.destino.getId()] = true ;
        
        listChecked.add(e) ;
        
		System.out.println(e.destino.getId());
        System.out.println(e.origem.getId());
        
        pred = predecessor[e.origem.getId()] ;
        goal = e.origem.getId() ;
        
        while(checked[pred] != true)
        {
        	System.out.println(pred);
        	listChecked.add(G.getVertex(pred).getAdj(goal)) ;
        	checked[pred] = true ;
        	goal = pred ;
        	pred = predecessor[pred] ;
        }
        listChecked.add(G.getVertex(pred).getAdj(goal)) ;
            
        System.out.println(pred);
    	
        return listChecked ;
    }
    
    private int getBottleneck ( ArrayList<Edge> cycleList)
    {
    	int i = 1;
    	Edge e = cycleList.get(cycleList.size()-i);
    	Vertex firstVertex = e.getOrigem() ;
    	
    	int bottleneck = e.capacity ;
    	
    	while(e.getDestino() != firstVertex)
    	{
    		bottleneck = Math.min(bottleneck, e.capacity) ;   	
    		i++ ;
    		e = cycleList.get(cycleList.size()-i);		
    	}
    	
    	bottleneck = Math.min(bottleneck, e.capacity) ; 
    	
    	System.out.println("bottleneck: " + bottleneck);
    	return bottleneck ;
    }
    
    @SuppressWarnings("null")
	public boolean bellmanFord(Graph G, int source)
    {
    	int[] distance = new int[G.numberVertices];
    	int[] predecessor = new int[G.numberVertices];
    	boolean changed = false ;
    	
        Arrays.fill(distance, INF);
        distance[source] = 0;
        predecessor[source] = -1 ;
        
        for (int i = 0; i < G.numberVertices-1; ++i)
        {
        	for (Edge e : G.edges)
        	{
                if (distance[e.origem.getId()] == INF) continue ;                               

        		int newDistance = distance[e.origem.getId()] + e.cost;
        		
        		if (newDistance < distance[e.destino.getId()])
        		{
        			if(e.capacity > 0)
        			{
        				distance[e.destino.getId()] = newDistance;
                    	predecessor[e.destino.getId()] = e.origem.getId() ;
                    	changed = true ;
        			}
        		}
        	}
        	
        	if(changed == false)
        		break ;
        }
         
        for (Edge e : G.edges)
        {      
        	if (distance[e.origem.getId()] != INF && (distance[e.destino.getId()] > distance[e.origem.getId()] + e.cost) && (e.capacity > 0))
        	{	
        		ArrayList<Edge> cycle = null ;
        		System.out.println("Negative edge weight cycles detected!");
        		
        		cycle = getCycle (G, e, predecessor) ;
        		getBottleneck (cycle) ;
        		return false;
        	}
        }
             
        for (int i = 0; i < distance.length; ++i)
        {
        	if (distance[i] == INF)
        		System.out.println("There's no path between " + source + " and " + i);
        	else
        		System.out.println("The shortest distance between nodes " + source + " and " + i + " is " + distance[i]);
        }
        
        return true ;

    }
}