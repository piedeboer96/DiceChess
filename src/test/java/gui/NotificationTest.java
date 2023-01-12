package gui;

import game.GameState;

final class NotificationTest {
    public static void main(String[] args) {
        Notification n0 = new Notification(GameState.ONGOING);
        Notification n1 = new Notification(GameState.DRAW);
        Notification n2 = new Notification(GameState.BLACK_WON);
        Notification n3 = new Notification(GameState.WHITE_WON);
        n0.setVisible(true);
        n1.setVisible(true);
        n2.setVisible(true);
        n3.setVisible(true);
    }
}
