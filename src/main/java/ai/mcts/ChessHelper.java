package ai.mcts;

import chess.interfaces.IChessMove;
import chess.utility.Chessboard;

import java.util.List;


public class ChessHelper{

    public List<State> getStatesFromNode(Node currentNode){

        State state = currentNode.state;
        int team = currentNode.getState().getTeam();
        Chessboard board =  state.getBoard();
        List<IChessMove> legalMoves = board.legalMovesOf(team);

        // TODO: how do are doing this regards depth and stochastic feature

        return null;
    }


    public void playRandom() {

        //TODO: use random bot here obviously

        // generate the possible states

        // make random move based on state

    }


}