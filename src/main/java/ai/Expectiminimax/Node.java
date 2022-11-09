package ai.Expectiminimax;

import java.util.List;

public interface Node
{
    public boolean isChanceNode();
    public boolean isLeafNode();
    public  boolean isRootNode();
    public List<Node> getChildren();
    public int getMaxChildValue() throws NoSuchMethodException;
    public int getMinChildValue() throws NoSuchMethodException;
    public double getExpectedValue() throws NoSuchMethodException;
    public Node getParent();

    public State getState();
    public int getTeam();
}
