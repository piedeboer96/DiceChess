/**
 * Here the state of the board is captured at any moment in time.
 * This should be used by the rendered package for everything graphics related.
 * Use FEN and basic chess notation to generate boards.
 */

package board;

import utility.Images;

import java.awt.*;

/**
 * Provides a chessboard for the Swing-frame that displays chess pieces using FEN-strings.
 **/
public class FenBoard extends Board {
    private final Images images;
    private String state;
    public FenBoard(int xOffset, int yOffset, int tileWidth, int tileHeight) {
        super(xOffset, yOffset, tileWidth, tileHeight);
        images = new Images();
    }

    /**
     * Draws the chess pieces on the board by interpreting a FEN-string.
     * Remark: Only pass the position/board segment of a FEN-string (i.e., rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR)
     * @param fen A string in the FEN-format denoting a chess match state.
     **/
    public void interpret(String fen){
        state = fen;
        updateUI();
    }

    @Override
    protected void drawChessPieces(Graphics g){
        if (state == null){ return; }
        String[] rows = state.split("/", 8);
        for (int i = 0; i < rows.length; i++){
            int yi = yOffset + tileHeight * i;
            for (int j = 0; j < rows[i].length(); j++){
                char c = rows[i].charAt(j);
                if (Character.isDigit(c)){
                    j = j + c;
                    continue;
                }
                int xj = xOffset + tileWidth * j;
                Image image = findImage(c);
                g.drawImage(image, xj + 1, yi + 1, tileWidth - 1, tileHeight - 1, null);
            }
        }
    }

    private Image findImage(char c){
        switch (c){
            case 'p':
                return images.blackPawn.getImage();
            case 'n':
                return images.blackKnight.getImage();
            case 'b':
                return images.blackBishop.getImage();
            case 'r':
                return images.blackRook.getImage();
            case 'q':
                return images.blackQueen.getImage();
            case 'k':
                return images.blackKing.getImage();
            case 'P':
                return images.whitePawn.getImage();
            case 'N':
                return images.whiteKnight.getImage();
            case 'B':
                return images.whiteBishop.getImage();
            case 'R':
                return images.whiteRook.getImage();
            case 'Q':
                return images.whiteQueen.getImage();
            case 'K':
                return images.whiteKing.getImage();
            default:
                throw new IllegalArgumentException("Unrecognized fen character for chess piece.");
        }
    }

    @Override
    public void paint(Graphics g){
        drawOutlines(g);
        drawTiles(g);
        drawChessPieces(g);
        drawHighlights(g);
    }
}
