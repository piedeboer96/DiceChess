package ai.hce;

import chess.utility.ChessBoard;
import chess.interfaces.*;
import chess.units.*;
import chess.utility.ChessBoardSquare;

import java.util.List;
import java.util.HashMap;

public class AttackingKingZone {
    private int teamToEvaluate;
    private int opponentTeam;
    private IChessMatch match;
    IChessPiece kingOfOpponent;
    private int kingZoneDistance = 1;
    private List<IChessMove> movesOfTeam;

    /**
     * attackingPiecesCount: number of pieces of the opponent that is
     * attacking the king zone.
    **/
    private int attackingPiecesCount = 0;
    /**
     * valueOfAttacks: Sum of the number of squares attacked times a constant
     * (based on the attacking piece of the square):
     * 20 for a knight, 20 for a bishop, 40 for a rook and 80 for a queen.
    **/
    private int valueOfAttacks = 0;

    /**
     * attackWeight: attack weight based on attackingPiecesCount.
    **/
    private int[] attackWeight = {0, 50, 75, 88, 94, 97, 99};


    public AttackingKingZone(int teamToEvaluate, IChessMatch match) {
        this.teamToEvaluate = teamToEvaluate;
        this.match = match;
        if(teamToEvaluate == 0) {
            opponentTeam = 1;
        } else {
            opponentTeam = 0;
        }
        kingOfOpponent = match.getKing(opponentTeam);
        movesOfTeam = match.generateMovesOf(opponentTeam);
    }

    /**
     * It updates/sets the variables valueOfAttacks and attackingPiecesCount.
    **/
    private void updateAttackValues() {
        int kingRank = kingOfOpponent.rank();
        int kingFile = kingOfOpponent.file();
        int rankStartDistance;
        int rankEndDistance;

        if(opponentTeam == 1) {
            rankStartDistance = 2;
            rankEndDistance = 1;
        } else {
            rankStartDistance = 1;
            rankEndDistance = 2;
        }

        for(int rankTemp = kingRank - rankStartDistance; rankTemp <= kingRank + rankEndDistance; rankTemp++) {
            for(int fileTemp = kingFile - kingZoneDistance; fileTemp <= kingFile + kingZoneDistance; fileTemp++) {
                if(rankTemp < 0 || rankTemp > 7 || fileTemp < 0 || fileTemp > 7) continue;
                if(rankTemp == kingRank && fileTemp == kingFile) continue;
                IChessBoardSquare squareTemp = new ChessBoardSquare(fileTemp, rankTemp);
                for(IChessMove move : movesOfTeam) {
                    if(move.canReach(squareTemp)) {
                        attackingPiecesCount++;
                        IChessPiece attackingPiece = move.owner();
                        if(attackingPiece instanceof Knight || attackingPiece instanceof Bishop) {
                            valueOfAttacks += 20;
                        } else if(attackingPiece instanceof Rook) {
                            valueOfAttacks += 40;
                        } else if(attackingPiece instanceof Queen) {
                            valueOfAttacks += 80;
                        } else {
                            attackingPiecesCount--;
                        }
                    }
                }
            }
        }
    }

    public double getAttackScore() {
        updateAttackValues();
        return valueOfAttacks * attackWeight[attackingPiecesCount] / 100;
    }

}