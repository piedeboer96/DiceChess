package gui.interfaces;

import java.util.List;

import chess.interfaces.IChessboardSquare;

/**
 * Defines a chessboard square highlighter.
 **/
public interface IHighlighter
{
    /**
     * Stores a list of chessboard squares, as "The movement destinations of the selected piece of the current frame.",
     * just to highlight it in the next frame.
     **/
    void rememberDestinations(List<IChessboardSquare> destinations);

    /**
     * Stores a chessboard square that has been clicked in the current frame, just to highlight it in the next frame.
     **/
    void rememberSelection(IChessboardSquare selected);
}
