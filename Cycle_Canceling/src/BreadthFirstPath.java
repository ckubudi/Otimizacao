import java.util.LinkedList;
import java.util.Queue;


public class BreadthFirstPath {

	
	private int[] parent;
    private Queue<Integer> queue;
    private Edge[] edgeTo ;
    private boolean[] visited;
    int number_of_vertices ;
    private int source ;
    private int goal ;
    
    public BreadthFirstPath(int n_vertices)
    {
        number_of_vertices = n_vertices;
        queue = new LinkedList<Integer>();
        parent = new int[number_of_vertices];
        edgeTo = new Edge[number_of_vertices];
        visited = new boolean[number_of_vertices];
    }
    
	public boolean bfs(int source, int goal,  Graph G)
    {
        boolean pathFound = false;
        int element;
        this.source = source ;
        this.goal = goal ;

        for(int vertex = 0; vertex < G.numberVertices; vertex++)
        {
            parent[vertex] = -1;
            visited[vertex] = false;
        }

        queue.add(source);
        parent[source] = -1;
        visited[source] = true;

        while (!queue.isEmpty())
        { 
        	
            element = queue.remove();
            Vertex current = G.getVertex(element);
            
            for(Edge e: current.adj)
            {
            	if( (e.capacity > 0)  && !visited[e.destino.getId()])
                {
                    parent[e.destino.getId()] = element;
                    edgeTo[e.destino.getId()] = e ;
                    queue.add(e.destino.getId());
                    visited[e.destino.getId()] = true;
                }
            }
        }

        if(visited[goal])
        {
            pathFound = true;
        }
        return pathFound;
    }
	
	public int get_source ()
	{
		return source ;
	}
	
	public int get_goal ()
	{
		return goal ;
	}
	
	public int getBottleneck (Graph G)
	{
		int vertex = goal ;
		int minimumCapacity = G.getVertex(parent[vertex]).getAdj(vertex).getCapacity() ;
		
		//System.out.println("minimum capacity para o goal " + minimumCapacity) ;
		
        while(parent[vertex] != source)
        {
        	vertex = parent[vertex] ;
        	
        	minimumCapacity = Math.min(minimumCapacity, G.getVertex(parent[vertex]).getAdj(vertex).getCapacity()) ;
      	
        }
        
        return minimumCapacity ;
	}
	
	public int getParent (int vertex)
	{
		return parent[vertex] ;
	}
	
    public void printBFS(int source, int goal) 
    {
    	int vertex = goal ;
        while(vertex != source)
        {
        	System.out.print(vertex + "->") ;
        	vertex = parent[vertex] ;
        }
        System.out.println(vertex);
    }
    
    public Edge getEdgeTo (int destVertex)
    {
    	return this.edgeTo[destVertex] ;
    }
	
}
