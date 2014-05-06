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
        
        public int getId() {
			return id;
		}
        
		public int getSupplyDemand() {
			return supplyDemand;
		}

		public void setSupplyDemand (int sup_dem) {
			this.supplyDemand = sup_dem;
		}
        
    }
