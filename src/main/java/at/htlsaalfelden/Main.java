package at.htlsaalfelden;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.connect(1,2, 0);
        graph.connect(2,3, 0);
        graph.connect(4,1, 0);
        graph.connect(1,4,1);

        System.out.println();
    }
}