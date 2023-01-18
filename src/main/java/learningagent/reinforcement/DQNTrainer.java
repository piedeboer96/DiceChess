package learningagent.reinforcement;

import dice.DefaultDie;
import game.DiceChess;
import game.GameState;
import game.Movement;
import game.Opportunity;
import learningagent.database.OneHotEncoding;
import learningagent.neuralnetwork.NeuralNetwork;

import java.sql.SQLOutput;
import java.util.*;

/**
 * Deep Q-network reinforcement learning that uses neural network
 * to predict strength move strength.
 *
 * How it works:
 *
 *      The Q-learning process uses the predictions of existing trained neural network ass starting point.
 *      It updates the weights of the network based on the observed rewards and the Q-values calculated from the Q-function.
 *
 *      By fine-tuning the pre-trained neural network during the Q-learning process,
 *      the agent can gain stronger performance than the existing neural network,
 *      as it adapts to the specific characteristics of the problem it is trying to solve.
 *
 * More information:
 *
 *      The `train()` method checks for the game state after each move using the statement
 *      "game.getState()==(...)" and assigns a reward of 1.0 or -1.0 respectively if the game is won or lost.
 *      If the game is ongoing, the agent uses the win probability predicted by the neural network as the reward.
 *      The method also saves the trained values after each 100 training epochs using the saveModel() method,
 *      it also stops the training after a certain number of training epochs.
 *
 */

// TODO:
//      (1) Do i correctly included 'REWARDS' [I think yes.]
//      (2) is the no-moves element correctly done
//      (3) make the adversarial 'genetic'
//      (4) update or not-update based on adversarial moves

public class DQNTrainer {

    // DiceChess
    private DiceChess game;
    private DefaultDie dice = new DefaultDie();
    private final Random RND = new Random();

    // Neural Network
    private NeuralNetwork nn;

    // Encoder
    private OneHotEncoding encode = new OneHotEncoding();

    // Q-Learning parameters
    private double epsilon = 0.1; // exploration rate
    private double learningRate = 0.1;
    private double discountFactor = 0.9;
    private int trainingEpochs = 10000;
    private int currentEpoch = 0;

    // Constructor
    public DQNTrainer() {

        nn = new NeuralNetwork();           // get a trained neural network
        game = new DiceChess();             // a fresh game

    }

    // Train method -- train for white
    public void train() {

        // number of iterations
        while (currentEpoch < trainingEpochs) {

            // build a new game
            DiceChess game = new DiceChess();

            // currentBoard;
            String currentBoard = game.toString();

            // game loop
            while (game.getState() == GameState.ONGOING) {

                // current board
                currentBoard = game.toString();

//                System.out.println("start: " + game.getActiveColor());
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

                        // NOTE:
                        //      In this phase, you are not updating the network weights because you are trying to explore new states and actions,
                        //      rather than exploiting the knowledge that the network already has.

                        // pick random move aka action
                        action = allMoves.get((int) (Math.random() * allMoves.size()));

                        // register the action
                        game.register(action);
                        game.switchActiveColor();

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
                            double q = predictions[0];         // only want white

                            if (q > maxQ) {
                                maxQ = q;
                                bestMove = move;
                            }

                            game.revert();
                        }

                        action = bestMove;

                        // register action          [MIGHT BREAK OUT OF LOOP, UPDATE AS NEEDED]
                        game.register(action);
                        game.switchActiveColor();

                        // update the neural network
                        nn.update(currentBoard, maxQ,  0);

                    }


                }

                // adversarial turn
//                System.out.println("always 1: " + game.getActiveColor());

                var opportunities = game.getTeamOpportunities(game.getActiveColor(), dice.roll());
                if (opportunities.size()>0) {
                    int randomOpportunityIndex = RND.nextInt(opportunities.size());
                    Opportunity opp = opportunities.get(randomOpportunityIndex);
                    int randomMovementIndex = RND.nextInt(opp.size());
                    Movement m = opp.select(randomMovementIndex);
                    System.out.println(game.getActiveColor());
                    game.register(m);
                    game.switchActiveColor();
                    // HERE WE DON'T UPDATE - we do however give a fix fine when we loose!
                } else {
                    // no moves available
                    game.switchActiveColor();       // switch
                    continue;                       // go back to start
                }

                // we need to stay in the adversarial it's turn...  (we get in this code if their was no game over..)

                String nextBoard = game.toString();
                String nextBoardFEN = nextBoard.split(" ", 2)[0];
                double[] encodeNextBoard = encode.oneHotEncodeSimplifiedFEN(nextBoardFEN);
                double[] predictNextBoard = nn.predict(encodeNextBoard);

                // check for game state and assign reward (migh
                double reward = predictNextBoard[0];

                // update Q-value
                String simpleCurrentBoard = currentBoard.split(" ", 2)[0];
                double[] encodeCurrentBoard = encode.oneHotEncodeSimplifiedFEN(simpleCurrentBoard);
                double[] predictCurrentBoard = nn.predict(encodeCurrentBoard);

                double q = predictCurrentBoard[0];
                double maxQ = Double.NEGATIVE_INFINITY;

                // get again all moves
                opportunities = game.getTeamOpportunities(0, dice.roll());

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

                //System.out.println("here 0 " + game.getActiveColor() );

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

                    game.revert();

                }

                // THE RE

                double newQ = q + learningRate * (reward + discountFactor * maxQ - q);


                // NOTE:
                //  In the update method, the newQ value captures the information
                //  about the action that was taken, as well as the new Q-value for that action.
                //  So, you don't need to pass the action as an argument to the update method,
                //  since the newQ value already contains that information
                //
                int black = 0;
                nn.update(currentBoard, newQ, black);


            }

            // GAME IS OVER
            double reward;
            double newQ;

            // reward based on win-lose
            if(game.getState() == GameState.BLACK_WON) {
                reward =  1.0;
            } else if(game.getState() == GameState.WHITE_WON){
                reward =  -1.0;
            } else {        // DRAW
                reward = 0.0;
            }

            //NOTE:
            // In this case, since the game is over, there is no next state and no next Q-value.
            // Set the nextQ to the final reward.

            // update the neural accordingly
            newQ = reward;
            System.out.println(" FINISH: " + reward);

            nn.update(currentBoard, newQ, 0);

            // ONE TRAINING OVER
            currentEpoch++;
            if (currentEpoch % 100 == 0) {
                // save model every 100

                //TODO: need to add this method...
                //nn.saveModel();
            }
        }


    }



    // Test the training =)
    public static void main(String[] args) {
        DQNTrainer dqnTrainer = new DQNTrainer();
        dqnTrainer.train();
    }
}


