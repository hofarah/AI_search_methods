import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RBFS {
    static Hashtable<String, State> saved = new Hashtable<>();
    static Hashtable<String, Boolean> inFrontier = new Hashtable<>();

    static float RBFSSearch(State s, float best) {
        inFrontier.put(s.hash(), true);
        ArrayList<State> children = s.successor();
        for (int i = 0; i < children.size(); i++) {
            if (!(inFrontier.containsKey(children.get(i).hash()))) {
                if (saved.containsKey(children.get(i).hash())) {
                    children.set(i, saved.get(children.get(i).hash()));
                }
            } else {
                children.remove(i);
                i--;
            }
        }
        if (children.isEmpty()) {
            inFrontier.remove(s.hash(), true);
            return s.f;
        }
        if (children.size() > 1) {
            children.sort((o1, o2) -> Float.compare(o1.f, o2.f));
        }
        for (int i = 0; i < children.size(); i++) {
            float secBest = secMin(best, children);
            if (best < children.get(i).f) {
                inFrontier.remove(s.hash(), true);
                return min(children);
            }
            if (isGoal(children.get(i))) {
                result(children.get(i));
                return Float.MIN_VALUE;
            }
            children.get(i).f = RBFSSearch(children.get(i), secBest);
            if (children.get(i).f == Float.MIN_VALUE) {
                return Float.MIN_VALUE;
            } else {
                saved.put(children.get(i).hash(), children.get(i));
            }
        }
        inFrontier.remove(s.hash(), true);
        return min(children);
    }

    static float secMin(float a, ArrayList<State> stateArrayList) {
        if (stateArrayList.isEmpty()) {
            return a;
        }
        if (stateArrayList.size() > 1) {
            stateArrayList.sort((o1, o2) -> Float.compare(o1.f, o2.f));
            return Math.min(a, stateArrayList.get(1).f);
        } else {
            return Math.max(a, stateArrayList.get(0).f);
        }
    }

    static float min(ArrayList<State> stateArrayList) {
        float min = 10000000;
        for (int i = 0; i < stateArrayList.size(); i++) {
            if (stateArrayList.get(i).f < min) {
                min = stateArrayList.get(i).f;
            }
        }
        return min;
    }

    public static void search(State initialState) {
        float res = 1;
        while (res != Float.MIN_VALUE)
            res = RBFSSearch(initialState, 1000000000);
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

    private static ArrayList<State> sort(ArrayList<State> a) {
        a.sort((o1, o2) -> Float.compare(o1.f, o2.f));
        return a;
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


