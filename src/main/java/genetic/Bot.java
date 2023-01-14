package genetic;

import game.*;
import utility.Promotion;

import java.util.ArrayList;
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
        List<Opportunity> opportunities = game.getTeamOpportunities(game.getActiveColor());
        if (opportunities.size() == 0) {                             //  O(n log n)
            game.switchActiveColor();
            return;
        }

        List<List<Integer>> opportunityEvaluations = new ArrayList<>();
        for (Opportunity mo : opportunities) {                                  // O(n)
            List<Integer> movementEvaluations = new ArrayList<>(mo.options().size());
            for (int i = 0; i < mo.size(); i++)                       {  // O(n^2)
                Movement m = mo.select(i);
                game.register(m);
                game.switchActiveColor();
                int evaluation = FUNCTION.evaluate(CHROMOSOME, game);
                movementEvaluations.add(evaluation);
                game.revert();
            }
            opportunityEvaluations.add(movementEvaluations);
        }
        int bestOpportunityIndex = -1, bestMoveIndex = -1, minimumValue = Integer.MAX_VALUE;
        for (int i = 0; i < opportunityEvaluations.size(); i++) {                 // O(n)
            List<Integer> movementEvaluations = opportunityEvaluations.get(i);
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
    public int compareTo(Bot object) {
        return wins - object.wins;
    }
}
