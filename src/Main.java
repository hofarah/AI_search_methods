public class Main {

    public static void main(String[] args) {

        //-----------------------------------------------> test1 :
//        Graph initialGraph= new Graph(5);
//        initialGraph.addNode(new Node(0, Color.Red));
//        initialGraph.addNode(new Node(1, Color.Red));
//        initialGraph.addNode(new Node(2, Color.Red));
//        initialGraph.addNode(new Node(3, Color.Green));
//        initialGraph.addNode(new Node(4, Color.Red));
//        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(1));
//        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(2));
//        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(3));
//        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(2));
//        initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(4));

        //-----------------------------------------------> test2 :
//        Graph initialGraph= new Graph(7);
//        initialGraph.addNode(new Node(0, Color.Red));
//        initialGraph.addNode(new Node(1, Color.Black));
//        initialGraph.addNode(new Node(2, Color.Green));
//        initialGraph.addNode(new Node(3, Color.Red));
//        initialGraph.addNode(new Node(4, Color.Red));
//        initialGraph.addNode(new Node(5, Color.Green));
//        initialGraph.addNode(new Node(6, Color.Red));
//        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(4));
//        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(2));
//        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(3));
//        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(4));
//        initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(5));
//        initialGraph.addLinkBetween(initialGraph.getNode(4), initialGraph.getNode(5));
//        initialGraph.addLinkBetween(initialGraph.getNode(5), initialGraph.getNode(6));

        Graph finalGraph= new Graph(7);
        finalGraph.addNode(new Node(0, Color.Green));
        finalGraph.addNode(new Node(1, Color.Green));
        finalGraph.addNode(new Node(2, Color.Green));
        finalGraph.addNode(new Node(3, Color.Green));
        finalGraph.addNode(new Node(4, Color.Green));
        finalGraph.addNode(new Node(5, Color.Green));
        finalGraph.addNode(new Node(6, Color.Green));
        finalGraph.addLinkBetween(finalGraph.getNode(0), finalGraph.getNode(4));
        finalGraph.addLinkBetween(finalGraph.getNode(1), finalGraph.getNode(2));
        finalGraph.addLinkBetween(finalGraph.getNode(1), finalGraph.getNode(3));
        finalGraph.addLinkBetween(finalGraph.getNode(1), finalGraph.getNode(4));
        finalGraph.addLinkBetween(finalGraph.getNode(3), finalGraph.getNode(5));
        finalGraph.addLinkBetween(finalGraph.getNode(4), finalGraph.getNode(5));
        finalGraph.addLinkBetween(finalGraph.getNode(5), finalGraph.getNode(6));

        //-----------------------------------------------> test3 :
        Graph initialGraph= new Graph(15);
        initialGraph.addNode(new Node(0, Color.Red));
        initialGraph.addNode(new Node(1, Color.Black));
        initialGraph.addNode(new Node(2, Color.Black));
        initialGraph.addNode(new Node(3, Color.Black));
        initialGraph.addNode(new Node(4, Color.Red));
        initialGraph.addNode(new Node(5, Color.Green));
        initialGraph.addNode(new Node(6, Color.Green));
        initialGraph.addNode(new Node(7, Color.Red));
        initialGraph.addNode(new Node(8, Color.Red));
        initialGraph.addNode(new Node(9, Color.Green));
        initialGraph.addNode(new Node(10, Color.Red));
        initialGraph.addNode(new Node(11, Color.Red));
        initialGraph.addNode(new Node(12, Color.Red));
        initialGraph.addNode(new Node(13, Color.Green));
        initialGraph.addNode(new Node(14, Color.Red));

        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(1));
        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(2));
        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(14));
        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(2));
        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(3));
        initialGraph.addLinkBetween(initialGraph.getNode(2), initialGraph.getNode(5));
        initialGraph.addLinkBetween(initialGraph.getNode(2), initialGraph.getNode(6));
        initialGraph.addLinkBetween(initialGraph.getNode(2), initialGraph.getNode(7));
        initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(13));
        initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(14));
        initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(7));
        initialGraph.addLinkBetween(initialGraph.getNode(4), initialGraph.getNode(6));
        initialGraph.addLinkBetween(initialGraph.getNode(4), initialGraph.getNode(11));
        initialGraph.addLinkBetween(initialGraph.getNode(5), initialGraph.getNode(10));
        initialGraph.addLinkBetween(initialGraph.getNode(5), initialGraph.getNode(12));
        initialGraph.addLinkBetween(initialGraph.getNode(6), initialGraph.getNode(11));
        initialGraph.addLinkBetween(initialGraph.getNode(7), initialGraph.getNode(8));
        initialGraph.addLinkBetween(initialGraph.getNode(7), initialGraph.getNode(9));
        initialGraph.addLinkBetween(initialGraph.getNode(8), initialGraph.getNode(14));


        State initialState= new State(initialGraph, -1, null,0);
//        State finalState= new State(finalGraph, -1, null,0);

//        BDS.search(initialState,finalState,10);
        Greedy.search(initialState);
    }
}
