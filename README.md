# Assignment 4: Graph Traversal and Representation System

## A. Project Overview

To completed assignment I used a graph structure. Grapth is a data structure consisting of vertices and edges. Vertices individual elements, while edges relathionship between vertices.

Breadth-First Search explores a graph level by level. Starting from a source vertex, it visits all immediate neighbors first, then their neighbors, and so on. Depth-First Search explores a graph by going as deep as possible along each branch before backtracking.

---

## B. Class Descriptions

### `Vertex`
Represents a single node in the graph. Each vertex has a unique `int id` and a `toString()` method that prints `VX`

### `Edge`
Represents a connection between a `source` vertex and a `destination` vertex. Provides getters and a `toString()` like `V0 -> V1`.

### `Graph`
The core class. Uses an adjacency list (`Map<Integer, List<Integer>>`) to store the graph structure.

Adjacency List — each vertex maps to a list of its neighbors:
V0 -> [V1, V2, V8, V9]
V1 -> [V0, V2, V3, V9]

This representation is memory-efficient for sparse graphs (O(V + E) space), compared to an adjacency matrix which always uses O(V²).

### `Experiment`
Handles graph construction, runs traversals on 3 different graph sizes, measures execution time using `System.nanoTime()`, and prints a comparison table.

### `Main`
Entry point. Creates an `Experiment` object and triggers all tests.

---

## C. Algorithm Descriptions

### BFS — Breadth-First Search

**Step-by-step:**
1. Mark the start vertex as visited; add it to a queue.
2. While the queue is not empty:
   - Dequeue a vertex.
   - Add it to the result list.
   - Enqueue all unvisited neighbors and mark them visited.

**Use cases:**
- Finding the shortest path in an unweighted graph
- Level-order traversal
- Web crawlers, social network friend suggestions

**Time complexity:** O(V + E) — every vertex and edge is processed once.

---

### DFS — Depth-First Search

**Step-by-step:**
1. Push the start vertex onto a stack.
2. While the stack is not empty:
   - Pop a vertex; if not visited, mark it and add to result.
   - Push all unvisited neighbors onto the stack (in reverse order).

**Use cases:**
- Detecting **cycles** in a graph
- Topological sorting
- Solving mazes / puzzles
- Checking connectivity

**Time complexity:** O(V + E) — every vertex and edge is processed once.

---

## D. Experimental Results

### Execution Time Comparison Table

| Graph Size      | Vertices | Edges | BFS Time (ns) | DFS Time (ns) |
|-----------------|----------|-------|---------------|---------------|
| Small           | 10       | 20    | 952,520       | 681,293       |
| Medium          | 30       | 60    | 313,126       | 107,143       |
| Large           | 100      | 200   | 209,548       | 239,276       |

### Observations
- DFS is generally faster on medium-sized graphs due to lower overhead (stack-based, cache-friendly traversal order).
- BFS overhead*comes from the Queue operations and visited checks across wider levels.
- Both algorithms follow O(V + E) complexity — time grows with graph size, but slowly, since E ≈ 2V here.

---

## E. Screenshots

<img width="455" height="472" alt="Снимок экрана 2026-05-18 211158" src="https://github.com/user-attachments/assets/08d28ab5-92af-4a6a-9d4a-90887596180b" />
<img width="428" height="210" alt="Снимок экрана 2026-05-18 211206" src="https://github.com/user-attachments/assets/2b831c31-e212-4586-97f8-b7a50620644f" />
<img width="624" height="443" alt="Снимок экрана 2026-05-18 211217" src="https://github.com/user-attachments/assets/c3386f72-4994-4620-887c-a2763ec2b610" />






## F. Reflection
**How does graph size affect BFS and DFS performance?**
-Performance time increasing linearly due to the O(V+E) time complexity

**Which traversal is faster in your experiments?**
-DFS shows faster execution in the medium and small graphs. In the context of the large graph, the BFS has managed to perform faster execution

**Do results match the expected complexity O(V + E)?**
-Yes, The measured times grow proportionally with graph size, not exponentially, which confirms linear O(V + E) complexity

**How does graph structure affect traversal order?**
-On the 10-vertex ring graph, BFS produced [0, 1, 2, 8, 9, 3, 4, 6, 7, 5] — spreading outward by distance from V0. DFS produced [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] — following the ring chain in order. A different graph would produce completely different orders for the same algorithms

**When is BFS preferred over DFS?**
-When you need level by level processing or when you need to fing shortest path between 2 vertices

**What are the limitations of DFS?**
-Recursive DFS can cause a stack overflow on very deep or large graphs. Also it cannot find minimum-cost paths in weighted graphs on its own


This work helped helped me to learn Graph structure and `Depth-First Serch(DFS)` and `Breadth-first search(BFS)` algorithms. The main difference between DFS and BFS that DFS is use `as deep as possible` principle, while BFS follows `level-by-level`. The main challenge about this project was implementind new data structure adjacencyList, because it was new to me and caused some problem during coding phase 
