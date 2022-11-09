package ai.Expectiminimax;

import java.util.ArrayList;
import java.util.List;

public class ExpectiMiniMaxNode implements Node
{
    Node parent;
    boolean isChance;
    boolean isRoot;
    boolean isLeaf;
    List<Node> children;

    State state;

    int childNumber;

    int team;


    public ExpectiMiniMaxNode(Node parent, int childNumber)
    {
    this.childNumber = childNumber;
    this.parent = parent;
    isChance = !parent.isChanceNode() && !parent.isRootNode();
    children = new ArrayList<Node>();

        if(isChance)
        {
            team = 2;
        }
        if(parent.isRootNode())
        {
            if(parent.getTeam() == 0)
            {
                team = 1;
            }
            if(parent.getTeam() == 1)
            {
                team = 0;
            }
        }
        else if(parent.getParent().getTeam() == 0)
        {
            team = 1;
        }
        else if(parent.getParent().getTeam() == 1)
        {
            team = 0;
        }
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
        if(isChance || isLeaf)
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
        if(isChance || isLeaf)
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

    @Override
    public int getTeam() {
        return team;
    }


}
