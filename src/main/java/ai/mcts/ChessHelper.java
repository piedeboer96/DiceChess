package ai.mcts;

import chess.interfaces.IChessMove;
import chess.utility.Chessboard;
import chess.utility.ChessMove;

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


    /**
     * We take in a node that has a state.
     * The board is represented in the state.
     * We pick a random legal move, execute this
     * and update the node it's board after execution of this move.
     * @param node
     */
    public void playRandom(Node node) {

        Node tempNode = node;
        State state = node.getState();
        Chessboard board = state.getBoard();

        List<IChessMove> legalMoves = board.legalMovesOf(state.getTeam());

        int amountOfLegalMoves = legalMoves.size();



        // TODO: ok... work with chessMatch or boardPositions, cause i need some action method to take move a

        /** PROBLEMS
         *  - what if player cannot make move
         */



    }

    public Chessboard executeMove(Chessboard board, int team) {

        //TODO:
        // (idea) Use the resultAI class here and incorporate board states ( use easy rules engine?)
        // (?) change fen into board state, or shall we use fen all the time?

        List<IChessMove> legalMoves = board.legalMovesOf(team);

        return null;

    }


}