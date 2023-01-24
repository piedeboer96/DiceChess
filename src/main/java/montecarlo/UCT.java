package montecarlo;

/**
 * Upper Confidence Bound for trees selection policy.
 * Balances exploration and exploitation of the tree.
 * @version 1.0
 */
public class UCT {
    /**
     * Calculates the upper confidence bound of a node
     *
     * @param node The node for which the upper confidence bound should be calculated.
     * @return A double >= 0.
     */
    private double bias = 0.5;

    public double calculateUCT(MonteCarloNode node) {
        final double c = Math.sqrt(2);
        double winCount = node.getWinCount();
        double visitCount = node.getVisitCount();
        double visitCountParent = (node.getParent().getVisitCount());

        if (visitCount == 0) {
            return Integer.MAX_VALUE;
        } else {
            return ((winCount / visitCount) + (c * Math.sqrt((Math.log(visitCountParent)) / visitCount)));
        }
    }

//    public double calculateRAVE(MonteCarloNode node) {
//        final double c = Math.sqrt(2);
//        double winCount = node.getWinCount();
//        double visitCount = node.getVisitCount();
//        double visitCountParent = (node.getParent().getVisitCount());
//        double raveWin = node.getRaveWin();
//        double raveVisit = node.getRaveVisit();
//        if (visitCount == 0) {
//            return Integer.MAX_VALUE;
//        } else {
//            return ((winCount / visitCount) + (raveWin / raveVisit) + (c * Math.sqrt((Math.log(visitCountParent)) / visitCount)));
//        }
//    }


    /**
     * Finds the leaf node with the highest upper confidence bound.
     *
     * @param node The node from where the search should be started.
     * @return The leaf node with the highest upper confidence bound.
     */
    public MonteCarloNode getMaxUCT(MonteCarloNode node) {
        double maxUCT = -1;

        MonteCarloNode bestNode = null;
        MonteCarloNode iteration = node;

        while (iteration.getChildren().size() > 0) {
            for (MonteCarloNode n : iteration.getChildren()) {
                double uct = calculateUCT(n);
                if (maxUCT < uct) {
                    bestNode = n;
                    maxUCT = uct;
                }
            }
            iteration = bestNode;
            maxUCT = -1;
        }
        return bestNode;
    }
//    public MonteCarloNode getMaxRAVE(MonteCarloNode node) {
//        double maxUCT = -1;
//
//        MonteCarloNode bestNode = null;
//        MonteCarloNode iteration = node;
//
//        while (iteration.getChildren().size() > 0) {
//            for (MonteCarloNode n : iteration.getChildren()) {
//                double RAVE = calculateRAVE(n);
//                if (maxUCT < RAVE) {
//                    bestNode = n;
//                    maxUCT = RAVE;
//                }
//            }
//            iteration = bestNode;
//            maxUCT = -1;
//        }
//        return bestNode;
//    }
//    public MonteCarloNode getMaxRAVE(MonteCarloNode node) {
//        double maxRAVE = -1;
//        MonteCarloNode bestNode = null;
//        if (!node.getChildren().isEmpty()) {
//            bestNode = node.getChildren().get(0);
//        }
//        MonteCarloNode iteration = bestNode;
//
//        while (iteration != null && iteration.getChildren().size() > 0) {
//            for (MonteCarloNode n : iteration.getChildren()) {
//                double RAVE = calculateRAVE(n);
//                if (maxRAVE < RAVE) {
//                    bestNode = n;
//                    maxRAVE = RAVE;
//                }
//            }
//            iteration = bestNode;
//            maxRAVE = -1;
//        }
//        return bestNode;
//    }
//    public MonteCarloNode getMaxRAVE(MonteCarloNode node) {
//        double maxRAVE = -1;
//        MonteCarloNode bestNode = null;
//        if (!node.getChildren().isEmpty()) {
//            bestNode = node.getChildren().get(0);
//        }
//        MonteCarloNode iteration = bestNode;
//
//        while (iteration != null && iteration.getChildren().size() > 0) {
//            for (MonteCarloNode n : iteration.getChildren()) {
//                double RAVE = calculateRAVE(n);
//                if (maxRAVE < RAVE) {
//                    bestNode = n;
//                    maxRAVE = RAVE;
//                }
//            }
//            iteration = bestNode;
//            maxRAVE = -1;
//        }
//        return bestNode;
//    }





//    public MonteCarloNode getMaxUCTRAVE(MonteCarloNode node) {
//        double maxUCT = -1;
//        double maxRAVE = -1;
//        MonteCarloNode bestNode = null;
//        MonteCarloNode iteration = node;
//
//        while (iteration.getChildren().size() > 0) {
//            for (MonteCarloNode n : iteration.getChildren()) {
//                double uct = calculateUCT(n);
//                double rave = calculateRAVE(n);
//                double value = (1 - this.bias) * uct + this.bias * rave;
//                if (value > maxUCT) {
//                    bestNode = n;
//                    maxUCT = value;
//                }
//            }
//            if (bestNode != null) {
//                iteration = bestNode;
//            }
//
//            maxUCT = -1;
//        }
//        return bestNode;
//    }

}