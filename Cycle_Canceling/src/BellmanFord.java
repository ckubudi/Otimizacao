import java.util.Arrays;
import java.util.Vector;

public class BellmanFord {
	
    public static int INF = Integer.MAX_VALUE;
           
    public BellmanFord()
    {
    	
    }

           
    public boolean bellmanFord(Graph G, int source)
    {
    	int[] distance = new int[G.numberVertices];
    	int[] parent = new int[G.numberVertices];
        Arrays.fill(distance, INF);
        distance[source] = 0;
        
        for (int i = 0; i < G.numberVertices; ++i)
        {
        	for (Edge e : G.edges)
        	{
                if (distance[e.origem.getId()] == INF) continue ;                               

        		int newDistance = distance[e.origem.getId()] + e.capacity*e.cost;

        		//distance[e.destino.getId()] = Math.min(distance[e.destino.getId()], newDistance) ;
        		
        		if (newDistance < distance[e.destino.getId()])
        		{
                    distance[e.destino.getId()] = newDistance;
                    parent[e.destino.getId()] = e.origem.getId() ;
        		}
        	}
        }
         
        for (Edge e : G.edges)
        {      
        	if (distance[e.origem.getId()] != INF && (distance[e.destino.getId()] > distance[e.origem.getId()] + e.capacity*e.cost))
        	{	
        		int next = 0 ;
        		boolean[] checked = new boolean[G.numberVertices];
                Arrays.fill(checked, false);
                
                checked[e.origem.getId()] = true ;
                checked[e.destino.getId()] = true ;
                
        		while(parent[next] != e.destino.getId())
        		{
        			next++;
        		}
        		System.out.println(e.origem.getId());
        		System.out.println(e.destino.getId());
        		System.out.println(next);
        		//if(checked[next] = true)
        		
        		System.out.println("Negative edge weight cycles detected!");
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