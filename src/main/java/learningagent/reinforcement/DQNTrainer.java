package learningagent.reinforcement;

import dice.DefaultDie;
import game.DiceChess;
import dice.Die;
import game.GameState;
import game.Movement;
import game.Opportunity;
import learningagent.database.OneHotEncoding;
import learningagent.neuralnetwork.NeuralNetwork;

import java.util.*;

/**
 * Deep Q-network reinforcement learning that uses neural network
 * to predict strength move strength.
 */

public class DQNTrainer {

    // DiceChess
    private DiceChess game;
    private DefaultDie dice;

    // Neural Network
    private NeuralNetwork nn;

    // Encoder
    private OneHotEncoding encode;

    // Q-Learning parameters
    private double epsilon = 0.1; // exploration rate
    private double learningRate = 0.1;
    private double discountFactor = 0.9;
    private int trainingEpochs = 10000;
    private int currentEpoch = 0;

    public DQNTrainer() {

        // TODO: return a trained neural network here
        nn = new NeuralNetwork();
        game = new DiceChess();
    }

    public void train() {

        while (currentEpoch < trainingEpochs) {

            // build a new game
            DiceChess game = new DiceChess();

            //TODO: what if we cannot make a move
            while (game.getState() == GameState.ONGOING) {

                // current board
                String currentBoard = game.toString();

                // black turn
                if (game.getActiveColor() == 0) {

                    // black rolls dice
                    List<Opportunity> opportunities = game.getTeamOpportunities(0, dice.roll());

                    // what if legal moves is 0
                    if (opportunities.size() == 0) {

                        // switch player
                        game.switchActiveColor();
                        continue;
                    }

                    // build all moves
                    List<Movement> allMoves = new ArrayList<>();
                    for (int i = 0; i < opportunities.size(); i++) {
                        for (int j = 0; j < opportunities.get(i).size(); j++) {
                            Movement m = opportunities.get(i).select(j);
                            // append the list of all moves
                            allMoves.add(m);
                        }
                    }


                    // select action using epsilon-greedy policy
                    Movement action;
                    if (Math.random() < epsilon) {
                        // explore --> RANDOM MOVE BASED ON EPSILON
                        // TODO: playing with epsilon allows explore/exploit balance

                        // pick random move aka action
                        action = allMoves.get((int) (Math.random() * allMoves.size()));

                    } else {
                        // exploit --> BEST MOVE
                        double maxQ = Double.NEGATIVE_INFINITY;
                        Movement bestMove = null;
                        for (Movement move : allMoves) {

                            game.register(move);
                            String FEN = game.toString();
                            String boardFen = FEN.split(" ", 2)[0];
                            double[] encodedBoardPosition = encode.oneHotEncodeSimplifiedFEN(boardFen);

                            double[] predictions = nn.predict(encodedBoardPosition);
                            double q = predictions[0];         // only want black

                            if (q > maxQ) {
                                maxQ = q;
                                bestMove = move;
                            }

                            game.revert();
                        }


                        action = bestMove;

                        // actually make the move
                        game.register(action);
                        // TODO: GAME OVER? DOES THIS SWITCH THE PLAYER?

                    }


                }

                // ADVERSARIAL TURN


                // TODO: PLAY ADVERSARIAL (OPTIMAL) MOVE * we will work from here [!]
                //game.playAdversarial;
                //game.updateBoard;

                //TODO: what if no move possible?
                String nextBoard = game.toString();
                String nextBoardFEN = nextBoard.split(" ", 2)[0];
                double[] encodeNextBoard = encode.oneHotEncodeSimplifiedFEN(nextBoardFEN);
                double[] predictNextBoard = nn.predict(encodeNextBoard);

                // reward time
                double reward = predictNextBoard[0];

                //TODO: add reward based on win/lose


                // update Q-value
                String simpleCurrentBoard = currentBoard.split(" ", 2)[0];
                double[] encodeCurrentBoard = encode.oneHotEncodeSimplifiedFEN(simpleCurrentBoard);
                double[] predictCurrentBoard = nn.predict(encodeCurrentBoard);

                double q = predictCurrentBoard[0];
                double maxQ = Double.NEGATIVE_INFINITY;

                // get again all moves
                // (we need to roll die)
                List<Opportunity> opportunities = game.getTeamOpportunities(0, dice.roll());

                // what if legal moves is 0
                if (opportunities.size() == 0) {

                    // switch player
                    game.switchActiveColor();
                    continue;       //TODO: check where we are going here...
                }

                // build all moves
                List<Movement> allMoves = new ArrayList<>();
                for (int i = 0; i < opportunities.size(); i++) {
                    for (int j = 0; j < opportunities.get(i).size(); j++) {
                        Movement m = opportunities.get(i).select(j);
                        // append the list of all moves
                        allMoves.add(m);
                    }
                }


                for (Movement nextMove : allMoves) {

                    Movement m = nextMove;
                    game.register(m);
                    String FEN = game.toString();
                    String sFEN = FEN.split(" ", 2)[0];
                    double[] encoded = encode.oneHotEncodeSimplifiedFEN(sFEN);
                    double[] predict = nn.predict(encoded);
                    double nextQ = predict[0];
                    if (nextQ > maxQ) {
                        maxQ = nextQ;
                    }
                }

                double newQ = q + learningRate * (reward + discountFactor * maxQ - q);

                // TODO : this nn update method.. spicy
                //nn.update(currentBoard, action, newQ, BLACK);


            }
        }
    }
}


