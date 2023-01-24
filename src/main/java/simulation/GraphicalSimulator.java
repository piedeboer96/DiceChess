package simulation;

import dice.Die;
import game.DiceChess;
import game.GameState;
import gui.Notification;
import gui.Projector;
import player.Human;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 * Defines a simulator to run simulations while visualizing each simulation such that the human eye can follow
 * what happens. Note, that running this simulator over the default simulator increases the simulation time.
 * @version 1.0
 **/
public final class GraphicalSimulator implements Simulator {
    private final long FRAME_UPDATE_DELAY;

    /**
     * Creates a graphical simulator that updates the frame every second.
     **/
    public GraphicalSimulator() {
        this(1000);
    }

    /**
     * @param updateDelay The number of milliseconds the simulator should wait before updating the game window.
     **/
    public GraphicalSimulator(long updateDelay) {
        FRAME_UPDATE_DELAY = updateDelay;
    }

    @Override
    public Player simulate(Player p1, Player p2) throws IOException {
        Projector window = new Projector();
        DiceChess game = new DiceChess();
        window.display(game.getBoard());
        window.setVisible(true);
        if (p1 instanceof Human h) {
            window.addListener(h);
        } else if (p2 instanceof Human h) {
            window.addListener(h);
        }
        final boolean[] terminated = new boolean[1];
        WindowListener l = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                terminated[0] = true;
                if (p1 instanceof Human h) {
                   h.terminate();
                } else if (p2 instanceof Human h) {
                   h.terminate();
                }
            }
        };
        window.addListener(l);

        Player[] players = {p1, p2};
        Die d = game.getDie();
        long startTime = 0;
        while (game.getState() == GameState.ONGOING && !terminated[0]) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime < FRAME_UPDATE_DELAY) {
                continue;
            }
            int roll = d.roll();
            window.display(roll, game.getActiveColor());

            String nextState = players[game.getActiveColor()].play(roll, game);
            game = new DiceChess(nextState);

            window.display(game.getBoard());
            startTime = System.currentTimeMillis();
        }

        Notification msg = new Notification(game.getState());
        msg.setVisible(true);

        if (game.getState() == GameState.ONGOING && terminated[0]) {
            throw new IllegalStateException("User has terminated simulation prematurely.");
        }

        return switch (game.getState()) {
            case DRAW      -> null;
            case BLACK_WON -> p1;
            case WHITE_WON -> p2;
            default        -> throw new IllegalStateException("Not supposed to here while the game has not ended.");
        };
    }
}
