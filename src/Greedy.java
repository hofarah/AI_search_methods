import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Greedy {

    public static void search(State initialState) {
        PriorityQueue<State> frontier = new PriorityQueue<>(new State.StateForGreedyComparator());
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if (isGoal(initialState)) {
            result(initialState);
            return;
        }
        frontier.add(initialState);
        inFrontier.put(initialState.hash(), true);
        while (!frontier.isEmpty()) {
            State tempState = frontier.poll();
            inFrontier.remove(tempState.hash());
            explored.put(tempState.hash(), true);
            ArrayList<State> children = tempState.successor();
            for (int i = 0; i < children.size(); i++) {
                if (!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                    if (isGoal(children.get(i))) {
                        result(children.get(i));
                        return;
                    }
                    frontier.add(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
        }
    }

    private static boolean isGoal(State state) {
        for (int i = 0; i < state.getGraph().size(); i++) {
            if (state.getGraph().getNode(i).getColor() == Color.Red
                    || state.getGraph().getNode(i).getColor() == Color.Black) {
                return false;
            }
        }
        return true;
    }

    private static void result(State state) {
        Stack<State> states = new Stack<State>();
        while (true) {
            states.push(state);
            if (state.getParentState() == null) {
                break;
            } else {
                state = state.getParentState();
            }
        }
        try {
            FileWriter myWriter = new FileWriter("BfsResult.txt");
            System.out.println("initial state : ");
            while (!states.empty()) {
                State tempState = states.pop();
                if (tempState.getSelectedNodeId() != -1) {
                    System.out.println("selected id : " + tempState.getSelectedNodeId());
                }
                tempState.getGraph().print();

                myWriter.write(tempState.getSelectedNodeId() + " ,");
                myWriter.write(tempState.outputGenerator() + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static float heuristic(State s) {
        float h = 0;
        int gc = 0;
        int rc = 0;
        int bc = 0;
        for (int i = 0; i < s.getGraph().size(); i++) {
            if (s.getGraph().getNode(i).getColor() == Color.Green) {
                gc++;
            }
        }

        for (int i = 0; i < s.getGraph().size(); i++) {
            if (s.getGraph().getNode(i).getColor() == Color.Black) {
                int greenNeighborsCount = 0;
                int redNeighborsCount = 0;
                int blackNeighborcount = 0;
                for (int j = 0; j < s.getGraph().getNode(i).getNeighborsIds().size(); j++) {
                    int neighborId = s.getGraph().getNode(i).getNeighborsIds().get(j);
                    switch (s.getGraph().getNode(neighborId).getColor()) {
                        case Green -> greenNeighborsCount++;
                        case Red -> redNeighborsCount++;
                        case Black -> blackNeighborcount++;
                    }
                }
                if (greenNeighborsCount > redNeighborsCount && greenNeighborsCount > blackNeighborcount) {
                    h+= s.getGraph().getNode(i).getNeighborsIds().size();
                }else if (redNeighborsCount > greenNeighborsCount && redNeighborsCount > blackNeighborcount) {
                    h+= s.getGraph().getNode(i).getNeighborsIds().size()-1;
                }else{
                    h-=s.getGraph().getNode(i).getNeighborsIds().size();
                }
            }
        }




//        h -= rc;
//        h -= bc;
//        h += gc;

        return h;
    }
}


