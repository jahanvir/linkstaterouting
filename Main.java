import java.io.*;
import java.util.*;

class Node implements Comparable<Node>{
	int val, cost;
	Node(int val, int cost){
		this.val=val;
		this.cost=cost;
	}
	public int compareTo(Node x){
		return Integer.compare(this.cost, x.cost);
	}
}

class Graph{
	private ArrayList<ArrayList<Node>> adj;
	private int numVertices,numEdges;

	Graph(int numVertices, int numEdges){
		this.numVertices=numVertices;
		this.numEdges=numEdges;

		adj = new ArrayList<ArrayList<Node>>(numVertices+1);
		for(int i=0;i<numVertices;i++){
			adj.add(new ArrayList<Node>(numVertices+1));
		}
	}
	
	ArrayList<ArrayList<Node>> getAdjacency(){
		return adj;
	}

	int getNumVertices(){
		return numVertices;
	}

	int getNumEdges(){
		return numEdges;
	}

	void addEdge(int source, int destination, int cost){
		if(isValidEdge(source,destination,cost)){
			addEdgeToGraph(source,destination,cost);
			System.out.println("Link added");
		}
		else{
			System.out.prinln("Link not added");
		}
	}
	private void addEdgeToGraph(int source, int destination, int cost){
		adj.get(source).add(new Node(destination,cost));
		adj.get(destination).add(new Node(source,cost));
	}

	private boolean isValidEdge(int source,int destination, int cost){
		if(cost<0){
			System.out.println("Negative cost not allowed");
			return false;
		}
		if(source<0 || source>=numVertices|| destination<0|| destination>=numVertices){
			System.out.println("Invalid vertices for edges -- Invalid edges");
			return false;
		}
		if(source == destination){
			System.out.println("Self loops are not allowed");
			return false;
		}
		return true;
	}
	boolean isEmpty(){
		int j=1;
		for(int i=0;i<numVertices;i++){
			if(adj.get(i).size()>0){
				j++;
			}
		}

		if(j==numVertices){
			return true;
		}
		else{
			return false;
		}
	}
}
 //need to implement this
class Dijsktra{
	private int[] dist, nextHop;
	private PriorityQueue<Node> pq;
	private ArrayList<ArrayList<Node>> adj;

	void dijsktraAlgorithm(int start, Graph graph){
		runDijisktraAlgorithm(start,graph);
	}

	private void runDijsktraAlgorithm(int start, Graph graph){
		int numVertices = graph.getNumVertices();
		adj = graph.getAdjacency();

		dist = new int[numVertices];
		Arrays.fill(dist,Integer.MAX_VALUE);

		nextHop = new int[numVertices];
		Arrays.fill(nextHop,-1);

		dist[start]=0;

		pq = new PriorityQueue<Node>();
		pq.add(new Node(start,0));

		//need to add more functions
	}
}

class Main {
	public static void main(String[] args) {

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

					// need to add in network topology
				}

				do {
					System.out.println("\nSimulating Router:");
					System.out.println("To simulate the router behaviour: ");
					System.out.print("Enter source router: ");
					src = sc.nextInt();
					System.out.print("Enter Destination router: ");
					dest = sc.nextInt();
					System.out.println("\n--------------------------------------");
					if (src > 0 && src < numRouters && dest > 0 && dest < numRouters) {
						System.out.println("The router will follow the path: ");
						// need to add that networktopology
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