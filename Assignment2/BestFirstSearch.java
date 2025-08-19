import java.util.*;
import java.util.stream.Collectors;

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

    // Getters if needed
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int[][] getGrid() { return grid; }
}

class NodeParentHeuristic{
    State node;
    State parent;
    int heuristicValue;
    public NodeParentHeuristic( State node , State parent , int heuristicValue ){
        this.node = node;
        this.parent = parent;
        this.heuristicValue = heuristicValue;
    }
}

class Search{
    List<State> removeSeen( List<State> children , PriorityQueue<NodeParentHeuristic> open , Map<NodeParentHeuristic,Boolean> vis ){
        List<State> newChildren = new ArrayList<>();
        
        Set<State> openStates = open.stream()
                            .map(nph -> nph.node)   
                            .collect(Collectors.toSet()); 
        
        Set<State> visStates = vis.keySet().stream().map( closed -> closed.node ).collect( Collectors.toSet() );

        for( State child : children ){
            if( !openStates.contains(child) && !visStates.contains(child) )
                newChildren.add(child);
        }

        return newChildren;

    }

    public int calculateHValue(State child) {
        int row = child.getRow();
        int col = child.getCol();
        int n = child.getGrid().length;

        int goalRow = n - 1;
        int goalCol = n - 1;

        return Math.abs(goalRow - row) + Math.abs(goalCol - col);
    }

    List<State> reConstructPath( NodeParentHeuristic nodeTriple , Map<NodeParentHeuristic,Boolean> vis ){
        List<State> path = new ArrayList<>();
        State current = nodeTriple.parent;

        Map<State,State> parentMap = new HashMap<>();
        for( NodeParentHeuristic nph : vis.keySet() ){
            if( vis.get(nph) ) parentMap.put( nph.node , nph.parent );
        }

        path.add( nodeTriple.node );

        while (current != null) {
            path.add(current); 
            current = parentMap.get(current);
        }

        Collections.reverse(path);

        return path;
    }

    public List<State> bestFirstSearch( State start ){

        int row = start.getRow();
        int col = start.getCol();
        int[][] grid = start.getGrid();
        if( grid[row][col] == 1 ) return Collections.emptyList();

        PriorityQueue<NodeParentHeuristic> open = new PriorityQueue<>( ( a , b ) -> a.heuristicValue - b.heuristicValue );
        Map<NodeParentHeuristic,Boolean> vis = new HashMap<>();
        open.add( new NodeParentHeuristic(start , null , calculateHValue(start)) );

        while( !open.isEmpty() ){

            NodeParentHeuristic nodeTriple = open.poll();
            State node = nodeTriple.node;
            
            if( node.goalTest() ){
                return reConstructPath(nodeTriple,vis);
            }
            
            vis.put( nodeTriple , true );
            List<State> children = node.moveGen();
            List<State> newNodes = removeSeen( children,open,vis );
            List<NodeParentHeuristic> newTriples = new ArrayList<>();
            for( State child : newNodes ){
                int hValCalc = calculateHValue( child );
                newTriples.add( new NodeParentHeuristic( child, node, hValCalc ) );
            }
            open.addAll(newTriples);
            
        }

        return Collections.emptyList();
    }
}


public class BestFirstSearch {
    public static void main(String[] args) {

        //TestCase1
        int[][] grid1 = {
            {0, 1},
            {1, 0},
        };

        State start1 = new State(0, 0, grid1);

        Search search1 = new Search();
        List<State> path1 = search1.bestFirstSearch(start1);

        if (path1.isEmpty()) {
            System.out.println("-1 (No path1 exists)");
        } else {
            System.out.println("Path found for TestCase1: " + path1);
            System.out.println("Path length: " + path1.size());
        }
        System.out.println();


        //TestCase2
        int[][] grid2 = {
            {0, 0, 0},
            {1, 1, 0},
            {1, 1, 0}
        };

        State start2 = new State(0, 0, grid2);

        Search search2 = new Search();
        List<State> path2 = search2.bestFirstSearch(start2);

        if (path2.isEmpty()) {
            System.out.println("-1 (No path2 exists)");
        } else {
            System.out.println("Path found for TestCase2: " + path2);
            System.out.println("Path length: " + path2.size());
        }
        System.out.println();

        //TestCase3
        int[][] grid3 = {
            {1, 0, 0},
            {1, 1, 0},
            {1, 1, 0}
        };

        State start3 = new State(0, 0, grid3);

        Search search3 = new Search();
        List<State> path3 = search3.bestFirstSearch(start3);

        if (path3.isEmpty()) {
            System.out.println("-1 (No path3 exists)");
        } else {
            System.out.println("Path found for TestCase3: " + path3);
            System.out.println("Path length: " + path3.size());
        }

    }
}
