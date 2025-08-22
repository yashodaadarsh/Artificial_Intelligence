# Pathfinding Algorithms: Best-First Search vs A* Search

This project demonstrates two informed search strategies for solving grid-based pathfinding problems: **Best-First Search (Greedy)** and **A* Search**.

---

##  Best-First Search
- **Evaluation Function**: `f(n) = h(n)`  
- **How it works**: Chooses the node that appears closest to the goal, using only the heuristic.  
- **Pros**:  
  - Can be fast with a good heuristic.  
  - Simple to implement.  
- **Cons**:  
  - Ignores cost so far (`g(n)`), which can lead to detours.  
  - **Not guaranteed** to find the optimal (shortest) path.  

---

##  A* Search
- **Evaluation Function**: `f(n) = g(n) + h(n)`  
- **How it works**: Balances the actual path cost from the start (`g(n)`) and the estimated cost to the goal (`h(n)`).  
- **Pros**:  
  - Finds the **optimal path** if the heuristic is admissible.  
  - More reliable than Best-First.  
- **Cons**:  
  - May expand more nodes (slower in some cases).  
  - Slightly more complex to implement.  

---

##  Comparison

| Feature                | Best-First Search            | A* Search                        |
|-------------------------|------------------------------|----------------------------------|
| **Evaluation Function** | `f(n) = h(n)`               | `f(n) = g(n) + h(n)`             |
| **Optimality**          | Not guaranteed              | Guaranteed (with admissible `h`) |
| **Speed**               | Usually faster, less accurate | Slower, more accurate            |
| **Exploration**         | Focuses on goal direction   | Balances path cost + heuristic   |
| **Use Case**            | Quick approximate solution  | Optimal pathfinding              |

---

##  Short Discussion

Best-First Search is *greedy* in nature. It looks promising in the short term because it only follows the heuristic, often reaching the goal quickly. However, it can fail to find the shortest path or even get stuck in poor paths if the heuristic is misleading.  

A* Search, on the other hand, combines the **actual cost so far** and the **estimated cost to the goal**. This makes it more thorough and guarantees the shortest path if the heuristic is admissible. Although A* may expand more nodes and take more time, it provides **better and optimal solutions**, making it more reliable for real-world applications like routing, robotics, and AI navigation.

---
