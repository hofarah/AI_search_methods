import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IDA_Star {

    public static void search(State initialState) {
        Stack<State> frontier = new Stack<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if (isGoal(initialState)) {
            result(initialState);
            return;
        }
        float cutOff = initialState.f;
        while (true) {
            float newCutOff = 10000;
            frontier.clear();
            inFrontier.clear();
            explored.clear();
            frontier.push(initialState);
            inFrontier.put(initialState.hash(), true);
            while (!frontier.isEmpty()) {
                State tempState = frontier.pop();
                inFrontier.remove(tempState.hash());
                ArrayList<State> children = tempState.successor();
                boolean explore = true;
                for (int i = 0; i < children.size(); i++) {
                    if (!(inFrontier.containsKey(children.get(i).hash()))
                            && !(explored.containsKey(children.get(i).hash()))) {
                        if (children.get(i).f <= cutOff) {
                            if (isGoal(children.get(i))) {
                                result(children.get(i));
                                return;
                            }
                            frontier.push(tempState);
                            inFrontier.put(tempState.hash(), true);
                            frontier.push(children.get(i));
                            inFrontier.put(children.get(i).hash(), true);
                            explore = false;
                            break;
                        } else {
                            if (children.get(i).f < newCutOff){
                                newCutOff = children.get(i).f;
                                explored.put(children.get(i).hash(), true);
                            }
                        }
                    }
                }
                if (explore) {
                    explored.put(tempState.hash(), true);
                }
            }
                cutOff = newCutOff;
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


