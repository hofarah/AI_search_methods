import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RBFS {

    static float NewRBFS(State s, float best) {
        if (best < s.f) {
            return s.f;
        }
        if (s.isGoal()) {
            s.result();
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
            NewRBFS(initialState, Float.MAX_VALUE);
    }



}


