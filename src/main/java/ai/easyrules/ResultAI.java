package ai.easyrules;

import java.util.ArrayList;

public class ResultAI {

	public Action action;
	public char fen;
	public int toRank;
	public int toFile;
	public int fromRank;
	public int fromFile;
	public ArrayList<String> bestMsgStack;
	

	

	public ResultAI(Action action, char fen, int fromFile, int fromRank, int toFile, int toRank) {
		this.action = action;
		this.fen = fen;
		this.fromFile = fromFile;
		this.fromRank = fromRank;
		this.toFile = toFile;
		this.toRank = toRank;
		 
	}




	public void setMsgStack(ArrayList<String> bestMsgStack) {
		this.bestMsgStack = bestMsgStack;
		 
		
	}

}
