package game;

import dice.DefaultDie;
import dice.Die;
import utility.Destinations;
import utility.DoubleStep;
import utility.Promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Defines a game of dice chess.
 **/
public final class DiceChess {
    private final Chessboard B = new Chessboard();
    private final Die D = new DefaultDie();

    public Stack<String> getHistory() {
        return HISTORY;
    }

    private final Stack<String> HISTORY = new Stack<>();
    private int activeColor, fullMoves, halfMoves;
    private GameState state;
    private String caField, epField;
    // Placeholder

    public DiceChess() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public DiceChess(String FEN) {
        update(FEN);
    }

    /**
     * Obtains the chessboard associated with the game.
     **/
    public Chessboard getBoard() {
        return B;
    }

    /**
     * Obtains the die associated with the game.
     **/
    public Die getDie() {
        return D;
    }

    /**
     * Obtains the state of the game.
     *
     * @return ONGOING, if the game has NOT ended. <br>
     *         DRAW, if the game has ended as a tie. <br>
     *         BLACK_WON, if black has captured the white king. <br>
     *         WHITE_WON, if white has captured the black king.
     **/
    public GameState getState() {
        return state;
    }

    /**
     * Obtains the active color of the game.
     *
     * @return 0, if the active color is black. <br>
     *         1, if the active color is white.
     **/
    public int getActiveColor() {
        return activeColor;
    }

    /**
     * Obtains the full move counter of the game.
     *
     * @return The counter the game uses to track the number of full moves.
     **/
    public int getFullMoves() {
        return fullMoves;
    }

    /**
     * Obtains the half move counter of the game.
     *
     * @return The counter the game uses to track the number of half moves.
     **/
    public int getHalfMoves() {
        return halfMoves;
    }

    /**
     * Obtains the movement opportunities the chess pieces of a color have.
     *
     * @param color The color for which the opportunities should be obtained.
     * @return A list of opportunities with a size >= 0.
     **/
    public List<Opportunity> getTeamOpportunities(int color) {
        List<Opportunity> l = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square s = Square.get(x, y);
                ChessPiece p = B.read(s);
                if (p == null || p.color() != color) {
                    continue;
                }
                List<Square> destinations = Destinations.determine(p, s, B);
                DoubleStep.expand(p, s, B, destinations);
                if (destinations.size() > 0) {
                    Opportunity o = new Opportunity(p, s, destinations);
                    l.add(o);
                }
            }
        }
        return l;
    }

    /**
     * Obtains the movement opportunities the chess pieces of a color have.
     *
     * @param color The color for which the opportunities should be obtained.
     * @param roll  The die roll associated with the color.
     * @return A list of opportunities with a size >= 0.
     **/
    public List<Opportunity> getTeamOpportunities(int color, int roll) {
        List<Opportunity> l = getTeamOpportunities(color);
        List<Opportunity> filtered = new ArrayList<>();
        for (Opportunity o : l) {
            ChessPiece p = o.owner();
            if (p.type() == roll || Promotion.isOneStepAway(p, o.origin())) {
                filtered.add(o);
            }
        }
        return filtered;
    }

    /**
     * Promotes a pawn to another chess piece.
     *
     * @param s The square of the pawn that should be promoted.
     * @param p The chess piece the pawn should be promoted to.
     **/
    public void promote(Square s, ChessPiece p) {
        // Confirming whether the game is allowed to be updated.
        if (state != GameState.ONGOING) {
            throw new IllegalStateException("Can not promote as the game is already deemed to be over.");
        }

        ChessPiece p0 = B.read(s);
        if (p0 == null) {
            throw new IllegalArgumentException("Square does not yield a chess piece.");
        } else if (p0.type() != 1) {
            throw new IllegalArgumentException("Square does not yield a pawn.");
        } else if (p0.color() != p.color()) {
            throw new IllegalArgumentException("Can not promote a pawn to a chess piece of the opponent color.");
        } else if (p0.color() != activeColor) {
            throw new IllegalArgumentException("Can not promote a pawn during the opponent's turn.");
        } else if (p.type() == 1) {
            throw new IllegalArgumentException("Can not promote a pawn to another pawn.");
        } else if (p.type() == 6) {
            throw new IllegalArgumentException("Can not promote a pawn to a king.");
        }
        B.store(p, s);
    }

    /**
     * Registers a movement causing the game to update itself.
     *
     * @param m The movement that should be registered.
     **/
    public void register(Movement m) {
        // Determining whether we are performing a proper move.
        // Reminder: Do NOT interpret this as "Confirming whether a chess piece has moved properly".
        ChessPiece selected = B.read(m.origin());
        ChessPiece targeted = B.read(m.endpoint());
        if (selected == null) {
            throw new IllegalArgumentException("Movement origin (square) is not occupied by a chess piece.");
        } else if (selected.color() != activeColor) {
            throw new IllegalArgumentException("Selected chess piece does not belong to the active color.");
        } else if (targeted != null && targeted.color() == activeColor) {
            throw new IllegalArgumentException("Movement targets a chess piece of the same color.");
        }

        // Updating the game.
        HISTORY.push(toString());
        B.store(null, m.origin());
        B.store(selected, m.endpoint());

        // Updating the half move counter.
        if (targeted == null && selected.type() != 1) {
            halfMoves++;
        } else {
            halfMoves = 0;
        }

        // Determining whether the opponent king has been captured.
        if (targeted != null && targeted.type() == 6) {
            switch (activeColor) {
                case 0 -> state = GameState.BLACK_WON;
                case 1 -> state = GameState.WHITE_WON;
            }
        }

        // Determining whether the game should be declared a draw.
        else if (halfMoves >= 50 && state == GameState.ONGOING) {
            state = GameState.DRAW;
        }
    }

    /**
     * Reverts the game back to its previous state.
     **/
    public void revert() {
        if (HISTORY.size() > 0) {
            update(HISTORY.pop());
        }
    }

    /**
     * Switches the active color. <br>
     * If the active color is 0, then the active color becomes 1. <br>
     * If the active color is 1, then the active coles becomes 0.
     **/
    public void switchActiveColor() {
        switch (activeColor) {
            case 0 -> {
                activeColor = 1;
                fullMoves++;
            }
            case 1 -> activeColor = 0;
        }
    }

    /**
     * Updates the game according to the game configuration described in a Forsyth-Edwards Notation.
     *
     * @param FEN The Forsyth-Edwards Notation of the game configuration the game should have.
     **/
    public void update(String FEN) {
        String[] fields = FEN.split(" ", 6);
        B.update(fields[0]);
        switch (fields[1].charAt(0)) {
            case 'b' -> activeColor = 0;
            case 'w' -> activeColor = 1;
            default  -> throw new IllegalArgumentException("Character " + fields[1].charAt(0) + " could not be recognized.");
        }
        caField = fields[2];
        epField = fields[3];
        halfMoves = Integer.parseInt(fields[4]);
        fullMoves = Integer.parseInt(fields[5]);

        // Updating the state of the game.
        if (!fields[0].contains("k")) {
            state = GameState.WHITE_WON;
        } else if (!fields[0].contains("K")) {
            state = GameState.BLACK_WON;
        } else if (halfMoves >= 50) {
            state = GameState.DRAW;
        } else {
            state = GameState.ONGOING;
        }

        HISTORY.push(FEN);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(B);

        sb.append(' ');
        switch (activeColor) {
            case 0 -> sb.append('b');
            case 1 -> sb.append('w');
        }

        sb.append(' ');
        sb.append(caField);

        sb.append(' ');
        sb.append(epField);

        sb.append(' ');
        sb.append(halfMoves);

        sb.append(' ');
        sb.append(fullMoves);
        return sb.toString();
    }
}