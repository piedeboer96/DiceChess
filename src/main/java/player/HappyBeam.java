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
        // double[] chromData = {-33, 9, 53, 56, 304, -12, -39, 66, -30, -80, -23, -431, 95, 31, 44, 3903, -47, 0, 68, -92, 31, 49, 51, 338, 87, 100, 3, 814, -15, 59, -54, 85, 3, 65, 74, 81, 434, -1};
        double[] chromData = {-18.819371588054878, 10.862451479563095, 48.12564670696829, 54.32596976130636, 311.83918411704985, -23.602455798288684, -24.663109438117957, 39.28855709420413, -24.553137355593357, -46.63842236051996, 2.303632748119176, -402.0294657649898, 48.78303443652011, 14.775949286392093, 27.154740517522463, 30114.723743436887, -36.307714217441045, 8.875325203637441, 51.4705028606628, -50.36446910191461, -9.280137131018222, 18.5504794273721, 19.558229597791478, 294.2082568967872, 43.78967122242212, 100.4977326742151, 5.215650701554208, 898.4927883718285, 2.7475154557128447, 50.881816281091886, -17.228944284433425, 49.274681437308146, 5.450525956840962, 48.90949199733171, 52.14367073561363, 54.06463361639077, 504.6368278191676, -9.571941784011099, 200.0,};

        BeamSearch bs = new BeamSearch(game, chromData, scoreWeight, roll, 100);

        Movement movement = bs.bestMove();
        if (movement != null) {
            game.register(movement);
        }
        game.switchActiveColor();

        return game.toString();
    }
}
