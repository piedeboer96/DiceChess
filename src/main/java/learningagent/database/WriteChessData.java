package learningagent.database;

import java.io.FileNotFoundException;

/**
 * Define the amount of games and run the main method to
 */
public class WriteChessData {

    static SelfPlay sp;

    static {
        try {
            sp = new SelfPlay();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        int numberOfGames = 1;
        sp.writeOutcomesToDB(numberOfGames);

    }

}
