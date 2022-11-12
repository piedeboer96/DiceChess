package ai.mcts;

import chess.ChessMatch;
import java.util.List;

import static chess.MatchState.ONGOING;

public class MonteCarloTreeSearch {

    //int winScore = 1;
    UCT uct = new UCT();


    /**
     * Find the nextMove based on current board (fen) and current team
     * @param fen fen that represents the board
     * @param team current team
     * @return fen-representation of updated board
     */
    public String nextMove(String fen, int team) {

        // TODO: where shall we work with match?

        // basic attributes
        int opponent;
        int currentIteration = 0;
        int iterations = 10000;

        // assign opponent
        if(team==1){
            opponent = 2;
        } else {
            opponent = 1;
        }

        // build the search tree
        Tree tree = new Tree();
        Node root = tree.getRoot();

        // represent board and set the team
        root.getState().setFen(fen);
        root.getState().setTeam(team);

        // attach match to node
        ChessMatch match = new ChessMatch(fen);
        root.getState().setMatch(match);

        // main loop
        while(currentIteration < iterations) {

            // selection - find a promising node
            Node bestNode = selection(root);

            // expansion - (looks ugly now...)
            if(bestNode.getState().getMatch().getState().equals(ONGOING)) {
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

        //Node winner = root.getMaxChild();
        //tree.setRoot(winner);


        //TODO: return fen of winner node
        return null;
    }


    // Selection
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
        List<State> availableStates = helper.getStatesFromNode(node);

        // iterate over the states
        // each layer flip between team and opponent
        for(State i: availableStates){
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
            // state.switchhPlayer();
//            helper.playRandom(node);
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