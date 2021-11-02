import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BDS {

    public static void search(State initialState, State finalState, int cutOff) {
        Stack<State> fromInitialStateFrontier = new Stack<State>();
        Hashtable<String, State> fromInitialStateInFrontier = new Hashtable<>();
        Hashtable<String, State> fromInitialStateExplored = new Hashtable<>();
        Stack<State> fromGoalStateFrontier = new Stack<State>();
        Hashtable<String, State> fromGoalStateInFrontier = new Hashtable<>();
        Hashtable<String, State> fromGoalStateExplored = new Hashtable<>();

        for (int currentLimit = 0; currentLimit <= cutOff; currentLimit++) {
            fromInitialStateFrontier.clear();
            fromInitialStateInFrontier.clear();
            fromInitialStateExplored.clear();
            fromInitialStateFrontier.push(initialState);
            fromInitialStateInFrontier.put(initialState.hash(), initialState);

            fromGoalStateFrontier.clear();
            fromGoalStateInFrontier.clear();
            fromGoalStateExplored.clear();
            fromGoalStateFrontier.push(finalState);
            fromGoalStateInFrontier.put(finalState.hash(), initialState);

            while (!fromInitialStateFrontier.isEmpty() && !fromGoalStateFrontier.isEmpty()) {
                State initialTmpState = fromInitialStateFrontier.pop();
                fromInitialStateInFrontier.remove(initialTmpState.hash());
                fromInitialStateExplored.put(initialTmpState.hash(), initialTmpState);
                ArrayList<State> initialChildren = initialTmpState.successor();

                State GoalTmpState = fromGoalStateFrontier.pop();
                fromGoalStateInFrontier.remove(GoalTmpState.hash());
                fromGoalStateExplored.put(GoalTmpState.hash(), GoalTmpState);
                ArrayList<State> goalChildren = GoalTmpState.successor();
                for (int i = initialChildren.size() - 1; i >= 0; i--) {
                    if (!(fromInitialStateInFrontier.containsKey(initialChildren.get(i).hash()))
                            && !(fromInitialStateExplored.containsKey(initialChildren.get(i).hash())) && initialChildren.get(i).getDepth() <= currentLimit) {
                        if (isGoal(null, null, fromGoalStateInFrontier, fromGoalStateExplored,  initialChildren.get(i),null)) {
                            return;
                        }
                        fromInitialStateFrontier.push(initialChildren.get(i));
                        fromInitialStateInFrontier.put(initialChildren.get(i).hash(), initialChildren.get(i));
                    }
                }
                for (int i = goalChildren.size() - 1; i >= 0; i--) {
                    if (!(fromGoalStateInFrontier.containsKey(goalChildren.get(i).hash()))
                            && !(fromGoalStateExplored.containsKey(goalChildren.get(i).hash())) && goalChildren.get(i).getDepth() <= currentLimit) {
                        if (isGoal(fromInitialStateInFrontier, fromInitialStateExplored, null, null, null, goalChildren.get(i))) {
                            return;
                        }
                        fromGoalStateFrontier.push(goalChildren.get(i));
                        fromGoalStateInFrontier.put(goalChildren.get(i).hash(), goalChildren.get(i));
                    }
                }

            }
        }
    }

    private static boolean isGoal(Hashtable<String, State> fromInitialStateInFrontier, Hashtable<String,
            State> fromInitialStateExplored, Hashtable<String,
            State> fromGoalStateExplored, Hashtable<String, State> fromGoalStateInFrontier, State initialState, State goalState) {
        if (fromGoalStateExplored != null && fromGoalStateInFrontier != null && initialState != null) {
            if (fromGoalStateExplored.containsKey(initialState.hash())) {
                result(initialState, fromGoalStateExplored.get(initialState.hash()));
                return true;
            }
            if (fromGoalStateInFrontier.containsKey(initialState.hash())) {
                result(initialState, fromGoalStateInFrontier.get(initialState.hash()));
                return true;
            }
        }


        if (fromInitialStateExplored != null && fromInitialStateInFrontier != null && goalState != null) {
            if (fromInitialStateExplored.containsKey(goalState.hash())) {
                result(fromInitialStateExplored.get(goalState.hash()), goalState);
                return true;
            }
            if (fromInitialStateInFrontier.containsKey(goalState.hash())) {
                result(fromInitialStateInFrontier.get(goalState.hash()), goalState);
                return true;
            }
        }
        return false;
    }

    private static void result(State initialState, State goalState) {
        ArrayList<State> states = new ArrayList<>();
        ArrayList<State> tmp1 = new ArrayList<>();
        while (true) {
            tmp1.add(goalState);
            if (goalState.getParentState() == null) {
                break;
            } else {
                goalState = goalState.getParentState();
            }
        }
        while (true) {
            states.add( initialState);
            if (initialState.getParentState() == null) {
                break;
            } else {
                initialState = initialState.getParentState();
            }
        }
        for (int i = 0; i <tmp1.size() ; i++) {
              states.add(0,tmp1.get(i));
        }

        try {
            FileWriter myWriter = new FileWriter("BfsResult.txt");
            System.out.println("initial state : ");
            while (!states.isEmpty()) {
                State tempState = states.remove(states.size() - 1);
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
