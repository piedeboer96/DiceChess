package gui;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import gui.interfaces.IHighlighter;

/**
 * Defines the basic structure any (human) player should have.
 **/
public abstract class Player extends MouseAdapter
{
    private final boolean playerCanPlayOnBothTeams;

    protected IChessMatch match;
    protected IChessboardSquare lastSelectedSquare;
    protected List<IChessMove> legalMoves;
    protected IChessPiece occupierOfLastSelectedSquare;
    protected List<IChessboardSquare> possibleDestinations;

    protected int team;

    public Player(boolean playerCanPlayOnBothTeams) { this.playerCanPlayOnBothTeams = playerCanPlayOnBothTeams; }

    /**
     * Performs a deselection; Clears the last selected square and any highlights (such as destination highlighting)
     * that have been made.
     * @param highlighter The highlighter that is responsible for highlighting chessboard squares on the chessboard
     *                    that the player is capable of interacting with.
     **/
    protected void deselect(IHighlighter highlighter)
    {
        lastSelectedSquare = null;
        possibleDestinations = null;
        occupierOfLastSelectedSquare = null;
        highlighter.rememberSelection(null);
        highlighter.rememberDestinations(null);
    }

    /**
     * When overridden, gets the legal moves the player has according the context the player is playing in.
     * Meaning, for a normal chess game, this method will get the all legal moves the player has during his or her turn,
     * while for a 'dice chess' game, this method will return the legal moves that the player is allowed to make
     * according to the results of the dice roll.
     **/
    protected abstract void loadLegalMoves();

    /**
     * Attempts to play the owner of the last selected square to the currently selected square.
     * If it is a valid move (meaning there is indeed a chess piece at the last selected square and
     * the currently selected square is a possible destination that this chess piece can move to),
     * then this move will be played.
     **/
    protected void play(IChessboardSquare selectedSquare)
    {
        // When the last selected square did not have a chess piece occupying it
        // or when no possible destinations have been registered,
        // it implies that the current selection is not yielding a valid move.
        if (occupierOfLastSelectedSquare == null || possibleDestinations == null || possibleDestinations.size() == 0) { return; }

        for (IChessboardSquare destination : possibleDestinations)
        {
            // If there is a destination registered with the same values as the square the player has selected,
            // then those two squares are fundamentally the same, therefore this results into a valid move.
            if (destination.equals(selectedSquare))
            {
                // Note: When a move is made, the match switches automatically to the next player.
                match.playMove(occupierOfLastSelectedSquare, selectedSquare);

                //
                // Promotion checking and a popup window to select the promotion type should be done here or somewhere
                // below, but at least before the break statement.
                //

                // Since a move has been played, the legal moves can be reset (and the die roll results
                // if it is a 'dice chess' game) as it is no longer relevant till the next turn to play for the player
                // has arrived (context will have changed till then). Thus, execute the onPlay method to clear it up.
                onPlay();

                // If the player is allowed to play on both teams (meaning there is a human vs human game going-on),
                // then re-assign the team variable's value to the other team, so the other team's chess pieces
                // can be controlled by a player.
                if (playerCanPlayOnBothTeams) { team = match.getPlayer(); }
                break;
            }
        }
    }

    public void playIn(int team, IChessMatch match)
    {
        this.team = team;
        this.match = match;
    }

    /**
     * When overridden, clears the variables that should be cleared only when a play has been made.
     **/
    protected abstract void onPlay();

    /**
     * Confirms the selected chessboard square through performing a few operations such as
     * whether a chess piece is located on the selected square and whether this chess piece
     * has any room to perform moves etc.
     * @param selectedSquare The square that just has been clicked.
     * @param highlighter The highlighter that is responsible for displaying the eye candy on the window
     *                    that has been interacted with.
     **/
    protected void onSelect(IChessboardSquare selectedSquare, IHighlighter highlighter)
    {
        // Getting the chess piece that is occupying the square the player has selected.
        // If null is returned, then it means there is no chess piece occupying this square.
        // Hence, there is nothing to do but only highlighting the square the player has clicked.
        occupierOfLastSelectedSquare = match.get(selectedSquare);
        if (occupierOfLastSelectedSquare != null)
        {
            // If no legal moves have been registered, then load the legal moves this player has.
            if (legalMoves == null) { loadLegalMoves(); }

            // Creating a possible destination list to register any possible destination the chess piece can reach.
            possibleDestinations = new ArrayList<>();

            // Searching through all legal moves the player has to determine whether the selected chess piece
            // has any moves it can perform.
            if(legalMoves!=null)
            for (IChessMove chessMove : legalMoves)
            {
                // If the chess piece has any moves to perform, then the possible destinations of each move
                // will be registered in one (large) list to ease access and checking.
                IChessPiece moveOwner = chessMove.owner();
                if (moveOwner.equals(occupierOfLastSelectedSquare)) { possibleDestinations.addAll(chessMove.possibilities()); }
            }

            // If there are any destinations the chess piece can move to, then these should be highlighted.
            if (possibleDestinations.size() > 0) { highlighter.rememberDestinations(possibleDestinations); }
        }

        // Highlighting the square that has been clicked.
        highlighter.rememberSelection(selectedSquare);
        
        // Finally register the selected square as the last selected square.
        lastSelectedSquare = selectedSquare;
    }
}
