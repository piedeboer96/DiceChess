package ai;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPlayer;

import java.util.List;
import java.util.Random;

public class RandomBot implements IChessPlayer
{
    private final Random random;

    public RandomBot()
    {
        // Get that random number generator.
        random = new Random();
    }

    /**
     * The match this bot is playing in.
     **/
    private IChessMatch match;

    /**
     * The team this bot is playing in.
     **/
    private int team;

    public void play()
    {
        // Determining whether the bot is allowed to play.
        List<IChessMove> possibleMoves;
        if (team != -1 && team != match.getPlayer()) { throw new IllegalCallerException("You can not call me when it is not my turn to play"); }
        // If it is allowed, then we are getting its moves.
        // When team = -1, it means this bot can just get the moves of the current player (turn owner).
        // Basically team = -1, means we won't throw an error due to not being my turn to play.
        else if (team == -1)
            { possibleMoves = match.legalMovesOf(match.getPlayer()); } //(WE NEED TO USE EVALUATION SORTING HERE...)
        else { possibleMoves = match.legalMovesOf(team); }

        // If there are no moves left, the game is over.
        if (possibleMoves.size() == 0) { throw new IllegalStateException("Have no moves left."); }

        // Getting a random move index.
        int moveIndex = random.nextInt(0, possibleMoves.size());
        IChessMove move = possibleMoves.get(moveIndex);

        // Getting a random destination.
        List<IChessBoardSquare> availableDestinations = move.possibilities();
        int destinationIndex = random.nextInt(0, availableDestinations.size());
        IChessBoardSquare destination = availableDestinations.get(destinationIndex);

        // Playing the random move.
        match.playMove(move.owner(), destination);
    }

    public void playIn(int team, IChessMatch match)
    {
        this.team = team;
        this.match = match;
    }
}
