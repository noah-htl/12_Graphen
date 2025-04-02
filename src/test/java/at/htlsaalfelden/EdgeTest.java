package at.htlsaalfelden;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @org.junit.jupiter.api.Test
    void getOther() {
        Vertex<Integer> v1 = new Vertex<>(1);
        Vertex<Integer> v2 = new Vertex<>(2);
        Edge<Integer> edge = new Edge<>(v1, v2, 99);

        assertSame(v1, edge.getOther(v2));
        assertSame(v2, edge.getOther(v1));
    }
}