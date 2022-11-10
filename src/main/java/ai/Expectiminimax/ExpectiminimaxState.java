package ai.Expectiminimax;

import chess.ChessMatch;
import chess.utility.Chessboard;

import java.util.List;

public class ExpectiminimaxState implements State
{
    private String snapshot;
    private ChessMatch match;

        public ExpectiminimaxState(String snapshot)
        {
            this.snapshot = snapshot;
            match = convertString();
        }

        public ExpectiminimaxState(ChessMatch match)
        {
            this.match = match;
        }

    @Override
    public List getAllPossibleMoves()
    {
        int team = match.getPlayer();
        return match.legalMovesOf(team);
    }

    @Override
    public int getStateEvaluation()
    {
        throw new RuntimeException("not implemented");
        //return 0;
    }

    public ChessMatch convertString()
    {
        return new ChessMatch(snapshot);
    }

    public ChessMatch getMatch()
    {
        return this.match;
    }
}

