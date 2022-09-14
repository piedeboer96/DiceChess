package ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Common
{
    public int pawn_ID;
    private static int number = 0;

    public Pawn(boolean isWhite)
    {
        if(isWhite)
        {
            pawn_ID = 100 + number;
        }
        else
        {
            pawn_ID = -100 - number;
        }
        number++;

        super.ID =  pawn_ID;
        super.n = number;
    }

    @Override
    public List legalMoves(int x, int y)
    {
        List moves = new ArrayList<>();

        if(pawn_ID < 0)
        {
            try
            {
                int temp = field[x-1][y-1];
                moves.add(x-1);
                moves.add(y-1);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {

            }

        }
        else
        {
            try
            {
                int temp = field[x+1][y+1];
                moves.add(x+1);
                moves.add(y+1);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {

            }
        }
        return moves;
    }
}
