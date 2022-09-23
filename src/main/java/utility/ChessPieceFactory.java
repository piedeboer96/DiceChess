package utility;

import chesspiece.*;
import game.Team;

/**
 * Provides a utility to build chess pieces.
 **/
public class ChessPieceFactory {

    /**
     * Builds a chess piece given its fen-character and location.
     * @param fen The character representing the chess piece and the team it is in.
     * @param tile The location on the board the chess piece should be on.
     **/
    public ChessPiece build(char fen, Tile tile) {
        switch (fen) {
            case 'b' : return new Bishop(Team.BLACK, tile);
            case 'k' : return new King(Team.BLACK, tile);
            case 'n' : return new Knight(Team.BLACK, tile);
            case 'p' : return new Pawn(Team.BLACK, tile);
            case 'q' : return new Queen(Team.BLACK, tile);
            case 'r' : return new Rook(Team.BLACK, tile);
            case 'B' : return new Bishop(Team.WHITE, tile);
            case 'K' : return new King(Team.WHITE, tile);
            case 'N' : return new Knight(Team.WHITE, tile);
            case 'P' : return new Pawn(Team.WHITE, tile);
            case 'Q' : return new Queen(Team.WHITE, tile);
            case 'R' : return new Rook(Team.WHITE, tile);
            default :  throw new IllegalArgumentException("Character " + fen + " is unrecognized.");
        }
    }
}
