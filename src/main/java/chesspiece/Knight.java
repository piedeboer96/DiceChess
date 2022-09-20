package chesspiece;

import game.Team;
import utility.Tile;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {
    private final char fen;

    public Knight(Team team, Tile tile) {
        super(team, tile, 400);
        if (team == Team.BLACK) { fen = 'n'; }
        else { fen = 'N'; }
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
        Tile north1 = new Tile(row - 2, column - 1);
        Tile north2 = new Tile(row - 2, column + 1);
        Tile east1  = new Tile(row - 1, column - 2);
        Tile east2  = new Tile(row + 1, column - 2);
        Tile west1  = new Tile(row - 1, column + 2);
        Tile west2  = new Tile(row + 1, column + 2);
        Tile south1 = new Tile(row + 2, column - 1);
        Tile south2 = new Tile(row + 2, column + 1);
        if (north1.isValid()) {
            ChessPiece piece = board[north1.row() * 8 + north1.column()];
            if (piece == null || piece.team != team) { moves.add(north1); }
        }
        if (north2.isValid()) {
            ChessPiece piece = board[north2.row() * 8 + north2.column()];
            if (piece == null || piece.team != team) { moves.add(north2); }
        }
        if ( east1.isValid()) {
            ChessPiece piece = board[east1.row() * 8 + east1.column()];
            if (piece == null || piece.team != team) { moves.add(east1); }
        }
        if ( east2.isValid()) {
            ChessPiece piece = board[east2.row() * 8 + east2.column()];
            if (piece == null || piece.team != team) { moves.add(east2); }
        }
        if ( west1.isValid()) {
            ChessPiece piece = board[west1.row() * 8 + west1.column()];
            if (piece == null || piece.team != team) { moves.add(west1); }
        }
        if ( west2.isValid()) {
            ChessPiece piece = board[west2.row() * 8 + west2.column()];
            if (piece == null || piece.team != team) { moves.add(west2); }
        }
        if (south1.isValid()) {
            ChessPiece piece = board[south1.row() * 8 + south1.column()];
            if (piece == null || piece.team != team) { moves.add(south1); }
        }
        if (south2.isValid()) {
            ChessPiece piece = board[south2.row() * 8 + south2.column()];
            if (piece == null || piece.team != team) { moves.add(south2); }
        }
        return  moves;
    }

    @Override public char toFen() {
        return fen;
    }
}
