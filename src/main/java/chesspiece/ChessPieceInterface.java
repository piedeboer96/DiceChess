package chesspiece;

import java.util.List;

public interface ChessPieceInterface
{
    int[] getPosition();
    List<int[]> legalMoves();
    void setPosition(int x, int y);
    char getTeam();
    char getType();
    int getNumber();
}
