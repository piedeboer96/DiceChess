package gui.interfaces;

import chess.interfaces.IChessPiece;

import java.util.List;

public interface IStalker
{
    void follow(List<IChessPiece> chessPieces);
}
