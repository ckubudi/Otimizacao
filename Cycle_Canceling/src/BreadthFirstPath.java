import java.util.LinkedList;
import java.util.Queue;


public class BreadthFirstPath {

	
	private int[] parent;
    private Queue<Integer> queue;
    private boolean[] visited;
    int number_of_vertices ;
    
    public BreadthFirstPath(int n_vertices)
    {
        number_of_vertices = n_vertices;
        queue = new LinkedList<Integer>();
        parent = new int[number_of_vertices];
        visited = new boolean[number_of_vertices];
    }
    
	public boolean bfs(int source, int goal,  Graph G)
    {
        boolean pathFound = false;
        int element;

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
	
    public void printBFS(int source, int goal) 
    {
    	int vertex = goal ;
        while(vertex != source)
        {
        	System.out.print(vertex + "->") ;
        	vertex = parent[vertex] ;
        }
        System.out.print(vertex);
    }
	
}
