package chesspiece;

import java.util.List;

public interface ChessPiece
{

    public int[] getPosition();
    public List legalMoves();
    public void setPosition(int x, int y);
    public char getTeam();
    public char getType();
    public int getNumber();

}
