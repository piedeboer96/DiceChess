package ai.mcts;

/**
 * Upper Confidence Bound for trees selection policy.
 */
public class UCT {

    /**
     * Calculate uct value of a given node
     * @param node node of tree
     * @return uct-value
     */
    public double uct(Node node) {

        final double c = Math.sqrt(2);

        double winCount = node.getState().getWinCount();
        double visitCount =  node.getState().getVisitCount();
        double visitCountParent = (node.getParent().getState().getVisitCount());

        if(visitCount==0){
            return Integer.MAX_VALUE;
        } else {
            return ((winCount/visitCount) + (c * Math.sqrt((Math.log(visitCountParent)) / visitCount)));
        }

    }

    /**
     * Find the max uct value of a given node
     * @param node node of tree
     * @return node with max uct
     */
    public Node getMaxUCT(Node node) {
        double maxUCT = Integer.MIN_VALUE;
        Node bestNode = node;
        for(Node i: node.children) {
            if(maxUCT < uct(i)) {
                bestNode = i;
                maxUCT = uct(i);
            }
        }
        return bestNode;
    }
}
