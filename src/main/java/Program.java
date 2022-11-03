import zzz.solutions.game.DiceChess;
import zzz.solutions.gui.DiceChessWindow;

public final class Program {
    public static void main(String[] args) {
        DiceChessWindow window = new DiceChessWindow();
        window.enable(0);
        window.enable(1);

        DiceChess game = DiceChess.newGame(window.getPlayer(0), window.getPlayer(1));
        game.attach(window.getConsole());
        game.attach(window);
        game.start();

        window.attach(game);
        window.activate();
        System.out.println("GAME ENDED WITH [#FULL MOVES: " + game.getFullMoves() + " | #HALF MOVES: " + game.getHalfMoves() + "]");
    }
}
