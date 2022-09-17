package chesspiece;

import utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Common
{
    public int rook_ID;
    private static int number = 0;
    private static Converter calculator = new Converter();

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
    public List legalMoves(int a)
    {
        List<Vector2d> vectors = new ArrayList<Vector2d>();

        boolean dir1 = true;
        boolean dir2 = true;
        boolean dir3 = true;
        boolean dir4 = true;
        int iterator = 0;
        int[] current = calculator.OneToTwo(a);


        while(dir1 || dir2 || dir3 || dir4)
        {
            iterator++;
            if(dir1)
            {
                if(checkDomain(current,iterator,0))
                {
                    vectors.add(new Vector2d(iterator,0));
                }
                else
                {
                    dir1 = false;
                }
            }
            if(dir2)
            {
                if(checkDomain(current,-iterator,0))
                {
                    vectors.add(new Vector2d(-iterator,0));
                }
                else
                {
                    dir2 = false;
                }
            }
            if(dir3)
            {
                if(checkDomain(current,0,iterator))
                {
                    vectors.add(new Vector2d(0,iterator));
                }
                else
                {
                    dir3 = false;
                }
            }
            if(dir4)
            {
                if(checkDomain(current,0,-iterator))
                {
                    vectors.add(new Vector2d(0,-iterator));
                }
                else
                {
                    dir4 = false;
                }
            }

        }























//        while(dir1 || dir2 || dir3 || dir4)
//        {
//            iterator++;
//            if(dir1)
//            {
//                try
//                {
//                    int temp = field[a+iterator];
//                    vectors.add(calculator.OneToVector(a,a+iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir1 = false;
//                }
//            }
//            if(dir2)
//            {
//                try
//                {
//                    int temp = field[a-iterator];
//                    vectors.add(calculator.OneToVector(a,a-iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir2 = false;
//                }
//            }
//            if(dir3)
//            {
//                try
//                {
//                    int temp = field[a-8*iterator];
//                    vectors.add(calculator.OneToVector(a,a-8*iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir3 = false;
//                }
//            }
//            if(dir4)
//            {
//                try
//                {
//                    int temp = field[a+8*iterator];
//                    vectors.add(calculator.OneToVector(a,a+8*iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir4 = false;
//                }
//            }
//        }



        return vectors;
    }

    public static void main(String[] args)
    {
        ArrayList x = new ArrayList<>();
        ArrayList y = new ArrayList<>();
        Rook r = new Rook(true);
        List<Vector2d> moves = r.legalMoves(31);
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
