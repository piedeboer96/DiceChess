package chesspiece;

import game.Team;
import utility.Tile;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {
    private final char fen;

    private final int step;
    public Pawn(Team team, Tile tile) {
        super(team, tile, 100);
        if (team == Team.BLACK) {
            fen = 'p';
            step = 1;
        }
        else {
            fen = 'P';
            step = -1;
        }
    }

    //Todo: Perhaps add the double tile move here?
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

        if (step == -1)
        {
            if (north != null)
            {
                Tile tile = north.position;
                if (row - tile.row() > 1) {
                    Tile move = new Tile(row + step, column);
                    moves.add(move);
                }

                if(!firstStepSet)
                {
                    if (row - tile.row() > 2) {
                        Tile move = new Tile(row + -2, column);
                        moves.add(move);
                    }
                }
            }
            else {
                Tile tile = new Tile(row + step, column);
                moves.add(tile);
            }
            if (northEast != null) {
                Tile tile = northEast.position;
                if (row - tile.row() == 1 && northEast.team != team) { moves.add(tile); }
            }
            if (northWest != null) {
                Tile tile = northWest.position;
                if (row - tile.row() == 1 && northWest.team != team) { moves.add(tile); }
            }
        }
        else {
            if (south != null)
            {
                Tile tile = south.position;
                if (tile.row() - row > 1)
                {
                    Tile move = new Tile(row + step, column);
                    moves.add(move);
                }

                if(!firstStepSet)
                {
                    if (tile.row() - row > 2) {
                        Tile move = new Tile(row + 2, column);
                        moves.add(move);
                    }
                }

            }
            else {
                Tile tile = new Tile(row + step, column);
                moves.add(tile);
            }
            if (southEast != null) {
                Tile tile = southEast.position;
                if (tile.row() - row == 1 && southEast.team != team) { moves.add(tile); }
            }
            if (southWest != null) {
                Tile tile = southWest.position;
                if (tile.row() - row == 1 && southWest.team != team) { moves.add(tile); }
            }
        }
        return moves;
    }

    @Override public char toFen() {
        return fen;
    }
}
