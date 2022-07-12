import java.util.Collection;
import java.util.Stack;

public class ShortestPathsTopological {
    private int[] parent;
    private int s;
    private double[] dist;

    public ShortestPathsTopological(WeightedDigraph G, int s) {
        // TODO
        TopologicalWD topo = new TopologicalWD(G);
        topo.dfs(s);
        this.s = s;
        this.parent = new int[G.V()];
        this.dist = new double[G.V()];
        for (int i = 0; i < G.V(); i++){
            dist[i] = Double.MAX_VALUE;
        }
        dist[s] = 0;
        Stack<Integer> st = new Stack<>();
        topo.order().addAll(st);
        while(!topo.order().empty()){
            st.add(topo.order().pop());}
        for (Integer i : st){
            for (DirectedEdge ed : G.incident(i)){
                relax(ed);
            }
        }



    }

    public void relax(DirectedEdge e) {
        // TODO
        if (dist[e.to()] > (dist[e.from()] + e.weight())){ //e.either();
            dist[e.to()] = dist[e.from()] + e.weight();    //e.either();
            this.parent[e.to()] = e.from();

        }
    }

    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }
}

