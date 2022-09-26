package chess.interfaces;

public interface IChessMatch extends IChessBoard
{
    int getPlayer();

    void interpret(String fen);

    void playMove(IChessPiece piece, IChessBoardSquare destination);

    void promote(IChessPiece piece, IChessPiece target);

    String toFen();
}
