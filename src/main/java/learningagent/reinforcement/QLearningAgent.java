package learningagent.reinforcement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import game.*;
import genetic.Chromosome;
import genetic.FitnessFunction;
import learningagent.database.OneHotEncoding;
import learningagent.deep4j.NeuralNetwork;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 *
 * Q-Learning
 *
 *      • The model predicts all the Q-values for all actions from a given state.
 *      • Either action with highest Q-value is chosen (exploit) or random (explore)
 *        using epsilon greedy policy [bandit approach]
 *
 *    Further explanation:
 *          - Move is chosen according to policy based epsilon greedy
 *          - q-value neural network learns is actually long term award
 *          - The update is done using the Bellman equation, which expresses the Q-value
 *            of a state-action pair in terms of the immediate reward
 *            and the maximum Q-value of the next state (predicted with value network)
 *
 */
public class QLearningAgent {

    // Q-Learning & Network
    private MultiLayerNetwork qFunction;
    OneHotEncoding hotEncode = new OneHotEncoding();
    private double learningRate=0.01;
    private double discountFactor=0.99;
    private double epsilon =0.01;
    Random rand = new Random();

    // Genetic Evaluation
    private final FitnessFunction FUNCTION = new FitnessFunction();
    private final Chromosome CHROMOSOME;

    // Reward scaling
    private double oc = 50;
    private double mc = 0.01;

    // Value Network
    NeuralNetwork nn = new NeuralNetwork();

    public QLearningAgent(MultiLayerNetwork qFunction, double learningRate, double discountFactor, Chromosome chromosome) {
        this.qFunction = qFunction;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        CHROMOSOME = chromosome;
    }

    /**
     * Run a q-learning iteration
     * for a single game.
     * @param game
     */
    public void qGame(DiceChess game) {

        // reward
        double reward;

        // game termination
        boolean whiteWINS=false, blackWINS=false, draw=false;

        // Q(s,a)
        double currentQValue;

        // qFunction
        INDArray input, output;

        // indices of actions
        int actionWHITE=0,actionBLACK=0;

        outerloop:
        while (game.getState() == GameState.ONGOING) {

            /** STEP 1: Legal Moves                                     */
            List<Movement> legalMovesWHITE = getLegalMoves(game.getTeamOpportunities(1));
            List<Movement> legalMovesBLACK = new ArrayList<>();

            // actual q-values
            double[] actualQs = new double[legalMovesWHITE.size()];

            /** STEP 2: Q-value network                                  */
            double maxValueWHITE = Double.NEGATIVE_INFINITY;
            double[] boardWHITE = getEncodedBoard(game.toString());
            double evalWHITE = FUNCTION.evaluate(CHROMOSOME, game);

            // q(s,a) prediction
            input = Nd4j.create(boardWHITE);
            output = qFunction.output(input);

            double maxQblack = 0.00;

            int k =0;

            // q-learning
            for (int i = 0; i < legalMovesWHITE.size(); i++) {

                /** STEP 3: Predict Q(S,A) with Neural QFunction                 */
                currentQValue = output.getDouble(i);

                if (currentQValue > maxValueWHITE) {
                    maxValueWHITE = currentQValue;
                    actionWHITE = i;
                }

                // calculate the reward
                game.register(legalMovesWHITE.get(i));
                game.switchActiveColor();

                // check if move terminates game
                if (game.getState() == GameState.WHITE_WON) {

                    //System.out.println("whiteWon");

                    // reward
                    reward = 1 * oc;
                    whiteWINS = true;

                    // revert
                    game.revert();

                    // update Q
                    maxQblack = 0;
                    currentQValue = currentQValue  + learningRate*(reward + discountFactor * (maxQblack - currentQValue));
                    actualQs[i] = currentQValue;

                    break outerloop;

                } else if (game.getState() == GameState.BLACK_WON) {

                    //System.out.println("blackWon");

                    // reward
                    reward = -1 * oc;
                    blackWINS = true;

                    // revert
                    game.revert();

                    // update q
                    maxQblack = 0;
                    currentQValue = currentQValue  + learningRate*(reward + discountFactor * (maxQblack - currentQValue));
                    actualQs[i] = currentQValue;

                    break outerloop;


                } else if (game.getState() == GameState.DRAW){

                    //System.out.println("draw");

                    // reward
                    reward = 0;
                    draw = true;

                    // revert
                    game.revert();

                    // currentQ
                    maxQblack = 0;
                    currentQValue = currentQValue  + learningRate*(reward + discountFactor * (0.0 - currentQValue));
                    actualQs[i] = currentQValue;

                    break outerloop;

                } else {

                    double materialNEXT = FUNCTION.evaluate(CHROMOSOME, game);
                    reward = (evalWHITE - materialNEXT) * mc;

                }

                // calculate the q-value
                /** STEP 5: Moves black                               */
                legalMovesBLACK = getLegalMoves(game.getTeamOpportunities(game.getActiveColor()));

                /** STEP 6: Q(S',A') from value network               */
                // legal moves black
                for (int j = 0; j < legalMovesBLACK.size(); j++) {

                    // value network (trained)
                    String FEN = game.toString();
                    String boardFEN = FEN.split(" ", 2)[0];
                    double prediction = nn.predict(hotEncode.oneHotEncodeSimplifiedFEN(boardFEN));
                    double qBlack = prediction;

                    // update if best q is found
                    if(maxQblack<qBlack){
                        maxQblack = qBlack;
                        //actionBLACK = j;
                        actionBLACK = k;
                    }

                }

                // currentQ
                currentQValue = currentQValue  + learningRate*(reward + discountFactor * (maxQblack - currentQValue));
                actualQs[i] = currentQValue;

                // revert
                game.revert();

                // all moves counter
                k++;

            }

            /** STEP 8: Reshape         */
            int size = 80;
            if (actualQs.length < size) {
                actualQs = Arrays.copyOf(actualQs, size);
                Arrays.fill(actualQs, actualQs.length - size, actualQs.length, 0.0);
            }
            int[] shape = {1, size};
            output = Nd4j.create(actualQs, shape);

            /** STEP 9: Train           ----*/
            //System.out.println("train");
            qFunction.fit(input, output);
            //System.out.println("output: " + output);


            /** Step 10: Play Based on Epsilon-Greedy */

            //== white ===

            // double check
            if(actionWHITE>legalMovesWHITE.size()){

                int rndMove = (int) (Math.random() * legalMovesWHITE.size());
                game.register(legalMovesWHITE.get(rndMove));
                game.switchActiveColor();

            } else {

                // explore
                if(Math.random() < epsilon) {

                    int rndMove = (int) (Math.random() * legalMovesWHITE.size());
                    game.register(legalMovesWHITE.get(rndMove));
                    game.switchActiveColor();

                } else {

                    // exploit
                    game.register(legalMovesWHITE.get(actionWHITE));
                    game.switchActiveColor();

                }



            }


            // === black ===

            // explore

//            if(Math.random() < epsilon){
                legalMovesBLACK = getLegalMoves(game.getTeamOpportunities(0));
                int rndMove = (int) (Math.random() * legalMovesBLACK.size());
                game.register(legalMovesBLACK.get(0));
                game.switchActiveColor();

                // exploit
//            } else {
//
//
//                game.register(legalMovesBLACK.get(k));
//                game.switchActiveColor();
//
//            }



        }


        System.out.println("TERMINATED");


    }

    public QLearningAgent(Chromosome chromosome) throws IOException {
        CHROMOSOME = chromosome;
        loadTrainedNetwork("path/to/save/trained_network.zip");
    }

    /** Q Function Neural Network */
    /**
     * Load the neural network.
     * @param filePath
     * @return
     * @throws IOException
     */
    public MultiLayerNetwork loadTrainedNetwork(String filePath) throws IOException {
        File locationToSave = new File(filePath);
        MultiLayerNetwork trainedNetwork = ModelSerializer.restoreMultiLayerNetwork(locationToSave);
        return trainedNetwork;
    }
    /**
     * Train and save the neural network.
     * @param numGames
     * @throws IOException
     */
    public void trainAndSaveNetwork(int numGames) throws IOException {
        // Initialize Q-function
        QFunction q = new QFunction();
        qFunction = q.getQFunction();

        for (int i = 0; i < numGames; i++) {
            DiceChess game = new DiceChess();
            qGame(game);
        }
        // Save the trained network
        File locationToSave = new File("path/to/save/trained_network.zip");
        locationToSave.getParentFile().mkdirs();
        ModelSerializer.writeModel(qFunction, locationToSave, true);
    }
    /**
     * Generate one-hot encoded board-fen for input to neural with 384 input-nodes
     *
     * @param FEN FEN
     * @return
     */
    public double[] getEncodedBoard(String FEN) {
        String boardFEN = FEN.split(" ", 2)[0];
        double[] encodedBoard = hotEncode.oneHotEncodeSimplifiedFEN(boardFEN);
        return encodedBoard;
    }


    /** AGENT */
    /**
     * To be used as simulator
     */
    public String getAgentQMove(DiceChess game, int roll) throws IOException {

        this.qFunction = loadTrainedNetwork("path/to/save/trained_network.zip");

        // ideal world jup (loaded Q-network)
        double[] encodedCurrentState = getEncodedBoard(game.toString());
        INDArray input = Nd4j.create(encodedCurrentState);
        INDArray output = qFunction.output(input);

        List<Opportunity> opportunities = game.getTeamOpportunities(game.getActiveColor(), roll);
        List<Movement> legalMoves = getLegalMoves(opportunities);

        if(opportunities.size()==0){
            game.switchActiveColor();
            return game.toString();
        } else {
            int action = 0;
            double maxValue = Double.NEGATIVE_INFINITY;
            for(int i = 0; i < legalMoves.size(); i++) {
                double qValue = output.getDouble(i);
                if(qValue > maxValue) {

                    maxValue = qValue;
                    action = i;
                }
            }

//            System.out.println(maxValue);
            Movement finalMove = legalMoves.get(action);
            game.register(finalMove);
            game.switchActiveColor();

            return game.toString();
        }
    }
    /**
     * Based on all movement opportunities we get a list of all legal moves.
     *
     * @param opportunities list of movement opportunities
     * @return legalMoves
     */
    public List<Movement> getLegalMoves(List<Opportunity> opportunities) {

        ArrayList<Movement> allMoves = new ArrayList<>();

        for (int i = 0; i < opportunities.size(); i++) {
            Opportunity op = opportunities.get(i);

            for (int j = 0; j < op.size(); j++) {
                allMoves.add(op.select(j));
            }

        }

        return allMoves;
    }

    /** MAIN METHOD */
    public static void main(String[] args) throws IOException {

        QFunction qf = new QFunction();
        MultiLayerNetwork q = qf.getQFunction();

        // Genetic
        final Chromosome CHROMOSOME = new Chromosome();

        // Training
        QLearningAgent qAgent = new QLearningAgent(q, 0.01, 0.95, CHROMOSOME);
        qAgent.trainAndSaveNetwork(100);




    }



}


