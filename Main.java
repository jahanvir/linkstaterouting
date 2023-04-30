import java.io.*;
import java.util.*;

class Node implements Comparable<Node> {
	int val, cost;

	Node(int val, int cost) {
		this.val = val;
		this.cost = cost;
	}

	public int compareTo(Node x) {
		return Integer.compare(this.cost, x.cost);
	}
}

class Graph {
	private ArrayList<ArrayList<Node>> adj;
	private int numVertices, numEdges;

	Graph(int numVertices, int numEdges) {
		this.numVertices = numVertices;
		this.numEdges = numEdges;

		adj = new ArrayList<ArrayList<Node>>(numVertices + 1);
		for (int i = 0; i < numVertices; i++) {
			adj.add(new ArrayList<Node>(numVertices + 1));
		}
	}

	ArrayList<ArrayList<Node>> getAdjacency() {
		return adj;
	}

	int getNumVertices() {
		return numVertices;
	}

	int getNumEdges() {
		return numEdges;
	}

	void addEdge(int source, int destination, int cost) {
		if (isValidEdge(source, destination, cost)) {
			addEdgeToGraph(source, destination, cost);
			System.out.println("Link added");
		} else {
			System.out.println("Link not added");
		}
	}

	private void addEdgeToGraph(int source, int destination, int cost) {
		adj.get(source).add(new Node(destination, cost));
		adj.get(destination).add(new Node(source, cost));
	}

	private boolean isValidEdge(int source, int destination, int cost) {
		if (cost < 0) {
			System.out.println("Negative cost not allowed");
			return false;
		}
		if (source < 0 || source >= numVertices || destination < 0 || destination >= numVertices) {
			System.out.println("Invalid vertices for edges -- Invalid edges");
			return false;
		}
		if (source == destination) {
			System.out.println("Self loops are not allowed");
			return false;
		}
		return true;
	}

	boolean isEmpty() {
		int j = 1;
		for (int i = 0; i < numVertices; i++) {
			if (adj.get(i).size() > 0) {
				j++;
			}
		}

		if (j == numVertices) {
			return true;
		} else {
			return false;
		}
	}
}

// need to implement this
class Dijsktra {
	private int[] dist, nextHop;
	private PriorityQueue<Node> pq;
	private ArrayList<ArrayList<Node>> adj;

	void dijsktraAlgorithm(int start, Graph graph) {
		runDijsktraAlgorithm(start, graph);
	}

	private void runDijsktraAlgorithm(int start, Graph graph) {
		int numVertices = graph.getNumVertices();
		adj = graph.getAdjacency();

		dist = new int[numVertices];
		Arrays.fill(dist, Integer.MAX_VALUE);

		nextHop = new int[numVertices];
		Arrays.fill(nextHop, -1);

		dist[start] = 0;

		pq = new PriorityQueue<Node>();
		pq.add(new Node(start, 0));

		while (pq.size() > 0) {
			Node curr = pq.poll();
			int currN = curr.val;

			Iterator<Node> it = adj.get(currN).iterator();

			while (it.hasNext()) {
				Node temp = it.next();

				if (dist[temp.val] > dist[currN] + temp.cost) {

					pq.add(new Node(temp.val, dist[currN] + temp.cost));
					dist[temp.val] = dist[currN] + temp.cost;
					updateNextHop(start, currN, temp);
				}
			}
		}
	}

	private void updateNextHop(int start, int currN, Node temp) {
		if (start != currN) {
			nextHop[temp.val] = currN;
			if (nextHop[temp.val] != -1) {
				int tempNode = nextHop[nextHop[temp.val]];

				while (tempNode != -1) {
					nextHop[temp.val] = nextHop[nextHop[temp.val]];
					tempNode = nextHop[nextHop[temp.val]];
				}
			}
		}
	}

	void printShortestPath(int start) {
		for (int i = 0; i < dist.length; i++) {
			if (i != start) {
				System.out.println(i + "\t" + ((dist[i] == Integer.MAX_VALUE) ? "No link"
						: dist[i] + "\t" + ((nextHop[i] == -1) ? "- " : nextHop[i]) + " " + "\t"));
			}
		}
	}

	int[] getNextHop() {
		return nextHop;
	}

	int[] getDist() {
		return dist;
	}

}

class RoutingTable {

	private int router, numRouters;
	private int[] dist, nextHop;

	RoutingTable(int router, int numRouters) {
		this.router = router;
		this.numRouters = numRouters;
		dist = new int[numRouters];
		Arrays.fill(dist, Integer.MAX_VALUE);
		nextHop = new int[numRouters];
		Arrays.fill(nextHop, -1);
	}

	void setDist(int[] dist) {
		this.dist = dist;
	}

	void setNextHop(int[] nextHop) {
		this.nextHop = nextHop;
	}

	int[] getNextHop() {
		return nextHop;
	}

	int[] getDist() {
		return dist;
	}

	void printRoutingTable() {
		for (int i = 0; i < numRouters; i++) {
			if (i != router) {
				System.out.println(i + "\t" + ((dist[i] == Integer.MAX_VALUE) ? "No Link" : dist[i] + "\t") +
						((nextHop[i] == -1) ? "- " : nextHop[i]) + " " + "\t");
			}
		}
	}

}

class NetworkTopology {
	Graph network;
	int numRouters;
	int numRouterLinks;

	Dijsktra d[];
	RoutingTable routingTable[];

	NetworkTopology(int numRouters, int numRouterLinks) {

		this.numRouters = numRouters;
		this.numRouterLinks = numRouterLinks;

		network = new Graph(numRouters, numRouterLinks);
		routingTable = new RoutingTable[numRouters];

		d = new Dijsktra[numRouters];

		for (int i = 0; i < numRouters; i++) {
			d[i] = new Dijsktra();
		}

	}

	void addLink(int source, int destination, int cost) {
		network.addEdge(source, destination, cost);
	}

	void buildRoutingTables() {

		for (int i = 0; i < numRouters; i++) {
			d[i].dijsktraAlgorithm(i, network);
			routingTable[i] = new RoutingTable(i, numRouters);
			routingTable[i].setDist(d[i].getDist());
			routingTable[i].setNextHop(d[i].getNextHop());
		}
	}

	void printRoutingTables() {

		for (int i = 0; i < numRouters; i++) {
			System.out.println("\n--------------------------------------");
			System.out.println("Routing Table for Router " + i);
			System.out.println("\n--------------------------------------");
			System.out.println("Node\tCost\tNext Hop");
			routingTable[i].printRoutingTable();
			System.out.println("\n--------------------------------------");
		}
	}

	void simulateRouter(int src, int dst) {
		if (src >= 0 && src < numRouters && dst >= 0 && dst < numRouters) {
			simulateRouterBehaviour(src, dst);
		} else {
			System.out.println("Invalid source and/or destination");
		}
	}

	private void simulateRouterBehaviour(int source, int destination) {
		int dist[] = routingTable[source].getDist();
		int nextHop[] = routingTable[source].getNextHop();

		if (dist[destination] == Integer.MAX_VALUE) {
			System.out.println("No Link Exists");
			return;
		}
		if (source == destination) {
			System.out.println("Self loop");
		} else {
			System.out.println("Link Exists");
		}

		int next = nextHop[destination];
		if (next == -1) {
			System.out.println(source + " - " + destination);
		} else {
			System.out.print(source + " - ");
			while (next != -1) {
				System.out.print(next + " - ");
				nextHop = routingTable[next].getNextHop();
				next = nextHop[destination];
			}
			System.out.println(destination);
		}

	}

}

class Main {
	public static void main(String[] args) {

		NetworkTopology networkTopology;
		int source, destination, cost;
		int src, dest, again, repeat;
		boolean valid = true;
		Scanner sc = new Scanner(System.in);
		do {

			System.out.println("\nLink state routing\n");
			System.out.println("Enter the number of routers in your network: ");
			int numRouters = sc.nextInt();

			System.out.println("Enter the number of router links in your network: ");
			int numLinks = sc.nextInt();

			valid = true;

			if (numRouters < 0) {
				System.out.println("\nInvalid number of routers\n");
				valid = false;
			} else if (numRouters == 0) {
				System.out.println("\nNo routers -  empty network\n");
				valid = false;
			} else if (numLinks < 0) {
				System.out.println("\nInvalid number of links\n");
				valid = false;
			} else if (numLinks == 0) {
				System.out.println("\nNo routers links -  empty network\n");
				valid = false;
			}

			if (valid) {
				System.out.println("\n--------------------------------------");
				System.out.println("Your routers are:");
				for (int i = 0; i < numRouters; i++) {
					System.out.print(i + " ");
				}
				System.out.println("\n--------------------------------------");
				networkTopology = new NetworkTopology(numRouters, numLinks);
				int i = 0;
				System.out.println("\nEnter inputs for your router links");

				while (numLinks-- > 0) {

					System.out.println("\nRouter Link " + ++i);
					System.out.print("Enter source router of the link: ");
					source = sc.nextInt();
					System.out.print("Enter destination of the link: ");
					destination = sc.nextInt();
					System.out.print("Enter cost of the link: ");
					cost = sc.nextInt();
					networkTopology.addLink(source, destination, cost);
				}
				networkTopology.buildRoutingTables();
				networkTopology.printRoutingTables();

				do {
					System.out.println("\nSimulating Router:");
					System.out.println("To simulate the router behaviour: ");
					System.out.print("Enter source router: ");
					src = sc.nextInt();
					System.out.print("Enter Destination router: ");
					dest = sc.nextInt();
					System.out.println("\n--------------------------------------");
					if (src >= 0 && src < numRouters && dest >= 0 && dest < numRouters) {
						System.out.println("The router will follow the path: ");
						networkTopology.simulateRouter(src, dest);
					} else {
						System.out.println("Invalid source and/or destination");
					}
					System.out.println("\n--------------------------------------");
					System.out.print("Do you want to simulate again? Enter 1 if yes: ");
					again = sc.nextInt();
					System.out.println("\n--------------------------------------");

				} while (again == 1);

			}
			System.out.println("\n--------------------------------------");
			System.out.print("Do you want to build a new network? Enter 1 if yes: ");
			repeat = sc.nextInt();
			System.out.println("\n--------------------------------------");

		} while (repeat == 1);
		System.out.println("\nThank you");
		System.out.println("\n--------------------------------------");
	}
}