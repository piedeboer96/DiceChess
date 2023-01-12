package expecti;

import game.DiceChess;

public interface Tree
{
    /**
     * the depth consists of 3 layers (ply's): 1 max node, 1 min node and 1 chance node
     */
    public final static int depth = 2;

    /**
     * the max number of layers for a given depth
     */
    public final int max_ply = ((depth-1)*4)+2;


    /**
     * the generateChildren(Node node) method generates all the children of the of a given Node object
     * @param node the Node object, we want to generate the children of
     * @throws IllegalStateException
     */
    public void generateChildren(Node node);

    /**
     * the computeLeafNodeValues() implements the evaluation function on the leaf nodes, to evaluate them
     */
    public void computeLeafNodeValues();

    /**
     * the expectiMiniMax() method passes the evaluation values of the leaf nodes up the tree, using the expectiminimax
     * algorithm
     * @throw
     */
    public String expectiMiniMax();


    /**
     * generates the tree used for the expectiminimax, containing all possible moves, without pruning yet
     * @param root the root node of the tree
     * @throws IllegalStateException
     */
    public void generateTree(Node root) ;

    /**
     *  This method returns the desired match state converted in FEN, this desired state is reached, by
     *  playing the optimal move
     * @param root the move must be played from an initial state, this is the Node root parameter
     * @return a FEN representation of the desired state
     */
    public String getFinalFen(Node root);

    /**
     * the leafCheck(Node node) checks the the specified node is a leaf node
     * @param node the node that must be checked
     * @return a boolean value representing if the node is a leaf node
     */
    public boolean leafCheck(Node node);

    /**
     * the toString(DiceChess match) converts the specified Dicechess obeject to its FEN representation, calls upon the,
     * toString method in DiceChess class
     * @param match the match that needs to be converted to a FEN representation
     * @return a FEN representation of the specified match
     */
    public String toString(DiceChess match);
}
