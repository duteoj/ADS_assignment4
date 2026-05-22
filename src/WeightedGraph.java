import java.util.*;
public class WeightedGraph {
    private Map<Integer, List<int[]>> adjacencyList;
    // vertex objects by id
    private Map<Integer, Vertex> vertices;

    public WeightedGraph() {
        adjacencyList = new LinkedHashMap<>();
        vertices = new LinkedHashMap<>();
    }
    public void addVertex(Vertex v) {
        vertices.put(v.getId(), v);
        adjacencyList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    public void addEdge(int from, int to, int weight) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            throw new IllegalArgumentException("Both vertices must exist before adding an edge.");
        }
        adjacencyList.get(from).add(new int[]{to, weight});
        adjacencyList.get(to).add(new int[]{from, weight});
    }
    public void printGraph() {
        System.out.println("Weighted Graph (Adjacency List):");
        for (Map.Entry<Integer, List<int[]>> entry : adjacencyList.entrySet()) {
            Vertex v = vertices.get(entry.getKey());
            System.out.print("  " + v + " -> ");
            List<String> neighbors = new ArrayList<>();
            for (int[] pair : entry.getValue()) {
                neighbors.add(vertices.get(pair[0]) + "(w=" + pair[1] + ")");
            }
            System.out.println(neighbors);
        }
    }
    public void dijkstra(int start) {
        int n = vertices.size();
        Integer[] ids = vertices.keySet().toArray(new Integer[0]);
        Map<Integer, Integer> idToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            idToIndex.put(ids[i], i);
        }

        int startIndex = idToIndex.get(start);

        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        int[] prev = new int[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[startIndex] = 0;

        for (int iter = 0; iter < n; iter++) {
            int u = -1;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && (u == -1 || dist[i] < dist[u])) {
                    u = i;
                }
            }

            if (dist[u] == Integer.MAX_VALUE) break;
            visited[u] = true;
            int uId = ids[u];
            for (int[] pair : adjacencyList.get(uId)) {
                int neighborId = pair[0];
                int weight = pair[1];
                int v = idToIndex.get(neighborId);

                if (!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                }
            }
        }

        System.out.println("\nDijkstra's Shortest Paths from V" + start + ":");
        System.out.println("  " + String.format("%-10s %-12s %s", "Vertex", "Distance", "Path"));
        System.out.println("  " + "-".repeat(40));
        for (int i = 0; i < n; i++) {
            String distance = (dist[i] == Integer.MAX_VALUE) ? "unreachable" : String.valueOf(dist[i]);
            String path = buildPath(i, prev, ids);
            System.out.println("  " + String.format("%-10s %-12s %s", "V" + ids[i], distance, path));
        }
    }

    private String buildPath(int targetIndex, int[] prev, Integer[] ids) {
        List<String> path = new ArrayList<>();
        int current = targetIndex;
        while (current != -1) {
            path.add("V" + ids[current]);
            current = prev[current];
        }
        Collections.reverse(path);
        return String.join(" -> ", path);
    }
}
