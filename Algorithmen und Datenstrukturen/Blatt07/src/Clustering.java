import java.util.*;
import java.awt.Color;

/**
 * This class solves a clustering problem with the Prim algorithm.
 */
public class Clustering {
	EdgeWeightedGraph G;
	List <List<Integer>>clusters; 
	List <List<Integer>>labeled; 
	
	/**
	 * Constructor for the Clustering class, for a given EdgeWeightedGraph and no labels.
	 * @param G a given graph representing a clustering problem
	 */
	public Clustering(EdgeWeightedGraph G) {
            this.G=G;
	    clusters= new LinkedList <List<Integer>>();
	}
	
    /**
	 * Constructor for the Clustering class, for a given data set with labels
	 * @param in input file for a clustering data set with labels
	 */
	public Clustering(In in) {
            int V = in.readInt();
            int dim= in.readInt();
            G= new EdgeWeightedGraph(V);
            labeled=new LinkedList <List<Integer>>();
            LinkedList labels= new LinkedList();
            double[][] coord = new double [V][dim];
            for (int v = 0;v<V; v++ ) {
                for(int j=0; j<dim; j++) {
                	coord[v][j]=in.readDouble();
                }
                String label= in.readString();
                    if(labels.contains(label)) {
                    	labeled.get(labels.indexOf(label)).add(v);
                    }
                    else {
                    	labels.add(label);
                    	List <Integer> l= new LinkedList <Integer>();
                    	labeled.add(l);
                    	labeled.get(labels.indexOf(label)).add(v);
                    	System.out.println(label);
                    }                
            }
             
            G.setCoordinates(coord);
            for (int w = 0; w < V; w++) {
                for (int v = 0;v<V; v++ ) {
                	if(v!=w) {
                	double weight=0;
                    for(int j=0; j<dim; j++) {
                    	weight= weight+Math.pow(G.getCoordinates()[v][j]-G.getCoordinates()[w][j],2);
                    }
                    weight=Math.sqrt(weight);
                    Edge e = new Edge(v, w, weight);
                    G.addEdge(e);
                	}
                }
            }
	    clusters= new LinkedList <List<Integer>>();
	}
	
    /**
	 * This method finds a specified number of clusters based on a MST.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * @param numberOfClusters number of expected clusters
	 */
	public void findClusters(int numberOfClusters) {
		// TODO
		if(numberOfClusters == 1){
			clusters.add(new ArrayList<Integer>());
			for(int i = 0; i < G.V(); ++i ){
				clusters.get(0).add(i);
			}
		}else {
			PrimMST mst = new PrimMST(G);
			LinkedList<Edge> kantenMst = new LinkedList<Edge>();
			for (Edge ed : mst.edges()) {
				kantenMst.add(ed);
			}
			for (int i = 0; i < numberOfClusters - 1 ; ++i){
				double maxWeight = 0;
				int indexMax = 0;
				for (Edge ed : kantenMst) {
					if(maxWeight < ed.weight()){
						maxWeight = ed.weight();
						indexMax = kantenMst.indexOf(ed);
					}
				}
				kantenMst.remove(indexMax);
			}
			clusters = connectedComponents(kantenMst);
		}
	}


	public List <List<Integer>> connectedComponents(List<Edge> kantenMst){
		UF unionclus = new UF(G.V());
		LinkedList <List<Integer>> connect = new LinkedList<List<Integer>>();
		for(Edge ed : kantenMst){
			unionclus.union(ed.either(), ed.other(ed.either()));
		}
		connect.add(new ArrayList<Integer>());
		connect.get(0).add(0);
		for (int i = 1; i < G.V(); i++){
			int n = 0;
			for (List<Integer> co : connect) {
				if (unionclus.connected(co.get(0),i)) {
					co.add(i);
					n = 1;
					break;
				}
			}
			if (n == 1){
				continue;
			}
			List<Integer> li = new ArrayList<Integer>();
			li.add(i);
			connect.add(li);
		}
		return connect;
	}
	/**
	 * This method finds clusters based on a MST and a threshold for the coefficient of variation.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * The edges are removed based on the threshold given. For further explanation see the exercise sheet.
	 *
	 * @param threshold for the coefficient of variation
	 */
	public void findClusters(double threshold){
		// TODO
		PrimMST mst = new PrimMST(G);
		List<Edge> mstsort = (List<Edge>) mst.edges();
		Collections.sort(mstsort);
		LinkedList<Edge> kantenMst = new LinkedList<Edge>();
		for (Edge ed : mstsort) {
			kantenMst.add(ed);
			double cv = coefficientOfVariation(kantenMst);
			if (cv > threshold){
				kantenMst.remove(ed);
			}
		}
		clusters = connectedComponents(kantenMst);
	}
	
	/**
	 * Evaluates the clustering based on a fixed number of clusters.
	 * @return array of the number of the correctly classified data points per cluster
	 */
	public int[] validation() {
		// TODO
		int [] valid = new int [labeled.size()];
		int i =0;
		for (List<Integer> lab : labeled){
			for (Integer  element : lab){
				for (int j = 0; j<clusters.get(i).size();j++){
					if(Objects.equals(element, clusters.get(i).get(j))){
						valid[i]++;
						break;
					}
				}
			}
			i++;
		}
		return valid;
	}
	
	/**
	 * Calculates the coefficient of variation.
	 * For the formula see exercise sheet.
	 * @param part list of edges
	 * @return coefficient of variation
	 */
	public double coefficientOfVariation(List <Edge> part) {
		// TODO
		if(part.size() != 0) {
			double sigma = 0;
			double mu = 0;
			int n = part.size();
			for (Edge ed : part) {
				mu += ed.weight();
			}
			mu = mu / n;
			for (Edge ed : part) {
				sigma += Math.pow(ed.weight(), 2);
			}
			sigma = sigma / n;
			sigma = sigma - Math.pow(mu, 2);
			sigma = Math.sqrt(sigma);
			return sigma / mu;
		}else {
			return 0.0;
		}
	}
	
	/**
	 * Plots clusters in a two-dimensional space.
	 */
	public void plotClusters() {
		int canvas=800;
	    StdDraw.setCanvasSize(canvas, canvas);
	    StdDraw.setXscale(0, 15);
	    StdDraw.setYscale(0, 15);
	    StdDraw.clear(new Color(0,0,0));
		Color[] colors= {new Color(255, 255, 255), new Color(128, 0, 0), new Color(128, 128, 128), 
				new Color(0, 108, 173), new Color(45, 139, 48), new Color(226, 126, 38), new Color(132, 67, 172)};
	    int color=0;
		for(List <Integer> cluster: clusters) {
			if(color>colors.length-1) color=0;
		    StdDraw.setPenColor(colors[color]);
		    StdDraw.setPenRadius(0.02);
		    for(int i: cluster) {
		    	StdDraw.point(G.getCoordinates()[i][0], G.getCoordinates()[i][1]);
		    }
		    color++;
	    }
	    StdDraw.show();
	}
	

    public static void main(String[] args) {
		// FOR TESTING

    }
}

