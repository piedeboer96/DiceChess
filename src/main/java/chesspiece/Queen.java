package chesspiece;

import utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Common
{
    public int queen_ID;
    private static int number = 0;

    private static Converter calculator = new Converter();

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
    public List legalMoves(int a)
    {
        List<Vector2d> vectors = new ArrayList<Vector2d>();

        boolean dir1 = true;
        boolean dir2 = true;
        boolean dir3 = true;
        boolean dir4 = true;
        boolean dir5 = true;
        boolean dir6 = true;
        boolean dir7 = true;
        boolean dir8 = true;
        int iterator = 0;
        int[] current = calculator.OneToTwo(a);


        while(dir1 || dir2 || dir3 || dir4 || dir5 || dir6 || dir7 || dir8)
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
            if(dir5)
            {
                if(checkDomain(current, iterator, iterator))
                {
                    vectors.add(new Vector2d(iterator, iterator));
                }
                else
                {
                    dir5 = false;
                }
            }
            if(dir6)
            {
                if(checkDomain(current, -iterator, iterator))
                {
                    vectors.add(new Vector2d(-iterator, iterator));
                }
                else
                {
                    dir6 = false;
                }
            }
            if(dir7)
            {
                if(checkDomain(current, iterator, -iterator))
                {
                    vectors.add(new Vector2d(iterator, -iterator));
                }
                else
                {
                    dir7 = false;
                }
            }
            if(dir8)
            {
                if(checkDomain(current, -iterator, -iterator))
                {
                    vectors.add(new Vector2d(-iterator, -iterator));
                }
                else
                {
                    dir8 = false;
                }
            }

        }











































//        while(dir1 || dir2 || dir3 || dir4 || dir5 || dir6 || dir7 || dir8)
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
//                }
//            }
//
//            if(dir5)
//            {
//                try
//                {
//                    int temp = field[a+iterator];
//                    vectors.add(calculator.OneToVector(a,a+iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir5 = false;
//                }
//            }
//            if(dir6)
//            {
//                try
//                {
//                    int temp = field[a-iterator];
//                    vectors.add(calculator.OneToVector(a,a-iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir6 = false;
//                }
//            }
//            if(dir7)
//            {
//                try
//                {
//                    int temp = field[a-8*iterator];
//                    vectors.add(calculator.OneToVector(a,a-8*iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir7 = false;
//                }
//            }
//            if(dir8)
//            {
//                try
//                {
//                    int temp = field[a+8*iterator];
//                    vectors.add(calculator.OneToVector(a,a+8*iterator));
//                }
//                catch (ArrayIndexOutOfBoundsException e)
//                {
//                    dir8 = false;
//                }
//            }
//        }



        return vectors;
    }

    public static void main(String[] args)
    {
        ArrayList x = new ArrayList<>();
        ArrayList y = new ArrayList<>();
        Queen q = new Queen(true);
        List<Vector2d> moves = q.legalMoves(31);
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
