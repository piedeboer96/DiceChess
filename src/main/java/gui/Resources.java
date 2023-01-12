package gui;

import game.ChessPiece;

import javax.swing.*;

/**
 * Provides a cache to load images into the memory.
 **/
public final class Resources {
    private final static String RESOURCE_DIRECTORY = "src/main/resources";
    private final static String CHESS_ICON_FILE_TYPE = ".png";
    private final static ImageIcon[][] CHESS_ICONS = new ImageIcon[7][2];

    public static ImageIcon getIcon(ChessPiece p) {
        if (CHESS_ICONS[p.type()][p.color()] == null) {
            CHESS_ICONS[p.type()][p.color()] = new ImageIcon(RESOURCE_DIRECTORY + '/' + p.type() + p.color() + CHESS_ICON_FILE_TYPE);
        }
        return CHESS_ICONS[p.type()][p.color()];
    }
}
