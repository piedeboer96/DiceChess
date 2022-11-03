package solutions.chess.utility;

import framework.chess.OpportunityManager;
import framework.game.Location;

public final class EPOpportunities implements OpportunityManager {
    private final Location[] TARGET_SQUARES = new Location[2];

    @Override
    public Location get(int team) {
        if (team == 0) { return TARGET_SQUARES[1]; }
        else if (team == 1){ return TARGET_SQUARES[0]; }
        else { throw new IllegalArgumentException("Team number is not valid!"); }
    }

    @Override
    public void set(int team, Location target) {
        if (team != 0 && team != 1) { throw new IllegalArgumentException("Team number is not valid!"); }
        TARGET_SQUARES[team] = target;
    }
}
