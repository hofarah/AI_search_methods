import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IDS {

    public static void search(State initialState, int cutOff) {
        Stack<State> frontier = new Stack<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if (initialState.isGoal()) {
            initialState.result();
            return;
        }

        for (int currentLimit = 0; currentLimit <= cutOff; currentLimit++) {
            frontier.clear();
            inFrontier.clear();
            explored.clear();
            frontier.push(initialState);
            inFrontier.put(initialState.hash(), true);

            while (!frontier.isEmpty()) {
                State tmpState = frontier.pop();
                inFrontier.remove(tmpState.hash());
                explored.put(tmpState.hash(), true);
                ArrayList<State> children = tmpState.successor();
                for (int i = children.size() - 1; i >= 0; i--) {
                    if (  children.get(i).getDepth() <= currentLimit&&!(inFrontier.containsKey(children.get(i).hash()))
                            && !(explored.containsKey(children.get(i).hash()))) {
                        if (children.get(i).isGoal()) {
                            children.get(i).result();
                            return;
                        }
                        frontier.push(children.get(i));
                        inFrontier.put(children.get(i).hash(), true);
                    }
                }
            }
        }
    }

}
