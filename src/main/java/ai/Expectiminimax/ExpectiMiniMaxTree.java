package ai.Expectiminimax;

import java.util.List;

public class ExpectiMiniMaxTree implements Tree
{
    @Override
    public void generateChildren(Node node) throws NoSuchMethodException
    {
        if(node.isLeafNode())
        {
            throw new NoSuchMethodException("method not allowed");
        }

        if(node.isChanceNode())
        {
            List allMoves = node.getParent().getState().getAllPossibleMoves();
            for (int i = 0; i < allMoves.size(); i++)
            {
                ExpectiMiniMaxNode child = new ExpectiMiniMaxNode(node, i);
            }
        }

        if(node.isRootNode())
        {
            List allMoves = node.getState().getAllPossibleMoves();
            for (int i = 0; i < allMoves.size(); i++)
            {
                ExpectiMiniMaxNode child = new ExpectiMiniMaxNode(node, i);
            }
        }
    }
        @Override
        public void computeLeafNodeValues ()
        {

        }

        @Override
        public void expectiMiniMax ()
        {

        }


}


/*
the childNumber is used to calculate the state of the child (corresponds to the move in the getAllPossibleMoves() of the parent)
 */