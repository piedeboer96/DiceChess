package ai.mcts;

import chess.ChessMatch;
import chess.MatchState;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;

import java.util.ArrayList;
import java.util.List;


public class ChessHelper {

    /**
     * Generate all possible states from a given node.
     * @param currentNode node from where we need the possible states
     * @return list of possible states
     */
    public List<State> getStatesFromNode(Node currentNode){

        List<State> states = new ArrayList<>();

        // get info
        int team = currentNode.getState().getTeam();
        String fen = currentNode.getState().getFen();

        // generate match
        ChessMatch match = new ChessMatch(fen);

        // get legal moves of team
        List<IChessMove> legalMoves = match.generateMovesOf(team);

        // iterate over the moves - UGH DOUBLE LOOP...
        for(IChessMove move: legalMoves) {

            for (IChessboardSquare destination : move.possibilities()) {

                match.playMove(move.owner(), destination);

                // does this update the match ?
                // player is updated - since we get into new layer

                State state = new State();

                state.setFen(match.toFen());
                state.setTeam(match.getPlayer());

                states.add(state);

            }
        }

        return states;
    }

    /**
     * We take in a node that has a state which represents the game with FEN.
     * We pick a random legal move, execute this
     * and update the node it's fen after execution of this move.
     * @param node node from which randomPlay-out is done
     */
    public void playRandom(Node node) {

        // get info
        int team = node.getState().getTeam();
        String fen = node.getState().getFen();

        // build match
        ChessMatch match = new ChessMatch(fen);

        // generate moves from of team at certain node
        List<IChessMove> legalMoves = match.legalMovesOf(team);

        // random move
        int maxIndex = legalMoves.size();
        int minIndex = 0;
        int randomPick = (int) ((Math.random() * (maxIndex - minIndex)) + minIndex);

        // pick random move
        IChessMove move = legalMoves.get(randomPick);

        // play while the match is taking place
        while( match.getState().equals(MatchState.ONGOING) ){
            executeMove(move, node);
        }

        // update winCount or winScore..

    }

    // update state of node after executing a move
    public void executeMove(IChessMove move, Node node) {

        ChessMatch match = node.getState().getMatch();

        // owner of the move
        IChessPiece moveOwner = move.owner();
        move.possibilities();

        // choose random move
        int upper = move.possibilities().size();
        int lower = 0;
        int randomPick = (int) ((Math.random() * (upper - lower)) + lower);

        // pick random destination for move
        IChessboardSquare destination = move.possibilities().get(randomPick);

        // play move
        match.playMoveNoTogglePlayer(moveOwner, destination);

        // update state
        node.getState().setFen(match.toFen());

        // check if we won?
        match.getState().equals(MatchState.ONGOING);

        // toggle player
        match.nextPlayer();

    }

}