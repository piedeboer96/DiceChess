//package ai.easyrules;
//
//import java.util.ArrayList;
//
//public class ResultAI {
//
//	public BoardAction action;
//	public char fen;
//	public int toRank;
//	public int toFile;
//	public int fromRank;
//	public int fromFile;
//	public ArrayList<String> bestMsgStack;
//
//	public ResultAI(BoardAction action, char fen, int fromFile, int fromRank, int toFile, int toRank) {
//		this.action = action;
//		this.fen = fen;
//		this.fromFile = fromFile;
//		this.fromRank = fromRank;
//		this.toFile = toFile;
//		this.toRank = toRank;
//	}
//
//	@Override
//	public String toString() {
//		return "ResultAI [action=" + action + ", fen=" + fen +", fromFile=" + fromFile +", fromRank=" + fromRank +  ", toFile=" + toFile +", toRank=" + toRank +   "]";
//	}
//
//	public void setMsgStack(ArrayList<String> bestMsgStack) {
//		this.bestMsgStack = bestMsgStack;
//
//	}
//
//}
