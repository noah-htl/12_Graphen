package at.htlsaalfelden;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex<T> {
    private T data;
    private List<Edge> edges;

    public Vertex(T data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public List<Vertex<?>> getNeighbours() {
        List<Vertex<?>> neighbours = new ArrayList<>();
        for(Edge e : edges) {
            if(e.getV()[0] == this) {
                neighbours.add(e.getV()[1]);
            } else {
                neighbours.add(e.getV()[0]);
            }
        }

        return neighbours;
    }


    @Override
    public final boolean equals(Object o) {
        if (!( o instanceof Vertex vertex )) return false;

        for(Edge e : edges) {
            if(!vertex.getEdges().contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEdges());
    }
}
