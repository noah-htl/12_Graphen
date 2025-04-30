package at.htlsaalfelden;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Graph<T> {
    private List<Vertex<T>> vertices;
    private List<Edge<T>> edges;

    private boolean omnidirectional = false;

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

        if(omnidirectional) {
            connect(vertex2, vertex1, cost);
        }
    }

    public void connect(Vertex<T> v1, Vertex<T> v2, int cost) {
        Edge<T> e = new Edge<T>(v1, v2, cost);
        if(edges.contains(e)) {
            return;
        }

        edges.add(e);
        v1.addEdge(e);
        //v2.addEdge(e);
    }

    public List<Vertex<T>> getVertices() {
        return vertices;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public Vertex<T> getVertex(T id) {
        var r = vertices.stream().filter(vertex -> Objects.equals(vertex.getData(), id)).findFirst();
        return r.orElse(null);
    }

    public boolean isOmnidirectional() {
        return omnidirectional;
    }

    public void setOmnidirectional(boolean omnidirectional) {
        this.omnidirectional = omnidirectional;
    }

    public void bfs(Consumer<Vertex<T>> c, T tStart) {
        SearchContext context = new SearchContext(this, c);
        Vertex<T> vStart = getVertex(tStart);

        context.consume(vStart);
        vStart.bfs(context);
    }

    public void dfs(Consumer<Vertex<T>> c, T tStart) {
        SearchContext context = new SearchContext(this, c);

        Vertex<T> vStart = getVertex(tStart);

        vStart.dfs(context);
    }

    public List<Edge<T>> aStar(T tStart, T tEnd) {
        List<Vertex<T>> closed = new LinkedList<>();
        List<Vertex<T>> open = new ArrayList<>(getVertices());

        Map<Vertex<T>, AStarNodeContext> contextMap = new HashMap<>();

        Vertex<T> vStart = getVertex(tStart);
        Vertex<T> vEnd = getVertex(tEnd);

        Vertex<T> current;

        contextMap.put(vStart, new AStarNodeContext(vStart, null, 0));

        while (!open.isEmpty()) {
            open.sort(Comparator.comparingInt(o -> contextMap.containsKey(o) ? contextMap.get(o).distance : Integer.MAX_VALUE));
            current = open.getFirst();
            closed.add(current);
            open.remove(current);
            if (current == vEnd) {
                /*if(!contextMap.containsKey(current)) {
                    return null;
                }*/
                return createPathList(contextMap, vEnd);
            }

            for (Edge<T> edge : current.getEdges()) {
                if(closed.contains(edge.getOther(current)) || !contextMap.containsKey(current)) {
                    continue;
                }

                Integer newDistance = contextMap.get(current).distance + edge.getCost();

                contextMap.putIfAbsent(edge.getOther(current), new AStarNodeContext(current, edge, newDistance));
                if (contextMap.get(edge.getOther(current)).distance > newDistance) {
                    contextMap.get(edge.getOther(current)).distance = newDistance;
                    contextMap.get(edge.getOther(current)).edgeToParent = edge;
                }
            }
        }

        return null;
    }

    private List<Edge<T>> createPathList(Map<Vertex<T>, AStarNodeContext> contextMap, Vertex<T> vEnd) {
        List<Edge<T>> edges = new LinkedList<>();

        if(!contextMap.containsKey(vEnd)) {
            return edges;
        }

        Vertex<T> current = vEnd;
        while (true) {
            if(current == null) {
                break;
            }
            AStarNodeContext context = contextMap.get(current);
            if(context.edgeToParent == null) {
                break;
            }
            edges.add(context.edgeToParent);
            current = context.edgeToParent.getOther(current);
        }

        return edges.reversed();
    }

    public String pathToString(List<Edge<T>> path, Vertex<T> vStart) {
        StringBuilder sb = new StringBuilder();

        if(path.isEmpty()) {
            return "No path found!";
        }

        Vertex<T> before = vStart;

        sb.append(before.getData());

        for(Edge<T> edge : path) {
            Vertex<T> v1 = edge.getOther(before);
            sb.append("->").append(v1.getData());
            before = v1;
        }

        return sb.toString();
    }

    private class AStarNodeContext {
        private final Vertex<T> vertex;
        private Edge<T> edgeToParent;
        private Integer distance;

        public AStarNodeContext(Vertex<T> vertex, Edge<T> edgeToParent) {
            this(vertex, edgeToParent, Integer.MAX_VALUE);
        }

        public AStarNodeContext(Vertex<T> vertex, Edge<T> edgeToParent, Integer distance) {
            this.vertex = vertex;
            this.edgeToParent = edgeToParent;
            this.distance = distance;
        }
    }

    public class SearchContext {
        public final List<Vertex<T>> visitedVertices = new ArrayList<>();
        private final Graph<T> graph;
        private final Consumer<Vertex<T>> consumer;

        private SearchContext(Graph<T> graph, Consumer<Vertex<T>> consumer) {
            this.graph = graph;
            this.consumer = consumer;
        }

        public List<Vertex<T>> getOpenVertices() {
            return graph.getVertices().stream().filter(tVertex -> !visitedVertices.contains(tVertex)).toList();
        }

        public void consume(Vertex<T> vertex) {
            consumer.accept(vertex);
        }
    }
}
