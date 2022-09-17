package chesspiece;

import utility.Vector2d;

import javax.management.MBeanAttributeInfo;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Common
{
    public int knight_ID;
    private static int number = 0;
    public int x;
    public int y;
    private Converter calculator = new Converter();

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

    //WORKS
    @Override
    public List legalMoves(int a)
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
        ArrayList x = new ArrayList<>();
        ArrayList y = new ArrayList<>();
        Knight k = new Knight(true);
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
