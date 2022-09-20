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
        return switch(fen) {
            case 'b' -> new Bishop(Team.BLACK, tile);
            case 'k' -> new King(Team.BLACK, tile);
            case 'n' -> new Knight(Team.BLACK, tile);
            case 'p' -> new Pawn(Team.BLACK, tile);
            case 'q' -> new Queen(Team.BLACK, tile);
            case 'r' -> new Rook(Team.BLACK, tile);

            case 'B' -> new Bishop(Team.WHITE, tile);
            case 'K' -> new King(Team.WHITE, tile);
            case 'N' -> new Knight(Team.WHITE, tile);
            case 'P' -> new Pawn(Team.WHITE, tile);
            case 'Q' -> new Queen(Team.WHITE, tile);
            case 'R' -> new Rook(Team.WHITE, tile);

            default  -> throw new IllegalArgumentException("Character " + fen + " is unrecognized.");
        };
    }
}
