package chesspiece;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Common
{
    public int rook_ID;
    private static int number = 0;

    public Rook(boolean isWhite)
    {
        if(isWhite)
        {
            rook_ID = 400 + number;
        }
        else
        {
            rook_ID = -400 - number;
        }
        number++;

        super.ID =  rook_ID;
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
                    int temp = field[x+i][y];
                    moves.add(x+i);
                    moves.add(y);
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
                    int temp = field[x-i][y];
                    moves.add(x-i);
                    moves.add(y);
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
                    int temp = field[x][y+i];
                    moves.add(x);
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
                    int temp = field[x][y-i];
                    moves.add(x);
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
