package at.htlsaalfelden;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    Graph<Integer> graph;

    @BeforeEach
    void setUp() {
        graph = new Graph<>();
    }

    @Test
    void addVertex() {
        graph.addVertex(1);
        assertEquals(1, graph.getVertices().size());
        assertEquals(1, graph.getVertices().getFirst().getData());
    }

    @Test
    void connect() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.setOmnidirectional(false);
        graph.connect(1,2,99);

        assertEquals(1, graph.getEdges().size());
        assertEquals(99, graph.getEdges().getFirst().getCost());

        assertEquals(1, graph.getEdges().getFirst().getV1().getData());
        assertEquals(2, graph.getEdges().getFirst().getV2().getData());
    }

    @Test
    void connectOmnidirectional() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.setOmnidirectional(true);
        graph.connect(1,2,99);

        assertEquals(2, graph.getEdges().size());
        assertEquals(99, graph.getEdges().getFirst().getCost());
        assertEquals(99, graph.getEdges().get(1).getCost());

        assertEquals(1, graph.getEdges().getFirst().getV1().getData());
        assertEquals(2, graph.getEdges().getFirst().getV2().getData());

        assertEquals(2, graph.getEdges().get(1).getV1().getData());
        assertEquals(1, graph.getEdges().get(1).getV2().getData());
    }

    @Test
    void bfs() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);

        graph.connect(1, 2,0);
        graph.connect(1, 3,0);
        graph.connect(1, 4,0);

        graph.connect(2, 5,0);
        graph.connect(2, 6,0);

        graph.connect(3, 7,0);
        graph.connect(3, 8,0);

        graph.connect(4, 9,0);
        graph.connect(4, 10,0);

        StringBuilder sb = new StringBuilder();

        graph.bfs(integerVertex -> sb.append(integerVertex.getData()), 1);

        assertEquals("12345678910", sb.toString());
    }

    @Test
    void dfs() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);

        graph.connect(1, 2,0);
        graph.connect(1, 3,0);
        graph.connect(1, 4,0);

        graph.connect(2, 5,0);
        graph.connect(2, 6,0);

        graph.connect(3, 7,0);
        graph.connect(3, 8,0);

        graph.connect(4, 9,0);
        graph.connect(4, 10,0);

        StringBuilder sb = new StringBuilder();

        graph.dfs(integerVertex -> sb.append(integerVertex.getData()), 1);

        assertEquals("12563784910", sb.toString());
    }

    @Test
    void dijkstra() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);

        graph.connect(1, 2,100);
        graph.connect(1, 3,540);
        graph.connect(1, 4,365);

        graph.connect(2, 5,542);
        graph.connect(2, 6,520);

        graph.connect(3, 7,657);
        graph.connect(3, 8,126);

        graph.connect(4, 9,80);
        graph.connect(4, 10,905);
        graph.connect(1,10,1271);

        int START = 1;
        int END = 10;

        assertEquals("1->4->10", graph.pathToString(graph.aStar(START, END), graph.getVertex(START)));
    }

    @Test
    void dijkstraNoPath() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);

        graph.connect(1, 2,100);
        graph.connect(1, 3,540);
        graph.connect(1, 4,365);

        graph.connect(2, 5,542);
        graph.connect(2, 6,520);

        graph.connect(3, 7,657);
        graph.connect(3, 8,126);

        graph.connect(4, 9,80);
        graph.connect(4, 10,905);
        graph.connect(1,10,1271);

        int START = 10;
        int END = 1;

        assertEquals("No path found!", graph.pathToString(graph.aStar(START, END), graph.getVertex(START)));
    }

    @Test
    void dijkstraOmnidirectional() {
        graph.setOmnidirectional(true);

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);

        graph.connect(1, 2,100);
        graph.connect(1, 3,540);
        graph.connect(1, 4,365);

        graph.connect(2, 5,542);
        graph.connect(2, 6,520);

        graph.connect(3, 7,657);
        graph.connect(3, 8,126);

        graph.connect(4, 9,80);
        graph.connect(4, 10,905);
        graph.connect(1,10,1271);

        int START = 10;
        int END = 1;

        assertEquals("10->4->1", graph.pathToString(graph.aStar(START, END), graph.getVertex(START)));
    }
}