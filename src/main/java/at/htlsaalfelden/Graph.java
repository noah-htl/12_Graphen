package at.htlsaalfelden;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addVertex(int vertex) {
        addVertex(new Vertex(vertex));
    }

    public void connect(int v1, int v2, int cost) {
        Vertex vertex1 = getVertex(v1);
        Vertex vertex2 = getVertex(v2);

        connect(vertex1, vertex2, cost);
    }

    public void connect(Vertex v1, Vertex v2, int cost) {
        Edge e = new Edge(v1, v2, cost);
        if(edges.contains(e)) {
            return;
        }

        edges.add(e);
        v1.addEdge(e);
        v2.addEdge(e);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Vertex getVertex(int id) {
        var r = vertices.stream().filter(vertex -> vertex.getData() == id).findFirst();
        return r.orElse(null);
    }
}
