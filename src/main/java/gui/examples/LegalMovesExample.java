package gui.examples;

import chess.ChessMatch;
import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.units.Knight;

import java.util.ArrayList;
import java.util.List;

public class LegalMovesExample
{
    public static void main(String[] args)
    {
        // Creates a new chess game (clean state etc).
        IChessMatch match = new ChessMatch();

        // Getting the moves of the current player easily by
        List<IChessMove> moves = match.legalMovesOf(match.getPlayer());

        // Let's say we want all the moves both our knights make.
        List<IChessMove> movesByKnight = new ArrayList<>();

        // Besides iterating to find our knight, let's also print out the moves to the console to get an idea of what we got.
        // Note: Each movement direction for each chess piece is independently stored.
        for (IChessMove move : moves)
        {
            IChessPiece moveOwner = move.owner();
            if (moveOwner instanceof Knight) { movesByKnight.add(move); }

            System.out.println("Fen of Piece: " + moveOwner.toFen());
            System.out.print("Possible destinations: ");
            for (IChessBoardSquare possibility : move.possibilities())
            {
                System.out.print("(" + possibility.rank() + "," + possibility.file() + ") ");
            }
            System.out.println('\n');
        }

        // If done well, both our knight are allowed to move in 2 directions, so in total we should have collected 4 moves.
        System.out.println("Total number of moves recorded for knights: " + movesByKnight.size());

        // If we want to play one of the moves performed by the knight (after having decided on which knight and which direction),
        // just tell the match to relocate the knight.
        IChessMove firstMove = movesByKnight.get(0);
        match.playMove(firstMove.owner(), firstMove.possibilities().get(0));
    }
}
