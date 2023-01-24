package montecarlo;

import game.*;
import learningagent.database.OneHotEncoding;
import learningagent.deep4j.NeuralNetwork;

import java.sql.SQLOutput;
import java.util.*;

public class MCTS {

    // Neural Network & Encoding
    private NeuralNetwork nn;
    private OneHotEncoding encoder;

    // Monte Carlo parameters
    private int numIterations;
    private int expansionParameter;


    // UCT
    UCT uct = new UCT();

    // Aux
    private static final Random RND = new Random();


    public MCTS(NeuralNetwork nn, int numIterations, int expansionParameter) {
        this.nn = nn;
        this.encoder = new OneHotEncoding();
        this.numIterations = numIterations;
        this.expansionParameter = expansionParameter;

    }

    public String solve(int roll, DiceChess game) {

        MonteCarloNode root = new MonteCarloNode(game.toString(), null, game.getActiveColor(), null);

        for (int i = 0; i < numIterations; i++) {

            MonteCarloNode leaf = selectLeaf(root);
            while (leaf != null && !leaf.isLeaf()) {
                leaf = selectChild(leaf);
            }

            if(leaf == null){
                continue;
            }
            if(expandPossible(leaf,roll,game)){
                expand(leaf, roll, game);
            } else {
                game.switchActiveColor();
                return game.toString();
            }

            int winner = simulate(leaf);
            backpropagate(leaf, winner);
        }

        return selectBestMove(root);
    }

    private MonteCarloNode selectLeaf(MonteCarloNode node) {
        while (!node.isLeaf()) {
            node = uct.selectChild(node);
        }
        return node;
    }

    public boolean expandPossible(MonteCarloNode node, int roll, DiceChess game){
        List<Opportunity> legalMoves = game.getTeamOpportunities(game.getActiveColor(), roll);
        if (legalMoves.size() == 0 || legalMoves == null) {
            return false;
        } else {
            return true;
        }
    }

    private void expand(MonteCarloNode node, int roll, DiceChess game) {

        // pruning
        if (node.getVisitCount() < expansionParameter) {
            //System.out.println("prune");
            return;
        }


        List<Opportunity> legalMoves = game.getTeamOpportunities(game.getActiveColor(), roll);

        for (Opportunity op : legalMoves) {

            for (int i = 0; i < op.size(); i++) {

                Movement move = op.select(i);

                game.register(move);
                game.switchActiveColor();

                MonteCarloNode child = new MonteCarloNode(game.toString(), node, game.getActiveColor(), move);
                node.addChild(child);

                game.revert();
            }
        }

    }

    private int simulate(MonteCarloNode node) {
        String FEN = node.getFEN();
        String boardFEN = FEN.split(" ", 2)[0];
        double[] encodedBoard = encoder.oneHotEncodeSimplifiedFEN(boardFEN);
        double winProbBlack = nn.predict(encodedBoard);
        double winProbWhite = 1-winProbBlack;

        int activeColor = node.getColor();

        if(activeColor==0){
            if (winProbBlack > 0.5) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if(winProbWhite >0.5){
                return 1;
            } else {
                return 0;
            }


        }




    }

    private void backpropagate(MonteCarloNode node, int winner) {
        while (node != null) {
            node.incrementVisitCount();
            if (node.getColor() == winner) {
                node.incrementWinCount();
            }
            node = node.getParent();
        }
    }

    private String selectBestMove(MonteCarloNode root) {
        double bestWinRate = 0;
        String bestFEN = "";
        for (MonteCarloNode child : root.getChildren()) {
            double winRate = (double) child.getWinCount() / child.getVisitCount();
            if (winRate > bestWinRate) {
                bestWinRate = winRate;
                bestFEN = child.getFEN();
            }
        }
        return bestFEN;
    }

    private MonteCarloNode selectChild(MonteCarloNode node) {
        MonteCarloNode selectedChild = null;
        double bestUCB1 = Double.NEGATIVE_INFINITY;
        for (MonteCarloNode child : node.getChildren()) {
            double ucb1 = calculateUCB1(child);
            if (ucb1 > bestUCB1) {
                bestUCB1 = ucb1;
                selectedChild = child;
            }
        }
        return selectedChild;
    }

    private double calculateUCB1(MonteCarloNode node) {
        double exploitation = (double) node.getWinCount() / node.getVisitCount();
        double exploration = Math.sqrt(2 * Math.log(node.getParent().getVisitCount()) / node.getVisitCount());
        return exploitation + exploration;
    }
}