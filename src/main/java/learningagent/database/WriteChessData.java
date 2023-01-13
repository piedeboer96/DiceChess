package learningagent.database;

/**
 * Define the amount of games and run the main method to
 */
public class WriteChessData {

    // selfPlay object is needed to run games
    static SelfPlay sp = new SelfPlay();

    public static void main(String[] args) {

        // write x number of games to the database
        sp.writeOutcomesToDB(10000);

    }


}
