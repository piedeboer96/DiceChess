package utility;

import javax.swing.*;
import java.awt.*;

/**
 * Provides a utility object to load chess piece images independently.
 **/
public class ImageFinder {
    public final ImageIcon BLACK_BISHOP, BLACK_KING, BLACK_KNIGHT, BLACK_PAWN, BLACK_QUEEN, BLACK_ROOK;
    public final ImageIcon WHITE_BISHOP, WHITE_KING, WHITE_KNIGHT, WHITE_PAWN, WHITE_QUEEN, WHITE_ROOK;

    /**
     * Loads all chess piece images on construction.
     * @exception java.io.IOException Thrown when (a) the file does not exist or (b) something goes wrong while loading the images.
     **/
    public ImageFinder() {
        BLACK_BISHOP = new ImageIcon("src/resources/bb.png");
        BLACK_KING = new ImageIcon("src/resources/bk.png");
        BLACK_KNIGHT = new ImageIcon("src/resources/bn.png");
        BLACK_PAWN = new ImageIcon("src/resources/bp.png");
        BLACK_QUEEN = new ImageIcon("src/resources/bq.png");
        BLACK_ROOK = new ImageIcon("src/resources/br.png");

        WHITE_BISHOP = new ImageIcon("src/resources/wb.png");
        WHITE_KING = new ImageIcon("src/resources/wk.png");
        WHITE_KNIGHT = new ImageIcon("src/resources/wn.png");
        WHITE_PAWN = new ImageIcon("src/resources/wp.png");
        WHITE_QUEEN = new ImageIcon("src/resources/wq.png");
        WHITE_ROOK = new ImageIcon("src/resources/wr.png");
    }

    /**
     * Returns a chess piece image by interpreting a fen-character.
     * @return The image representing the chess piece denoted by the fen-character.
     * @exception IllegalArgumentException Thrown when the character is unrecognized.
     **/
    public Image getImage(char fen) {
        return switch (fen) {
            case 'b' -> BLACK_BISHOP.getImage();
            case 'k' -> BLACK_KING.getImage();
            case 'n' -> BLACK_KNIGHT.getImage();
            case 'p' -> BLACK_PAWN.getImage();
            case 'q' -> BLACK_QUEEN.getImage();
            case 'r' -> BLACK_ROOK.getImage();

            case 'B' -> WHITE_BISHOP.getImage();
            case 'K' -> WHITE_KING.getImage();
            case 'N' -> WHITE_KNIGHT.getImage();
            case 'P' -> WHITE_PAWN.getImage();
            case 'Q' -> WHITE_QUEEN.getImage();
            case 'R' -> WHITE_ROOK.getImage();

            default  -> throw new IllegalArgumentException("Character " + fen + " is unrecognized.");
        };
    }
}
