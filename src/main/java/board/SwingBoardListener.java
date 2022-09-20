package board;

import game.Match;
import utility.Tile;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class SwingBoardListener implements MouseListener {
    private final SwingBoard board;
    private final Match match;
    private List<Tile> moves;
    private Tile selected;

    public SwingBoardListener(SwingBoard board, Match match) {
        this.board = board;
        this.match = match;
        board.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Tile tile = board.determineTile(x, y);
        if (tile == null) {
            selected = null;
            moves = null;
            board.setHighlights(null);
        }
        else if (selected == null) {
            selected = tile;
            moves = match.getMoves(selected);
            moves.add(selected);
            board.setHighlights(moves);
        }
        else {
            moves.remove(selected);
            for (Tile move : moves) {
                if (tile.equals(move)){
                    match.play(selected, move);
                }
            }
            selected = null;
            moves = null;
            board.setHighlights(null);
        }

        board.updateUI();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
