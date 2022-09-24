package chesspiece;

import game.Team;
import utility.Tile;

import java.util.List;

public abstract class ChessPiece {
    private final int value;
    protected Team team;
    protected Tile position;

    public int turnsPassed = 0;

    public boolean firstStepSet = false;

    /**
     * @param team The team the chess piece is in.
     * @param tile The location of the chess piece on the board.
     * @param value The priority value the chess piece has.
     **/
    public ChessPiece(Team team, Tile tile, int value) {
        if (team == Team.BLACK) { this.value = -value; }
        else { this.value = value; }
        this.team = team;
        position = tile;
    }

    /**
     * Determines the moves the chess piece is allowed to make given the current state of the board.
     * @param board The board the chess piece is located on.
     *              Array must have a length 64, where each consecutive 8 entries form a row.
     * @return A list of the moves the chess piece can make.
     **/
    public abstract List<Tile> determineMoves(ChessPiece[] board);

    /**
     * Gets the tile the chess piece is located on.
     * @return The position of chess piece represented by a tile.
     **/
    public Tile getPosition() {
       return position;
    }

    /**
     * Gets the team the chess piece belongs to.
     * @return Either team black or team white.
     **/
    public Team getTeam(){
        return team;
    }

    /**
     * Gets the priority value of the chess piece.
     * @return The value the chess piece is worth.?
     **/
    public int getValue() {
        return value;
    }

    /**
     * Determines whether there is a chess piece in the eastern direction of this chess piece.
     * @param board The board the chess piece is located on.
     *              Array must have a length 64, where each consecutive 8 entries form a row.
     * @return The chess piece that is east of this chess piece. Null, if there is nothing.
     **/
    public ChessPiece lookEast(ChessPiece[] board) {
        ChessPiece piece = null;
        int index = position.row() * 8;
        for (int j = position.column() - 1; j > -1; j--) {
            piece = board[index + j];
            if (piece != null) { break; }
        }
        return piece;
    }

    /**
     * Determines whether there is a chess piece in the northern direction of this chess piece.
     * @param board The board the chess piece is located on.
     *              Array must have a length 64, where each consecutive 8 entries form a row.
     * @return The chess piece that is north of this chess piece. Null, if there is nothing.
     **/
    public ChessPiece lookNorth(ChessPiece[] board) {
        ChessPiece piece = null;
        int j = position.column();
        for (int i = position.row() - 1; i > -1; i--) {
            piece = board[i * 8 + j];
            if (piece != null) { break; }
        }
        return piece;
    }

    /**
     * Determines whether there is a chess piece in the north-eastern direction of this piece.
     * @param board The board the chess piece is located on.
     *              Array must have a length 64, where each consecutive 8 entries form a row.
     * @return The chess piece that is north-east of this chess piece. Null, if there is nothing.
     **/
    public ChessPiece lookNorthEast(ChessPiece[] board) {
        ChessPiece piece = null;
        int i = position.row();
        int j = position.column();
        int min = Math.min(i, j);
        for (int k = --min; k > -1; k--) {
            piece = board[--i * 8 + --j];
            if (piece != null) { break; }
        }
        return piece;
    }

    /**
     * Determines whether there is a chess piece in the north-western direction of this chess piece.
     * @param board The board the chess piece is located on.
     *              Array must have a length 64, where each consecutive 8 entries form a row.
     * @return The chess piece that is north-west of this chess piece. Null, if there is nothing.
     **/
    public ChessPiece lookNorthWest(ChessPiece[] board) {
        ChessPiece piece = null;
        int i = position.row();
        int j = position.column();
        int dj = 7 - j;
        int min = Math.min(i, dj);
        for (int k = --min; k > -1; k--) {
            piece = board[--i * 8 + ++j];
            if (piece != null) { break; }
        }
        return piece;
    }

    /**
     * Determines whether there is a chess piece in the southern direction of this chess piece.
     * @param board The board the chess piece is located on.
     *              Array must have a length 64, where each consecutive 8 entries form a row.
     * @return The chess piece that is south of this chess piece. Null, if there is nothing.
     **/
    public ChessPiece lookSouth(ChessPiece[] board) {
        ChessPiece piece = null;
        int j = position.column();
        for (int i = position.row() + 1; i < 8; i++) {
            piece = board[i * 8 + j];
            if (piece != null) { break; }
        }
        return piece;
    }

    /**
     * Determines whether there is a chess piece in the south-eastern direction of this chess piece.
     * @param board The board the chess piece is located on.
     *              Array must have a length 64, where each consecutive 8 entries form a row.
     * @return The chess piece that is south-east of this chess piece. Null, if there is nothing.
     **/
    public ChessPiece lookSouthEast(ChessPiece[] board) {
        ChessPiece piece = null;
        int i = position.row();
        int j = position.column();
        int di = 7 - i;
        int min = Math.min(di, j);
        for (int k = --min; k > -1; k--) {
            piece = board[++i * 8 + --j];
            if (piece != null) { break; }
        }
        return piece;
    }

    /**
     * Determines whether there is a chess piece in the south-western direction of this chess piece.
     * @param board The board the chess piece is located on.
     *              Array must have a length 64, where each consecutive 8 entries form a row.
     * @return The chess piece that is south-west of this piece. Null, if there is nothing.
     **/
    public ChessPiece lookSouthWest(ChessPiece[] board) {
        ChessPiece piece = null;
        int i = position.row();
        int j = position.column();
        int max = Math.max(i, j);
        for (int k = ++max; k < 8; k++) {
            piece = board[++i * 8 + ++j];
            if (piece != null) { break; }
        }
        return piece;
    }

    /**
     * Determines whether there is a chess piece in the western direction of this chess piece.
     * @param board The board the chess piece is located on.
     *              Array must have a length 64, where each consecutive 8 entries form a row.
     * @return The chess piece that is west of this chess piece. Null, if there is nothing.
     **/
    public ChessPiece lookWest(ChessPiece[] board) {
        ChessPiece piece = null;
        int index = position.row() * 8;
        for (int j = position.column() + 1; j < 8; j++) {
            piece = board[index + j];
            if (piece != null) { break; }
        }
        return piece;
    }

    /**
     * Sets the position of the chess piece.
     * @param tile The tile the chess piece will be located on.
     **/
    public void setPosition(Tile tile) {
        position = tile;
    }

    /**
     * Gets the fen-character representing the chess piece type and the team it is on.
     * @return The fen-character representing the chess piece.
     **/
    public abstract char toFen();
}
