package chesspiece;

import utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Common
{
    public int pawn_ID;
    private static int number = 0;
    private Converter calculator = new Converter();

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
    public List legalMoves(int a)
    {
        List<Vector2d> vectors = new ArrayList<Vector2d>();

        if(pawn_ID < 0)
        {
            try
            {
                int temp = field[a-8];
                vectors.add(calculator.OneToVector(a,a-8));
            }
            catch (ArrayIndexOutOfBoundsException e)
            {

            }

        }
        else
        {
            try
            {
                int temp = field[a-16];
                vectors.add(calculator.OneToVector(a,a-16));
            }
            catch (ArrayIndexOutOfBoundsException e)
            {

            }
        }
        return vectors;
    }
}
