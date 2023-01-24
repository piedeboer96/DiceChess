package simulation;

import java.io.IOException;

/**
 * Defines a simulator to run dice chess game simulations.
 * @version 1.0
 **/
public interface Simulator {
    /**
     * Simulates a game of dice chess between 2 players.
     *
     * @param p1 The player controlling the black colored chess pieces.
     * @param p2 The player controlling the white colored chess pieces.
     * @return p1, if the color black has won. <br>
     *         p2, if the color white has won. <br>
     *         null, if the simulation ended with a tie.
     **/
    Player simulate(Player p1, Player p2) throws IOException;
}
