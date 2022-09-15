package utility;

import javax.swing.*;

/**
 * Allows preloading of all chess piece images.
 **/
public class Images {
    public final ImageIcon blackBishop, blackKing, blackKnight, blackPawn, blackQueen, blackRook;
    public final ImageIcon whiteBishop, whiteKing, whiteKnight, whitePawn, whiteQueen, whiteRook;

    /**
     * Loads all chess piece images on construction.
     * @exception java.io.IOException Thrown when (a) the file does not exist or (b) something goes wrong while loading the images.
     **/
    public Images(){
        blackBishop = new ImageIcon("src/resources/black-bishop.png");
        blackKing = new ImageIcon("src/resources/black-king.png");
        blackKnight = new ImageIcon("src/resources/black-knight.png");
        blackPawn = new ImageIcon("src/resources/black-pawn.png");
        blackQueen = new ImageIcon("src/resources/black-queen.png");
        blackRook = new ImageIcon("src/resources/black-rook.png");

        whiteBishop = new ImageIcon("src/resources/white-bishop.png");
        whiteKing = new ImageIcon("src/resources/white-king.png");
        whiteKnight = new ImageIcon("src/resources/white-knight.png");
        whitePawn = new ImageIcon("src/resources/white-pawn.png");
        whiteQueen = new ImageIcon("src/resources/white-queen.png");
        whiteRook = new ImageIcon("src/resources/white-rook.png");
    }
}
