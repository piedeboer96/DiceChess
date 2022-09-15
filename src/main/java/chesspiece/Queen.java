package chesspiece;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Common
{
    public int queen_ID;
    private static int number = 0;

    public Queen(boolean isWhite)
    {
        if(isWhite)
        {
            queen_ID = 500 + number;
        }
        else
        {
            queen_ID = -500 - number;
        }
        number++;

        super.ID =  queen_ID;
        super.n = number;
    }

    @Override
    public List legalMoves(int x, int y)
    {
        boolean dir1 = true;
        boolean dir2 = true;
        boolean dir3 = true;
        boolean dir4 = true;
        boolean dir5 = true;
        boolean dir6 = true;
        boolean dir7 = true;
        boolean dir8 = true;
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

            if(dir5)
            {
                try
                {
                    int temp = field[x+i][y];
                    moves.add(x+i);
                    moves.add(y);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    dir5 = false;
                }
            }
            if(dir6)
            {
                try
                {
                    int temp = field[x-i][y];
                    moves.add(x-i);
                    moves.add(y);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    dir6 = false;
                }
            }
            if(dir7)
            {
                try
                {
                    int temp = field[x][y+i];
                    moves.add(x);
                    moves.add(y+i);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    dir7 = false;
                }
            }
            if(dir8)
            {
                try
                {
                    int temp = field[x][y-i];
                    moves.add(x);
                    moves.add(y-i);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    dir8 = false;
                }
            }

        }
        return moves;
    }
}
