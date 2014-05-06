import java.util.ArrayList;
import java.util.List;


public class Edge {
    Vertex origem;
    Vertex destino;
    int lowBound ;
	int capacity ;
    int cost ;
    int flow;

    public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	Edge(Vertex origem, Vertex destino, int lowBound, int capacity , int cost) {
        this.origem = origem;
        this.destino = destino;
        this.lowBound = lowBound ;
        this.capacity = capacity ;
        this.cost = cost ;
    }
    
    public Vertex getOrigem() {
		return origem;
	}

	public void setOrigem(Vertex origem) {
		this.origem = origem;
	}

	public Vertex getDestino() {
		return destino;
	}

	public void setDestino(Vertex destino) {
		this.destino = destino;
	}

	public int getLowBound() {
		return lowBound;
	}

	public void setLowBound(int lowBound) {
		this.lowBound = lowBound;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}


    
    
}

