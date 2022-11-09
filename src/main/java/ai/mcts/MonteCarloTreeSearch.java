package ai.mcts;

import chess.ChessMatch;
import chess.utility.Chessboard;
import java.util.List;

import static chess.MatchState.ONGOING;

public class MonteCarloTreeSearch {

    UCT uct = new UCT();
    ChessHelper helper = new ChessHelper();

    public Chessboard nextMove(Chessboard board, int team) {

        //
        String startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        ChessMatch match = new ChessMatch(startPos);


        // basic attributes
        int opponent;
        int currentIteration = 0;
        int iterations = 10000;

        if(team==1){
            opponent = 2;
        } else {
            opponent = 1;
        }

        // build the search tree
        Tree tree = new Tree();
        Node root = tree.getRoot();

        // this fixes the two-team problem
        root.getState().setBoard(board);
        root.getState().setTeam(team);

        // main loop
        while(currentIteration < iterations) {

            // selection
            Node bestNode = selection(root);
           
            // expansion
            if(bestNode.getState().equals(ONGOING)) {
                expansion(bestNode);
            }  

            Node exploreNode = bestNode;

            if(bestNode.hasChildren(exploreNode)) {
                exploreNode = bestNode.pickRandomChild(exploreNode);
            }

            int simulationResult = simulation(exploreNode);

            /** BACKPROPAGATION */
            backPropagation(exploreNode, simulationResult);




        }

        return null;
    }


    // SELECTION
    public Node selection(Node node) {

        if(node.children.size() != 0) {
            return (uct.getMaxUCT(node));
        }

        return node;

    }

    // Expansion
    public void expansion(Node node) {

        // we need to find the possible states
        ChessHelper helper = new ChessHelper();
        List<State> availibleStates = helper.getStatesFromNode(node);

        Node localNode = node;

        // iterate over the states
        // each layer flip between team and opponent
        for(State i: availibleStates){
            Node addedNode = new Node();
            addedNode.setState(i);
            addedNode.setParent(node);
            int opponent = node.getState().getOpponent(node.state.getTeam());
            addedNode.getState().setTeam( opponent );
            node.getChildren().add(addedNode);
        }


    }

    // Simulation
    public int simulation(Node node) {

        Node currentNode = node;
        State currentState = currentNode.getState();

        boolean gameStatus = currentState.isGameGoingOn();

        // while game is in progress

        // TODO !!!
        while (gameStatus) {
            // state.swithPlayer();
            helper.playRandom(node);
            // boardStatus = tempState.getBoard().machtState();
        }
    
        return 0;


    }

    // Backpropagation
    private void backPropagation(Node node, int team){

        Node localNode = node;

        while(localNode != null){
            localNode.getState().visitCount += 1;

            if(localNode.getState().getTeam() == team) {

                // TODO: how do I do scoring here...
                //node.getState().addScore(WINSCORE);
            }

            // update to parent
            localNode = localNode.getParent();

        }
    }




}