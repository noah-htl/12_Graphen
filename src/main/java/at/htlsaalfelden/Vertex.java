package at.htlsaalfelden;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex<T> {
    private T data;
    private List<Edge<T>> edges;

    public Vertex(T data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public void addEdge(Edge<T> edge) {
        this.edges.add(edge);
    }

    public List<Vertex<T>> getNeighbours() {
        List<Vertex<T>> neighbours = new ArrayList<>();
        for(Edge<T> e : edges) {
            if(e.getV1() == this) {
                neighbours.add(e.getV2());
            } else {
                neighbours.add(e.getV1());
            }
        }

        return neighbours;
    }

    public void bfs(Graph<T>.SearchContext context) {
        context.visitedVertices.add(this);

        List<Vertex<T>> neighbours = getNeighbours();

        for (Vertex<T> vertex : neighbours) {
            if(context.visitedVertices.contains(vertex)) {
                continue;
            }
            context.consume(vertex);
        }
        for (Vertex<T> vertex : neighbours) {
            if(context.visitedVertices.contains(vertex)) {
                continue;
            }
            vertex.bfs(context);
        }
    }

    public void dfs(Graph<T>.SearchContext context) {
        context.consume(this);
        context.visitedVertices.add(this);

        for (Vertex<T> vertex : getNeighbours()) {
            if(context.visitedVertices.contains(vertex)) {
                continue;
            }
            vertex.dfs(context);
        }
    }


    @Override
    public final boolean equals(Object o) {
        if (!( o instanceof Vertex vertex )) return false;


        return Objects.equals(vertex.data, data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEdges());
    }
}
