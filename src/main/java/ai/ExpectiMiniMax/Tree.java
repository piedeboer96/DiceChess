package ai.ExpectiMiniMax;

import java.util.List;

public interface Tree
{
    public final static int depth = 2;
    public final float probability = (1/6);
    public void generateChildren(Node node) throws NoSuchMethodException;
    public void computeLeafNodeValues();
    public void expectiMiniMax();
}
