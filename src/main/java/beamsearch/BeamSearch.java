package beamsearch;
import game.*;
import genetic.FitnessFunction;
import player.*;
import simulation.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class BeamSearch {
    private double evaluationWeight;
    private int roll;
    private double[] chromosomeData;
    private DiceChess game;
    private int time;

    public BeamSearch(DiceChess game, double[] chromosomeData, double scoreWeight, int roll, int time) {
        this.evaluationWeight = scoreWeight;
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
//                System.out.println(child);
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
//        List<BeamState> leafs = new ArrayList<>(positions.stream().toList());
//        System.out.println("THIS IS THE LEAF WITH LOWEST DEPTH (LAYER)");
//        leafs.sort(null);
//        System.out.println(leafs.get(0));
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

    public static Player matchPlayers(Player player1, Player player2) throws IOException {
        DiceChess game = new DiceChess();
        Player[] players = {player1, player2};
        while (game.getState() == GameState.ONGOING) {
            int roll = game.getDie().roll();
            String nextState = players[game.getActiveColor()].play(roll, game);
            game = new DiceChess(nextState);
        }
        //System.out.println("Finished game " + gamesFinished++);

        return switch (game.getState()) {
            case BLACK_WON -> player1;
            case WHITE_WON -> player2;
            case DRAW -> null;
            default -> throw new IllegalStateException("Something went wrong with the game state.");
        };
    }

    public static void main(String[] args) throws IOException {
////        String startPos = "3qkb1r/2p2ppp/n1bp4/2pN4/P3P3/5N1P/1rPBQ1P1/R4RK1 b - - 0 1";
//        String startPos = "4kb1r/2p2ppp/n1bp1q2/2pN4/P3P3/5N1P/1rPBQ1P1/R4RK1 w - - 0 1";
////        String startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
//        DiceChess game = new DiceChess(startPos);
//        // double[] chromData = {-33, 9, 53, 56, 304, -12, -39, 66, -30, -80, -23, -431, 95, 31, 44, 3903, -47, 0, 68, -92, 31, 49, 51, 338, 87, 100, 3, 814, -15, 59, -54, 85, 3, 65, 74, 81, 434, -1};
//        double[] chromData = {-18.77599310779766, 10.840277665373705, 47.93622353916436, 54.50009518085518, 310.9753182323636, -23.53453140609233, -24.38864715031104, 39.184568721870214, -24.285645755183396, -46.6734011532375, 2.8962997595950397, -401.9068232086311, 49.34810803454471, 15.087657805510469, 26.935052464671994, 3072.122960094753, -36.58720968853096, 8.795996785343341, 52.297521128287066, -50.92254277667105, -8.749340964633744, 18.96760713827327, 19.279946574108056, 294.40210499624527, 44.46093071074533, 100.50345368862263, 5.20643801344257, 900.3781437716922, 2.6034192912795207, 50.24790862245176, -16.819537772558384, 49.374470905991146, 5.41164621141734, 49.06605126448517, 50.556746404613634, 54.835198369233105, 505.011781180023, -9.716360297901703, 189.0};
//        Chromosome c = new Chromosome(chromData);
//        BeamSearch bs = new BeamSearch(game, chromData, 1, 2, 60);
//        game.register(bs.bestMove());
//        FitnessFunction ff = new FitnessFunction();
//        System.out.println("THE FINAL EVALUATION IS: " + ff.evaluate(c, game));


        int w2 = 1;
        Player gambler = new Gambler();
        HappyBeam beam = new HappyBeam(w2);
        int gamesFinished = 0;
        int player1Wins = 0;
        int player2Wins = 0;
        int draws = 0;
        while (gamesFinished < 100) {
            System.out.println("Games finished: " + gamesFinished);
            Player winnerBeam = gamesFinished % 2 == 0 ? BeamSearch.matchPlayers(gambler, beam) : BeamSearch.matchPlayers(beam, gambler);
            gamesFinished++;
            if (winnerBeam == gambler) player1Wins++;
            else if (winnerBeam == beam) player2Wins++;
            else draws++;
            System.out.println("Gambler wins: " + player1Wins);
            System.out.println("Beam wins: " + player2Wins);
            System.out.println("Draws: " + draws);
        }
        System.out.println("Gambler FINAL wins: " + player1Wins);
        System.out.println("Beam FINAL wins: " + player2Wins);
        System.out.println("FINAL Draws: " + draws);
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
            acceptanceScore = evaluation != 0 ? -evaluation* evaluationWeight + layer : layer;
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
//                    System.out.println(child);
                    childStates.add(child);
                    game.revert();
                }
            }
            for (int i = 0; i < typesCanMove.length; i++) {
                if (!typesCanMove[i]) {
                    game.switchActiveColor();
                    BeamState child = new BeamState(game.toString(), this, i + 1);
//                    System.out.println(child);
                    childStates.add(child);
                    game.switchActiveColor();
                }
            }
            if (opportunities.isEmpty()) {
                game.switchActiveColor();
                BeamState child = new BeamState(game.toString(), this, -1);
//                System.out.println(child);
                childStates.add(child);
                game.switchActiveColor();
            }
            hasChildren = true;
            return childStates;
        }

        @Override
        public int compareTo(BeamState s) {
            return Double.compare(acceptanceScore, s.acceptanceScore);
        }

        @Override
        public String toString() {
            return "(" + layer + ", " + evaluation + " -> " + acceptanceScore + ")";
        }
    }
}