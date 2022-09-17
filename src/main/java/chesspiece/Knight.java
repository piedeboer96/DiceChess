package chesspiece;

import utility.Vector2d;

import javax.management.MBeanAttributeInfo;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Common
{
    private static int numberWhite = 0;
    private static int numberBlack = 0;
    private Converter calculator = new Converter();

    public Knight(boolean isWhite ,int position)
    {
        if(isWhite)
        {
           ID = 200 + numberWhite;
            numberWhite++;
        }
        else
        {
            ID = -200 - numberBlack;
            numberBlack++;
        }
        a = position;
        field[a] = ID;
    }

    public Knight(boolean isWhite ,int[] coords)
    {
        if(isWhite)
        {
            ID = 200 + numberWhite;
            numberWhite++;
        }
        else
        {
            ID = -200 - numberBlack;
            numberBlack++;
        }
        a = calculator.TwoToOne(coords);
        field[a] = ID;
    }


    //WORKS
    @Override
    public List legalMoves()
    {

        //int[] domain = getDomain(a);
        int[] current = calculator.OneToTwo(a);
        List<Vector2d> vectors = new ArrayList<Vector2d>();


        if(checkDomain(current,1,2))
        {
            vectors.add(new Vector2d(1, 2));
        }

        if(checkDomain(current,2,1))
        {
            vectors.add(new Vector2d(2, 1));
        }

        if(checkDomain(current,-1,2))
        {
            vectors.add(new Vector2d(-1, 2));
        }

        if(checkDomain(current,-2,1))
        {
            vectors.add(new Vector2d(-2, 1));
        }

        if(checkDomain(current,1,-2))
        {
            vectors.add(new Vector2d(1, -2));
        }

        if(checkDomain(current,2,-1))
        {
            vectors.add(new Vector2d(2, -1));
        }

        if(checkDomain(current,-1,-2))
        {
            vectors.add(new Vector2d(-1, -2));
        }

        if(checkDomain(current,-2,-1))
        {
            vectors.add(new Vector2d(-2, -1));
        }



//        try
//        {
//            int temp = field[a-10];
//            vectors.add(calculator.OneToVector(a,a-10));
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//
//        }
//        try
//        {
//            int temp = field[a-17];
//            vectors.add(calculator.OneToVector(a,a-17));
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//
//        }
//
//        try
//        {
//            int temp = field[a-6];
//            vectors.add(calculator.OneToVector(a,a-6));
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//
//        }
//        try
//        {
//            int temp = field[a-15];
//            vectors.add(calculator.OneToVector(a,a-15));
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//
//        }
//
//
//        try
//        {
//            int temp = field[a+6];
//            vectors.add(calculator.OneToVector(a,a+6));
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//
//        }
//        try
//        {
//            int temp = field[a+15];
//            vectors.add(calculator.OneToVector(a,a+15));
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//
//        }
//
//        try
//        {
//            int temp = field[a+10];
//            vectors.add(calculator.OneToVector(a,a+10));
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
//
//        }
//        try
//        {
//            int temp = field[a+17];
//            vectors.add(calculator.OneToVector(a,a+17));
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
//        Knight k = new Knight(true, 31);
//        List<Vector2d> moves = k.legalMoves();
//        for(int i = 0; i < moves.size(); i++)
//        {
//        x.add(moves.get(i).x);
//        y.add(moves.get(i).y);
//        }
//
//        for(int i = 0; i < x.size(); i++)
//        {
//            System.out.println(x.get(i) + " " + y.get(i));
//            System.out.println();
//        }
        Knight k = new Knight(false, 48);
        System.out.println(k.getType() + ": Type");
        System.out.println(k.getTeam()+ ": Team");
        System.out.println(k.getPosition()+ ": Position");
    }
}
