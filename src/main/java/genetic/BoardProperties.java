package genetic;

import game.*;

import java.util.ArrayList;
import java.util.List;

final class BoardProperties {
    public final Chessboard BOARD;
    public final List<Square> BLACK_PAWNS = new ArrayList<>();
    public final List<Square> BLACK_KNIGHTS = new ArrayList<>();
    public final List<Square> BLACK_BISHOPS = new ArrayList<>();
    public final List<Square> BLACK_ROOKS = new ArrayList<>();
    public final List<Square> BLACK_QUEENS = new ArrayList<>();
    public final List<Square> WHITE_PAWNS = new ArrayList<>();
    public final List<Square> WHITE_KNIGHTS = new ArrayList<>();
    public final List<Square> WHITE_BISHOPS = new ArrayList<>();
    public final List<Square> WHITE_ROOKS = new ArrayList<>();
    public final List<Square> WHITE_QUEENS = new ArrayList<>();
    public Square BLACK_KING, WHITE_KING;

    public final List<Opportunity> BLACK_MOVES, WHITE_MOVES;

    public BoardProperties(DiceChess game) {
        BOARD = game.getBoard();
        for (char file = 97; file < 105; file++) {
            for (int rank = 1; rank < 9; rank++) {
                Square s = Square.get(file, rank);
                ChessPiece p = BOARD.read(s);
                if (p != null) {
                    if (p.color() == 0) {
                        switch (p.type()) {
                            case 1 -> BLACK_PAWNS.add(s);
                            case 2 -> BLACK_KNIGHTS.add(s);
                            case 3 -> BLACK_BISHOPS.add(s);
                            case 4 -> BLACK_ROOKS.add(s);
                            case 5 -> BLACK_QUEENS.add(s);
                            case 6 -> BLACK_KING = s;
                        }
                    } else {
                        switch (p.type()) {
                            case 1 -> WHITE_PAWNS.add(s);
                            case 2 -> WHITE_KNIGHTS.add(s);
                            case 3 -> WHITE_BISHOPS.add(s);
                            case 4 -> WHITE_ROOKS.add(s);
                            case 5 -> WHITE_QUEENS.add(s);
                            case 6 -> WHITE_KING = s;
                        }
                    }
                }
            }
        }

        BLACK_MOVES = game.getTeamOpportunities(0);
        WHITE_MOVES = game.getTeamOpportunities(1);
    }
}