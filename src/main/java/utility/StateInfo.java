package utility;

import java.util.HashMap;
import chesspiece.ChessPiece;
import game.Team;

public class StateInfo {
    private HashMap<Tile, ChessPiece> tilesWithPieces;
    private HashMap<Tile, ChessPiece[]> tilesUnderAttack;
    private HashMap<ChessPiece, ChessPiece[]> piecesUnderAttack;
    private HashMap<ChessPiece, ChessPiece[]> piecesDefended;
    private HashMap<Team, Integer> teamsValues;

    public void update_TilesWithPieces(ChessPiece chessPiece, Tile fromTile, Tile toTile) {
        tilesWithPieces.replace(fromTile, chessPiece, null);
        tilesWithPieces.replace(toTile, chessPiece);
        update_PiecesUnderAttack(chessPiece, fromTile, toTile);
    }

    public HashMap get_TilesWithPieces() {
        return tilesWithPieces;
    }

    public void update_PiecesUnderAttack(ChessPiece chessPiece, Tile fromTile, Tile toTile) {

    }

    public HashMap get_PiecesUnderAttack() {
        return piecesUnderAttack;
    }

    public void update_PiecesDefended() {

    }

    public HashMap get_PiecesDefended() {
        return piecesDefended;
    }

    public void update_TilesUnderAttack() {

    }

    public HashMap get_TilesUnderAttack() {
        return tilesUnderAttack;
    }

    public void update_TeamsValues(ChessPiece lostPiece) {
        Team team;
        if (lostPiece.getTeam() == Team.WHITE) {
            team = Team.WHITE;
        } else {
            team = Team.BLACK;
        }
        teamsValues.replace(team, teamsValues.get(team) - lostPiece.getValue());
    }

    public HashMap get_TeamsValues() {
        return teamsValues;
    }
}
