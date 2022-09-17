package chesspiece;

import utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class King extends Common
{
    private static int numberWhite = 0;
    private static int numberBlack = 0;
    private static Converter calculator = new Converter();

    public King(boolean isWhite,  int position)
    {
        if(isWhite)
        {
            ID = 10000 + numberWhite;
            numberWhite++;
        }
        else
        {
            ID = -10000 - numberBlack;
            numberBlack++;
        }
        a = position;
        field[a] = ID;
    }
    public King(boolean isWhite, int coords[])
    {
        if(isWhite)
        {
            ID = 10000 + numberWhite;
            numberWhite++;
        }
        else
        {
            ID = -10000 - numberBlack;
            numberBlack++;
        }
        a = calculator.TwoToOne(coords);
        field[a] = ID;
    }



    @Override
    public List legalMoves()
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
//        ArrayList x = new ArrayList<>();
//        ArrayList y = new ArrayList<>();

//        List<Vector2d> moves = k.legalMoves();
//        for(int i = 0; i < moves.size(); i++)
//        {
//            x.add(moves.get(i).x);
//            y.add(moves.get(i).y);
//        }
//
//        for(int i = 0; i < x.size(); i++)
//        {
//            System.out.println(x.get(i) + " " + y.get(i));
//            System.out.println();
//        }
        King k = new King(false, 63);
        System.out.println(k.getType() + ": Type");
        System.out.println(k.getTeam()+ ": Team");
        System.out.println(k.getPosition()+ ": Position");

    }
}