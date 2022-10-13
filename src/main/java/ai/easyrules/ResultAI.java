package ai.easyrules;

import chess.utility.ChessMove;

public class ResultAI {

	public Action action;
	public  ChessMove bestMove;

	public ResultAI(Action action, ChessMove bestMove) {
		this.action = action;
		this.bestMove = bestMove;
	}

}
