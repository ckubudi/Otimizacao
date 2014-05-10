// Este é um exemplo simples de implementação de grafo representado por lista
// de adjacências

import java.util.List;
import java.util.ArrayList;

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
            r += (u.getId()+1) + "(demandSupply:" + u.getSupplyDemand() + "/e:" + u.gete() +  "/pi:" + u.getPi() + ") -> ";
            for (Edge e : u.adj) {
                Vertex v = e.destino;
                r += (v.getId()+1) + "(flow:" + e.getFlow()  + "/capacity:" + e.getCapacity() + "/cost:" + e.cost + "),";
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
    	Vertex temp ;
    	for(Vertex v : this.supplyNodes)
    	{
    		temp = resGraph.getVertex(v.getId());
    		temp.setSupplyDemand(v.getSupplyDemand());
    		resGraph.supplyNodes.add(temp);
    	}
    	
    	for(Vertex v : this.demandNodes)
    	{
    		temp = resGraph.getVertex(v.getId());
    		temp.setSupplyDemand(v.getSupplyDemand());
    		resGraph.demandNodes.add(temp);
    	}
    	
    	Edge e1, e2 ;
    	for (Edge e : this.edges)
    	{
    		e1 = resGraph.addEdge(resGraph.getVertex(e.getOrigem().getId()), resGraph.getVertex(e.getDestino().getId()), e.getLowBound(), ( e.getCapacity() - e.getFlow() ) , e.getCost(), e);
    		e2 = resGraph.addEdge(resGraph.getVertex(e.getDestino().getId()),resGraph.getVertex(e.getOrigem().getId()), e.getLowBound(), e.getFlow() , -e.getCost(), e);
    		e1.mirror = e2 ;
    		e2.mirror = e1 ;
    	}
    }
    
    
        
    public void shortestPathAlgorithm ()
    {
       	int contadorIteracoes = 0; 
    	
    	for (Vertex v: resGraph.vertices)
    	{
    		v.setPi(0);
    		v.sete(v.getSupplyDemand());
    	}
    	while (!(this.resGraph.supplyNodes.isEmpty()))
    	{
    		//System.out.println("Grafo Residual na Itera��o numero " + contadorIteracoes + ":\n" + this.resGraph );
    		System.out.println("Iteracao numero: " + contadorIteracoes) ;
    		contadorIteracoes++ ;
    		
    		Vertex sup = this.resGraph.supplyNodes.get(0) ;
    		Vertex dem = this.resGraph.demandNodes.get(0) ;
    		
    		System.out.println("Id do supply escolhido: " + sup.getId() ) ;
    		System.out.println("Id do demand escolhido: " + dem.getId() ) ;
    		
    		
    		Dijksrta dijkstra = new Dijksrta (this.resGraph) ;
    		dijkstra.setShortestPath(sup.getId()) ;
    	    		
    		for (Vertex v: resGraph.vertices)
    			v.setPi(v.getPi() - dijkstra.getShortestDistance(v.getId()) ) ;
    		
    		for ( Edge e : resGraph.edges)
    			e.setCost(e.getCost() - e.getOrigem().getPi() + e.getDestino().getPi()) ;
    		
    		//dijkstra.printShortestPath();
    		
    		dijkstra.setBottleneck(dem);
    		
    		int maxFlow = Math.min(dijkstra.getBottleneck(), sup.gete());
    		
    		maxFlow =  Math.min(maxFlow, -dem.gete());
    		
    		System.out.println("Bottleneck final: " + maxFlow);
    		
    		sup.sete(sup.gete() - maxFlow);
    		dem.sete(dem.gete() + maxFlow);
    		if(sup.gete() == 0)
    			resGraph.supplyNodes.remove(0);
    		if(dem.gete() == 0)
    			resGraph.demandNodes.remove(0);
    		

    		Edge tempEdge = dijkstra.getEdgeTo(dem.getId());
    		while ( tempEdge != null)
    		{
    			tempEdge.setCapacity(tempEdge.getCapacity() - maxFlow);
    			tempEdge.mirror.setCapacity(tempEdge.mirror.getCapacity() + maxFlow);
    			
    			if(tempEdge.origem.getId() == tempEdge.originalEdge.origem.getId())
    				tempEdge.originalEdge.flow += maxFlow ;
    			else
    				tempEdge.originalEdge.flow -= maxFlow ;
    			
				tempEdge = dijkstra.getEdgeTo(tempEdge.getOrigem().getId());
    			
    		}  		
    		
    	}
      }
    


	public int minCostFlow()
    {
    	int minCostFlow = 0 ;
    	//int minTest = 0 ;
    	for(Edge e : this.edges)
    	{
    		minCostFlow += e.cost*e.flow ;
    	}
    	
    	return minCostFlow ;
    }
    

    
    
}