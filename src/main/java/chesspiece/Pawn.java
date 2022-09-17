package chesspiece;

import utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Common
{
    private static int numberWhite = 0;
    private static int numberBlack = 0;
    private Converter calculator = new Converter();

    public Pawn(boolean isWhite, int position)
    {
        if(isWhite)
        {
            ID = 100 + numberWhite;
            numberWhite++;
        }
        else
        {
            ID = -100 - numberBlack;
            numberBlack++;
        }
        a = position;
        field[a] = ID;

    }

    public Pawn(boolean isWhite, int[] coords)
    {
        if(isWhite)
        {
            ID = 100 + numberWhite;
        }
        else
        {
            ID = -100 - numberBlack;
        }
        a = calculator.TwoToOne(coords);
        field[a] = ID;

    }

    @Override
    public List legalMoves()
    {
        List<Vector2d> vectors = new ArrayList<Vector2d>();
        int[] current = calculator.OneToTwo(a);


        if(ID<0)
        {
            if(checkDomain(current,0,1))
            {
                vectors.add(new Vector2d(0,1));
            }

        }
        else
        {
            if(checkDomain(current,0,-1))
            {
                vectors.add(new Vector2d(0,-1));
            }
        }


//
//        if(pawn_ID < 0)
//        {
//            try
//            {
//                int temp = field[a-8];
//                vectors.add(calculator.OneToVector(a,a-8));
//            }
//            catch (ArrayIndexOutOfBoundsException e)
//            {
//
//            }
//
//        }
//        else
//        {
//            try
//            {
//                int temp = field[a-16];
//                vectors.add(calculator.OneToVector(a,a-16));
//            }
//            catch (ArrayIndexOutOfBoundsException e)
//            {
//
//            }
//        }
        return vectors;
    }

    public static void main(String[] args)
    {
//        ArrayList x = new ArrayList<>();
//        ArrayList y = new ArrayList<>();
//        Pawn p = new Pawn(false, 31);
//        List<Vector2d> moves = p.legalMoves();
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

        Pawn k = new Pawn(false, 14);
        Pawn a= new Pawn(true, 45);
        Pawn b= new Pawn(false, 56);
        Pawn v = new Pawn(true, 34);
        Pawn kc = new Pawn(true, 23);
        Pawn kfec = new Pawn(false, 21);
        Pawn kfd = new Pawn(true, 44);
        Pawn kcw = new Pawn(false, 13);
        System.out.println(numberWhite+": numberW");
        System.out.println(numberBlack+": numberB");


    }
}
