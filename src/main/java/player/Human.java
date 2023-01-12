package player;

import game.*;
import gui.BoardGraphics;
import simulation.Player;
import utility.Promotion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public final class Human extends MouseAdapter implements Player {
    private final CountDownLatch[] LATCHES = new CountDownLatch[1];
    private List<Opportunity> opportunities;
    private DiceChess dc;
    private Square previous;

    @Override
    public void mousePressed(MouseEvent e) {
        Object obj = e.getSource();
        if (obj instanceof BoardGraphics b) {
            Square s = b.determineLocation(e.getX(), e.getY());
            if (dc != null && opportunities != null) {
                for (Opportunity o : opportunities) {
                    if (o.origin() == previous) {
                        for (Square t : o.options()) {
                            if (t == s) {
                                Movement m = new Movement(previous, t);
                                dc.register(m);

                                if (Promotion.isEligible(o.owner(), t)) {
                                    dc.promote(t, ChessPiece.get(5, dc.getActiveColor()));
                                }

                                previous = null;
                                terminate();
                                return;
                            }
                        }
                    }
                }
                previous = s;
            }
        }
    }

    @Override
    public String play(int roll, DiceChess game) {
        opportunities = game.getTeamOpportunities(game.getActiveColor(), roll);
        if (opportunities.size() > 0) {
            dc = game;
            try {
                LATCHES[0] = new CountDownLatch(1);
                LATCHES[0].await();
            } catch (InterruptedException ignored) {}
            LATCHES[0] = null;
        }
        dc = null;
        opportunities = null;
        game.switchActiveColor();
        return game.toString();
    }

    public void terminate() {
        if (LATCHES[0] != null) {
            LATCHES[0].countDown();
        }
    }
}
