package zzz.solutions.gui;

import framework.game.*;
import zzz.solutions.chess.pieces.Pawn;
import zzz.solutions.chess.pieces.Queen;
import zzz.framework.game.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CountDownLatch;

public final class HumanPlayer extends MouseAdapter implements Player {
    private final CountDownLatch[] COUNTDOWNS;
    private int rolled = -1;
    private Game game;
    private Location[] destinations = {};
    private Location previous;
    private Setup setup;
    private Unit selected;
    private int team;

    public HumanPlayer() {
        COUNTDOWNS = new CountDownLatch[1];
    }

    @Override
    public void join(int team, Game g) {
        game = g;
        setup = g.world();
        this.team = team;
    }

    @Override
    public void leave() {
        game = null;
        setup = null;
        team = -1;
        rolled = -1;
        if (COUNTDOWNS[0] != null) { COUNTDOWNS[0].countDown(); }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (game == null || setup == null) { throw new IllegalStateException("Interactions are not allowed without joining a game!"); }
        else if (game.getActiveTeam() != team || COUNTDOWNS[0] == null) { return; }

        if (e.getSource() instanceof Chessboard board) {
            Location pressed = board.determine(e.getX(), e.getY());
            if (pressed == null) { return; }
            Unit u = setup.read(pressed);
            if (selected != null && (u == null || u.getTeam() != team)) {
                board.remove(previous);
                for (Location destination : destinations) {
                    board.remove(destination);
                    if (destination == pressed) {
                        game.move(previous, pressed);
                        // Check for promotion.
                        if (selected.getType() == 1 && pressed.row() == Pawn.PROMOTION_ROWS[team]) {
                            System.out.println("PAWN CAN BE PROMOTED!");
                            if (team == 0) { game.promote(pressed, Queen.BLACK); }
                            else { game.promote(pressed, Queen.WHITE); }
                        }
                        COUNTDOWNS[0].countDown();
                    }
                }
                selected = null;
            } else if (u != null && (selected != u || pressed != previous) && u.getTeam() == team){
                System.out.println("FILTERING UNITS BASED ON THE ROLL RESULT " + rolled + " IS SKIPPED!");
                board.remove(previous);
                for (Location destination : destinations) { board.remove(destination); }
                switch(u.getType()) {
                    case 6  -> destinations = u.destinations(pressed, setup, game.permissions());
                    case 1  -> destinations = u.destinations(pressed, setup, game.opportunities());
                    default -> destinations = u.destinations(pressed, setup);
                }
                for (Location destination : destinations) { board.mark(destination); }
                board.mark(pressed);
                selected = u;
            } else if (pressed != previous){
                board.remove(previous);
                for (Location destination : destinations) { board.remove(destination); }
            }
            previous = pressed;
            board.highlight();
        }
    }

    @Override
    public void play() {
        if (game == null || setup == null || team == -1) { return; }
        game.roll();
        rolled = game.result();
        COUNTDOWNS[0] = new CountDownLatch(1);
        try { COUNTDOWNS[0].await(); }
        catch (InterruptedException ignored) {}
        COUNTDOWNS[0] = null;
    }
}
