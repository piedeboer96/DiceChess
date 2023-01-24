package player;

import game.DiceChess;
import game.Movement;
import game.Opportunity;
import simulation.Player;

import java.util.Random;

public class Lunatic implements Player {
    private final Random RND = new Random();
    @Override
    public String play(int roll, DiceChess game) {

        var opportunities = game.getTeamOpportunities(game.getActiveColor(), roll);
        if (opportunities.size()>0){
            int randomOpportunityIndex = RND.nextInt(opportunities.size());
            Opportunity opp = opportunities.get(randomOpportunityIndex);
            int randomMovementIndex = RND.nextInt(opp.size());
            Movement m = opp.select(randomMovementIndex);
            game.register(m);
        }
        game.switchActiveColor();
        return game.toString();
    }
}
