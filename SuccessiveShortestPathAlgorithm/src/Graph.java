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
    
    public Edge getEdge(int id)
    {
    	return edges.get(id);
    }

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
    	Edge e1, e2 ;
    	for (Edge e : this.edges)
    	{
    		e1 = resGraph.addEdge(resGraph.getVertex(e.getOrigem().getId()), resGraph.getVertex(e.getDestino().getId()), e.getLowBound(), ( e.getCapacity() - e.getFlow() ) , e.getCost(), e);
    		e2 = resGraph.addEdge(resGraph.getVertex(e.getDestino().getId()),resGraph.getVertex(e.getOrigem().getId()), e.getLowBound(), e.getFlow() , -e.getCost(), e);
    		e1.mirror = e2 ;
    		e2.mirror = e1 ;
    	}
    }
    
    
    private static Edge getMatchingResidualEdge(Graph g, int source, int dest, Edge original)
    {
    	Vertex  v = g.getVertex(source) ;
		return v.getAdjRes(dest, original) ;
    }
    
    
    private void updateTempEdge (Edge tempEdge, Edge tempResEdge1, Edge tempResEdge2, int bottleneck)
    {
    	
    	//original graph
		tempEdge = tempResEdge1.originalEdge ;

		if (tempEdge.origem.getId() == tempResEdge1.origem.getId())
			tempEdge.flow += bottleneck ;
		else
			tempEdge.flow -= bottleneck ;
		
    }
    
        
    public void shortestPathAlgorithm ()
    {
    	for (Vertex v: resGraph.vertices)
    	{
    		v.setPi(0);
    		v.sete(v.getSupplyDemand());
    	}
    	while (!(this.resGraph.supplyNodes.isEmpty()))
    	{
    		Vertex sup = this.resGraph.supplyNodes.get(0) ;
    		Vertex dem = this.resGraph.demandNodes.get(0) ;
    		
    		Dijksrta dijkstra = new Dijksrta (this.resGraph) ;
    		dijkstra.setShortestPath(sup.getId()) ;
    		
    		for (Vertex v: resGraph.vertices)
    			v.setPi(v.getPi() - dijkstra.getShortestDistance(v.getId()) ) ;
    		
    		for ( Edge e : resGraph.edges)
    			e.setCost(e.getCost() - e.getOrigem().getPi() + e.getDestino().getPi()) ;
    		
    		dijkstra.setBottleneck(dem);
    		
    		int maxFlow = Math.min(dijkstra.getBottleneck(), sup.gete());
    		
    		maxFlow =  Math.min(maxFlow, dem.gete());
    		
    		sup.sete(sup.gete() - maxFlow);
    		dem.sete(dem.gete() + maxFlow);
    		if(sup.gete() == 0)
    			resGraph.supplyNodes.remove(0);
    		if(dem.gete() == 0)
    			resGraph.supplyNodes.remove(0);
    		
    		
    		Edge tempEdge = dijkstra.getEdgeTo(dem.getId());
    		while ( tempEdge != null)
    		{
    			tempEdge.setCapacity(tempEdge.getCapacity() - maxFlow);
    			tempEdge.mirror.setCapacity(tempEdge.mirror.getCapacity() + maxFlow);
    			
    			if(tempEdge.origem == tempEdge.originalEdge.origem)
    				tempEdge.originalEdge.flow += maxFlow ;
    			else
    				tempEdge.originalEdge.flow -= maxFlow ;
    		}  		
    		
    	}
    	
    	System.out.println( "min cost " + this.minCostFlow()) ;
    }
    
    
    private void removeSupportComponents(int sId, int tId) {
		Vertex s = this.getVertex(sId);
		Vertex t = this.getVertex(tId);
    	ArrayList<Edge> removedEdges = new ArrayList<Edge>(); 
    	for(Edge e: edges)
    	{
			if(e.getOrigem() == s || e.getOrigem() == t)
			{
				removedEdges.add(e);
				numberEdges-- ;
			}
			else if (e.getDestino() == t || e.getDestino() == s)
			{
				e.getOrigem().adj.remove(e);
				removedEdges.add(e);
				numberEdges-- ;
			}
    	}
    	
    	for (Edge e : removedEdges)
    		edges.remove(e);
    	
    	
		vertices.remove(s);
    	vertices.remove(t);
    	numberVertices -= 2 ;
	}

	public int minCostFlow()
    {
    	int minCostFlow = 0 ;
    	//int minTest = 0 ;
    	for(Edge e : this.edges)
    	{
    		//minTest = minCostFlow ;
    		minCostFlow += e.cost*e.flow ;
    		//System.out.println("mincost: " + minCostFlow) ;
    		/*if(minCostFlow == -2127939497)
    		{
    			System.out.println("minCost anterior: " + minTest) ;
    			System.out.println("origem: " + e.origem.getId() + " destino: " + e.destino.getId()) ;
    			System.out.println("custo: " + e.cost + " fluxo: " + e.flow + " " + "fluxo*cost: " + e.cost*e.flow) ;
    			System.exit(1) ;
    		}*/
    	}
    	
    	return minCostFlow ;
    }
    

    
    
}