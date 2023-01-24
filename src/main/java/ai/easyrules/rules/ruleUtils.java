//package ai.easyrules.rules;
//
//import java.util.ArrayList;
//
//import chess.ForsythEdwardsNotation;
//import chess.interfaces.IChessPiece;
//import chess.interfaces.IChessboardSquare;
//import chess.units.Pawn;
//import chess.utility.Factory;
//
//public class ruleUtils
//{
//    public static boolean checkColumnForPawnFromScratch(int column, int team, String fen)
//    {
//        Factory factory = new Factory();
//        IChessPiece[] squares = new IChessPiece[64];
//        ArrayList<IChessPiece> pieces = new ArrayList<>();
//        factory.constructChessBoard(pieces,squares,fen);
//        return innerCheck(column, team, squares);
//    }
//
//
//    public static boolean checkColumnForPawn(int column, int team, IChessPiece[] squares)
//    {
//        return innerCheck(column, team, squares);
//    }
//
//
//    public static boolean innerCheck(int column, int team, IChessPiece[] squares)
//    {
//        boolean out_of_bounds = false;
//        int i = 0;
//        while(!out_of_bounds)
//        {
//            try
//            {
//                if(squares[i*8+column] instanceof Pawn && squares[i*8+column].team()==team)
//                {
//                    return true;
//                }
//            }
//            catch(IndexOutOfBoundsException e)
//            {
//                out_of_bounds = true;
//            }
//
//        }
//        return false;
//    }
//
//
//    public static boolean checkEnpassant(int team, IChessPiece piece, String fen)
//    {
//        IChessboardSquare enpassant_square  = ForsythEdwardsNotation.getEnPassantOpportunity(fen);
//        if(team==0)
//        {
//            if((piece.file()-1 == enpassant_square.file() || piece.file()+1 == enpassant_square.file()) && piece.rank()-1 == enpassant_square.rank())
//            {
//                return true;
//            }
//        }
//        if(team==1)
//        {
//            if((piece.file()-1 == enpassant_square.file() || piece.file()+1 == enpassant_square.file()) && piece.rank()+1 == enpassant_square.rank())
//            {
//                return true;
//            }
//        }
//        return false;
//
//    }
//
//}
