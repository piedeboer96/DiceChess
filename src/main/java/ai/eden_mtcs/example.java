package ai.eden_mtcs;

import chess.ChessMatch;
import chess.MatchState;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboard;
import chess.interfaces.IChessboardSquare;

import java.util.Random;

class Node {
    private final Random RND = new Random();
    private final int TEAM;
    private double value;

    public Node(int team) {
        TEAM = team;
    }

    /* *
     * Let this decide how u select ur destinations from a move.
     * */
    public IChessboardSquare decideDestination(IChessMove m) {
        var destinations = m.possibilities();
        int index = RND.nextInt(0, destinations.size());
        return destinations.get(index);
    }

    /* *
     * Let this decide how u select ur move from all moves u can make.
     * */
    public IChessMove decideMove(IChessboard b) {
        var moves = b.generateMovesOf(TEAM);
        int index = RND.nextInt(0, moves.size());
        return moves.get(index);
    }

    /* *
     * Let this be the method ur simulation runner needs.
     * */
    public void play(IChessMatch match) {
        var move = decideMove(match);
        var destination = decideDestination(move);
        match.playMove(move.owner(), destination);
    }

    /* *
     * Let this be the node value updater whenever the game is over.
     * */
    public void onGameEnded(IChessMatch match) {
        switch (match.getState()) {
            case BLACK_WON -> {
                if (TEAM == 0) { value += 1.0; }
                else { value -= 1.0; }
            }
            case WHITE_WON -> {
                if (TEAM == 1) { value += 1.0; }
                else { value -= 1.0; }
            }
            case DRAW -> { value += 0.5; }
            case ONGOING -> throw new IllegalArgumentException("Game has not yet ended.");
            default -> throw new IllegalArgumentException("Match state has issues.");
        }
    }

    public int team() {
        return TEAM;
    }

    public double value() {
        return value;
    }
}





//class Simulation {
//    public void run(Node n1, Node n2) {
//        if (n1.team() == n2.team()){
//            throw new IllegalArgumentException("Can not simulate two nodes that are on the same team.");
//        }
//        Node[] nodes;
//        if (n1.team() == 0){
//            nodes = new Node[]{ n1, n2 };
//        } else {
//            nodes = new Node[] { n2, n1 };
//        }
//        IChessMatch match = new ChessMatch();
//        while (match.getState() == MatchState.ONGOING) {
//            nodes[match.getPlayer()].play(match);
//        }
//        for (Node n : nodes) {
//            n.onGameEnded(match);
//        }
//    }
//}