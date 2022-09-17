package chesspiece;

import utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Common
{
    public int bishop_ID;
    private static int number = 0;
    private static Converter calculator = new Converter();

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
    public List legalMoves(int a)
    {
        boolean dir1 = true;
        boolean dir2 = true;
        boolean dir3 = true;
        boolean dir4 = true;
        List<Vector2d> vectors = new ArrayList<Vector2d>();
        int iterator = 0;
        int[] current = calculator.OneToTwo(a);



        while(dir1 || dir2 || dir3 || dir4)
        {
            iterator++;

            if(dir1)
            {
                if(checkDomain(current, iterator, iterator))
                {
                    vectors.add(new Vector2d(iterator, iterator));
                }
                else
                {
                    dir1 = false;
                }
            }
            if(dir2)
            {
                if(checkDomain(current, -iterator, iterator))
                {
                    vectors.add(new Vector2d(-iterator, iterator));
                }
                else
                {
                    dir2 = false;
                }
            }
            if(dir3)
            {
                if(checkDomain(current, iterator, -iterator))
                {
                    vectors.add(new Vector2d(iterator, -iterator));
                }
                else
                {
                    dir3 = false;
                }
            }
            if(dir4)
            {
                if(checkDomain(current, -iterator, -iterator))
                {
                    vectors.add(new Vector2d(-iterator, -iterator));
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
//                    int temp = field[a+7*iterator];
//                    vectors.add(calculator.OneToVector(a,a+7*iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir1 = false;
//
//                }
//            }
//            if(dir2)
//            {
//                try
//                {
//                    int temp = field[a-7*iterator];
//                    vectors.add(calculator.OneToVector(a,a-7*iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir2 = false;
//
//                }
//            }
//            if(dir3)
//            {
//                try
//                {
//                    int temp = field[a+9*iterator];
//                    vectors.add(calculator.OneToVector(a,a+9*iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir3 = false;
//
//                }
//            }
//            if(dir4)
//            {
//                try
//                {
//                    int temp = field[a-9*iterator];
//                    vectors.add(calculator.OneToVector(a,a-9*iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir4 = false;
//
//                }
//            }
//        }



        return vectors;
    }


    public static void main(String[] args)
    {
        ArrayList x = new ArrayList<>();
        ArrayList y = new ArrayList<>();
        Bishop b = new Bishop(true);
        List<Vector2d> moves = b.legalMoves(27);
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
