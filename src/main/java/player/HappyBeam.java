package player;

import beamsearch.BeamSearch;
import expecti.ExpectiMiniMaxTree;
import game.ChessPiece;
import game.DiceChess;
import game.Movement;
import game.Square;
import simulation.Player;

public class HappyBeam implements Player {
    private double scoreWeight;
    public HappyBeam(double scoreWeight) {
        this.scoreWeight = scoreWeight;
    }
    @Override
    public String play(int roll, DiceChess game) {
        double[] chromData = {-33, 9, 53, 56, 304, -12, -39, 66, -30, -80, -23, -431, 95, 31, 44, 3903, -47, 0, 68, -92, 31, 49, 51, 338, 87, 100, 3, 814, -15, 59, -54, 85, 3, 65, 74, 81, 434, -1};

        BeamSearch bs = new BeamSearch(game, chromData, scoreWeight, roll, 0);

        Movement movement = bs.bestMove();
        if (movement != null) {
            game.register(movement);
        }
        game.switchActiveColor();

        return game.toString();
    }
}
