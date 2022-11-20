//package ai.eden_mtcs;
//
//import chess.ChessMatch;
//import chess.MatchState;
//import chess.utility.ChessMove;
//
//import java.util.ArrayList;
//
//import static ai.eden_mtcs.UCT.*;
//
//public class Node {
//    ChessMatch match;
//    ChessMove move;
//    Node parent;
//    double value;
//    int visit;
//    ArrayList<Node> children;
//    public Node (ChessMatch match, ChessMove move, Node parent){
//        this.match = match;
//        this.move = move;
//        this.parent = parent;
//        this.children = new ArrayList<>();
//        this.visit = 0;
//        this.value = 0;
//    }
//
//
//    public void expansionNode(ChessMatch match) {
//        if(match.getState()==MatchState.ONGOING){
//
//        }
//    }
//
//    public void update(int visit){
//        this.visit++;
//        if (this.visit == visit) this.value++;
//    }
//    public ChessMatch getMatch() {
//        return match;
//    }
//    public void setMatch(ChessMatch match) {
//        this.match = match;
//    }
//    public ChessMove getMove() {
//        return move;
//    }
//    public void setMove(ChessMove move) {
//        this.move = move;
//    }
//    public Node getParent() {
//        return parent;
//    }
//    public void setParent(Node parent) {
//        this.parent = parent;
//    }
//    public void setValue(){
//        this.value = UCT.solution(winning,sample,tWinning);
//    }
//    public double getValue(){return value;}
//
//}
