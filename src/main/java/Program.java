import board.SwingBoard;
import board.SwingBoardListener;
import game.Match;
import renderer.SwingUI;

public class Program {
    public static void main(String[] args) {
        Match match = new Match("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        SwingBoard board = new SwingBoard(360, 50, 64, 64);
        board.setPieces(match.board);

        new SwingBoardListener(board, match);
        SwingUI frame = new SwingUI(1280, 720);
        frame.add(board);
        frame.setVisible(true);
        frame.center();
    }
}
