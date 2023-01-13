package learningagent.neuralnetwork;//package learningagent.neuralnetwork;
//
//import learningagent.datapipeline.GenerateTrainValidate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SimpleNetwork {
//
//    static GenerateTrainValidate g = new GenerateTrainValidate();
//    public static void main(String[] args) {
//
//        // build simple network
//        ActivationFunction SigmoidActivationFunction = new SigmoidActivationFunction();
//        NeuralNetwork nn = new NeuralNetwork(384,256,1, SigmoidActivationFunction);
//        double learningRate = 0.01;
//
//        // get black team data
//        HashMap<double[], Double> blackData = g.getEncodedDataTeam(10000,0);
//
//        // iterate over the hashMap
//        for (Map.Entry<double[], Double> entry : blackData.entrySet()) {
//
//            double[] boardPosition = entry.getKey();
//            double blackWP = entry.getValue();
//
//            // do something with the position and value
//
//            double error = nn.train(boardPosition, blackWP, learningRate);
//            //  double predict = nn.predict(boardPosition);
//
//            System.out.println("actual: " + blackWP + " predict: " + nn.predict(boardPosition));
//
//            System.out.println(error);
//        }
//
//
//
//
//
//    }
//
//
//}
