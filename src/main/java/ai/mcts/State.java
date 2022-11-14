package ai.mcts;

import chess.ChessMatch;
import chess.MatchState;
import chess.utility.Chessboard;

// TODO: Working with chessMatch convenient? (cause just board probably not)

public class State {

    /** DiceChess Attributes */
    Chessboard board;       // might not be needed
    int team;

    public ChessMatch getMatch() {
        return match;
    }

    public void setMatch(ChessMatch match) {
        this.match = match;
    }

    ChessMatch match;
    String fen;

    /** UCT Attributes */
    int winCount;
    int visitCount;

    /** Getters and Setters */
    public void setTeam(int team){
        this.team = team;
    }

    public void setBoard(Chessboard board) {
        this.board = board;
    }

    public int getOpponent(int team) {
        int opponent;

        if(team==1){
            opponent = 2;
        } else {
            opponent = 1;
        }
        return opponent;
    }

    public int getWinCount(){
        return winCount;
    }
    public int getVisitCount(){
        return visitCount;
    }

    public Chessboard getBoard() {
        return board;
    }

    public int getTeam() {
        return team;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    /** AUXILIARY METHODS */
    public boolean isGameGoingOn() {
        if(this.match.getState().equals(MatchState.ONGOING))
            return true;

        return false;
    }

    public void increaseWinCount(int score){
        this.winCount = this.winCount + score;
    }

    // TODO !!!
    public String getFen(){

        // need to be implemented

        return "";
    }

    public void switchPlayer(){
        int currentTeam = this.team;

        if(currentTeam==1){
            this.team = 2;
        } else {
            this.team = 1;
        }
    }


}