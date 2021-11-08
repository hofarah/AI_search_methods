import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RBFS {

    static float NewRBFS(State s, float best) {
        if (best < s.f) {
            return s.f;
        }
        if (isGoal(s)) {
            result(s);
            return Float.MIN_VALUE;
        }
        ArrayList<State> children = s.successor();

        if (children.isEmpty()) {
            return Float.MAX_VALUE;
        }
        if (children.size() > 1) {
            children.sort((o1, o2) -> Float.compare(o1.f, o2.f));
        }
        float bestLimit = best;
        if (children.size() > 1) {
            bestLimit = Math.min(children.get(1).f, best);
        }
        float childBest = children.get(0).f;
        while (childBest <= best) {
            //sort
            if (children.size() > 1) {
                children.sort((o1, o2) -> Float.compare(o1.f, o2.f));
                bestLimit = Math.min(children.get(1).f, best);
            } else {
                bestLimit = best;
            }
            float newF = NewRBFS(children.get(0), bestLimit);
            if (newF == Float.MIN_VALUE) return Float.MIN_VALUE;
            children.get(0).f = newF;
            if (children.size() > 1) {
                childBest = Math.min(newF, children.get(1).f);
            } else {
                childBest = newF;
            }
        }
        return childBest;
    }

    public static void search(State initialState) {
            NewRBFS(initialState, 1000000000);
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


