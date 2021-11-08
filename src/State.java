import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class State {
    static class StateForUDSComparator implements Comparator<State> {
        @Override
        public int compare(State s1, State s2) {
            if (s1.cost > s2.cost)
                return 1;
            else if (s1.cost < s2.cost)
                return -1;
            return 0;
        }
    }

    static class StateForHueristicComparator implements Comparator<State> {
        @Override
        public int compare(State s1, State s2) {
            if (s1.heuristic > s2.heuristic)
                return 1;
            else if (s1.heuristic < s2.heuristic)
                return -1;
            return 0;
        }
    }

    static class StateForFComparator implements Comparator<State> {
        @Override
        public int compare(State s1, State s2) {
            if (s1.f > s2.f)
                return 1;
            else if (s1.f < s2.f)
                return -1;
            return 0;
        }
    }

    private Graph graph;
    private int selectedNodeId;
    private State parentState;
    private int depth = 0;
    public int cost;
    public float heuristic;
    public float f;
    public float g;
    public String hashh;

    public State(Graph graph, int selectedNodeId, State parentState, int cost) {
        this.graph = graph.copy();
        this.selectedNodeId = selectedNodeId;
        if (parentState != null) {
            this.parentState = parentState;
            this.depth = parentState.depth + 1;
            this.cost = cost;
        } else {
            this.heuristic = heuristic(this);
            this.parentState = null;
        }
    }

    public ArrayList<State> successor() {
        ArrayList<State> children = new ArrayList<State>();
        for (int i = 0; i < this.graph.size(); i++) {
            int nodeId = this.graph.getNode(i).getId();
            if (nodeId != selectedNodeId) {
                int toAddCost = 0;
                switch (this.graph.getNode(nodeId).getColor()) {
                    case Red -> toAddCost = 1;
                    case Black -> toAddCost = 2;
                    case Green -> toAddCost = 3;
                }
                State newState = new State(this.graph.copy(), nodeId, this, this.cost + toAddCost);
                LinkedList<Integer> nodeNeighbors = newState.getGraph().getNode(nodeId).getNeighborsIds();
                for (int j = 0; j < nodeNeighbors.size(); j++) {
                    int neighborId = nodeNeighbors.get(j);
                    newState.getGraph().getNode(neighborId).reverseNodeColor();
                }
                if (newState.getGraph().getNode(nodeId).getColor() == Color.Black) {
                    int greenNeighborsCount = 0;
                    int redNeighborsCount = 0;
                    int blackNeighborcount = 0;
                    for (int j = 0; j < nodeNeighbors.size(); j++) {
                        int neighborId = nodeNeighbors.get(j);
                        switch (newState.getGraph().getNode(neighborId).getColor()) {
                            case Green -> greenNeighborsCount++;
                            case Red -> redNeighborsCount++;
                            case Black -> blackNeighborcount++;
                        }
                    }
                    if (greenNeighborsCount > redNeighborsCount && greenNeighborsCount > blackNeighborcount) {
                        newState.getGraph().getNode(nodeId).changeColorTo(Color.Green);
                    } else if (redNeighborsCount > greenNeighborsCount && redNeighborsCount > blackNeighborcount) {
                        newState.getGraph().getNode(nodeId).changeColorTo(Color.Red);
                    }
                } else {
                    newState.getGraph().getNode(nodeId).reverseNodeColor();
                }
                //comment this line if you do not use agahane algorithm.fixme
                newState.heuristic = heuristic(newState);
                float lastG = 0;
                if (parentState != null) lastG = parentState.g;
//                newState.g = newState.cost + lastG;
                newState.g = newState.depth + lastG;
                newState.f = newState.g + newState.heuristic;
                newState.hashh = newState.hash();
                children.add(newState);
            }
        }
        return children;
    }

    public float heuristic(State s) {
        float h = 0;
        Hashtable<Integer, Boolean> checked = new Hashtable<>();
        for (int i = 0; i < s.getGraph().size(); i++) {
            Node node = s.getGraph().getNode(i);
            if (node.groupID != 0) continue;

            checked.put(node.getId(), true);
            LinkedList<Integer> neighborsIds = node.getNeighborsIds();
            int redNeighbors = 0;
            int greenNeighbors = 0;
            double groupID = Math.random();
            for (int j = 0; j < neighborsIds.size(); j++) {
                Node neighborNode = s.graph.getNode(neighborsIds.get(j));
                if (neighborNode.getColor() == Color.Red && neighborNode.groupID == 0) {
                    redNeighbors++;
                }
                if (neighborNode.getColor() == Color.Green) {
                    greenNeighbors++;
                }
            }
            if (redNeighbors == 0) {
                if (node.getColor() == Color.Red) h++;
            }//2 approach
            if (greenNeighbors > redNeighbors) h -= greenNeighbors;

            if (redNeighbors > 0) {//second approach is greater than half
                for (int j = 0; j < neighborsIds.size(); j++) {
                    Node neighborNode = s.graph.getNode(neighborsIds.get(j));
                    if (neighborNode.getColor() == Color.Red && neighborNode.groupID == 0) {
                        s.graph.getNode(neighborNode.getId()).groupID = groupID;
                        if (checked.containsKey(neighborNode.getId())) h--;
                    }
                }
                if (node.getColor() != Color.Green) {

                    s.graph.getNode(node.getId()).groupID = groupID;
                }
                h++;
            }
//            if (node.getColor() == Color.Black) {
//                if (greenNeighbors > redNeighbors) h++;
//            }


        }
        return h;
    }
    public void result() {
        Stack<State> states = new Stack<State>();
        State state=this;
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
    public boolean isGoal() {
        for (int i = 0; i < this.getGraph().size(); i++) {
            if (this.getGraph().getNode(i).getColor() == Color.Red
                    || this.getGraph().getNode(i).getColor() == Color.Black) {
                return false;
            }
        }
        return true;
    }


    public String hash() {
        String result = "";
        for (int i = 0; i < graph.size(); i++) {
            switch (graph.getNode(i).getColor()) {
                case Green -> result += "g";
                case Red -> result += "r";
                case Black -> result += "b";
            }
        }
        return result;
    }

    public String outputGenerator() {
        String result = "";
        for (int i = 0; i < graph.size(); i++) {
            String color = switch (graph.getNode(i).getColor()) {
                case Red -> "R";
                case Green -> "G";
                case Black -> "B";
            };
            result += graph.getNode(i).getId() + color + " ";
            for (int j = 0; j < graph.getNode(i).getNeighborsIds().size(); j++) {
                int neighborId = graph.getNode(i).getNeighborsId(j);
                String neighborColor = switch (graph.getNode(neighborId).getColor()) {
                    case Red -> "R";
                    case Green -> "G";
                    case Black -> "B";
                };
                result += neighborId + neighborColor + " ";
            }
            result += ",";
        }
        return result;
    }

    public Graph getGraph() {
        return graph;
    }

    public int getDepth() {
        return this.depth;
    }

    public State getParentState() {
        return parentState;
    }

    public int getSelectedNodeId() {
        return selectedNodeId;
    }
}
