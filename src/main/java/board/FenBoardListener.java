package board;

import utility.Vector2d;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Provides mouse click interactions on a chess board that interprets fen-strings.
 **/
public class FenBoardListener implements MouseListener {
    private final FenBoard board;
    private Vector2d lastClicked;
    public FenBoardListener(FenBoard chessBoard){
        board = chessBoard;
        board.addMouseListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        // Todo - Determine whether a mouse click is:
        //  1. A new chess piece selection --> Highlight piece and its legal moves / possible displacements.
        //  2. A deselection of its current piece --> Remove highlights.
        //  3. A move/en-passant --> update the fen-string and pass it to the board.
        //  Remark: Since we use FEN-strings, ya will need a class to calculate its legal moves.
        int mouseX = e.getX();
        int mouseY = e.getY();

        Vector2d coordinate = board.convertToCoordinate(mouseX, mouseY);
        if (coordinate != null && coordinate.Equals(lastClicked)){
            lastClicked = null;
            board.highlight(null);
        }
        else{
            lastClicked = coordinate;
            board.highlight(new Vector2d[]{ coordinate });
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
