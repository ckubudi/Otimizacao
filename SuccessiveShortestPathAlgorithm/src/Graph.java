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
			r += (u.getId()) + "(demandSupply:" + u.getSupplyDemand() + "/e:" + u.gete() +  "/pi:" + u.getPi() + ") -> ";
			for (Edge e : u.adj) {
				Vertex v = e.destino;
				r += (v.getId()) + "(flow:" + e.getFlow()  + "/capacity:" + e.getCapacity() + "/cost:" + e.cost + "),"; 	
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
    	long maximumTime = 1000 * 60 *5 ;	   	
    	long startTime = System.currentTimeMillis() ;
    	
		Vertex transportation = this.addVertex(numberVertices, 0);
		this.numberVertices++ ;
		
		//add supernode
		for (Vertex v : vertices)
			if (v.getId() != (numberVertices-1))
			{
				this.addEdge(transportation, v, 0, Integer.MAX_VALUE/5, Integer.MAX_VALUE/5);
				this.addEdge(v, transportation, 0, Integer.MAX_VALUE/5, Integer.MAX_VALUE/5);

			}

		this.buildResidualGraph() ;

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

			//update pi
			for (Vertex v: resGraph.vertices)
				v.setPi(v.getPi() - dijkstra.getShortestDistance(v.getId())) ;

			//update cost
			for ( Edge e : resGraph.edges)
			{
				if (e.originalEdge.getOrigem().getId() == e.getOrigem().getId())
					e.setCost(e.originalEdge.cost - e.getOrigem().getPi() + e.getDestino().getPi()) ;
				else
					e.setCost(-e.originalEdge.cost - e.getOrigem().getPi() + e.getDestino().getPi()) ;
			}
			
			dijkstra.setBottleneck(dem);

			int maxFlow = Math.min(dijkstra.getBottleneck(), sup.gete());
			maxFlow =  Math.min(maxFlow, -dem.gete());

			//update e
			sup.sete(sup.gete() - maxFlow);
			dem.sete(dem.gete() + maxFlow);
			if(sup.gete() == 0)
				resGraph.supplyNodes.remove(0);
			if(dem.gete() == 0)
				resGraph.demandNodes.remove(0);


			//update flow and capacity
			Edge tempEdge = dijkstra.getEdgeTo(dem.getId());
			while (tempEdge != null)
			{
				tempEdge.setCapacity(tempEdge.getCapacity() - maxFlow);
				tempEdge.mirror.setCapacity(tempEdge.mirror.getCapacity() + maxFlow);

				if(tempEdge.origem.getId() == tempEdge.originalEdge.origem.getId())
					tempEdge.originalEdge.flow += maxFlow ;
				else
					tempEdge.originalEdge.flow -= maxFlow ;

				tempEdge = dijkstra.getEdgeTo(tempEdge.getOrigem().getId());
			}
			
    		if(System.currentTimeMillis() - startTime >= maximumTime)
    		{
    			System.out.println("Excedeed 5 minutes!") ;
    			System.exit(1) ;
    		}
		}
		
		long minCostFlow = this.minCostFlow() ;
    	long elapsedTime = System.currentTimeMillis() - startTime ;
    	System.out.println("minCostFlow: " + minCostFlow + " found in " + elapsedTime/1000. + " seconds");
	}

	public void shortestPathAlgorithmCapacityScaling ()
	{
		
    	long maximumTime = 1000 * 60 *5 ;	   	
    	long startTime = System.currentTimeMillis() ;
    	
    	
		int maxCapacity = 0 ;
		int delta ;
		double tempDelta ;

		Vertex transportation = this.addVertex(numberVertices, 0);
		this.numberVertices++ ;

		//create super node
		for (Vertex v : vertices)
		{
			if (v.getId() != (numberVertices-1))
			{
				this.addEdge(transportation, v, 0, Integer.MAX_VALUE/5, Integer.MAX_VALUE/5);
				this.addEdge(v, transportation, 0, Integer.MAX_VALUE/5, Integer.MAX_VALUE/5);

			}
		}

		//get highest edge capacity
		for (Edge e : edges)
		{
			maxCapacity = Math.max(maxCapacity, e.capacity) ;
		}

		//set delta
		tempDelta = Math.log10(maxCapacity) / Math.log10(2) ;
		delta = (int) tempDelta ;
		tempDelta = Math.pow(2, delta) ;
		delta = (int) tempDelta ;

		this.buildResidualGraph() ;      	

		for (Vertex v: resGraph.vertices)
		{
			v.setPi(0);
			v.sete(v.getSupplyDemand());
		}

		while(delta >= 1)
		{
			// scaling phase
			for( Edge e : this.resGraph.edges)
			{
				if(e.capacity >= delta && e.cost < 0)
				{

					if(e.originalEdge.origem.getId() == e.origem.getId())
						e.originalEdge.flow += e.capacity ;
					else
						e.originalEdge.flow -= e.capacity ;
					
					setE (e.origem, -e.capacity) ;
					setE (e.destino, e.capacity) ;
					
					e.mirror.capacity = e.mirror.capacity + e.capacity ;
					e.capacity = 0 ; 								
				}
			}

			Vertex sup ;
			Vertex dem ;
			
			while ((sup = getDeltaVertex (this.resGraph.supplyNodes, delta)) != null && 
					(dem = getDeltaVertex (this.resGraph.demandNodes, delta)) != null)
			{
				Dijksrta dijkstra = new Dijksrta (this.resGraph) ;
				dijkstra.setShortestPath(sup.getId()) ;

				//update pi
				for (Vertex v: resGraph.vertices)
					v.setPi(v.getPi() - dijkstra.getShortestDistance(v.getId()) ) ;

				//update cost
				for ( Edge e : resGraph.edges)
				{
					if (e.originalEdge.getOrigem().getId() == e.getOrigem().getId())
						e.setCost(e.originalEdge.cost - e.getOrigem().getPi() + e.getDestino().getPi()) ;
					else
						e.setCost(-e.originalEdge.cost - e.getOrigem().getPi() + e.getDestino().getPi()) ;
				}

				dijkstra.setBottleneck(dem);

				int maxFlow = Math.min(dijkstra.getBottleneck(), sup.gete());
				maxFlow =  Math.min(maxFlow, -dem.gete());

				//update e
				sup.sete(sup.gete() - maxFlow);
				dem.sete(dem.gete() + maxFlow);
				if(sup.gete() == 0)
					resGraph.supplyNodes.remove(sup);
				if(dem.gete() == 0)
					resGraph.demandNodes.remove(dem);


				//update flow and capacity
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
				
	    		if(System.currentTimeMillis() - startTime >= maximumTime)
	    		{
	    			System.out.println("Excedeed 5 minutes!") ;
	    			System.exit(1) ;
	    		}
			}

			delta /= 2 ;
		}
		
		long minCostFlow = this.minCostFlow() ;
    	long elapsedTime = System.currentTimeMillis() - startTime ;
    	System.out.println("minCostFlow: " + minCostFlow + " found in " + elapsedTime/1000. + " seconds");
	}
	
	public void setE (Vertex v, int capacity)
	{
		int lastE = v.gete() ;
		v.sete(v.gete() + capacity) ;
		
		if(lastE == 0)
		{
			if(v.gete() < 0)
				this.resGraph.demandNodes.add(v) ;
			else if (v.gete() > 0)
				this.resGraph.supplyNodes.add(v) ;
		}
		else if (lastE > 0)
		{
			if(v.gete() == 0)
				this.resGraph.supplyNodes.remove(v) ;
			else if (v.gete() < 0)
			{
				this.resGraph.supplyNodes.remove(v) ;
				this.resGraph.demandNodes.add(v) ;
			}
		}
		else
		{
			if(v.gete() == 0)
				this.resGraph.demandNodes.remove(v) ;
			else if (v.gete() > 0)
			{
				this.resGraph.demandNodes.remove(v) ;
				this.resGraph.supplyNodes.add(v) ;
			}
		}

	}

	public Vertex getDeltaVertex (List<Vertex> list, int delta)
	{
		for( Vertex v : list)
		{
			if(Math.abs(v.gete()) >= delta)
				return v ;
		}
		
		return null ;
	}

	public long minCostFlow()
	{
		long minCostFlow = 0 ;

		for(Edge e : this.edges)
		{
			minCostFlow += e.cost*e.flow ;
		}

		return minCostFlow ;
	}




}