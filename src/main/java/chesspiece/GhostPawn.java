package chesspiece;

import game.Team;
import utility.Tile;

import java.util.List;

public class GhostPawn extends ChessPiece
{
    private final char fen;
    public GhostPawn(Team team, Tile tile)
    {
        super(team,tile,101);
        if (team == Team.BLACK) {
            fen = 'g';
        }
        else {
            fen = 'G';
        }
    }

    @Override
    public List<Tile> determineMoves(ChessPiece[] board) {
        return null;
    }

    @Override
    public char toFen() {
        return fen;
    }
}
