package chesspiece;

import java.util.List;

public interface ChessPiece
{

    public int getPosition();                         //can return coords[]
    public List legalMoves();                           //took out the para. int a
    public void setPosition(int x);              //can use coords[] as parameter
    public char getTeam();
    public char getType();
    public int getNumber();

}
