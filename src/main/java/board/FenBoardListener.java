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
    private Vector2d[] legalMoves;
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
        if (coordinate != null){
            // If the click is on the same tile as previously clicked, then it's a deselection.
            if (coordinate.Equals(lastClicked)){
                lastClicked = null;
                legalMoves = null;
                board.highlightTiles(null);
            }
            // If there are legal moves stored in the memory, then it could be a move.
            else if (legalMoves != null){
                boolean isMove = false;
                for (int i = 0; i < legalMoves.length; i++){
                    Vector2d move = legalMoves[i];
                    isMove = coordinate.Equals(move);
                    if (isMove){
                        //Todo: Interpret the meaning of this move (is it an en-passant, castle-swap or whatever it was called etc.),
                        //      then update the (position segment of the) fen-string to have it contain all positions.
                        //      Finally pass the position segment of the fen-string to the fen-board.
                        break;
                    }
                }
                // If it was not a move, then it is a deselection.
                if (!isMove){
                    lastClicked = null;
                    legalMoves = null;
                    board.highlightTiles(null);
                }
            }
            // If click is not a move and nothing was selected before, then it has to be a selection.
            else{
                lastClicked = coordinate;
                //Todo: Determine chess piece from the fen-string and the gotten coordinate,
                //      then determine its legal moves,
                //      then highlight combine the moves and the current coordinate into one array.
                board.highlightTiles(new Vector2d[]{ coordinate });
            }
        }
        // If there was no coordinate retrieved, then the mouse was out of bounds (so it can be considered a deselection).
        else {
            lastClicked = null;
            legalMoves = null;
            board.highlightTiles(null);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
