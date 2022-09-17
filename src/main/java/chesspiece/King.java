package chesspiece;

import utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class King extends Common
{
    public int king_ID;
    private static int number = 0;
    private static Converter calculator = new Converter();

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
    public List legalMoves(int a)
    {
        List<Vector2d> vectors = new ArrayList<Vector2d>();
        int[] current = calculator.OneToTwo(a);

        if(checkDomain(current,1,1))
        {
            vectors.add(new Vector2d(1,1));
        }
        if(checkDomain(current,-1,1))
        {
            vectors.add(new Vector2d(-1,1));
        }
        if(checkDomain(current,1,-1))
        {
            vectors.add(new Vector2d(1,-1));
        }
        if(checkDomain(current,-1,-1))
        {
            vectors.add(new Vector2d(-1,-1));
        }
        if(checkDomain(current,1,0))
        {
            vectors.add(new Vector2d(1,0));
        }
        if(checkDomain(current,-1,0))
        {
            vectors.add(new Vector2d(-1,0));
        }
        if(checkDomain(current,0,1))
        {
            vectors.add(new Vector2d(0,1));
        }
        if(checkDomain(current,0,-1))
        {
            vectors.add(new Vector2d(0,-1));
        }




//        for(int j = -9; j < -6; j++)
//                {
//                    try
//                    {
//                        int temp = field[a+j];
//                        vectors.add(calculator.OneToVector(a,a+j));
//                    }
//                    catch (ArrayIndexOutOfBoundsException e)
//                    {
//
//                    }
//                }
//        for(int j = 9; j > 6; j--)
//        {
//            try
//            {
//                int temp = field[a+j];
//                vectors.add(calculator.OneToVector(a,a+j));
//            }
//            catch (ArrayIndexOutOfBoundsException e)
//            {
//
//            }
//        }
//
//        try
//        {
//            int temp = field[a+1];
//            vectors.add(calculator.OneToVector(a,a+1));
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//
//        }
//
//        try
//        {
//            int temp = field[a-1];
//            vectors.add(calculator.OneToVector(a,a-1));
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//
//        }


        return vectors;
    }


    public static void main(String[] args)
    {
        ArrayList x = new ArrayList<>();
        ArrayList y = new ArrayList<>();
        King k = new King(true);
        List<Vector2d> moves = k.legalMoves(31);
        for(int i = 0; i < moves.size(); i++)
        {
            x.add(moves.get(i).x);
            y.add(moves.get(i).y);
        }

        for(int i = 0; i < x.size(); i++)
        {
            System.out.println(x.get(i) + " " + y.get(i));
            System.out.println();
        }
    }
}