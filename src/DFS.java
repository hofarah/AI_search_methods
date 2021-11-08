import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DFS {

    public static void search(State initialState) {
        Stack<State> frontier = new Stack<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if (initialState.isGoal()) {
            initialState.result();
            return;
        }
        frontier.push(initialState);
        inFrontier.put(initialState.hash(), true);
        while (!frontier.isEmpty()) {
            State tmpState = frontier.pop();
            inFrontier.remove(tmpState.hash());
            explored.put(tmpState.hash(), true);
            ArrayList<State> children = tmpState.successor();
            for (int i = children.size() - 1; i >= 0; i--) {
                if (!(inFrontier.containsKey(children.get(i).hash()))
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
