package montecarlo;

import game.*;
import learningagent.database.OneHotEncoding;
import learningagent.neuralnetwork.NeuralNetwork;

import java.util.*;


/**
 *
 * MONTE CARLO x NEURAL version I
 *
 * DON'T CHANGE THIS CODE.
 *
 *
 */
public class MctsNeural {

    // UCT
    private UCT uct = new UCT();

    // Neural Network & Encoding
    NeuralNetwork nn = new NeuralNetwork();
    OneHotEncoding encode = new OneHotEncoding();

    // Monte Carlo
    long numIterations = 5;


    public String solve(int roll, DiceChess game) {

        // online monte carlo
        MonteCarloNode root = new MonteCarloNode(game.toString(), null, game.getActiveColor());

        // make a move
        if (!expand(root, roll, game)) {
            game.switchActiveColor();
            return game.toString();
        } else {
            expand(root, roll, game);
        }

        // main Loop
        for(int i=0; i< numIterations; i++) {

            MonteCarloNode leaf = selection(root);

            if(!expand(leaf, game)) {
                System.out.println("can't expand.");
                break;
            }

            expand(leaf, game);

            int team = leaf.getColor();
            int winner = simulation(leaf, team);

            backPropagation(leaf, winner);

            i++;
        }

        return getMostVictoriousNode(root).getFEN();
    }

    // pick node with highest winCount
    public MonteCarloNode getMostVictoriousNode(MonteCarloNode node){

        int winCount = -1;
        MonteCarloNode bestNode = null;

        for(MonteCarloNode u: node.getChildren()) {

            if(u.getWinCount() > winCount){
                winCount = u.getWinCount();
                bestNode = u;
            }

        }

        return bestNode;

    }

    // simulation
    public int simulation(MonteCarloNode node, int team) {

        String FEN = node.getFEN();
        String boardFEN = FEN.split(" ", 2)[0];
        double[] encodedBoard = encode.oneHotEncodeSimplifiedFEN(boardFEN);
        double[] prediction = nn.predict(encodedBoard);

        if (team == 0) {

            if (prediction[0] > prediction[1]) {
                return 1;
            } else {
                return 0;
            }
        } else  {

            if (prediction[1] > prediction[0]) {
                return 1;
            } else {
                return 0;
            }
        }


    }


    /**
     * Select node based on UCT which balances exploration and exploitation.
     * @param node node from where to perform selection
     * @return best UCT based node
     */
    public MonteCarloNode selection(MonteCarloNode node) {

        // if there are no kids, pick it
        if(node.getChildren().size()==0){
            return node;
        }

        // else, pick the kid based on UCT
        return uct.getMaxUCT(node);
    }


    /**
     * Expand without die roll
     * @param node from which to expand
     * @param game game object
     * @return true if expansion is possible
     */
    public boolean expand(MonteCarloNode node, DiceChess game) {

        List<Opportunity> opportunities = game.getTeamOpportunities(game.getActiveColor());

        if(opportunities.size()==0){
            return false;
        }

        return performExpansion(node, game, opportunities);
    }

    /**
     * Expand using die roll
     * @param node the node which to expand
     * @param dieRoll die roll
     * @param game game object
     * @return true if we can expan
     */
    public boolean expand(MonteCarloNode node, int dieRoll, DiceChess game) {

        List<Opportunity> opportunities = game.getTeamOpportunities(game.getActiveColor(), dieRoll);

        if(opportunities.size()==0){
            return false;
        }

        return performExpansion(node, game, opportunities);
    }

    /**
     *
     * @param node
     * @param game
     * @param opportunities
     * @return
     */
    public boolean performExpansion(MonteCarloNode node, DiceChess game, List<Opportunity> opportunities) {

        int activeTeam = game.getActiveColor();
        double maxWinProb = -1;
        Movement bestMove = null;

        for (int i = 0; i < opportunities.size(); i++) {
            for (int j = 0; j < opportunities.get(i).size(); j++) {

                Movement m = opportunities.get(i).select(j);
                game.register(m);
                String boardFEN = game.toString().split(" ", 2)[0];
                double[] encodedBoard = encode.oneHotEncodeSimplifiedFEN(boardFEN);
                double[] prediction = nn.predict(encodedBoard);

                game.revert();
                double winProb = prediction[activeTeam];
                if (winProb > maxWinProb) {
                    maxWinProb = winProb;
                    bestMove = m;
                }

            }

            if (bestMove != null) {
                game.register(bestMove);
                MonteCarloNode newNode = new MonteCarloNode(game.toString(), node, activeTeam);
                node.addChild(newNode);
                game.revert();
            }

        }

        return true;
    }


    /**
     * Backpropagation that updates winCount and visitCount accordingly.
     * @param bottomNode down in the tree, from where we want to go back (prop)
     * @param winningTeam the team that wins and gets a point in every layer
     */
    private void backPropagation(MonteCarloNode bottomNode, int winningTeam){
        MonteCarloNode tempNode = bottomNode;

        while(tempNode != null) {
            tempNode.incrementVisitCount();

            if(tempNode.getColor() == winningTeam) {
                // increase the score for the team that won
                tempNode.incrementWinCount();
            }

            // update to parent
            tempNode = tempNode.getParent();

        }
    }


}