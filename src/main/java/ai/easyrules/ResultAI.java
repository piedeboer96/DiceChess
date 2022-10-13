package ai.easyrules;

import chess.utility.ChessMove;

public class ResultAI {

	public Action action;
	public char fen;
	public int toRank;
	public int toFile;
	public int fromRank;
	public int fromFile;
	

	

	public ResultAI(Action action, char fen, int fromFile, int fromRank, int toFile, int toRank) {
		this.action = action;
		this.fen = fen;
		this.fromFile = fromFile;
		this.fromRank = fromRank;
		this.toFile = toFile;
		this.toRank = toRank;
		 
	}

}
