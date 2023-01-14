package learningagent.database;

import dice.Die;
import game.DiceChess;
import game.GameState;
import player.*;
import simulation.Player;

import java.io.FileNotFoundException;
import java.sql.*;


import java.util.*;

// TODO: build dataset that self-plays also with the different AIs
public class SelfPlay {

    // Two random chess players
    Player r1 = new Lunatic();
    Player r2 = new Lunatic();

    // Two GA chess players
    Player ga1 = new Darwin();
    Player ga2 = new Darwin();

    public SelfPlay() throws FileNotFoundException {
    }

    // This method generates one outcome list using two random chess players
    //      0 = random bot
    //      1 = genetic bot
    public ArrayList<Outcome> playChess(int playerType) {

        if(playerType==0){
            return (simulate(r1, r2));
        } else {
            return (simulate(ga1, ga2));
        }

    }

    // Simulate one match and generate list with simplified FEN with outcome information
    public ArrayList<Outcome> simulate(Player p1, Player p2) {

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

            if (isFenVisited(FEN, outcomes)) {
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
    public void writeOutcomesToDB(int numberOfGames, int playerType) {
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

                ArrayList<Outcome> outcomes = playChess(playerType);

                for (Outcome outcome : outcomes) {

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

    /**
     * AUXILIARY
     */
    // Helper method that checks if FEN was already visited
    public boolean isFenVisited(String FEN, ArrayList<Outcome> outcomes) {
        if (outcomes == null || outcomes.size() == 0) {
            return false;
        }

        for (Outcome outcome : outcomes) {
            if (outcome.getFen().equals(FEN)) {
                return true;
            }
        }

        return false;

    }

}



