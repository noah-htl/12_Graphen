package at.htlsaalfelden;

public class Edge {
    private int cost;

    private Vertex[] v;

    public Edge(Vertex v1, Vertex v2, int cost) {
        this.v = new Vertex[] {v1, v2};
        this.cost = cost;
    }

    public Vertex[] getV() {
        return v;
    }

    public int getCost() {
        return cost;
    }
}
