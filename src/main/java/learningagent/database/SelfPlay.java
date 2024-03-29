package learningagent.database;

import dice.Die;
import game.DiceChess;
import game.GameState;
import player.*;
import simulation.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;


import java.util.*;

/**
 * Class to simulate selfplay to build database.
 */
public class SelfPlay {

    Player ga1 = new Lunatic();
    Player ga2 = new Lunatic();

    public SelfPlay() throws FileNotFoundException {
    }

    /**
     * Generate games using Genetic Algorithm playouts.
     * @return ArrayList of game outcomes
     */
    public ArrayList<Outcome> playChess() throws IOException {

        return simulate(ga1,ga2);

    }

    // Simulate one match and generate list with simplified FEN with outcome information
    public ArrayList<Outcome> simulate(Player p1, Player p2) throws IOException {

        // outcomes
        ArrayList<Outcome> outcomes = new ArrayList<>();

        // no humans allowed
        if (p1 instanceof Human || p2 instanceof Human) {
            throw new IllegalArgumentException("Not allowed to do back-end simulation with a human player.");
        }

        // specify players
        Player[] players = {p1, p2};
        DiceChess game = new DiceChess();
        Die d = game.getDie();

        while (game.getState() == GameState.ONGOING) {
            String nextState = players[game.getActiveColor()].play(d.roll(), game);
            game.update(nextState);
        }

        Stack<String> HISTORY = game.getHistory();

        while (HISTORY.size() > 1) {
            String FEN = HISTORY.pop();
            String boardFen = FEN.split(" ", 2)[0];

            if (isFenVisited(boardFen, outcomes)) {
                // REPEATED POSITION
            } else {
                Outcome o = new Outcome(boardFen);
                switch (game.getState()) {
                    case DRAW -> o.incrementDraws();
                    case BLACK_WON -> o.incrementBlackWhiteWINS();
                    case WHITE_WON -> o.incrementWhiteWINS();
                }
                outcomes.add(o);
            }
        }
        return outcomes;
    }

    /**
     * DATABASE
     */
    public void writeOutcomesToDB(int numberOfGames) {
        // JDBC driver name and database URL
        final String DB_URL = "jdbc:mysql://localhost:3306/chess";
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

        //  Database credentials
        final String USER = "cakeboy";
        final String PASS = "cake043";

        Connection conn = null;
        Statement stmt = null;

        try {

            //System.out.println(System.getProperty("java.class.path"));
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query
            stmt = conn.createStatement();

            // HERE WE RUN GAMES THAT WE ADD TO THE DATABASE
            for (int i = 0; i < numberOfGames; i++) {

                ArrayList<Outcome> outcomes = playChess();

                for (Outcome outcome : outcomes) {

//                    System.out.println(outcome.getFen());

                    String sql = "INSERT INTO genetic_dice (FEN, blackWins, whiteWins, Draws) VALUES (?, ?, ?, ?) "
                            + "ON DUPLICATE KEY UPDATE blackWins = blackWins + ?, whiteWins = whiteWins + ?, Draws = Draws + ?";

                    PreparedStatement preparedStmt = conn.prepareStatement(sql);
                    preparedStmt.setString(1, outcome.getFen());
                    preparedStmt.setInt(2, outcome.getBlackWINS());
                    preparedStmt.setInt(3, outcome.getWhiteWINS());
                    preparedStmt.setInt(4, outcome.getDRAWS());
                    preparedStmt.setInt(5, outcome.getBlackWINS());
                    preparedStmt.setInt(6, outcome.getWhiteWINS());
                    preparedStmt.setInt(7, outcome.getDRAWS());
                    preparedStmt.execute();

                }

            }

            // Clean-up environment
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

    }


    // Helper method that checks if FEN was already visited
    public boolean isFenVisited(String FEN, ArrayList<Outcome> outcomes) {
        if (outcomes == null || outcomes.size() == 0) {
            return false;
        }

        for (Outcome outcome : outcomes) {
            if (outcome.getFen().equals(FEN)) {
                //System.out.println("Visited: " + outcome.getFen());
                return true;
            }
        }

        return false;

    }

}



