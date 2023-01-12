package gui;

import game.DiceChess;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

final class ProjectorTest {
    public static void main(String[] args) {
        DiceChess game = new DiceChess();
        Projector p = new Projector();
        p.display(game.getBoard());
        p.display(1, 1);
        p.setVisible(true);

        WindowListener l = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Notification n = new Notification(game.getState());
                n.setVisible(true);
            }
        };
        p.addListener(l);
    }
}
