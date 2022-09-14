package ChessPiece;

public interface ChessPiece
{
    public int[] getPosition();
    public int[] legalMoves();
    public void setPosition();
    public int getTeam();
    public int getType();

}
