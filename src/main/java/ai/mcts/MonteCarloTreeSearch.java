package ai.mcts;

import chess.ChessMatch;
import java.util.List;

import static chess.MatchState.ONGOING;

public class MonteCarloTreeSearch {

    // score for winning a game
    int winningScore = 1;
    // uct object that contains uct methods
    UCT uct = new UCT();
    // helper methods
    ChessHelper helper = new ChessHelper();

    /**
     * Find the nextMove based on current board (fen) and current team
     *
     *
     *
     * @param fen fen that represents the board
     * @param team current team
     * @return fen-representation of updated board
     */
    public String nextMove(String fen, int team) {

        // TODO: where shall we work with 'match'  (in  state that each node has...)

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
            ChessMatch currentMatch = bestNode.getState().getMatch();
            if(currentMatch.getState().equals(ONGOING)) {
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


        Node winner = root.getMaxChild();
        tree.setRoot(winner);


        return winner.state.getFen();
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

    // TODO: value returned based on win or lose?
    // Simulation
    public int simulation(Node node) {


        // copy of node from where we run a simulation
        Node currentNode = node;

        // grab the state
        State currentState = node.getState();

        // while the game is on based on match attribute in state
        boolean isGameOn = currentState.isGameGoingOn();

        // here we can either run playRandom or heuristic play
        while (isGameOn) {
             currentState.switchPlayer();

             // this method should update the state 'after the randomPlay'
             this.helper.playRandom(currentNode);

        }

        // todo: return value of win or lose?
        return 0;


    }

    // Backpropagation
    private void backPropagation(Node node, int team){

        Node localNode = node;

        while(localNode != null){
            localNode.getState().visitCount += 1;

            if(localNode.getState().getTeam() == team) {

                // increase the score when won
                node.getState().increaseWinCount(winningScore);
            }

            // update to parent
            localNode = localNode.getParent();

        }
    }




}