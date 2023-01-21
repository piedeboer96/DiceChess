package beamsearch;
import game.*;
import genetic.Chromosome;
import genetic.FitnessFunction;
import player.HappyBeam;

import java.util.*;


public class BeamSearch {
    private double scoreWeight;
    private int roll;
    private double[] chromosomeData;
    private DiceChess game;
    private static final Random RANDOM = new Random();
    private int time;

    public BeamSearch(DiceChess game, double[] chromosomeData, double scoreWeight, int roll, int time) {
        this.scoreWeight = scoreWeight;
        this.game = game;
        this.chromosomeData = chromosomeData;
        this.roll = roll;
        this.time = time;
    }

    public Movement bestMove() {
        BeamState root = new BeamState(game.toString(), null, -1);
        Queue<BeamState> positions = new PriorityQueue<>();

        List<Opportunity> opportunities = game.getTeamOpportunities(game.getActiveColor(), roll);

        List<BeamState> rootChildren = new ArrayList<>();
        List<Movement> childMoves = new ArrayList<>();
        for (Opportunity mo : opportunities) {
            for (int i = 0; i < mo.size(); i++) {
                Movement m = mo.select(i);
                childMoves.add(m);
                int mPieceType = game.getBoard().read(m.origin()).type();
                game.register(m);
                game.switchActiveColor();
                BeamState child = new BeamState(game.toString(), root, mPieceType);
                rootChildren.add(child);
                positions.add(child);
                game.revert();
            }
        }

        if (rootChildren.isEmpty()) return null;

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < time) {
            BeamState currentState = positions.peek();
            if (currentState == null) break;
            if (currentState.game.getState() == GameState.ONGOING) {
                positions.remove();
                ArrayList<BeamState> children = currentState.exploreStates();
                positions.addAll(children);
            } else currentState.acceptanceScore = -10000.0;
        }

        BeamState maxChild = null;
        double maxValue = Double.NEGATIVE_INFINITY;
        for (BeamState child: rootChildren) {
            double value = stateValue(child);
            if (value > maxValue) {
                maxChild = child;
                maxValue = value;
            }
        }
        Movement bestMove = childMoves.get(rootChildren.indexOf(maxChild));
        // ChessPiece piece = game.getBoard().read(bestMove.origin());
        // System.out.println("THE BEST MOVE IS: Player of color " + piece.color() + " moves piece " + piece.type() + " from  square (" + bestMove.origin().file() + bestMove.origin().rank() + "), to square (" + bestMove.endpoint().file() + bestMove.endpoint().rank() + ")");

        return bestMove;
    }

    public double stateValue(BeamState parent) {
        if(!parent.hasChildren) {
            if (parent.game.getActiveColor() == game.getActiveColor()) return -parent.evaluation;
            return parent.evaluation;
        }
        double sum = 0;
        for (int i = 1; i < 7; i++) {
            final int type = i;
            List<BeamState> typeMoveStates = parent.childStates.stream().filter(child -> child.pieceType == type).toList();
            if (parent.game.getActiveColor() == game.getActiveColor()) {
                double maxChildValue = Double.NEGATIVE_INFINITY;
                for (BeamState typeMoveState : typeMoveStates) {
                    maxChildValue = Math.max(maxChildValue, stateValue(typeMoveState));
                }
                sum += maxChildValue;
            } else {
                double minChildValue = Double.POSITIVE_INFINITY;
                for (BeamState typeMoveState : typeMoveStates) {
                    minChildValue = Math.min(minChildValue, stateValue(typeMoveState));
                }
                sum += minChildValue;
            }
        }
        return sum / 6;
    }

    public static HappyBeam matchBeams(HappyBeam beam1, HappyBeam beam2) {
        DiceChess game = new DiceChess();
        HappyBeam[] beams = {beam1, beam2};
        while (game.getState() == GameState.ONGOING) {
            int dieRoll = RANDOM.nextInt(1, 7);
            beams[game.getActiveColor()].play(dieRoll, game);
        }
        //System.out.println("Finished game " + gamesFinished++);

        return switch (game.getState()) {
            case BLACK_WON -> beam1;
            case WHITE_WON -> beam2;
            case DRAW -> null;
            default -> throw new IllegalStateException("Something went wrong with the game state.");
        };
    }

    public static void main(String[] args) {
//        String startPos = "3qkb1r/2p2ppp/n1bp4/2pN4/P3P3/5N1P/1rPBQ1P1/R4RK1 b - - 0 1";
        String startPos = "4kb1r/2p2ppp/n1bp1q2/2pN4/P3P3/5N1P/1rPBQ1P1/R4RK1 w - - 0 1";
//        String startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        DiceChess game = new DiceChess(startPos);
        double[] chromData = {-33, 9, 53, 56, 304, -12, -39, 66, -30, -80, -23, -431, 95, 31, 44, 3903, -47, 0, 68, -92, 31, 49, 51, 338, 87, 100, 3, 814, -15, 59, -54, 85, 3, 65, 74, 81, 434, -1};
        Chromosome c = new Chromosome(chromData);
        BeamSearch bs = new BeamSearch(game, chromData, 1, 2, 3);
        game.register(bs.bestMove());
        FitnessFunction ff = new FitnessFunction();
        System.out.println("THE FINAL EVALUATION IS: " + ff.evaluate(c, game));


//        int w1 = 0;
//        int w2 = 1;
//        HappyBeam beam0 = new HappyBeam(w1);
//        HappyBeam beam1 = new HappyBeam(w2);
//        int gamesFinished = 0;
//        int beam0Wins = 0;
//        int beam1Wins = 0;
//        int draws = 0;
//        while (gamesFinished < 1) {
//            System.out.println("Games finished: " + gamesFinished);
//            HappyBeam winnerBeam = gamesFinished % 2 == 0 ? BeamSearch.matchBeams(beam0, beam1) : BeamSearch.matchBeams(beam1, beam0);
//            gamesFinished++;
//            if (winnerBeam == beam0) beam0Wins++;
//            else if (winnerBeam == beam1) beam1Wins++;
//            else draws++;
//        }
//        System.out.println("Beam0 (weight " + w1 + ") wins: " + beam0Wins);
//        System.out.println("Beam1 (weight " + w2 + ") wins: " + beam1Wins);
//        System.out.println("Draws: " + draws);
    }

    public class BeamState implements Comparable<BeamState> {
        private final DiceChess game;
        private final BeamState parent;
        private final double evaluation;
        private double acceptanceScore;
        private final int layer;
        private boolean hasChildren = false;
        private ArrayList<BeamState> childStates;
        private final int pieceType;

        public BeamState(String fen, BeamState parent, int pieceType) {
            this.game = new DiceChess(fen);
            this.parent = parent;
            this.pieceType = pieceType;
            layer = parent == null ? 0 : parent.layer + 1;
            evaluation = -(new FitnessFunction()).evaluate(game, chromosomeData);
            acceptanceScore = evaluation != 0 ? scoreWeight / evaluation + layer: layer;
        }

        // adds all the states that can be generated from the state (this) to the priority queue
        public ArrayList<BeamState> exploreStates() {
            childStates = new ArrayList<>();
            List<Opportunity> opportunities = this.parent == null ? game.getTeamOpportunities(game.getActiveColor(), roll) : game.getTeamOpportunities(game.getActiveColor());
            boolean[] typesCanMove = new boolean[6];
            for (Opportunity mo : opportunities) {
                for (int i = 0; i < mo.size(); i++) {
                    Movement m = mo.select(i);
                    int mPieceType = game.getBoard().read(m.origin()).type();
                    typesCanMove[mPieceType - 1] = true;
                    game.register(m);
                    game.switchActiveColor();
                    BeamState child = new BeamState(game.toString(), this, mPieceType);
                    childStates.add(child);
                    game.revert();
                }
            }
            for (int i = 0; i < typesCanMove.length; i++) {
                if (!typesCanMove[i]) {
                    game.switchActiveColor();
                    BeamState child = new BeamState(game.toString(), this, i + 1);
                    childStates.add(child);
                    game.switchActiveColor();
                }
            }
            if (opportunities.isEmpty()) {
                game.switchActiveColor();
                BeamState child = new BeamState(game.toString(), this, -1);
                childStates.add(child);
                game.switchActiveColor();
            }
            hasChildren = true;
            return childStates;
        }

        @Override
        public int compareTo(BeamState s) {
            return s.acceptanceScore < this.acceptanceScore ? 1 : -1;
        }

        @Override
        public String toString() {
            return "(" + layer + ", " + evaluation + " -> " + acceptanceScore + ")";
        }
    }
}