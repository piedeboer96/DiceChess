package ai.geneticAI;
import chess.ChessMatch;
import chess.MatchState;
import chess.interfaces.IChessMove;
import gui.DiceChessWindow;
import gui.die.Die;
import java.util.concurrent.TimeUnit;



public class RunGA {
    
    public static void main(String[] args) throws InterruptedException {

        RunGA runGame = new RunGA();
        runGame.game();

    }

    public void game() throws InterruptedException {
        // Creating a new window.
        DiceChessWindow window = new DiceChessWindow(800, 640, false);
        // full pieces
        String startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        // Creating a new match.
        ChessMatch match = new ChessMatch(startPos);
        match.loadKings();
        // roll the dice
        Die die = new Die();
        // Displays the match
        window.display(match);
        //play the game
        int iteration=0;
        while (iteration++ < 1000) {
            int currentPlayer = match.getPlayer();
            // first we need fire the roll
            char rollOne = window.getDice().roll(currentPlayer)[0];
            char rollTwo = window.getDice().roll(currentPlayer)[0];

            sleep(500);

            // assign the object bot to the best bot in the GA population
            Bot bot = Trainer.bestBot();
            IChessMove decision = bot.bestMove(match, rollOne, rollTwo);
            if (decision == null) {
                continue;
            }
            match.playMove(decision.owner(), decision.possibilities().get(0));

            window.refresh();

            sleep(500);

            window.display(match);
        }
    }


    /**
     * @param sec
     */
    private void sleep(int sec) {
        try {
            TimeUnit.MILLISECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        
}
