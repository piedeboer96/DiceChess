package phase1version.gui.interfaces;

import java.util.List;

import phase1version.chess.interfaces.IChessPiece;

public interface IStalker
{
    void follow(List<IChessPiece> chessPieces);
}
