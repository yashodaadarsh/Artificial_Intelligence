import java.util.*;

class State {
    List<String> state; // "E" or "W"
    String umbrellaPos;
    List<Integer> requiredTimes;
    int timeTaken;

    public State(List<String> state, String umbrellaPos, List<Integer> requiredTimes, int timeTaken) {
        this.state = new ArrayList<>(state);
        this.umbrellaPos = umbrellaPos;
        this.requiredTimes = requiredTimes;
        this.timeTaken = timeTaken;
    }

    public boolean goalTest() {
        return state.stream().allMatch(s -> s.equals("W"));
    }

    public List<State> moveGen() {
        List<State> children = new ArrayList<>();
        String newPos = umbrellaPos.equals("E") ? "W" : "E";

        for (int i = 0; i < state.size(); i++) {
            for (int j = 0; j < state.size(); j++) {
                if (i == j && state.get(i).equals(umbrellaPos)) {
                    List<String> newState = new ArrayList<>(state);
                    newState.set(i, newPos);
                    int newTime = timeTaken + requiredTimes.get(i);
                    children.add(new State(newState, newPos, requiredTimes, newTime));
                } else if (i != j && state.get(i).equals(umbrellaPos) && state.get(j).equals(umbrellaPos)) {
                    List<String> newState = new ArrayList<>(state);
                    newState.set(i, newPos);
                    newState.set(j, newPos);
                    int newTime = timeTaken + Math.max(requiredTimes.get(i), requiredTimes.get(j));
                    children.add(new State(newState, newPos, requiredTimes, newTime));
                }
            }
        }

        return children;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) return false;
        State other = (State) obj;
        return this.state.equals(other.state) && this.umbrellaPos.equals(other.umbrellaPos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, umbrellaPos);
    }

    @Override
    public String toString() {
        return "--> " + state + ", umbrella position: " + umbrellaPos + ", time taken: " + timeTaken;
    }
}

public class RiverBridge {

    public static void printPath(List<State> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.println(i + " * " + path.get(i));
        }
    }

    public static List<State> constructPath(State goal, Map<State, State> parentMap) {
        List<State> path = new ArrayList<>();
        State current = goal;
        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public static void BFS(State start) {
        Queue<State> queue = new LinkedList<>();
        Map<State, State> parentMap = new HashMap<>();
        Set<State> visited = new HashSet<>();

        queue.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.goalTest() && current.timeTaken <= 60) {
                printPath(constructPath(current, parentMap));
                return;
            }

            visited.add(current);

            for (State child : current.moveGen()) {
                if (!visited.contains(child) && !parentMap.containsKey(child) && child.timeTaken <= 60) {
                    queue.add(child);
                    parentMap.put(child, current);
                }
            }
        }
    }

    public static void DFS(State start) {
        Stack<List<State>> stack = new Stack<>();
        stack.push(Arrays.asList(start));

        while (!stack.isEmpty()) {
            List<State> path = stack.pop();
            State current = path.get(path.size() - 1);

            if (current.goalTest() && current.timeTaken <= 60) {
                printPath(path);
                return;
            }

            for (State child : current.moveGen()) {
                if (!path.contains(child) && child.timeTaken <= 60) {
                    List<State> newPath = new ArrayList<>(path);
                    newPath.add(child);
                    stack.push(newPath);
                }
            }
        }
    }



    public static void main(String[] args) {
        List<String> initialState = Arrays.asList("E", "E", "E", "E");
        List<Integer> times = Arrays.asList(5, 10, 20, 25);
        State start = new State(initialState, "E", times, 0);

        System.out.println("***Path Using BFS Technique*** ");
        BFS(start);
        System.out.println();

        System.out.println("***Path Using DFS Technique*** ");
        DFS(start);
    }
}


/*
                                   OUTPUT

                       ***Path Using BFS Technique***
        0 * --> [E, E, E, E], umbrella position: E, time taken: 0
        1 * --> [W, W, E, E], umbrella position: W, time taken: 10
        2 * --> [E, W, E, E], umbrella position: E, time taken: 15
        3 * --> [E, W, W, W], umbrella position: W, time taken: 40
        4 * --> [E, E, W, W], umbrella position: E, time taken: 50
        5 * --> [W, W, W, W], umbrella position: W, time taken: 60

                       ***Path Using DFS Technique***
        0 * --> [E, E, E, E], umbrella position: E, time taken: 0
        1 * --> [W, W, E, E], umbrella position: W, time taken: 10
        2 * --> [W, E, E, E], umbrella position: E, time taken: 20
        3 * --> [W, E, W, W], umbrella position: W, time taken: 45
        4 * --> [E, E, W, W], umbrella position: E, time taken: 50
        5 * --> [W, W, W, W], umbrella position: W, time taken: 60

 */