package phase1version.gui.interfaces;

import phase1version.chess.interfaces.IChessboardSquare;

public interface IChessboard
{
    IChessboardSquare determineSquare(int mouseX, int mouseY);

    IHighlighter getHighlighter();

    IStalker getStalker();
}
