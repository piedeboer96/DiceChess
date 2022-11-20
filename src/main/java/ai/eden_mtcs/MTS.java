package ai.eden_mtcs;

import chess.ChessMatch;

public class MTS {

    ChessMatch match;
    Node parent;

    public MTS(ChessMatch match, Node parent){
        this.match = match;
        this.parent = parent;
    }
}
