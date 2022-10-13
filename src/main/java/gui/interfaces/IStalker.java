package gui.interfaces;

import java.util.List;

import chess.interfaces.IChessPiece;

public interface IStalker
{
    void follow(List<IChessPiece> chessPieces);
}
