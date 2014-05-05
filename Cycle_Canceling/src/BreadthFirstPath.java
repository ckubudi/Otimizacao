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
        parent = new int[number_of_vertices + 1];
        visited = new boolean[number_of_vertices + 1];
    }
    
	public boolean bfs(int source, int goal,  Graph G)
    {
        boolean pathFound = false;
        int destination, element;
System.out.println("passou0001");
        for(int vertex = 1; vertex <= G.get_number_nodes(); vertex++)
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
            destination = 1;
            
            while (destination <= G.get_number_nodes())
            {
            	System.out.println(element);
            	System.out.println(destination);
            	Arc elem_dest = G.get_node(element).get_arc(destination) ;
            	System.out.println("passou");
            	System.out.println(elem_dest);
            	
            	if(elem_dest != null && (elem_dest.get_maximum_capacity()-elem_dest.get_flow() > 0)   && !visited[destination])
                {
                    parent[destination] = element;
                    queue.add(destination);
                    visited[destination] = true;
                }
                destination++;
            }
        }

        if(visited[goal])
        {
            pathFound = true;
        }
        return pathFound;
    }
	
}
