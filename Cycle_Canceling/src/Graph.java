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

    public String toString() {
        String r = "";
        for (Vertex u : vertices) {
            r += u.getId() + "(flow:" + u.getSupplyDemand() + ") -> ";
            for (Edge e : u.adj) {
                Vertex v = e.destino;
                r += v.getId() + ", ";
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
    		resGraph.addEdge(e.getOrigem(), e.getDestino(), e.getLowBound(), ( e.getCapacity() - e.getFlow() ) , e.getCost());
    		resGraph.addEdge(e.getDestino(), e.getOrigem(), e.getLowBound(), e.getFlow() , -e.getCost());
    	}
    }
    
    
    public void setMaxCostFlow()
    {
    	//cria nos s e t e arestas respectivas
    	Vertex s = this.addVertex(numberVertices, 0); 
    	Vertex t = this.addVertex(numberVertices+1, 0);
    	numberVertices+=2 ;
    	int originalEdges = numberEdges ;
    	boolean bfsFoundPath ;
    	
    	for(Vertex v: supplyNodes) //supply is positive
    		this.addEdge(s, v, 0, v.getSupplyDemand(), 0);
    	
    	for(Vertex v: demandNodes) //demand is negative
    		this.addEdge(v, t, 0, v.getSupplyDemand(), 0);
    	
    	this.buildResidualGraph();
    	
    	BreadthFirstPath bfs = new BreadthFirstPath(this.numberVertices) ;
    	bfsFoundPath = bfs.bfs(s.getId(), t.getId(), this.resGraph) ;
    	
    	if(bfsFoundPath)
    		bfs.printBFS(s.getId(), t.getId()) ;
    	
    	
    }

    
    
}