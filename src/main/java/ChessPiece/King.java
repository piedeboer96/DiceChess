package ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class King extends Common
{
    public int king_ID;
    private static int number = 0;

    public King(boolean isWhite)
    {
        if(isWhite)
        {
            king_ID = 10000 + number;
        }
        else
        {
            king_ID = -10000 - number;
        }
        number++;

        super.ID = king_ID;
        super.n = number;
    }



    @Override
    public List legalMoves(int x, int y)
    {
        List moves = new ArrayList<>();
            for(int i = -1; i < 2; i++)
            {
                for(int j = 1; j > -2; j--)
                {
                    try
                    {
                        int temp = field[x+i][y+j];
                        moves.add(x+i);
                        moves.add(y+j);
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {

                    }
                }
            }

        return moves;
    }

}
