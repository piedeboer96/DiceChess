package beamsearch;
import game.*;
import genetic.FitnessFunction;

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
            acceptanceScore = -evaluation * evaluationWeight + layer;
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
            return Double.compare(acceptanceScore, s.acceptanceScore);
        }
    }
}