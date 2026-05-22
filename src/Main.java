public class Main {
    public static void main(String[] args) {

        Experiment experiment = new Experiment();
        experiment.runMultipleTests();

        System.out.println("\n--- Performance Comparison Table ---");
        experiment.printResults();

        WeightedGraph wg = new WeightedGraph();
        for (int i = 0; i < 6; i++) {
            wg.addVertex(new Vertex(i));
        }
        wg.addEdge(0, 1, 2);
        wg.addEdge(1, 2, 3);
        wg.addEdge(0, 3, 6);
        wg.addEdge(1, 4, 8);
        wg.addEdge(2, 5, 7);
        wg.addEdge(3, 4, 1);
        wg.addEdge(4, 5, 5);

        wg.printGraph();
        wg.dijkstra(0);

    }
}
