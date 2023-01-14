package learningagent.database;

import java.io.FileNotFoundException;

/**
 * Define the amount of games and run the main method to
 */
public class WriteChessData {

    // selfPlay object is needed to run games
    static SelfPlay sp;

    static {
        try {
            sp = new SelfPlay();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        // 0 is random
        // 1 is genetic
        int playerType = 1;
        int numberOfGames = 1;

        // write x number of games to the database
        sp.writeOutcomesToDB(numberOfGames, playerType);

    }


}
