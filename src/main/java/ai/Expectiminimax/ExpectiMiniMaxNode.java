package ai.Expectiminimax;

import chess.ChessMatch;

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


    public ExpectiMiniMaxNode(Node parent, int childNumber)
    {
        this.ply = parent.getPly()+1;
    this.childNumber = childNumber;
    this.parent = parent;
    isChance = !parent.isChanceNode() && !parent.isRootNode();
    children = new ArrayList<Node>();


        if(parent.getParent().getTeam() == 0)
        {
            team = 1;
        }
        if(parent.getParent().getTeam() == 1)
        {
            team = 0;
        }
    }
    public ExpectiMiniMaxNode(Node parent, int childNumber, ExpectiminimaxState state)
    {
        this.ply = parent.getPly()+1;
        this.childNumber = childNumber;
        this.parent = parent;
        isChance = !parent.isChanceNode() && !parent.isRootNode();
        children = new ArrayList<Node>();


        if(parent.getParent().getTeam() == 0)
        {
            team = 1;
        }
        if(parent.getParent().getTeam() == 1)
        {
            team = 0;
        }

        this.state = state;
    }

    public ExpectiMiniMaxNode(boolean isRoot, ExpectiminimaxState state)
    {
        this.ply = parent.getPly()+1;
        this.childNumber = 0;
        this.parent = null;
        isChance = false;
        this.isRoot = true;
        children = new ArrayList<Node>();

        team = 0;

        this.state = state;
    }

    public ExpectiMiniMaxNode(Node parent, boolean isChance)
    {
        this.ply = parent.getPly()+1;
        this.childNumber = 0;
        this.isChance = true;
        isRoot = false;
        children = new ArrayList<Node>();
        team = 2;
        state = null;
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
    public int getMaxChildValue() throws NoSuchMethodException
    {
        if(isChance || isLeaf || team == 1)
        {
            throw new NoSuchMethodException("not applicable to this node");
        }
        int currentValue = Integer.MIN_VALUE;

        for (Node child : children)
        {
            int potentialValue = child.getState().getStateEvaluation();
            if (currentValue < potentialValue) {
                currentValue = potentialValue;
            }
        }
        return currentValue;
    }

    @Override
    public int getMinChildValue() throws NoSuchMethodException
    {
        if(isChance || isLeaf || team == 2)
        {
            throw new NoSuchMethodException("not applicable to this node");
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
    public double getExpectedValue() throws NoSuchMethodException
    {
        if(!isChance || isLeaf)
        {
            throw new NoSuchMethodException("not applicable to this node");
        }
        int currentValue = 0;
        int i = 0;
        for(Node child : children)
        {
            currentValue += child.getState().getStateEvaluation();
            i++;
        }
        return (double)currentValue/(double)i;
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

    public ChessMatch getMatch() {return state.getMatch();}

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

    public void setExpecti(double value)
    {
        this.expectiValue = value;
    }

}
