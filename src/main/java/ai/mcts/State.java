package ai.mcts;

import chess.utility.Chessboard;

// TODO: Working with chessMatch not convenient?

// TODO: need to bring all legalMoves into new board states ('execute' legal moves')

public class State {

    /** DiceChess Attributes */
    Chessboard board;
    int team;

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

    /** AUXILIARY METHODS */
    public boolean isGameGoingOn() {
        if(board.playerIsCheckMated(0) || board.playerIsCheckMated(1))
            return true;

        return false;
    }



}