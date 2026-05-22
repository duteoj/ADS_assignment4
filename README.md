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


# Bonus Task: Dijkstra's Shortest Path Algorithm

---

## Overview

Dijkstra's algorithm finds the **shortest path from one starting vertex to all other vertices** in a weighted graph. "Shortest" means the path with the smallest total edge weight.

**Real-world examples:**
- GPS navigation (shortest driving route)
- Network routing (lowest latency path)
- Game pathfinding (cheapest movement cost)

---

## Changes to Existing Code

### `Edge.java` — added `weight` field

The `Edge` class was extended with an integer `weight` field and a new constructor:

```java
public Edge(Vertex source, Vertex destination, int weight) {
    this.source = source;
    this.destination = destination;
    this.weight = weight;
}
```

The original no-weight constructor still exists (defaults to weight = 1) so the rest of the project continues to work unchanged.

### `WeightedGraph.java` — new class

A new class was created instead of modifying `Graph.java`, to keep the original BFS/DFS code clean.

The adjacency list stores `int[]` pairs instead of plain integers:

```java
Map<Integer, List<int[]>> adjacencyList;
// each int[] = { neighborId, weight }
```

Example: `V0 -> [[1, 2], [3, 6]]` means V0 connects to V1 (weight 2) and V3 (weight 6).

---

## Algorithm: Step-by-Step

Given this graph:

```
      2       3
 V0 ----- V1 ----- V2
 |          |         |
6|         8|        7|
 |          |         |
 V3 ----- V4 ----- V5
      1       5
```

**Step 1 — Initialize:**
- dist[V0] = 0 (start here)
- dist[all others] = ∞
- visited = none

**Step 2 — Pick unvisited vertex with smallest distance → V0 (dist=0):**
- Relax neighbors: dist[V1] = 0+2 = 2, dist[V3] = 0+6 = 6
- Mark V0 visited

**Step 3 — Pick next → V1 (dist=2):**
- Relax neighbors: dist[V2] = 2+3 = 5, dist[V4] = 2+8 = 10
- dist[V3] stays 6 (2+8 > 6)
- Mark V1 visited

**Step 4 — Pick next → V2 (dist=5):**
- dist[V5] = 5+7 = 12
- Mark V2 visited

**Step 5 — Pick next → V3 (dist=6):**
- dist[V4] = min(10, 6+1) = **7** ← updated!
- Mark V3 visited

**Step 6 — Pick next → V4 (dist=7):**
- dist[V5] = min(12, 7+5) = 12 (no change)
- Mark V4 visited

**Step 7 — Pick next → V5 (dist=12):**
- No unvisited neighbors
- Mark V5 visited — done!

---

## Output

```
Weighted Graph (Adjacency List):
  V0 -> [V1(w=2), V3(w=6)]
  V1 -> [V0(w=2), V2(w=3), V4(w=8)]
  V2 -> [V1(w=3), V5(w=7)]
  V3 -> [V0(w=6), V4(w=1)]
  V4 -> [V1(w=8), V3(w=1), V5(w=5)]
  V5 -> [V2(w=7), V4(w=5)]

Dijkstra's Shortest Paths from V0:
  Vertex     Distance     Path
  ----------------------------------------
  V0         0            V0
  V1         2            V0 -> V1
  V2         5            V0 -> V1 -> V2
  V3         6            V0 -> V3
  V4         7            V0 -> V3 -> V4
  V5         12           V0 -> V1 -> V2 -> V5
```

Note that V4 is reached via V3 (distance 7 = 6+1), **not** via V1 (distance 10 = 2+8). This shows Dijkstra correctly choosing the cheaper path even when it goes through more vertices.

---

## Time Complexity

This implementation uses **simple arrays and loops** (no priority queue), as allowed by the task requirements.

| Step | Cost |
|------|------|
| Finding minimum distance vertex | O(V) per iteration |
| Total iterations | V |
| Relaxing edges | O(E) total |
| **Overall** | **O(V² + E) = O(V²)** |

A priority-queue version would run in O((V + E) log V), which is faster for sparse graphs but more complex to implement.

---

## Why Dijkstra Does Not Work with Negative Weights

Dijkstra assumes that once a vertex is marked visited, its distance is final. With negative weights, a later path could give a shorter distance to an already-visited vertex — breaking this assumption. For graphs with negative weights, **Bellman-Ford algorithm** should be used instead.

---
