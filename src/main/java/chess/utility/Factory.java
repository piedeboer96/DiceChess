package chess.utility;

import chess.interfaces.IChessPiece;
import chess.units.*;

import java.util.List;

/**
 * A utility class to 'build' chess pieces and boards.
 **/
public class Factory
{
    /**
     * Builds a chess piece using a fen-representation and the information of the square (rank, file)
     * the chess piece will be located on.
     **/
    public IChessPiece buildChessPiece(char fen, int file, int rank)
    {
        if (Character.toLowerCase(fen) == 'b') { return new Bishop(fen, file, rank); }
        else if (Character.toLowerCase(fen) == 'k') { return new King(fen, file, rank); }
        else if (Character.toLowerCase(fen) == 'n') { return new Knight(fen, file, rank); }
        else if (Character.toLowerCase(fen) == 'p') { return new Pawn(fen, file, rank); }
        else if (Character.toLowerCase(fen) == 'q') { return new Queen(fen, file, rank); }
        else if (Character.toLowerCase(fen) == 'r') { return new Rook(fen, file, rank); }
        else { throw new IllegalArgumentException("Character " + fen + " is unrecognized."); }
    }

    /**
     * Builds the chessboard and fills the 'un-captured' chess piece list using a board information field from a fen-string.
     **/
    public void constructChessBoard(List<IChessPiece> pieces, IChessPiece[] squares, String fenField)
    {
        // Getting the rows described in the fen.
        String[] rows = fenField.split("/", 8);

        for (int rank = 0; rank < rows.length; rank++)
        {
            String rowInfo = rows[rank];

            // The current rank we are currently iterating for.
            int index = rank * 8;

            // Iterative file variable. Increases as we read the row information.
            int file = 0;

            for (int entry = 0; entry < rowInfo.length(); entry++)
            {
                char c = rowInfo.charAt(entry);

                // If the character is a digit, then it means a few squares or the entire row is empty.
                if (Character.isDigit(c))
                {
                    int digit = Character.getNumericValue(c);
                    // Emptying the board on every empty square we have interpreted.
                    for (int k = file; k < digit; k++) { squares[index + k] = null; }

                    // Updating the file number as we have added a few empty squares.
                    file = file + digit;
                }
                else
                {
                    // Building the chess piece, so we can assign it to the board and the 'un-captured' list.
                    IChessPiece piece = buildChessPiece(c, file, rank);
                    squares[piece.toIndex()] = piece;
                    pieces.add(piece);

                    // Increase the file number as we increase the entry number for the row information.
                    file++;
                }
            }
        }
    }
}
