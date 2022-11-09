package ai.Expectiminimax;

import java.util.List;

public interface State
{
    public List getAllPossibleMoves();
    public int getStateEvaluation();
}
