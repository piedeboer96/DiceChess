package chesspiece;

import game.Team;
import utility.Tile;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {
    private final char fen;

    public King(Team team, Tile tile) {
        super(team, tile, 20000);
        if (team == Team.BLACK) { fen = 'k'; }
        else { fen = 'K'; }
    }

    @Override public List<Tile> determineMoves(ChessPiece[] board) {
        //Todo: Check whether the king is in any danger from any axis,
        //      then determine the tiles to which king can move.
        //      But also determine whether the king's en-passant opportunity is safe.
        //      Perhaps also include castling here?
        ChessPiece north = lookNorth(board);
        ChessPiece south = lookSouth(board);
        ChessPiece east = lookEast(board);
        ChessPiece west = lookWest(board);
        ChessPiece northEast = lookNorthEast(board);
        ChessPiece southWest = lookSouthWest(board);
        ChessPiece northWest = lookNorthWest(board);
        ChessPiece southEast = lookSouthEast(board);


        return new ArrayList<>();
    }

    @Override public char toFen() {
        return fen;
    }
}
