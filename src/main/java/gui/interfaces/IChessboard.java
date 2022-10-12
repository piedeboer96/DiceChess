package gui.interfaces;

import chess.interfaces.IChessboardSquare;

public interface IChessboard
{
    IChessboardSquare determineSquare(int mouseX, int mouseY);

    IHighlighter getHighlighter();

    IStalker getStalker();
}
