# Pathfinding Algorithms: Best-First Search vs A* Search

This project demonstrates two classic informed search algorithms — **Greedy Best-First Search** and **A* Search** — applied to a grid-based pathfinding problem.  
The grid is represented as a 2D array where `0` indicates a free cell and `1` indicates an obstacle.  
The start state is always at the top-left corner `(0,0)` and the goal is at the bottom-right corner `(n-1,n-1)`.

---

## 🚀 Algorithms Implemented

### 1. Greedy Best-First Search
- Uses **only the heuristic** function `h(n)` (Manhattan distance to the goal).
- Always expands the node that appears closest to the goal.
- Very fast in practice, but **does not guarantee the optimal path**.
- Can get stuck exploring misleading paths if the heuristic is not well-informed.

### 2. A* Search
- Uses both path cost and heuristic: `f(n) = g(n) + h(n)`.
- `g(n)` = cost from start to current node (steps taken).  
- `h(n)` = estimated cost from current node to goal (Manhattan distance).  
- Balances exploration and optimality, ensuring **shortest path is always found** if the heuristic is admissible.

---

## 📊 Comparison of Results and Performance

Best-First Search often finds a path more quickly because it only follows the heuristic, which makes it computationally lighter and faster. However, the path it finds may not be optimal, and in some cases it might even fail to find a path if misled by obstacles.  

A* Search, on the other hand, explores more states since it considers both actual cost and heuristic. This makes it slower and more memory-intensive than Best-First, but it guarantees finding the **optimal path** whenever one exists. In short:  
- **Best-First Search** → Faster, less reliable.  
- **A\* Search** → Slower, guaranteed optimal solution.  

---
