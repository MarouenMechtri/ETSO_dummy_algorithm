package dummy_algo;

import java.util.ArrayList;

public class WeightedGraph {

	private static final int INFINITE = Integer.MAX_VALUE;

	private Cost[][] edges; // adjacency matrix
	private Server[] nodes;

	public WeightedGraph(int n) {
		edges = new Cost[n][n];
		nodes = new Server[n];
	}

	public WeightedGraph(Cost[][] edges, Server[] nodes) {
		super();
		this.edges = new Cost[nodes.length][nodes.length];
		this.nodes = new Server[nodes.length];
		this.edges = edges;
		this.nodes = nodes;
		// print();
	}

	public int nbNodes() {
		return nodes.length;
	}

	public void setNodes(Server[] nodes) {
		this.nodes = nodes;
	}

	public void setNodes(int vertex, Server nodes) {
		this.nodes[vertex] = nodes;
	}

	public Server getNodes(int vertex) {
		return nodes[vertex];
	}

	public void setEdges(Cost[][] edges) {
		this.edges = edges;
	}

	public void addEdge(int source, int target, Cost w) {
		edges[source][target] = w;
	}


	public Cost getWeight(int source, int target) {
		return edges[source][target];
	}
	
	public void setWeight(int source, int target, Cost e) {
		edges[source][target] = e;
	}

	

	public void buildMatrixWithdirectLink(WeightedGraph G) {
		Cost[][] adgencyConnex = new Cost[G.nbNodes()][G.nbNodes()];
		for (int j = 0; j < G.nbNodes(); j++) {
			for (int k = 0; k < G.nbNodes(); k++) {
				adgencyConnex[j][k] = G.edges[j][k];
			}

		}
		for (int i = 0; i < G.nbNodes(); i++) {
			for (int n = 0; n < G.nbNodes(); n++) {
				if (adgencyConnex[i][n] == null) {
					adgencyConnex[i][n] = new Cost();
				} else {
					ArrayList<Server> directLink = new ArrayList<Server>();
					directLink.add(G.getNodes(i));
					directLink.add(G.getNodes(n));
					adgencyConnex[i][n].setPath(directLink);

				}
			}
		}
		G.edges = adgencyConnex;
	}

	public static void main(String args[]) {

	}

}