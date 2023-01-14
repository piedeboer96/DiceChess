package expecti;

import game.DiceChess;

import java.util.ArrayList;
import java.util.List;

public class ExpectiMiniMaxNode implements Node
{
    Node parent;
    int ply;
    boolean isChance;
    boolean isRoot;
    boolean isLeaf;
    List<Node> children;

    double expectiValue;
    ExpectiminimaxState state;

    int childNumber;

    int team;


    /**
     * This node is used to construct max/min nodes, excluding the root node
     * @param parent the parent of the constructed node
     * @param childNumber the childNumber is used to calculate the state of the child (corresponds to the move in the getAllPossibleMoves() of the parent)
     * @param state the match state of the constructed node
     */
    public ExpectiMiniMaxNode(Node parent, int childNumber, ExpectiminimaxState state)
    {
        this.ply = parent.getPly()+1;
        this.childNumber = childNumber;
        this.parent = parent;
        isChance = !parent.isChanceNode() && !parent.isRootNode();
        children = new ArrayList<Node>();


        if(parent.isRootNode())
        {
            team = 1;
        }
        else
        {
            if(parent.getParent().getTeam() == 0)
            {
                team = 1;
            }
            if(parent.getParent().getTeam() == 1)
            {
                team = 0;
            }
        }
        this.state = state;
    }

    /**
     * This node is used to construct the root node
     * @param isRoot always set to True, used to identify the root node constructor
     * @param state the match state of the constructed node
     */
    public ExpectiMiniMaxNode(boolean isRoot, ExpectiminimaxState state)
    {
        this.ply = 1;
        this.childNumber = 0;
        this.parent = null;
        isChance = false;
        this.isRoot = true;
        children = new ArrayList<Node>();

        team = 0;

        this.state = state;
    }

    /**
     * This node is used to construct the chance node
     * @param isChance always set to True, used to identify the chance node constructor
     * @param parent the parent of the constructed node
     */
    public ExpectiMiniMaxNode(Node parent, boolean isChance)
    {
        this.ply = parent.getPly()+1;
        this.childNumber = 0;
        this.isChance = true;
        isRoot = false;
        children = new ArrayList<Node>();
        team = 2;
        state = null;
        this.parent = parent;
    }


    @Override
    public boolean isChanceNode()
    {
        return isChance;
    }

    @Override
    public boolean isLeafNode() {
        return isLeaf;
    }

    @Override
    public boolean isRootNode() {
        return isRoot;
    }

    @Override
    public List<Node> getChildren()
    {
        return children;
    }

    @Override
    public double getMaxChildValue()
    {
        if(isChance || isLeaf || team == 1)
        {
            throw new IllegalStateException("not applicable to this node");
        }
        double currentValue = Integer.MIN_VALUE;

        for (Node child : children)
        {
            double potentialValue = (child.getExpectiValue());
            if (currentValue < potentialValue) {
                currentValue = potentialValue;
            }
        }
        return currentValue;
    }

    @Override
    public int getMinChildValue()
    {
        if(isChance || isLeaf || team == 2)
        {
            throw new IllegalStateException("not applicable to this node");
        }
        int currentValue = Integer.MAX_VALUE;

        for (Node child : children)
        {
            int potentialValue = child.getState().getStateEvaluation();
            if (currentValue > potentialValue)
            {
                currentValue = potentialValue;
            }
        }
        return currentValue;
    }

    @Override
    public double getExpectedValue()
    {
        if(!isChance || isLeaf)
        {
            throw new IllegalStateException("not applicable to this node");
        }
        double currentValue = 0;
        for(Node child : children)
        {
            currentValue += child.getState().getStateEvaluation();
        }
        return currentValue/MAX_OPTIONS;
    }

    @Override
    public Node getParent()
    {
        return parent;
    }

    @Override
    public State getState() {
        return state;
    }

    public DiceChess getMatch() {return state.getMatch();}

    @Override
    public void addChild(Node newNode)
    {
        children.add(newNode);
    }


    @Override
    public int getPly() {
        return ply;
    }

    @Override
    public int getTeam() {
        return team;
    }


    @Override
    public void setExpecti(double value)
    {
        if(isChance)
        {
            parent.setExpecti(value);
        }
        this.expectiValue = value;
    }


    @Override
    public double getExpectiValue(){return this.expectiValue;}

    @Override
    public int getChildNumber(){return this.childNumber;}

    @Override
    public void setleaf(){this.isLeaf = true;}

}
