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
    public double calculate(MonteCarloNode node) {
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
                double uct = calculate(n);
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
}
