


public class Dijksrta {

	private Graph graph ;
	private int[] shortestPath;
	private boolean [] visited ;
	private int[] previous;
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
		for (int i = 0 ; i < edgeTo.length ; i++)
			edgeTo[i] = null ;

		int min ;
		while ((min = getmin()) >= 0)
		{
			visited[min] = true; // Marca
			for (Edge e: graph.getVertex(min).adj) { // Percorre arestas
				if (e.capacity > 0 )
				{
					if(e.cost < 0 )
					{
						System.out.println("ALERTA! Arco com custo reduzido negativo detectado");
						System.out.println("Aresta de " + e.origem.getId() + " ate " + e.destino.getId() + " com custo " + e.cost);
						System.exit(1);
					}
					if ( (shortestPath[min] + e.cost) < (shortestPath[e.destino.getId()]) && !visited[e.destino.getId()] ) 
					{
						shortestPath[e.destino.getId()] = shortestPath[min] + e.cost;
						previous[e.destino.getId()] = min;
						edgeTo[e.destino.getId()] = e ;
					}
				}
			}

		}

	}

	public void setBottleneck (Vertex sink)
	{
		Vertex destino = sink;
		Edge edge = edgeTo[destino.getId()] ;
		if (edge == null)
		{
			System.out.println("Erro! Aresta para o vertice " + (sink.getId())  + " demand nula");
			System.exit(1);
		}
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
			if (visited[i] == false) { // Procura um desmarcado
				if (shortestPath[i] < cmin) { // Se o caminho minimo desse vertice for menor do que a variavel local
					cmin = shortestPath[i]; // Substituimos a nova variavel local pelo caminho minimo encontrado
					imin = i; // E armazenamos seu vertice
				}
			}
		}

		return imin; // Retornamos o vertice
	}


	public void printShortestPath ()
	{
		Edge temp ;
		System.out.println("Shortest Paths:");
		for(int i = 0 ; i < this.shortestPath.length ; i++)
		{
			System.out.println("Shortest Path to vertice " + (i+1) +  ":" + shortestPath[i]);
			System.out.println("Path used (reverse):");
			temp = edgeTo[i] ;
			while(temp != null)
			{
				System.out.println("> " + (temp.getOrigem().getId()+1) );
				temp = edgeTo[temp.getOrigem().getId()];
			}


		}
	}


}
