package ai.geneticAI;
import ai.easyrules.BoardAction;
import chess.ChessMatch;
import gui.DiceChessWindow;
import java.util.concurrent.TimeUnit;



public class RunGA {
    
    public static void main(String[] args) {

        RunGA runGame = new RunGA();
        runGame.game();

    }

    public void game() {

        // Creating a new window.
        DiceChessWindow window = new DiceChessWindow(800, 640, false);

        // full pieces
        String startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";


        // Creating a new match.
        ChessMatch match = new ChessMatch(startPos);
        match.loadKings();

        // Displays the match
        window.display(match);

        //play the game
        int currentPlayer;
        int i = 0;
        int maxMove = 1000;
        while (i++ < maxMove) {
            currentPlayer = match.getPlayer();
            // first we need fire the roll
            char rollOne = window.getDice().roll(currentPlayer)[0];
            char rollTwo = window.getDice().roll(currentPlayer)[0];

            sleep(500);

            BoardAction play = Trainer.bestBot().play().action;
            if (play == BoardAction.FINISH_MATCH) {
                while (true) {
                    window.refresh();
                    sleep(500);
                }
            }
            //
            window.refresh();

            sleep(1000);
            window.display(match);
        }
    }

    private void sleep(int sec) {
        try {
            TimeUnit.MILLISECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        
}
