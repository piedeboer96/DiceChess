package genetic;

import game.*;
import utility.Promotion;

import java.util.List;

public class Bot implements Comparable<Bot> {
    private final Chromosome CHROMOSOME;
    private final FitnessFunction FUNCTION = new FitnessFunction();
    public int wins;
    int currentPlayer;

    public Bot() {
        CHROMOSOME = new Chromosome();
        wins = 0;
        currentPlayer = 0;
    }

    public Bot(Chromosome bestChromosome) {
        CHROMOSOME = bestChromosome;
    }

    public Chromosome getChromosome() {
        return CHROMOSOME;
    }

    public void play(DiceChess game, int roll) {
        List<Opportunity> l = game.getTeamOpportunities(game.getActiveColor(), roll);
        if (l.size() == 0) {
            game.switchActiveColor();
            return;
        }
        int minimumOpponentValue = Integer.MAX_VALUE;
        ChessPiece owner = null;
        Movement best = null;
        for (Opportunity o : l) {
            for (int i = 0; i < o.size(); i++) {
                Movement m = o.select(i);
                game.register(m);
                if (game.getState() == GameState.ONGOING && Promotion.isEligible(o.owner(), m.endpoint())) {
                    game.promote(m.endpoint(), ChessPiece.get(5, game.getActiveColor()));
                }
                game.switchActiveColor();
                int evaluation;
                switch (game.getState()) {
                    case ONGOING -> evaluation = FUNCTION.evaluate(CHROMOSOME, game);
                    case DRAW -> evaluation = 0;
                    default -> evaluation = Integer.MIN_VALUE;
                }
                if (evaluation < minimumOpponentValue) {
                    best = m;
                    owner = o.owner();
                }
                game.revert();
            }
        }
        game.register(best);
        if (game.getState() == GameState.ONGOING && Promotion.isEligible(owner, best.endpoint())) {
            game.promote(best.endpoint(), ChessPiece.get(5, game.getActiveColor()));
        }
        game.switchActiveColor();
    }

    /**
     * @param object the object to be compared.
     * @return return the sort of best objects in a decreasing order
     */
    @Override
    public int compareTo(Bot object) {
        return wins - object.wins;
    }
}
