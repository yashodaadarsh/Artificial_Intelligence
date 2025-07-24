import java.util.*;

class State{
    List<Character> state;
    private final List<Character> goal = Arrays.asList('W', 'W', 'W', '_', 'E', 'E', 'E');

    public State(List<Character> state) {
        this.state = new ArrayList<>(state);
    }

    public boolean goalTest() {
        return state.equals( goal );
    }

    public List<State> moveGen() {
        List<State> children = new ArrayList<>();
        for (int i = 0; i < state.size(); i++) {
            char ch = state.get(i);
            if (ch == 'E') {
                if (i + 1 < state.size() && state.get(i + 1) == '_') {
                    List<Character> newState = new ArrayList<>(state);
                    Collections.swap(newState, i, i + 1);
                    children.add(new State(newState));
                } else if (i + 2 < state.size() && state.get(i + 2) == '_') {
                    List<Character> newState = new ArrayList<>(state);
                    Collections.swap(newState, i, i + 2);
                    children.add(new State(newState));
                }
            } else if (ch == 'W') {
                if (i - 1 >= 0 && state.get(i - 1) == '_') {
                    List<Character> newState = new ArrayList<>(state);
                    Collections.swap(newState, i, i - 1);
                    children.add(new State(newState));
                } else if (i - 2 >= 0 && state.get(i - 2) == '_') {
                    List<Character> newState = new ArrayList<>(state);
                    Collections.swap(newState, i, i - 2);
                    children.add(new State(newState));
                }
            }
        }
        return children;
    }

    @Override
    public boolean equals(Object obj) {
        return this.state.equals(((State) obj).state);
    }

    @Override
    public String toString() {
        return state.toString();
    }
}

public class RabbitLeap {

    public List<State> constructPath( State curState , Map<State , State> parentMap ){
        List<State> path = new ArrayList<>();
        State temp = curState;
        while( temp != null ){
            path.add( temp );
            temp = parentMap.get(temp);
        }
        Collections.reverse(path);
        return path;
    }

    public void printPath( List<State> path ){
        for( int i = 0 ; i < path.size() ; i++ ){
            System.out.println( i + " --> " + path.get(i) );
        }
    }

    public void bfs( State start ){
        Queue<State> q = new LinkedList<>();
        Map<State,State> parentMap = new HashMap<>();
        Map<State,Boolean> visited = new HashMap<>();

        visited.put(start,true);
        parentMap.put(start,null);

        q.offer(start);

        while( !q.isEmpty() ){
            State curState = q.poll();
            if( curState.goalTest() ){
                List<State> path = constructPath(curState,parentMap);
                printPath(path);
                return;
            }
            visited.put(curState , true );
            List<State> childrens = curState.moveGen();
            for( State children : childrens ){
                if( !visited.getOrDefault(children,false) ){
                    q.offer( children );
                    parentMap.put( children , curState );
                }
            }
        }
    }
    public void dfs( State start ){
        Stack<State> st = new Stack<>();
        Map<State,State> parentMap = new HashMap<>();
        Map<State,Boolean> visited = new HashMap<>();

        visited.put(start,true);
        parentMap.put(start,null);
        st.add(start);

        while( !st.isEmpty() ){
            State curState = st.pop();
            if( curState.goalTest() ){
                List<State> path = constructPath(curState,parentMap);
                printPath(path);
                return;
            }
            visited.put(curState,true);
            List<State> childrens = curState.moveGen();
            for( State children : childrens ){
                if( !visited.getOrDefault(children,false) ){
                    parentMap.put(children,curState);
                    st.add(children);
                }
            }

        }

    }

    public static void main(String[] args) {
        List<Character> initialState = Arrays.asList('E', 'E', 'E','_','W', 'W', 'W');
        State start = new State(initialState);
        RabbitLeap rabbitLeap = new RabbitLeap();
        System.out.println("***Path Using BFS Technique*** ");
        rabbitLeap.bfs(start);
        System.out.println();
        System.out.println("***Path Using DFS Technique*** ");
        rabbitLeap.dfs(start);
    }
}


/*
                          OUTPUT

                ***Path Using BFS Technique***
                0 --> [E, E, E, _, W, W, W]
                1 --> [E, E, _, E, W, W, W]
                2 --> [E, E, W, E, _, W, W]
                3 --> [E, E, W, E, W, _, W]
                4 --> [E, E, W, _, W, E, W]
                5 --> [E, _, W, E, W, E, W]
                6 --> [_, E, W, E, W, E, W]
                7 --> [W, E, _, E, W, E, W]
                8 --> [W, E, W, E, _, E, W]
                9 --> [W, E, W, E, W, E, _]
                10 --> [W, E, W, E, W, _, E]
                11 --> [W, E, W, _, W, E, E]
                12 --> [W, _, W, E, W, E, E]
                13 --> [W, W, _, E, W, E, E]
                14 --> [W, W, W, E, _, E, E]
                15 --> [W, W, W, _, E, E, E]

                ***Path Using DFS Technique***
                0 --> [E, E, E, _, W, W, W]
                1 --> [E, E, E, W, _, W, W]
                2 --> [E, E, _, W, E, W, W]
                3 --> [E, _, E, W, E, W, W]
                4 --> [E, W, E, _, E, W, W]
                5 --> [E, W, E, W, E, _, W]
                6 --> [E, W, E, W, E, W, _]
                7 --> [E, W, E, W, _, W, E]
                8 --> [E, W, _, W, E, W, E]
                9 --> [_, W, E, W, E, W, E]
                10 --> [W, _, E, W, E, W, E]
                11 --> [W, W, E, _, E, W, E]
                12 --> [W, W, E, W, E, _, E]
                13 --> [W, W, E, W, _, E, E]
                14 --> [W, W, _, W, E, E, E]
                15 --> [W, W, W, _, E, E, E]
*/