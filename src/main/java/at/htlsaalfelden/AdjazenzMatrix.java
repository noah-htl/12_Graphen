package at.htlsaalfelden;

import java.util.HashMap;
import java.util.Map;

public class AdjazenzMatrix {
    public static <T> void print(Graph<T> graph) {
        int size = graph.getVertices().size();
        String[][] printout = new String[size+1][size+1];

        Map<T, Integer> map = new HashMap<>();

        int i = 1;
        printout[0][0] = "#";
        for(Vertex<T> v : graph.getVertices()) {
            printout[i][0] = v.getData().toString();
            printout[0][i] = v.getData().toString();

            map.put(v.getData(), i);

            i++;
        }

        for(Edge<T> e : graph.getEdges()) {
            int a = map.get(e.getV1().getData());
            int b = map.get(e.getV2().getData());

            printout[a][b] = e.getCost() + "";
        }

        int width = getMinDimensions(printout);
        padToWidth(printout, width);

        for (int j = 0; j < printout.length; j++) {
            for (int k = 0; k < printout[j].length; k++) {
                System.out.print(printout[j][k]);
                if(k < printout[j].length - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }

    private static int getMinDimensions(String[][] printout) {
        int dimension = 0;
        for (int i = 0; i < printout.length; i++) {
            for (int j = 0; j < printout[i].length; j++) {
                if(printout[i][j] != null && printout[i][j].length() > dimension) {
                    dimension = printout[i][j].length();
                }
            }
        }

        return dimension;
    }

    private static void padToWidth(String[][] printout, int target) {
        for (int i = 0; i < printout.length; i++) {
            for (int j = 0; j < printout[i].length; j++) {
                if(printout[i][j] == null) {
                    printout[i][j] = " ".repeat(target);
                }
                if(printout[i][j].length() < target) {
                    printout[i][j] = " ".repeat(Math.ceilDiv(target - printout[i][j].length(), 2)) + printout[i][j] + " ".repeat(Math.floorDiv(target - printout[i][j].length(), 2));
                }
            }
        }
    }
}
