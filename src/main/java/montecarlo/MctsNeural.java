package montecarlo;

import game.*;
import learningagent.database.OneHotEncoding;
import learningagent.neuralnetwork.NeuralNetwork;
import player.Darwin;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

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

    /** MONTE CARLO TREE SEARCH **/

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


    // Selection
    public MonteCarloNode selection(MonteCarloNode node) {

        // if there are no kids, pick it
        if(node.getChildren().size()==0){
            return node;
        }

        // else, pick the kid based on UCT
        return uct.getMaxUCT(node);
    }


    /** Expand with or without roll... */
    public boolean expand(MonteCarloNode node, DiceChess game) {

        List<Opportunity> opportunities = game.getTeamOpportunities(game.getActiveColor());

        if(opportunities.size()==0){
            return false;
        }

        return performExpansion(node, game, opportunities);
    }

    public boolean expand(MonteCarloNode node, int dieRoll, DiceChess game) {

        List<Opportunity> opportunities = game.getTeamOpportunities(game.getActiveColor(), dieRoll);

        if(opportunities.size()==0){
            return false;
        }

        return performExpansion(node, game, opportunities);
    }

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








    // Expansion with die roll

    /**
     * Backpropagation that updates wincount and visitcount accordingly.
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


    // RETURN LIST OF SORTED MOVEMENTS FOR EXPANSION
    public List<Movement> informedRollout(List<Opportunity> opportunities, DiceChess game, int team){

        HashMap<Movement, Double> map = new HashMap<Movement, Double>();

        for (int i = 0; i < opportunities.size(); i++) {
            for (int j = 0; j < opportunities.get(i).size(); j++) {

                // get the movement
                Movement m = opportunities.get(i).select(j);

                game.register(m);

                // use the neural prediction
                String FEN = game.toString();
                String boardFEN = FEN.split(" ", 2)[0];
                double[] encodedBoard = encode.oneHotEncodeSimplifiedFEN(boardFEN);
                double winProb = nn.predict(encodedBoard)[team];

                // put in the map
                map.put(m,winProb);

                // revert
                game.revert();
            }
        }

//        printMap(map);


        // return list of sorted movements
        List<Movement> sortedMovements = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        return sortedMovements;




    }

    /** New Methods */
    private String getBestMove(MonteCarloNode root) {
        MonteCarloNode bestChild = null;
        double bestWinRate = -1;
        for (MonteCarloNode child : root.getChildren()) {
            double winRate = child.getWinCount() / child.getVisitCount();
            if (winRate > bestWinRate) {
                bestWinRate = winRate;
                bestChild = child;
            }
        }
        return bestChild.getFEN();
    }








}