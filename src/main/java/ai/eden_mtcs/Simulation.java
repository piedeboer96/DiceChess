//package ai.eden_mtcs;
//
//import chess.ChessMatch;
//import chess.MatchState;
//import chess.interfaces.IChessMatch;
//
//class Simulation {
//    secondNode node = new  secondNode(0);
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
