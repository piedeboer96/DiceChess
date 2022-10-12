package gui.interfaces;

import chess.interfaces.IChessboardSquare;

import java.util.List;

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
     * Stores a list of chessboard squares as "The chessboard squares on which chess pieces are located that are
     * in state or allowed to move to another chessboard square.", just to highlight it in the next frame.
     **/
    void rememberSelectableOptions(List<IChessboardSquare> selectable);

    /**
     * Stores a chessboard square that has been clicked in the current frame, just to highlight it in the next frame.
     **/
    void rememberSelection(IChessboardSquare selected);
}
