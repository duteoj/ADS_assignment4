# Assignment 4: Graph Traversal and Representation System

## A. Project Overview

This project implements a graph data structure in Java and demonstrates two classic traversal algorithms — **Breadth-First Search (BFS)** and **Depth-First Search (DFS)** — on graphs of different sizes.

### What is a Graph?
A **graph** is a data structure consisting of:
- **Vertices (nodes)** — individual elements (e.g., cities, people, web pages)
- **Edges (connections)** — relationships between vertices (e.g., roads, friendships, links)

Graphs can be **directed** (edges have a direction) or **undirected** (edges go both ways). This project uses an **undirected** graph.

### BFS Overview
Breadth-First Search explores a graph **level by level**. Starting from a source vertex, it visits all immediate neighbors first, then their neighbors, and so on. It uses a **queue** (FIFO).

### DFS Overview
Depth-First Search explores a graph by going **as deep as possible** along each branch before backtracking. It uses a **stack** (LIFO) — either explicitly or via recursion.

---

## B. Class Descriptions

### `Vertex`
Represents a single node in the graph. Each vertex has a unique `int id` and a `toString()` method that prints `VX` (e.g., `V0`, `V5`).

### `Edge`
Represents a directed connection between a `source` vertex and a `destination` vertex. Provides getters and a `toString()` like `V0 -> V1`.

### `Graph`
The core class. Uses an **adjacency list** (`Map<Integer, List<Integer>>`) to store the graph structure.

**Adjacency List** — each vertex maps to a list of its neighbors:
```
V0 -> [V1, V2, V8, V9]
V1 -> [V0, V2, V3, V9]
...
```
This representation is memory-efficient for **sparse graphs** (O(V + E) space), compared to an adjacency matrix which always uses O(V²).

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
- Finding the **shortest path** in an unweighted graph
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

Graphs were built as rings with shortcuts: each vertex `i` is connected to `(i+1) % n` and `(i+2) % n`, making a realistic, connected, undirected graph.

### Execution Time Comparison Table

| Graph Size      | Vertices | Edges | BFS Time (ns) | DFS Time (ns) |
|-----------------|----------|-------|---------------|---------------|
| Small           | 10       | 20    | ~952,520      | ~681,293      |
| Medium          | 30       | 60    | ~313,126      | ~107,143      |
| Large           | 100      | 200   | ~209,548      | ~239,276      |

> Note: Times vary per run due to JVM warm-up effects.

### Observations
- **DFS is generally faster** on medium-sized graphs due to lower overhead (stack-based, cache-friendly traversal order).
- **BFS overhead** comes from the `Queue` operations and `visited` checks across wider levels.
- Both algorithms follow **O(V + E)** complexity — time grows with graph size, but slowly, since E ≈ 2V here.
- JVM warm-up effects explain some counter-intuitive results (e.g., the small graph appearing slower than medium).

---

## E. Screenshots

### Graph Structure Output (10 vertices)
```
Graph (Adjacency List):
  V0 -> [V1, V2, V8, V9]
  V1 -> [V0, V2, V3, V9]
  V2 -> [V0, V1, V3, V4]
  V3 -> [V1, V2, V4, V5]
  V4 -> [V2, V3, V5, V6]
  V5 -> [V3, V4, V6, V7]
  V6 -> [V4, V5, V7, V8]
  V7 -> [V5, V6, V8, V9]
  V8 -> [V6, V7, V9, V0]
  V9 -> [V7, V8, V0, V1]
```

### BFS Traversal Output
```
BFS order: [0, 1, 2, 8, 9, 3, 4, 6, 7, 5]
```
BFS visits vertex 0, then all its neighbors (1, 2, 8, 9), then their unvisited neighbors, etc.

### DFS Traversal Output
```
DFS order: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
```
DFS dives deep: 0 → 1 → 2 → 3 → ... following the chain as far as possible.

### Performance Results
```
╔══════════════════╦══════════════════╦══════════════════╗
║   Graph Size     ║   BFS Time (ns)  ║   DFS Time (ns)  ║
╠══════════════════╬══════════════════╬══════════════════╣
║ Small (10)       ║          952,520 ║          681,293 ║
║ Medium (30)      ║          313,126 ║          107,143 ║
║ Large (100)      ║          209,548 ║          239,276 ║
╚══════════════════╩══════════════════╩══════════════════╝
```

---

## F. Reflection

This assignment gave me a solid understanding of how graphs work internally. Implementing the adjacency list made it clear why this representation is preferred for sparse graphs — you only store what actually exists, unlike a matrix that allocates space for every possible connection. The difference between BFS and DFS became very visible in the traversal order: BFS produced a "ripple" effect from the start vertex, while DFS followed a single path to its end before exploring alternatives.

The main challenge was getting the iterative DFS right — especially handling the visited check after popping (not before pushing) to avoid issues with duplicate entries on the stack. Another challenge was understanding JVM warm-up: the small graph sometimes appeared slower than the medium one in nanosecond measurements, which is a known effect caused by JIT compilation not yet being applied on the first run. In a real benchmark, one would use a warm-up loop before measuring.

---
