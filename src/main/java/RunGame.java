import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import gui.Window;

public class RunGame
{
    public static void main(String[] args)
    {
        IChessMatch match = new ChessMatch();
        Window window = new Window();
        window.displayMatch(match);

    }
}
