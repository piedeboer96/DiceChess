package ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Common
{
    public int bishop_ID;
    private static int number = 0;

    public Bishop(boolean isWhite)
    {
        if(isWhite)
        {
            bishop_ID = 300 + number;
        }
        else
        {
            bishop_ID = -300 - number;
        }
        number++;

        super.ID =  bishop_ID;
        super.n = number;
    }



    @Override
    public List legalMoves(int x, int y)
    {
        boolean dir1 = true;
        boolean dir2 = true;
        boolean dir3 = true;
        boolean dir4 = true;
        List moves = new ArrayList<>();

        for(int i = 1; i <= 8; i++)
        {
            if(dir1)
            {
                try
                {
                    int temp = field[x+i][y+i];
                    moves.add(x+i);
                    moves.add(y+i);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    dir1 = false;
                }
            }
            if(dir2)
            {
                try
                {
                    int temp = field[x+i][y-i];
                    moves.add(x+i);
                    moves.add(y-i);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    dir2 = false;
                }
            }
            if(dir3)
            {
                try
                {
                    int temp = field[x-i][y+i];
                    moves.add(x-i);
                    moves.add(y+i);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    dir3 = false;
                }
            }
            if(dir4)
            {
                try
                {
                    int temp = field[x-i][y-i];
                    moves.add(x-i);
                    moves.add(y-i);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    dir4 = false;
                }
            }

        }
        return moves;
    }

}
