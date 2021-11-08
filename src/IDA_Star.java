import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IDA_Star {

    public static void search(State initialState) {
        Stack<State> frontier = new Stack<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        if (initialState.isGoal()) {
            initialState.result();
            return;
        }
        float cutOff = initialState.f;
        while (true) {
            float newCutOff = 10000;
            frontier.clear();
            inFrontier.clear();
            frontier.push(initialState);
            inFrontier.put(initialState.hash(), true);
            while (!frontier.isEmpty()) {
                State tempState = frontier.pop();
                inFrontier.remove(tempState.hash());
                ArrayList<State> children = tempState.successor();
                for (int i = 0; i < children.size(); i++) {
                    if (!(inFrontier.containsKey(children.get(i).hash()))) {
                        if (children.get(i).f <= cutOff) {
                            if (children.get(i).isGoal()) {
                                children.get(i).result();
                                return;
                            }

                            frontier.push(children.get(i));
                            inFrontier.put(children.get(i).hash(), true);
                        } else {
                            if (children.get(i).f < newCutOff){
                                newCutOff = children.get(i).f;
                            }
                        }
                    }
                }

            }
                cutOff = newCutOff;
        }
    }

}


