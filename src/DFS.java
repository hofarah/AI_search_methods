import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DFS {

    public static void search(State initialState) {
        Stack<State> frontier = new Stack<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if (isGoal(initialState)) {
            result(initialState);
            return;
        }
        frontier.push(initialState);
        inFrontier.put(initialState.hash(), true);
        while (!frontier.isEmpty()) {
            State tmpState = frontier.pop();
            inFrontier.remove(tmpState.hash());
            ArrayList<State> children = tmpState.successor();
            boolean explore = true;
            for (int i = 0; i < children.size(); i++) {
                if (!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                    if (isGoal(children.get(i))) {
                        result(children.get(i));
                        return;
                    }
                    frontier.push(tmpState);
                    inFrontier.put(tmpState.hash(), true);
                    frontier.push(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                    explore = false;
                    break;
                }
            }
            if (explore) explored.put(tmpState.hash(), true);
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


}
