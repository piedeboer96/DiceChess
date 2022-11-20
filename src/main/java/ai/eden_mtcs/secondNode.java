package ai.eden_mtcs;

import chess.ChessMatch;
import chess.MatchState;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import chess.interfaces.IChessboard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
// Loop for the depth search
// track nodes
// I dont go to the next leaf when i finish the first simulation

public class secondNode {
    public static void main(String[] args) throws InterruptedException {
        secondNode game = new secondNode(0);
        System.out.println("Running :)");
        while(System.currentTimeMillis() < System.currentTimeMillis()+150) {
            game.selectAction();
            System.out.println(game.toString());
            Thread.sleep(0);
        }
    }
    static Random r = new Random();
    static int nActions = 8;
    static double epislon = 1e-6;;
    secondNode[] children;
    double nVisits, totValue;
    IChessboard board;
    private final int TEAM;
    public secondNode(int team){TEAM = team;}

    public int getTEAM() {return TEAM;}

    public void selectAction(){
        List<secondNode> visited = new LinkedList<secondNode>();
        secondNode current = this;
        visited.add(this);
        while(!current.isLeaf()){
            current = current.select();
            visited.add(current);
        }
        current.expand(); // I m always expanding, that's make no sense
        // this should be a new methods
        secondNode newNode = current.select();
        visited.add(newNode);
        double value = value();
        assert newNode != null;
        Simulation.run(current,newNode);
        for (secondNode node : visited) {
            node.updateValue(value);
        }
        // until here
    }

    public void expand(){
        children = new secondNode[nActions];
        int opponent;
        if (TEAM == 0) { opponent = 1; } // Solution 
        else if (TEAM == 1) { opponent = 0; } // Solution 
        else { throw new IllegalStateException("Expansion node is not assigned to a proper team number."); }
        for (int i = 0; i < nActions; i++) {
            //children[i] = new secondNode(getTEAM()); This is your issue as you assign the same team to the children.
            children[i] = new secondNode(opponent); // Solution
        }
    }

    private secondNode select(){
        secondNode selected = null;
        double bestValue = Double.MIN_VALUE;
        for (secondNode c : children) {
            double UCT = c.totValue / (c.nVisits + epislon ) +
                    Math.sqrt(Math.log(nVisits+1)) / (c.nVisits + epislon) +
                    r.nextDouble() * epislon;
            // small random number to break ties randomly in unexpanded nodes
            if (UCT>bestValue) {
                selected = c;
                bestValue = UCT;
            }
        }
        return selected;
    }
    public boolean isLeaf(){
        return children == null;
    }
    public void updateValue(double value){
        nVisits++;
        totValue += value;
    }
    public int childrenLenght(){
        return children == null ? 0 : children.length;
    }

    /* *
     * Let this decide how you select ur destinations from a move.
     * */
    public IChessboardSquare decideDestination(IChessMove m) {
        var destinations = m.possibilities();
        int index = r.nextInt(0, destinations.size());
        return destinations.get(index);
    }
    /* *
     * Let this decide how you select ur move from all moves u can make.
     * */
    public IChessMove decideMove(IChessboard b) {
        var moves = b.generateMovesOf(TEAM);
        int index = r.nextInt(0, moves.size());
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
                if (TEAM == 0) { totValue += 1.0; }
                else { totValue -= 1.0; }
            }
            case WHITE_WON -> {
                if (TEAM == 1) { totValue += 1.0; }
                else { totValue -= 1.0; }
            }
            case DRAW -> { totValue += 0.5; }
            case ONGOING -> throw new IllegalArgumentException("Game has not yet ended.");
            default -> throw new IllegalArgumentException("Match state has issues.");
        }
    }
    public int team() {
        return TEAM;
    }

    public double value() {
        return totValue;
    }

    @Override
    public String toString() {
        return "secondNode{" +
                "children=" + Arrays.toString(children) +
                ", nVisits=" + nVisits +
                ", totValue=" + totValue +
                ", board=" + board +
                ", TEAM=" + TEAM +
                '}';
    }










}
class Simulation {
    public static void run(secondNode n1, secondNode n2) {
        if (n1.team() == n2.team()) {
            throw new IllegalArgumentException("Can not simulate two nodes that are on the same team.");
        }
        secondNode[] nodes;
        if (n1.team() == 0) {
            nodes = new secondNode[]{n1, n2};
        } else {
            nodes = new secondNode[]{n2, n1};
        }
        IChessMatch match = new ChessMatch();
        while (match.getState() == MatchState.ONGOING) {
            nodes[match.getPlayer()].play(match);
        }
        for (secondNode n : nodes) {
            n.onGameEnded(match);
        }
    }
}















//    public IChessMove randomMove(IChessboard board, int team){
//        var moves = board.generateMovesOf(team);
//        Random ran = new Random();
//        int randomIndex = ran.nextInt(0, moves.size());
//        return moves.get(randomIndex);
//    }
//    public double whoWins(ChessMatch match){
//        while (match.getState()== MatchState.ONGOING){
//            var move = randomMove(board, team);
//            var destination = move.possibilities();
//            Random ran = new Random();
//            int randomIndex = ran.nextInt(0,destination.size());
//            match.playMove(move.owner(),destination.get(randomIndex));
//
//        }
//    }
