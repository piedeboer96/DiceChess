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
        switch (fen) {
            case 'b' : return BLACK_BISHOP.getImage();
            case 'k' : return BLACK_KING.getImage();
            case 'n' : return BLACK_KNIGHT.getImage();
            case 'p' : return BLACK_PAWN.getImage();
            case 'q' : return BLACK_QUEEN.getImage();
            case 'r' : return BLACK_ROOK.getImage();
            case 'B' : return WHITE_BISHOP.getImage();
            case 'K' : return WHITE_KING.getImage();
            case 'N' : return WHITE_KNIGHT.getImage();
            case 'P' : return WHITE_PAWN.getImage();
            case 'Q' : return WHITE_QUEEN.getImage();
            case 'R' : return WHITE_ROOK.getImage();
            default : throw new IllegalArgumentException("Character " + fen + " is unrecognized.");
        }
    }
}
