import java.util.*;

class State {
    private int row;
    private int col;
    private int[][] grid;
    private int n;

    public State(int row, int col, int[][] grid) {
        this.row = row;
        this.col = col;
        this.grid = grid;
        this.n = grid.length;
    }

    public boolean goalTest() {
        return row == n - 1 && col == n - 1;
    }

    public List<State> moveGen() {
        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
        };

        List<State> children = new ArrayList<>();
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                if (grid[newRow][newCol] == 0) {
                    children.add(new State(newRow, newCol, grid));
                }
            }
        }
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State other = (State) o;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public int[][] getGrid() { return grid; }
}

class Search {

    // h(N) = Manhattan distance from current to goal
    public int calculateHValue(State current) {
        int n = current.getGrid().length;
        int goalRow = n - 1;
        int goalCol = n - 1;
        return Math.abs(goalRow - current.getRow()) + Math.abs(goalCol - current.getCol());
    }

    List<State> reConstructPath(State goal, Map<State, State> parentMap) {
        List<State> path = new ArrayList<>();
        State current = goal;

        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    private void propagateImprovement(
            State m,
            Map<State, Integer> g,
            Map<State, Integer> f,
            Map<State, State> parent,
            Set<State> closed
    ) {
        for (State x : m.moveGen()) {
            int tentativeG = g.get(m) + 1; // g(M) + k(M,X)

            if (tentativeG < g.getOrDefault(x, Integer.MAX_VALUE)) {
                parent.put(x, m);
                g.put(x, tentativeG);
                f.put(x, tentativeG + calculateHValue(x));

                if (closed.contains(x)) {
                    propagateImprovement(x, g, f, parent, closed);
                }
            }
        }
    }

    public List<State> AStarSearch(State start) {

        int row = start.getRow();
        int col = start.getCol();
        int[][] grid = start.getGrid();
        if( grid[row][col] == 1 ) return Collections.emptyList();

        Map<State, Integer> g = new HashMap<>();
        Map<State, Integer> f = new HashMap<>();
        Map<State, State> parent = new HashMap<>();

        parent.put(start, null);
        g.put(start, 0); // g(start) = 0
        f.put(start, g.get(start) + calculateHValue(start));

        PriorityQueue<State> open = new PriorityQueue<>(
            Comparator.comparingInt(s -> f.getOrDefault(s, Integer.MAX_VALUE))
        );

        open.add(start);

        Set<State> closed = new HashSet<>();

        while (!open.isEmpty()) {
            State n = open.poll();
            closed.add(n);

            if (n.goalTest()) {
                return reConstructPath(n, parent);
            }

            for (State m : n.moveGen()) {
                int tentativeG = g.get(n) + 1; // g(N) + k(N,M)

                if (tentativeG < g.getOrDefault(m, Integer.MAX_VALUE)) {
                    parent.put(m, n);
                    g.put(m, tentativeG);
                    f.put(m, tentativeG + calculateHValue(m));

                    if (open.contains(m)) {
                        continue;
                    }
                    if (closed.contains(m)) {
                        propagateImprovement(m, g, f, parent, closed);
                    } else {
                        open.add(m);
                    }
                }
            }
        }

        return Collections.emptyList();
    }
}

public class AStarSearchDemo {
    public static void main(String[] args) {
        // TestCase1
        int[][] grid1 = {
            {0, 1},
            {1, 0},
        };
        State start1 = new State(0, 0, grid1);
        Search search1 = new Search();
        List<State> path1 = search1.AStarSearch(start1);
        if (path1.isEmpty()) {
            System.out.println("-1 (No path1 exists)");
        } else {
            System.out.println("Path found for TestCase1: " + path1);
            System.out.println("Path length: " + path1.size());
        }
        System.out.println();

        // TestCase2
        int[][] grid2 = {
            {0, 0, 0},
            {1, 1, 0},
            {1, 1, 0}
        };
        State start2 = new State(0, 0, grid2);
        Search search2 = new Search();
        List<State> path2 = search2.AStarSearch(start2);
        if (path1.isEmpty()) {
            System.out.println("-1 (No path2 exists)");
        } else {
            System.out.println("Path found for TestCase2: " + path2);
            System.out.println("Path length: " + path2.size());
        }
        System.out.println();

        // TestCase3
        int[][] grid3 = {
            {1, 0, 0},
            {1, 1, 0},
            {1, 1, 0}
        };
        State start3 = new State(0, 0, grid3);
        Search search3 = new Search();
        List<State> path3 = search3.AStarSearch(start3);
        if (path1.isEmpty()) {
            System.out.println("-1 (No path3 exists)");
        } else {
            System.out.println("Path found for TestCase3: " + path3);
            System.out.println("Path length: " + path3.size());
        }
        System.out.println();

    }
}


/*
Output:-

Path found for TestCase1: [(0, 0), (1, 1)]
Path length: 2

Path found for TestCase2: [(0, 0), (0, 1), (1, 2), (2, 2)]
Path length: 4

Path found for TestCase3: []
Path length: 0

*/