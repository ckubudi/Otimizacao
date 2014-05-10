import java.util.ArrayList;
import java.util.List;

public class Vertex {
        private int id ;
        private int supplyDemand ;
        private int pi ;
        private int e ;

		List<Edge> adj;

        Vertex(int id, int flow) {
            this.id = id;
            this.adj = new ArrayList<Edge>();
            this.supplyDemand = flow ;
            this.pi = 0 ;
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

		public int getPi() {
			return pi;
		}

		public void setPi(int pi) {
			this.pi = pi;
		}

		public int gete() {
			return e;
		}

		public void sete(int e) {
			this.e = e;
		}
        
    }
