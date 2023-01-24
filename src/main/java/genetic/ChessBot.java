package genetic;

import game.DiceChess;
import game.Movement;
import game.Opportunity;

import java.util.ArrayList;
import java.util.List;

public class ChessBot implements Comparable<ChessBot> {
    private final Chromosome CHROMOSOME;
    private final FitnessFunction FUNCTION = new FitnessFunction();
    public int wins;
    int currentPlayer;

    public ChessBot() {
        CHROMOSOME = new Chromosome();
        wins = 0;
        currentPlayer = 0;
    }

    public ChessBot(Chromosome bestChromosome) {
        CHROMOSOME = bestChromosome;
    }

    public Chromosome getChromosome() {
        return CHROMOSOME;
    }

    public void play(DiceChess game) {
        List<Opportunity> opportunities = game.getTeamOpportunities(game.getActiveColor());
        if (opportunities.size() == 0) {                             //  O(n log n)
            game.switchActiveColor();
            return;
        }

        List<List<Double>> opportunityEvaluations = new ArrayList<>();
        for (Opportunity mo : opportunities) {                                  // O(n)
            List<Double> movementEvaluations = new ArrayList<>(mo.options().size());
            for (int i = 0; i < mo.size(); i++)                       {  // O(n^2)
                Movement m = mo.select(i);
                game.register(m);
                game.switchActiveColor();
                double evaluation = FUNCTION.evaluate(CHROMOSOME, game);
                movementEvaluations.add(evaluation);
                game.revert();
            }
            opportunityEvaluations.add(movementEvaluations);
        }
        int bestOpportunityIndex = -1, bestMoveIndex = -1;
        double minimumValue = Integer.MAX_VALUE;
        for (int i = 0; i < opportunityEvaluations.size(); i++) {                 // O(n)
            List<Double> movementEvaluations = opportunityEvaluations.get(i);
            for (int j = 0; j < movementEvaluations.size(); j++) {                // O(n^2)
                if (minimumValue > movementEvaluations.get(j)) {                  // O(n log n)
                    bestOpportunityIndex = i;
                    bestMoveIndex = j;
                    minimumValue = movementEvaluations.get(j);
                }
            }
        }
        Opportunity bestOpportunity = opportunities.get(bestOpportunityIndex);
        Movement m = bestOpportunity.select(bestMoveIndex);
        game.register(m);
        game.switchActiveColor();
    }

    /**
     * @param object the object to be compared.
     * @return return the sort of best objects in a decreasing order
     */
    @Override
    public int compareTo(ChessBot object) {
        return wins - object.wins;
    }
}
