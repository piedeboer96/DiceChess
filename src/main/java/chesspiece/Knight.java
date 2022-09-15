package chesspiece;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Common
{
    public int knight_ID;
    private static int number = 0;
    public int x;
    public int y;

    public Knight(boolean isWhite)
    {
        if(isWhite)
        {
            knight_ID = 200 + number;
        }
        else
        {
            knight_ID = -200 - number;
        }
        number++;

        super.ID =  knight_ID;
        super.x = this.x;
        super.y = this.y;
        super.n = number;
    }

    @Override
    public List legalMoves(int x, int y)
    {
        List moves = new ArrayList<>();

        try
        {
            int temp = field[x+1][y+2];
            moves.add(x+1);
            moves.add(y+2);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }
        try
        {
            int temp = field[x+2][y+1];
            moves.add(x+2);
            moves.add(y+1);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }

        try
        {
            int temp = field[x-1][y+2];
            moves.add(x-1);
            moves.add(y+2);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }
        try
        {
            int temp = field[x-2][y+1];
            moves.add(x-2);
            moves.add(y+1);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }


        try
        {
            int temp = field[x+1][y-2];
            moves.add(x+1);
            moves.add(y-2);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }
        try
        {
            int temp = field[x+2][y-1];
            moves.add(x+2);
            moves.add(y-1);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }

        try
        {
            int temp = field[x-1][y-2];
            moves.add(x-1);
            moves.add(y-2);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }
        try
        {
            int temp = field[x-2][y-1];
            moves.add(x-2);
            moves.add(y-1);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }



        return moves;
    }
}
