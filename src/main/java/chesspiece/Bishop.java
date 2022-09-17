package chesspiece;

import utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Common
{
    private static int numberWhite = 0;
    private static int numberBlack = 0;
    private static Converter calculator = new Converter();

    public Bishop(boolean isWhite, int position)
    {
        if(isWhite)
        {
            ID = 300 + numberWhite;
            numberWhite++;
        }
        else
        {
            ID = -300 - numberBlack;
            numberBlack++;
        }
        a = position;
        field[a] = ID;

    }

    public Bishop(boolean isWhite, int[] coords)
    {
        if(isWhite)
        {
            ID = 300 + numberWhite;
            numberWhite++;
        }
        else
        {
            ID = -300 - numberBlack;
            numberBlack++;
        }
        a = calculator.TwoToOne(coords);
        field[a] = ID;
    }

    @Override
    public List legalMoves()
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
//        ArrayList x = new ArrayList<>();
//        ArrayList y = new ArrayList<>();
//        Bishop b = new Bishop(true, 31);
//        List<Vector2d> moves = b.legalMoves();
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

        Bishop k = new Bishop(true, 54);
        System.out.println(k.getType() + ": Type");
        System.out.println(k.getTeam()+ ": Team");
        System.out.println(k.getPosition()+ ": Position");

    }
}
