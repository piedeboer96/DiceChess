package expecti;

import game.Opportunity;

import java.util.List;

public class test
{
    // test all possible moves from a state for a non-root node
    public static int countAllMoves(ExpectiminimaxState state)
    {
        List<Opportunity> moves = state.getAllPossibleMoves();
        int counter = 0;
        for(int i = 0; i < moves.size(); i++)
        {
            counter += moves.get(i).size();
        }
        return counter;
    }

    // test all possible moves from a state for a root node
    public static int countAllLegalMoves(ExpectiminimaxState state)
    {
        List<Opportunity> moves = state.getAllLegalMoves();
        int counter = 0;
        for(int i = 0; i < moves.size(); i++)
        {
            counter += moves.get(i).size();
        }
        return counter;
    }
}

