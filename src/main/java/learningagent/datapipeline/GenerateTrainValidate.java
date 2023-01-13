package learningagent.datapipeline;//package learningagent.datapipeline;
//
///**
// * Here we generate a training and validation set based on random split
// */
//
//import learningagent.datapipeline.GenerateRaw;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
///**
// * FOR NOW:
// * - Always consider the human player is WHITE (or a non NN-network AI)
// * - therefore work with blackWinProb, obviously this is just to progress quicker npw
// */
//
//
//public class GenerateTrainValidate {
//
//    GenerateRaw grd =  new GenerateRaw();
//    Random rand = new Random();
//    double ratio = 0.8; // use 80% of the data for training
//
//    class TrainValidate {
//        ArrayList<double[]> trainingX;
//        ArrayList<Double> trainingY;
//        ArrayList<double[]> validationX;
//        ArrayList<Double> validationY;
//
//        public TrainValidate(ArrayList<double[]> trainingX, ArrayList<Double> trainingY, ArrayList<double[]> validationX, ArrayList<Double> validationY){
//            this.trainingX = trainingX;
//            this.trainingY = trainingY;
//            this.validationX = validationX;
//            this.validationY = validationY;
//        }
//
//    }
//
//    // black = 0 , white = 1
//    public HashMap<double[], Double> getEncodedDataTeam(int numberOfGames, int team){
//
//        return grd.rawDataTeam(numberOfGames, team);
//
//    }
//
//    public TrainValidate createSplit(HashMap<double[], Double> dataTeam) {
//
//        ArrayList<double[]> trainingX = new ArrayList<>();
//        ArrayList<Double> trainingY = new ArrayList<>();
//        ArrayList<double[]> validationX = new ArrayList<>();
//        ArrayList<Double> validationY = new ArrayList<>();
//
//        for ( Map.Entry<double[], Double> entry : dataTeam.entrySet()) {
//            double[] boardPosition = entry.getKey();
//            Double winProb = entry.getValue();
//
//            if (rand.nextDouble() < ratio) {
//                trainingX.add(boardPosition);
//                trainingY.add(winProb);
//            } else {
//                validationX.add(boardPosition);
//                validationY.add(winProb);
//            }
//        }
//
//        return (new TrainValidate(trainingX,trainingY,validationX,validationY));
//
//    }
//
//
//
//
//
//}
