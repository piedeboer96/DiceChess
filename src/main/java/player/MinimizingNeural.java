package player;

import game.DiceChess;
import game.GameState;
import game.Movement;
import game.Opportunity;
import learningagent.MoveAdvisor;
import simulation.Player;

import java.util.List;

public final class MinimizingNeural implements Player {
    private final MoveAdvisor ADVISOR = new MoveAdvisor();

    @Override
    public String play(int roll, DiceChess game) {
        if (game.getState() != GameState.ONGOING) {
            throw new IllegalArgumentException("Can not play a move as game is already over.");
        }
        List<Opportunity> l = game.getTeamOpportunities(game.getActiveColor(), roll);
        if (l.size() == 0) {
            game.switchActiveColor();
            return game.toString();
        }
        Movement selected = null;
        double smallestProbability = 1;
        for (Opportunity o : l) {
            for (int i = 0; i < o.size(); i++) {
                Movement m = o.select(i);
                game.register(m);
                game.switchActiveColor();
                String FEN = game.toString();
                String PPD = FEN.split(" ", 2)[0];
                double opponentWP = ADVISOR.getMostPromisingBoardPosition(PPD, game.getActiveColor());
                if (opponentWP < smallestProbability) {
                    smallestProbability = opponentWP;
                    selected = m;
                }
                game.revert();
            }
        }
        if (selected == null) {
            throw new IllegalStateException("Could not decide or find a move to play.");
        }
        game.register(selected);
        game.switchActiveColor();
        return game.toString();
    }
}
