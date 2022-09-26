package gui.utility;

import javax.swing.*;
import java.awt.*;

/**
 * Loads the chess piece images (file data) to the memory.
 **/
public class Cache
{
    // Each chess piece image array is ordered as follows { black piece, white piece }
    private final ImageIcon[] bishops, kings, knights, pawns, queens, rooks;

    public Cache()
    {
        bishops = new ImageIcon[2];
        bishops[0] = new ImageIcon("src/main/java/gui/resources/bb.png");
        bishops[1] = new ImageIcon("src/main/java/gui/resources/wb.png");

        kings = new ImageIcon[2];
        kings[0] = new ImageIcon("src/main/java/gui/resources/bk.png");
        kings[1] = new ImageIcon("src/main/java/gui/resources/wk.png");

        knights = new ImageIcon[2];
        knights[0] = new ImageIcon("src/main/java/gui/resources/bn.png");
        knights[1] = new ImageIcon("src/main/java/gui/resources/wn.png");

        pawns = new ImageIcon[2];
        pawns[0] = new ImageIcon("src/main/java/gui/resources/bp.png");
        pawns[1] = new ImageIcon("src/main/java/gui/resources/wp.png");

        queens = new ImageIcon[2];
        queens[0] =  new ImageIcon("src/main/java/gui/resources/bq.png");
        queens[1] = new ImageIcon("src/main/java/gui/resources/wq.png");

        rooks = new ImageIcon[2];
        rooks[0] = new ImageIcon("src/main/java/gui/resources/br.png");
        rooks[1] = new ImageIcon("src/main/java/gui/resources/wr.png");
    }

    public Image getImage(char fen)
    {
        Image image;
        switch (fen)
        {
            case 'b':
                image = bishops[0].getImage();
                break;
            case 'B':
                image = bishops[1].getImage();
                break;
            case 'k':
                image = kings[0].getImage();
                break;
            case 'K':
                image = kings[1].getImage();
                break;
            case 'n':
                image = knights[0].getImage();
                break;
            case 'N':
                image = knights[1].getImage();
                break;
            case 'p':
                image = pawns[0].getImage();
                break;
            case 'P':
                image = pawns[1].getImage();
                break;
            case 'q':
                image = queens[0].getImage();
                break;
            case 'Q':
                image = queens[1].getImage();
                break;
            case 'r':
                image = rooks[0].getImage();
                break;
            case 'R':
                image = rooks[1].getImage();
                break;
            default:
                throw new IllegalArgumentException("Character " + fen + " is unrecognized.");
        }
        return image;
    }
}
