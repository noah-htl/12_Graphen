package at.htlsaalfelden;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Graph<T> {
    private List<Vertex<T>> vertices;
    private List<Edge> edges;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
    }

    public void addVertex(T vertex) {
        addVertex(new Vertex<>(vertex));
    }

    public void connect(T v1, T v2, int cost) {
        Vertex<T> vertex1 = getVertex(v1);
        Vertex<T> vertex2 = getVertex(v2);

        connect(vertex1, vertex2, cost);
    }

    public void connect(Vertex<?> v1, Vertex<?> v2, int cost) {
        Edge e = new Edge(v1, v2, cost);
        if(edges.contains(e)) {
            return;
        }

        edges.add(e);
        v1.addEdge(e);
        v2.addEdge(e);
    }

    public List<Vertex<T>> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Vertex<T> getVertex(T id) {
        var r = vertices.stream().filter(vertex -> Objects.equals(vertex.getData(), id)).findFirst();
        return r.orElse(null);
    }
}
