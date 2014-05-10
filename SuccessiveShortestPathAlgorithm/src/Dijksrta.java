import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class Dijksrta {

	private Graph graph ;
	private int[] shortestPath;
	private boolean [] visited ;
	private int[] previous;
	private Queue<Vertex> queue = new LinkedList<Vertex>(); 
	private Edge[] edgeTo ;
	private int bottleneck ;

	public Edge getEdgeTo(int id)
	{
		return edgeTo[id] ;
	}
	
	public int getBottleneck() {
		return bottleneck;
	}

	public void setBottleneck(int bottleneck) {
		this.bottleneck = bottleneck;
	}

	public int getShortestDistance (int id)
	{
		return shortestPath[id] ;
	}
	
	public int getPrevious (int id)
	{
		return previous[id] ;
	}


	public Dijksrta (Graph g)
	{
		this.graph = g ;
		shortestPath = new int[g.numberVertices];
		previous = new int[g.numberVertices] ;
		visited = new boolean[g.numberVertices] ;
		edgeTo = new Edge[g.numberVertices] ;
	}


	public void setShortestPath(int source)
	{
		for (int i = 0 ; i < graph.numberVertices ; i++ )
		{
			shortestPath[i] = Integer.MAX_VALUE ;
			previous[i] = -1 ;
			visited[i] = false ;
		}

		shortestPath[source] = 0 ;
		edgeTo[source] = null ;

		int min ;
		while ((min = getmin()) >= 0)
		{
			visited[min] = true; // Marca
			for (Edge e: graph.getVertex(min).adj) { // Percorre arestas
				if (e.capacity > 0)
					if ( (shortestPath[min] + e.cost) < (shortestPath[e.destino.getId()]) ) 
					{
						shortestPath[e.destino.getId()] = shortestPath[min] + e.cost;
						previous[e.destino.getId()] = min;
						edgeTo[e.destino.getId()] = e ;
					}
			}

		}
	}
	
	public void setBottleneck (Vertex sink)
    {
    	Vertex destino = sink;
    	Edge edge = edgeTo[destino.getId()] ;
    	bottleneck = edge.capacity ;
    	
    	while(edge != null)
    	{
    		bottleneck = Math.min(bottleneck, edge.capacity) ;   	    		
    		destino = edge.getOrigem() ;
    		edge = edgeTo[destino.getId()];
    	}
    }


	private int getmin ()
	{

		int i;
		int cmin = Integer.MAX_VALUE; // Comeca com infinito
		int imin = -1; // O menor vai ser sempre substituido
		for (i = 0; i < graph.numberVertices; i++) { // Percorre vetor de vertices
			if (visited[i] = false) { // Procura um desmarcado
				if (shortestPath[i] < cmin) { // Se o caminho minimo desse vertice for menor do que a variavel local
					cmin = shortestPath[i]; // Substituimos a nova variavel local pelo caminho minimo encontrado
					imin = i; // E armazenamos seu vertice
				}
			}
		}

		return imin; // Retornamos o vertice
	}




}
