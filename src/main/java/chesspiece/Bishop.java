package chesspiece;

import game.Team;
import utility.Tile;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {
    private final char fen;
    public Bishop(Team team, Tile tile) {
        super(team, tile, 330);
        if (team == Team.BLACK) { fen = 'b'; }
        else { fen = 'B'; }
    }

    @Override public List<Tile> determineMoves(ChessPiece[] board) {
        // Checking whether the team's king is in danger if the piece were to move
        // Checking the vertical axis
        ChessPiece north = lookNorth(board);
        ChessPiece south = lookSouth(board);
        if (north != null && south != null && north.team != south.team) {
            if (north instanceof King && north.team == team && (south instanceof Queen || south instanceof Rook)) {
                return new ArrayList<>();
            } else if (south instanceof King && south.team == team && (north instanceof Queen || north instanceof Rook)) {
                return new ArrayList<>();
            }
        }

        // Checking the horizontal axis
        ChessPiece east = lookEast(board);
        ChessPiece west = lookWest(board);
        if (east != null && west != null && east.team != west.team) {
            if (east instanceof King && east.team == team && (west instanceof Queen || west instanceof Rook)) {
                return new ArrayList<>();
            } else if (west instanceof King && west.team == team && (east instanceof Queen || east instanceof Rook)) {
                return new ArrayList<>();
            }
        }

        // Checking the 1st diagonal axis
        ChessPiece northEast = lookNorthEast(board);
        ChessPiece southWest = lookSouthWest(board);
        if (northEast != null && southWest != null && northEast.team != southWest.team) {
            if (northEast instanceof King && northEast.team == team && (southWest instanceof Queen || southWest instanceof Bishop)) {
                return new ArrayList<>();
            } else if (southWest instanceof King && southWest.team == team && (northEast instanceof Queen || northEast instanceof Bishop)) {
                return new ArrayList<>();
            }
        }

        // Checking the 2nd diagonal axis
        ChessPiece northWest = lookNorthWest(board);
        ChessPiece southEast = lookSouthEast(board);
        if (northWest != null && southEast != null && northWest.team != southEast.team) {
            if (northWest instanceof King && northWest.team == team && (southEast instanceof Queen || southEast instanceof Bishop)) {
                return new ArrayList<>();
            } else if (southEast instanceof King && southEast.team == team && (northWest instanceof Queen || northWest instanceof Bishop)) {
                return new ArrayList<>();
            }
        }

        // Determining available moves now
        List<Tile> moves = new ArrayList<>();
        int row = position.row();
        int column = position.column();


        // Determining the moves towards the north-eastern direction
        int limit = -1;
        int i = row;
        int j = column;
        int min = Math.min(row, column);
        if (northEast != null) {
            Tile tile = northEast.position;
            limit = Math.min(tile.row(), tile.column());
            if (northEast.team != team) { moves.add(tile); }
        }
        for (int k = --min; k > limit; k--) {
            Tile tile = new Tile(--i, --j);
            moves.add(tile);
        }

        // Determining the moves towards the north-western direction
        limit = -1;
        i = row;
        j = column;
        min = Math.min(row, 7 - j);
        if (northWest != null) {
            Tile tile = northWest.position;
            limit = Math.min(tile.row(), 7 - tile.column());
            if (northWest.team != team) { moves.add(tile); }
        }
        for (int k = --min; k > limit; k--) {
            Tile tile = new Tile(--i, ++j);
            moves.add(tile);
        }

        // Determining the moves towards the south-eastern direction
        limit = -1;
        i = row;
        j = column;
        min = Math.min(7 - row, j);
        if (southEast != null) {
            Tile tile = southEast.position;
            limit = Math.min(7 - tile.row(), tile.column());
            if (southEast.team != team) { moves.add(tile); }
        }
        for (int k = --min; k > limit; k--) {
            Tile tile = new Tile(++i, --j);
            moves.add(tile);
        }

        // Determining the moves towards the south-western direction
        limit = 8;
        i = row;
        j = column;
        int max = Math.max(i, j);
        if (southWest != null) {
            Tile tile = southWest.position;
            limit = Math.max(tile.row(), tile.column());
            if (southWest.team != team) { moves.add(tile); }
        }
        for (int k = ++max; k < limit; k++) {
            Tile tile = new Tile(++i, ++j);
            moves.add(tile);
        }

        return  moves;
    }

    @Override public char toFen() {
        return fen;
    }
}
