import java.util.List;
public class Experiment {
    // results storage
    private String[] graphLabels = {"Small (10)", "Medium (30)", "Large (100)"};
    private long[] bfsTimes = new long[3];
    private long[] dfsTimes = new long[3];

    public long[] runTraversals(Graph g, boolean printOrder) {
        int startVertex = 0;

        long startBfs = System.nanoTime();
        List<Integer> bfsOrder = g.bfs(startVertex);
        long endBfs = System.nanoTime();
        long bfsTime = endBfs - startBfs;

        long startDfs = System.nanoTime();
        List<Integer> dfsOrder = g.dfs(startVertex);
        long endDfs = System.nanoTime();
        long dfsTime = endDfs - startDfs;

        if (printOrder) {
            System.out.println("  BFS order: " + bfsOrder);
            System.out.println("  DFS order: " + dfsOrder);
        }

        System.out.printf("  BFS time: %,d ns%n", bfsTime);
        System.out.printf("  DFS time: %,d ns%n", dfsTime);

        return new long[]{bfsTime, dfsTime};
    }

    public void runMultipleTests() {
        int[] sizes = {10, 30, 100};

        for (int i = 0; i < sizes.length; i++) {
            int n = sizes[i];
            System.out.println("\n=== " + graphLabels[i] + " graph (" + n + " vertices) ===");

            Graph g = buildGraph(n);

            if (n == 10) {
                g.printGraph();
                System.out.println();
            }

            boolean printOrder = (n == 10);
            long[] times = runTraversals(g, printOrder);
            bfsTimes[i] = times[0];
            dfsTimes[i] = times[1];
        }
    }

    public void printResults() {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("=   Graph Size     =   BFS Time (ns)  =   DFS Time (ns)  =");
        System.out.println("=".repeat(60));
        for (int i = 0; i < graphLabels.length; i++) {
            System.out.printf("= %-16s = %16s = %16s =%n",
                    graphLabels[i],
                    String.format("%,d", bfsTimes[i]),
                    String.format("%,d", dfsTimes[i]));
        }
        System.out.println("=".repeat(60));

        System.out.println("\nObservations:");
        for (int i = 0; i < 3; i++) {
            String faster = bfsTimes[i] <= dfsTimes[i] ? "BFS" : "DFS";
            System.out.printf("  [%s] Faster algorithm: %s%n", graphLabels[i], faster);
        }
    }

    public static Graph buildGraph(int n) {
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }
        for (int i = 0; i < n; i++) {
            g.addEdge(i, (i + 1) % n);
            if (n > 5) {
                g.addEdge(i, (i + 2) % n);
            }
        }
        return g;
    }
}
