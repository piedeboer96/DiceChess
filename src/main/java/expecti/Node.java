package expecti;

import game.DiceChess;

import java.util.List;

public interface Node {
    int MAX_OPTIONS = 6;

    /**
     * isChanceNode() is a boolean method that returns if the current node is a chance node
     * @return boolean value, indicating if the current node is a chance node
     */
    public boolean isChanceNode();

    /**
     * isLeafNode() is a boolean method that returns if the current node is a leaf node
     * @return boolean value, indicating if the current node is a leaf node
     */
    public boolean isLeafNode();

    /**
     * isRootNode() is a boolean method that returns if the current node is a root node
     * @return boolean value, indicating if the current node is a root node
     */
    public  boolean isRootNode();


    /**
     * getChildren() is a method that returns all the Children of the current node
     * @return a List<Node> containing all the child nodes of the current node
     */
    public List<Node> getChildren();

    /**
     * getMaxChildValue() is a method, that chooses the maximum evaluation score of all the children nodes for the current
     * node (only used for the root node)
     * @return an int that is the maximum evaluation score the current node can pick
     * @throws IllegalStateException
     */
    public double getMaxChildValue();


    /**
     * getMinChildValue() is a method, that chooses the minimum evaluation score of all the children nodes for the current
     * node (not used)
     * @return an int that is the minimum evaluation score the current node can pick
     * @throws IllegalStateException
     */
    public int getMinChildValue();


    /**
     * getExpectedValue() is a method, that calculates the mean of all the child node values (used for the chance nodes)
     * @return an int, that is the mean value of all the children node evaluation values
     * @throws IllegalStateException
     */
    public double getExpectedValue();


    /**
     * getParent() is a method, that is used to get the parent of the current node
     * @return a Node object, that is the parent of the current node
     */
    public Node getParent();


    /**
     * getState() is a method, that returns the state of the current node, used for the evaluation and child generation,
     * see 'State'
     * @return the State object of the current noide
     */
    public State getState();

    /**
     * getTeam() is a method, that returns the max (player) or min (opponent) or chance
     * @return 0 for max, 1 for min, 2 for chance
     */
    public int getTeam();

    /**
     * getMatch() gets the DiceChess Object at the current node, used by the state object
     * @return DiceChess Object at the current node
     */
    public DiceChess getMatch();

    /**
     *  addChild(Node newNode) method adds a Node object to the children list of the current node
     * @param newNode the node being added to the children of the current node
     */
    public void addChild(Node newNode);


    /**
     * getPly() method gets the layer of the current node within the expectiminimax tree
     * @return an int representing the current layer
     */
    public int getPly();

    /**
     * The setExpecti(double value) sets the value of the current node, determined by the chance node, expected value or
     * max value. Used by the tree search
     * @param value the value being assigned to the current node
     */
    public void setExpecti(double value);

    /**
     * The getExpectiValue() gets the value of the current chance node.
     * @return the value of the current node
     */
    public double getExpectiValue();


    /**
     * the getChildNumber() gets the the number of the child in order of which it was created,
     * This makes it easy to determine what move is made to go from the parent node to this child node
     * @return the number of the child in order
     */
    public int getChildNumber();

    /**
     * the setLeaf() method sets the current node to be recognized as a leaf, useful for the
     * ExpectiMiniMax algorithm.
     */
    public void setleaf();
}
