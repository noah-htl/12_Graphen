package at.htlsaalfelden;

public class Edge<T> {
    private int cost;

    private Vertex<T> v1;
    private Vertex<T> v2;

    public Edge(Vertex<T> v1, Vertex<T> v2, int cost) {
        this.v1 = v1;
        this.v2 = v2;
        this.cost = cost;
    }

    public Vertex<T> getV1() {
        return v1;
    }
    public Vertex<T> getV2() {
        return v2;
    }

    public int getCost() {
        return cost;
    }

    public Vertex<T> getOther(Vertex<T> c) {
        if(c == v1) {
            return v2;
        } else {
            return v1;
        }
    }

    @Override
    public String toString() {
        return v1.getData() + "->" + v2.getData();
    }
}
