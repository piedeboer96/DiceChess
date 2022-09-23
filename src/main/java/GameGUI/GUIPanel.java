package GameGUI;
import chesspiece.ChessPiece;
import game.Match;
import utility.ImageFinder;
import utility.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class GUIPanel extends JPanel {
    private final ImageFinder finder;
    private  List<Tile> highlights;
    private ChessPiece[] pieces;

    public final Color BOARD_COLOR1 = new Color(235,235,208);
    public final Color BOARD_COLOR2 = new Color(119,148,85);
    public final Color HIGHLIGHT_COLOR = new Color(200, 200, 1, 100);

    public final int ENDPOINT_X, ENDPOINT_Y;
    public final int OFFSET_X, OFFSET_Y;
    public final int TILE_WIDTH, TILE_HEIGHT;
    public final int WIDTH, HEIGHT;



    public GUIPanel(int x, int y, int tileWidth, int tileHeight) {
        finder = new ImageFinder();
        WIDTH = tileWidth * 8;
        HEIGHT = tileHeight * 8;
        ENDPOINT_X = x + WIDTH;
        ENDPOINT_Y = y + HEIGHT;
        OFFSET_X = x;
        OFFSET_Y = y;
        TILE_WIDTH = tileWidth;
        TILE_HEIGHT = tileHeight;
    }

    /**
     * Determines the tile the mouse has pressed or clicked on.
     * @param mouseX The x-coordinate of the mouse press or click (-> press and release).
     * @param mouseY The y-coordinate of the mouse press or click (-> press and release).
     * @return The tile the moused has pressed or clicked on. Null, if the press or click was outside the board.
     **/
    public Tile determineTile(int mouseX, int mouseY) {
        if (mouseX < OFFSET_X || ENDPOINT_X < mouseX) { return null; }
        else if (mouseY < OFFSET_Y || ENDPOINT_Y < mouseY) { return null; }

        Tile tile = null;
        for (int i = 1; i < 9; i++) {
            int upperBound = OFFSET_X + TILE_WIDTH * i;
            if (upperBound < mouseX) { continue; }
            for (int j = 1; j < 9; j++) {
                upperBound = OFFSET_Y + TILE_HEIGHT * j;
                if (upperBound < mouseY) { continue; }
                tile = new Tile(j - 1, i - 1);
                break;
            }
            break;
        }
        return tile;
    }

    /**
     * Draws the tiles that need to be highlighted.
     **/
    private void drawHighlights(Graphics g) {
        if (highlights == null) { return; }
        g.setColor(HIGHLIGHT_COLOR);
        for (Tile tile : highlights) {
            int x = OFFSET_X + TILE_WIDTH * tile.column();
            int y = OFFSET_Y + TILE_HEIGHT * tile.row();
            g.fillRect(x + 1, y + 1, TILE_WIDTH - 1, TILE_HEIGHT - 1);
        }
    }

    /**
     * Draws the chess pieces on the board.
     **/
    private void drawPieces(Graphics g) {
        if (pieces == null) { return; }
        for (ChessPiece piece : pieces) {
            if (piece == null) { continue; }
            Image image = finder.getImage(piece.toFen());
            Tile tile = piece.getPosition();
            int x = OFFSET_X + TILE_WIDTH * tile.column();
            int y = OFFSET_Y + TILE_HEIGHT * tile.row();
            g.drawImage(image, x + 1, y + 1, TILE_WIDTH - 1, TILE_HEIGHT - 1, null);
        }
    }

    /**
     * Draws the board tile outlines.
     **/
    private void drawOutlines(Graphics g) {
        g.drawRect(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT);
        for (int i = 1; i < 8; i++) {
            int x = OFFSET_X + TILE_WIDTH * i;
            int y = OFFSET_Y + TILE_HEIGHT * i;
            g.drawLine(OFFSET_X, y, ENDPOINT_X, y);
            g.drawLine(x, OFFSET_Y, x, ENDPOINT_Y);
        }
    }

    /**
     * Draws the board tiles.
     **/
    private void drawTiles(Graphics g) {
        for (int i = 0; i < 8; i++) {
            int y = OFFSET_Y + TILE_HEIGHT * i;
            for (int j = 0; j < 8; j++) {
                int x = OFFSET_X + TILE_WIDTH * j;
                if ((i + j) % 2 == 0) { g.setColor(BOARD_COLOR1); }
                else { g.setColor(BOARD_COLOR2); }
                g.fillRect(x + 1, y + 1, TILE_WIDTH - 1, TILE_HEIGHT - 1);
            }
        }
    }

    public static class SwingBoardListener implements MouseListener{
        private final GUIPanel board;
        private final Match match;
        private List<Tile> moves;
        private Tile selected;

        public SwingBoardListener(GUIPanel board, Match match) {
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

    @Override public void paint(Graphics g) {
        drawOutlines(g);
        drawTiles(g);
        drawPieces(g);
        drawHighlights(g);
    }

    public void setHighlights(List<Tile> tiles) {
        highlights = tiles;
    }

    public void setPieces(ChessPiece[] pieces) {
        this.pieces = pieces;
    }
}
