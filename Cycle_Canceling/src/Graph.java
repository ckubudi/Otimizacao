// Este é um exemplo simples de implementação de grafo representado por lista
// de adjacências

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

public class Graph {
    List<Vertex> vertices;
    List<Edge> edges;
    List<Vertex> supplyNodes ;
    List<Vertex> demandNodes ;
    Graph resGraph ;
    int numberVertices ;
    int numberEdges = 0;

    public Graph(int numVertices) {
        vertices = new ArrayList<Vertex>();
        supplyNodes = new ArrayList<Vertex>();
        demandNodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        numberVertices = numVertices ;
        
    	for (int i = 0 ; i < numVertices ; i++)
			this.addVertex(i , 0);
    }

    Vertex addVertex(int id, int flow) {
        Vertex v = new Vertex(id, flow);
        vertices.add(v);
        return v;
    }

    Edge addEdge(Vertex origem, Vertex destino, int lowBound, int cap, int cost) {
        Edge e = new Edge(origem, destino, lowBound, cap, cost);
        origem.addAdj(e);
        edges.add(e);
        numberEdges++ ;
        return e;
    }

    
    Edge addEdge(Vertex origem, Vertex destino, int lowBound, int cap, int cost, Edge ed) {
        Edge e = new Edge(origem, destino, lowBound, cap, cost, ed);
        origem.addAdj(e);
        edges.add(e);
        numberEdges++ ;
        return e;
    }
    
    public String toString() {
        String r = "";
        for (Vertex u : vertices) {
            r += u.getId() + "(demandSupply:" + u.getSupplyDemand() + ") -> ";
            for (Edge e : u.adj) {
                Vertex v = e.destino;
                r += v.getId() + "(flow:" + e.getFlow()  + "/capacity:" + e.getCapacity() + "/cost:" + e.cost + "),";
            }
            r += "\n";
        }
        return r;
    }
    
    public Vertex getVertex(int id)
    {
    	return vertices.get(id);
    	
    }
    
    public boolean flowOptCondition()
    {
    	int accumFlow = 0;
    	for (Vertex v : vertices)
    	{
    		accumFlow += v.getSupplyDemand() ;
    	}
    	
    	if (accumFlow == 0)
    		return true ;
    	else
    		return false;
    }
    
    public void buildResidualGraph()
    {
    	resGraph = new Graph(numberVertices);
    	for (Edge e : this.edges)
    	{
    		resGraph.addEdge(resGraph.getVertex(e.getOrigem().getId()), resGraph.getVertex(e.getDestino().getId()), e.getLowBound(), ( e.getCapacity() - e.getFlow() ) , e.getCost(), e);
    		resGraph.addEdge(resGraph.getVertex(e.getDestino().getId()),resGraph.getVertex(e.getOrigem().getId()), e.getLowBound(), e.getFlow() , -e.getCost(), e);
    	}
    }
    
    
    private static Edge getEdge(Graph g, int source, int dest)
    {
    	Vertex  v = g.getVertex(source) ;
		return v.getAdj(dest) ;
    }
    
    public int updateGraph(BreadthFirstPath bfs)
    {
		int sourceVertexId ;
		int destVertexId = bfs.get_goal() ;
		Edge tempEdge ;
		Edge tempResEdge1, tempResEdge2 ;
		int bottleneck ;
		
		bottleneck = bfs.getBottleneck(resGraph) ;
		
		while(destVertexId != bfs.get_source())
        {
			sourceVertexId = bfs.getParent(destVertexId) ;
			
			//residual Graph
			tempResEdge1 = Graph.getEdge(resGraph, sourceVertexId, destVertexId);
			tempResEdge1.capacity -= bottleneck ;
			
			tempResEdge2 = Graph.getEdge(resGraph, destVertexId, sourceVertexId);
			tempResEdge2.capacity += bottleneck ;
			
			//original graph
			tempEdge = Graph.getEdge(this, sourceVertexId, destVertexId) ;

			if (tempEdge.origem == tempResEdge1.origem)
				tempEdge.flow -= bottleneck ;
			else
				tempEdge.flow += bottleneck ;
			
			destVertexId = sourceVertexId ;
					
        }
		
		return bottleneck ;
    }
    
    public void updateTempEdge (Edge tempEdge, Edge tempResEdge1, Edge tempResEdge2, int bottleneck)
    {
    	
    	//original graph
		tempEdge = Graph.getEdge(this, tempResEdge1.getOrigem().getId(), tempResEdge1.getDestino().getId()) ;
		if(tempEdge != null)
		{
			if (tempEdge.origem == tempResEdge1.origem)
				tempEdge.flow -= bottleneck ;
			else
				tempEdge.flow += bottleneck ;
		}
		
		if(tempEdge == null)
		{
			tempEdge = Graph.getEdge(this, tempResEdge1.getDestino().getId(), tempResEdge1.getOrigem().getId()) ;
			if (tempEdge.origem == tempResEdge2.origem)
				tempEdge.flow += bottleneck ;
			else
				tempEdge.flow -= bottleneck ;
		}
		
    }
    
    public void updateGraph(BellmanFord bell)
    {
		Edge tempEdge = null ;
		Edge tempResEdge1, tempResEdge2 ;
		int bottleneck ;
		ArrayList<Edge> cycleList ;
		
		cycleList = bell.getCycle() ;
		bottleneck = bell.getBottleneck() ;
		
		int i = 1;
		tempResEdge1 = cycleList.get(cycleList.size()-i);
    	Vertex firstVertex = tempResEdge1.getOrigem() ;

    	while(tempResEdge1.getDestino() != firstVertex)
    	{
    		//residual Graph
    		tempResEdge1.capacity -= bottleneck ;
    		tempResEdge2 = Graph.getEdge(resGraph, tempResEdge1.getDestino().getId(), tempResEdge1.getOrigem().getId());
    		tempResEdge2.capacity += bottleneck ;
    		
    		updateTempEdge (tempEdge, tempResEdge1, tempResEdge2, bottleneck) ;

    		i++ ;
    		tempResEdge1 = cycleList.get(cycleList.size()-i);		
    	}
    	
    	//residual Graph
		tempResEdge1.capacity -= bottleneck ;
		tempResEdge2 = Graph.getEdge(resGraph, tempResEdge1.getDestino().getId(), tempResEdge1.getOrigem().getId());
		tempResEdge2.capacity += bottleneck ;
		
		updateTempEdge (tempEdge, tempResEdge1, tempResEdge2, bottleneck) ;

    }
        
    public Vertex setMaxCostFlow()
    {
    	//cria nos s e t e arestas respectivas
    	Vertex s = this.addVertex(numberVertices, 0); 
    	Vertex t = this.addVertex(numberVertices+1, 0);
    	numberVertices+=2 ;
    	int originalEdges = numberEdges ;
    	boolean bfsFoundPath ;
    	int maxFlow = 0 ;
    	int sumBottleneck = 0;
    	
    	for(Vertex v: supplyNodes) //supply is positive
    	{
    		this.addEdge(s, v, 0, v.getSupplyDemand(), 0);
    		maxFlow += v.getSupplyDemand() ;
    	}
    	
    	for(Vertex v: demandNodes) //demand is negative
    	{
    		this.addEdge(v, t, 0, -v.getSupplyDemand(), 0);
    		//System.out.println(v.getSupplyDemand()) ;
    	}
    
    	
    	this.buildResidualGraph();
    	
    	BreadthFirstPath bfs = new BreadthFirstPath(this.numberVertices) ;
    	while (bfs.bfs(s.getId(), t.getId(), this.resGraph) )
    	{
    		sumBottleneck += updateGraph(bfs) ;
    		//System.out.println("Original:");
    		//System.out.println(this);
    		//System.out.println("Residual:");
    		//System.out.println(resGraph);
    	}
    	
		System.out.println("Original:");
		System.out.println(this);
		System.out.println("Residual:");
		System.out.println(resGraph);
    	
    	if(sumBottleneck == maxFlow)
    	{
    		System.out.println("maxFlow: " + maxFlow) ;
    	}
    	
    	return s ;
    	
    }
    
    public int minCostFlow()
    {
    	int minCostFlow = 0 ;
    	for(Edge e : this.edges)
    	{
    		minCostFlow += e.cost*e.flow ;
    	}
    	
    	return minCostFlow ;
    }
    

    
    
}