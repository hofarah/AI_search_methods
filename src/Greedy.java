import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Greedy {

    public static void search(State initialState) {
        PriorityQueue<State> frontier = new PriorityQueue<>(new State.StateForHueristicComparator());
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if (initialState.isGoal()) {
            initialState.result();
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
                    if (children.get(i).isGoal()) {
                        children.get(i).result();
                        return;
                    }
                    frontier.add(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
        }
    }

}


