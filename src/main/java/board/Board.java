package board;

import utility.Vector2d;

import javax.swing.*;
import java.awt.*;

/**
 * Provides a chessboard for the Swing-framework.
 **/
public abstract class Board extends JPanel {
    private Vector2d[] positions2highlight;
    public final Color boardColor1 = new Color(80, 60, 60);
    public final Color boardColor2 = new Color(190, 180, 170);
    public final Color highlightColor = new Color(120, 240, 120, 90);
    public final int xOffset, yOffset;
    public final int xEndPoint, yEndPoint;
    public final int tileWidth, tileHeight;

    public Board(int xOffset, int yOffset, int tileWidth, int tileHeight){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        xEndPoint = xOffset + 8 * tileWidth;
        yEndPoint = yOffset + 8 * tileHeight;
    }

    /**
     * @return A 2-dimensional vector representing the tile on which the (mouse) position is located.
     *         Null if the position is outside of board boundary.
     **/
    public Vector2d convertToCoordinate(int mouseX, int mouseY){
        Vector2d coordinate = null;
        int _x = xOffset; // 'lower bound' for the x-coordinate
        int _y = yOffset; // 'lower bound' for the y-coordinate.
        for (int i = 1; i < 9; i++){
            int y = yOffset + tileHeight * i;
            if (mouseY < _y || mouseY > y){
                _y = y;
                continue;
            }
            for (int j = 1; j < 9; j++){
                int x = xOffset + tileWidth * j;
                if (mouseX < _x || mouseX > x){
                    _x = x;
                    continue;
                }
                coordinate = new Vector2d(j - 1, i - 1);
                break;
            }
            if (coordinate != null){ break; }
            else { _y = y; }
        }
        return coordinate;
    }

    protected abstract void drawChessPieces(Graphics g);

    protected void drawHighlights(Graphics g){
        if (positions2highlight == null){ return; }
        g.setColor(highlightColor);
        for (int i = 0; i < positions2highlight.length; i++){
            Vector2d position = positions2highlight[i];
            int xi = xOffset + tileWidth * position.x;
            int yi = yOffset + tileHeight * position.y;
            g.fillRect(xi + 1, yi + 1, tileWidth - 1, tileHeight -1);
        }
    }

    protected void drawOutlines(Graphics g){
        g.drawLine(xOffset, yOffset, xEndPoint, yOffset);
        g.drawLine(xOffset, yOffset, xOffset, yEndPoint);
        for (int i = 1; i < 8; i++){
            int xi = xOffset + tileWidth * i;
            int yi = yOffset + tileHeight * i;
            g.drawLine(xOffset, yi, xEndPoint, yi);
            g.drawLine(xi, yOffset, xi, yEndPoint);
        }
        g.drawLine(xOffset, yEndPoint, xEndPoint, yEndPoint);
        g.drawLine(xEndPoint, yOffset, xEndPoint, yEndPoint);
    }

    protected void drawTiles(Graphics g){
        for (int i = 0; i < 8; i++){
            int yi = yOffset + tileHeight * i;
            for (int j = 0; j < 8; j++){
                int xj = xOffset + tileWidth * j;
                if ((i + j) % 2 == 0){ g.setColor(boardColor1); }
                else { g.setColor(boardColor2); }
                g.fillRect(xj + 1, yi + 1, tileWidth - 1, tileHeight - 1);
            }
        }
    }

    public void highlight(Vector2d[] positions){
        positions2highlight = positions;
        updateUI();
    }
}
