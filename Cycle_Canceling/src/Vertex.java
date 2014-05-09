import java.util.ArrayList;
import java.util.List;

public class Vertex {
        private int id ;
        private int supplyDemand ;

		List<Edge> adj;

        Vertex(int id, int flow) {
            this.id = id;
            this.adj = new ArrayList<Edge>();
            this.supplyDemand = flow ;
        }

        void addAdj(Edge e) {
            adj.add(e);
        }
        
        Edge getAdj(int goal) {
        	for(Edge e : adj)
        	{
        		if(e.getDestino().getId() == goal)
        			return e ;
        	}
            return null ;
        }
        
        public int getId() {
			return id;
		}
        
		public int getSupplyDemand() {
			return supplyDemand;
		}

		public void setSupplyDemand (int sup_dem) {
			this.supplyDemand = sup_dem;
		}

		public Edge getAdjRes(int dest, Edge original) {
        	for(Edge e : adj)
        	{
        		if(e.getDestino().getId() == dest && e.originalEdge == original)
        			return e ;
        	}
            return null ;
		}
        
    }
