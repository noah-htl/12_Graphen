package at.htlsaalfelden;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();
        graph.setOmnidirectional(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");


        graph.connect("A", "B", 0);
        graph.connect("A", "D", 0);
        graph.connect("B", "C", 0);


        AdjazenzMatrix.print(graph);

        System.out.println();

        //graph.bfs(integerVertex -> System.err.println(integerVertex.getData()), "A");
        List<Edge<String>> edges = graph.aStar("C", "A");
        System.out.println(edges);
    }
}