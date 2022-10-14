package chess;

import chess.interfaces.IChessMatch;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import chess.units.Bishop;
import chess.units.King;
import chess.units.Knight;
import chess.units.Pawn;
import chess.units.Queen;
import chess.units.Rook;
import chess.utility.Chessboard;
import chess.utility.ChessboardSquare;
import chess.utility.Factory;
import gui.Window;
import gui.utility.PromotionScreen;

public class ChessMatch extends Chessboard implements IChessMatch
{
    /**
     * Provides a utility to help building a chessboard and chess pieces using the board information from a fen-string.
     **/
    private final Factory factory;

    /**
     * Represents the number of times the turn has been passed to the black (or team 0).
     **/
    private int fullMoves;

    /**
     * Represents the number of consecutive turns that neither a pawn has moved and an en-passant opportunity was available.
     **/
    private int halfMoves;

    /**
     * Represents the team that is currently playing / allowed to make a move.
     **/
    private int player;

    /**
     * Represents the state of the match.
     **/
    private MatchState state;

    /**
     * Creates a new clean chess match.
     **/
    public ChessMatch()
    {
        super();
        factory = new Factory();
        state = MatchState.ONGOING;

        // Interprets the fen-string of a new fresh game.
        interpret("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }


    /**
     * Creates the match from where it was left according to the fen-string.
     *   { board, player, castle opportunities, en-passant target square, half moves, full moves }
     **/
    public ChessMatch(String fen)
    {
        super();
        factory = new Factory();
        interpret(fen);
    }

    public int getPlayer() { return player; }

    public MatchState getState() { return state; }

    public void interpret(String fenString)
    {
        String[] fields = ForsythEdwardsNotation.getFields(fenString);

        // Building the chessboard and chess pieces first.
        factory.constructChessBoard(pieces, squares, fields[0]);
        loadKings();

        player = ForsythEdwardsNotation.getPlayer(fields[1]);
        castleMatrix = ForsythEdwardsNotation.getCastleOpportunities(fields[2]);


        // Interpreting the number of half moves that have recorded.
        halfMoves = Integer.parseInt(fields[4]);

        // Interpreting the number of full moves that have been recorded.
        fullMoves = Integer.parseInt(fields[5]);
    }

    /**
     * Gives the turn to play to the opposition.
     **/
    public int nextPlayer()
    {
        if (player == 1)
        {
            player = 0;
            fullMoves++;
        } else { player = 1; }
        return player;
    }

    public void playMove(IChessPiece piece, IChessboardSquare destination)
    {
        if (piece.team() != player) { throw new IllegalArgumentException("It is not allowed to move the opponent's chess piece."); }

        // Starting with getting the index on which the chess piece is currently stored and
        // the index where the piece is going to move to.
        int currentIndex = piece.toIndex();
        int destinationIndex = destination.toIndex();

        // Getting the occupant of the square where our piece is moving to.
        IChessPiece occupier = squares[destinationIndex];

        // If there is indeed an occupant, then we have to remove this occupant from the 'un-captured' chess piece list.
        if (occupier != null) { pieces.remove(occupier); }
        // Else ff there was no occupant, let's check whether we are a pawn and the destination square
        // is the en-passant target square. If that's the case, remove the en-passant target square owner.
        else if (enPassantTargetSquare != null && destination.equals(enPassantTargetSquare) && piece instanceof Pawn)
        {
            IChessboardSquare targetOwner;

            // Determining on which square the en-passant target square owner is located.
            if (player == 0) { targetOwner = new ChessboardSquare(enPassantTargetSquare.file(), enPassantTargetSquare.rank() - 1); }
            else { targetOwner = new ChessboardSquare(enPassantTargetSquare.file(), enPassantTargetSquare.rank() + 1); }

            // Getting the owner and removing him from our 'un-captured' chess piece list.
            int enPassantIndex = targetOwner.toIndex();
            occupier = squares[enPassantIndex];
            if (occupier != null) { pieces.remove(occupier); }
            squares[enPassantIndex] = null;
        }

        // If we remembered an en-passant target square,
        // then we should nullify this information was only allowed to be used this turn.
        if (enPassantTargetSquare != null) { enPassantTargetSquare = null; }

        // If we happen to have moved two steps in one go as a pawn,
        // then we need to tell the opposition that there is a target square for them to eventually use.
        if (piece instanceof Pawn)
        {
            int deltaRank = piece.rank() - destination.rank();

            // Checking whether we went 2 rows down or up to determine the en-passant target square.
            if (deltaRank == 2) { enPassantTargetSquare = new ChessboardSquare(piece.file(), piece.rank() - 1); }
            else if (deltaRank == -2) { enPassantTargetSquare = new ChessboardSquare(piece.file(), piece.rank() + 1); }
        }
        // Else if we are a king, we should check if we have had any castle opportunities and whether we have used them.
        else if (piece instanceof King)
        {
            int deltaFile = piece.file() - destination.file();

            // Checking whether we castled king side or queen side.
            if (deltaFile == 2 && castleMatrix[1][player])
            {
                IChessboardSquare rookDestination =  new ChessboardSquare(destination.file() + 1, destination.rank());
                int rookDestinationIndex = destinationIndex + 1;
                int rookIndex = destinationIndex - 2;
                IChessPiece rook = squares[rookIndex];
                rook.setPosition(rookDestination);
                squares[rookDestinationIndex] = rook;
                squares[rookIndex] = null;
            }
            else if (deltaFile == -2 && castleMatrix[0][player])
            {
                IChessboardSquare rookDestination =  new ChessboardSquare(destination.file() - 1, destination.rank());
                int rookDestinationIndex = destinationIndex - 1;
                int rookIndex = destinationIndex + 1;
                IChessPiece rook = squares[rookIndex];
                rook.setPosition(rookDestination);
                squares[rookDestinationIndex] = rook;
                squares[rookIndex] = null;
            }

            // Since we have moved the king, we no longer have the opportunity to castle at all.
            castleMatrix[0][player] = false;
            castleMatrix[1][player] = false;

            // Since we already checked whether we were a pawn or we should just also increase the half move counter.
            // For more information of the half move counter, just see the parameter documentation.
            halfMoves++;
        }
        // Else if we are a rook, we should check whether we had any opportunity to castle.
        else if (piece instanceof Rook)
        {
            // Checking whether we were on the king side or queen side and whether we had the opportunity to castle.
            if (piece.file() == 0)
            {
                castleMatrix[player][0] = false;
                if (destination.file() == 3 && castleMatrix[1][player])
                {
                    var king = kings[player];
                    int kingIndex = king.toIndex();
                    var kingDestinationSquare = new ChessboardSquare(destination.file() - 1, destination.rank());
                    int kingDestinationIndex = kingDestinationSquare.toIndex();
                    squares[kingIndex] = null;
                    squares[kingDestinationIndex] = king;
                    king.setPosition(kingDestinationSquare);
                    castleMatrix[player][1] = false;
                }
            }
            else if (piece.file() == 7)
            {
                castleMatrix[player][1] = false;
                if (destination.file() == 5 && castleMatrix[0][player])
                {
                    var king = kings[player];
                    int kingIndex = king.toIndex();
                    var kingDestinationSquare = new ChessboardSquare(destination.file() + 1, destination.rank());
                    int kingDestinationIndex = kingDestinationSquare.toIndex();
                    squares[kingIndex] = null;
                    squares[kingDestinationIndex] = king;
                    king.setPosition(kingDestinationSquare);
                    castleMatrix[player][0] = false;
                }
            }
            halfMoves++;
        }
        // Else only increment half move counter.
        else { halfMoves++; }

        // Now it's time to update entries on the board.
        squares[destinationIndex] = piece;
        squares[currentIndex] = null;

        // Finally, let's also update the square that the chess piece remembers as its location.
        piece.setPosition(destination);

        if(piece.promotable())
        {
            PromotionScreen popup = new PromotionScreen(Window.instance,"choose promotion", this, piece);
            popup.setVisible(true);
            System.out.println("piece is promotable");
        }

        // Closing the play with giving the turn to the next player.
        nextPlayer();
    }

    public void onNoMovesLeft()
    {
        if (playerIsCheckMated(player))
        {
            // If we are checked mated as black, then white must have won.
            if (player == 0) { state = MatchState.WHITE_WON; }
            // Else black must have won.
            else { state = MatchState.BLACK_WON; }
        } else { state = MatchState.DRAW; }
    }

    public void promote(IChessPiece piece, char decision)
    {
        if (piece == null) { throw new IllegalArgumentException("Can not promote null."); }
        else if (!piece.promotable()) { throw new IllegalArgumentException("Can not promote this chess piece."); }

        pieces.remove(piece);
        if (decision == 'b')
        {
            if(piece.team() == 0)
            {
                piece = new Bishop('b', piece.file(), piece.rank()); squares[piece.toIndex()] = piece; pieces.add(piece);
            }
            else
            {
                piece = new Bishop('B', piece.file(), piece.rank()); squares[piece.toIndex()] = piece; pieces.add(piece);
            }
        }
        else if (decision == 'n')
        {
            if(piece.team() == 0)
            {
                piece = new Knight('n', piece.file(), piece.rank()); squares[piece.toIndex()] = piece; pieces.add(piece);
            }
            else
            {
                piece = new Knight('N', piece.file(), piece.rank()); squares[piece.toIndex()] = piece; pieces.add(piece);
            }
        }
        else if (decision == 'q')
        {
            if(piece.team() == 0)
            {
                piece = new Queen('q', piece.file(), piece.rank()); squares[piece.toIndex()] = piece; pieces.add(piece);
            }
            else
            {
                piece = new Queen('Q', piece.file(), piece.rank()); squares[piece.toIndex()] = piece; pieces.add(piece);
            }
        }
        else if (decision == 'r')
        {
            if(piece.team() == 0)
            {
                piece = new Rook('r', piece.file(), piece.rank()); squares[piece.toIndex()] = piece; pieces.add(piece);
            }
            else
            {
                piece = new Rook('R', piece.file(), piece.rank()); squares[piece.toIndex()] = piece; pieces.add(piece);
            }
        }
    }

    public String toFen()
    {
        StringBuilder sb = new StringBuilder();

        // Building field 1: The board state
        for (int rank = 0; rank < 8; rank++)
        {
            int baseIndex = rank * 8;
            int consecutivelyEmpty = 0;
            for (int file = 0; file < 8; file++)
            {
                IChessPiece piece = squares[baseIndex + file];
                if (piece == null)
                {
                    consecutivelyEmpty++;
                    continue;
                }
                else if (consecutivelyEmpty > 0) { sb.append(consecutivelyEmpty); }
                sb.append(piece.toFen());
                consecutivelyEmpty = 0;
            }
            if (consecutivelyEmpty > 0) { sb.append(consecutivelyEmpty); }
            if (rank < 7) { sb.append('/'); }
            else { sb.append(' '); }
        }

        // Building field 2: The turn owner
        if (player == 0) { sb.append('b'); }
        else { sb.append('w'); }
        sb.append(' ');

        // Building field 3: Castle opportunities
        int appended = 0;
        if (castleMatrix[0][1]) { sb.append('K'); appended++; }
        if (castleMatrix[1][1]) { sb.append('Q'); appended++; }
        if (castleMatrix[0][0]) { sb.append('k'); appended++; }
        if (castleMatrix[0][0]) { sb.append('q'); appended++; }
        if (appended == 0) { sb.append('-'); }
        sb.append(' ');

        // Building field 4: En-passant target square
        if (enPassantTargetSquare != null)
        {
            int file = enPassantTargetSquare.file();
            if (file == 0) { sb.append('a'); }
            else if (file == 1) { sb.append('b'); }
            else if (file == 2) { sb.append('c'); }
            else if (file == 3) { sb.append('d'); }
            else if (file == 4) { sb.append('e'); }
            else if (file == 5) { sb.append('f'); }
            else if (file == 6) { sb.append('g'); }
            else if (file == 7) { sb.append('h'); }
            sb.append((7 - enPassantTargetSquare.rank()));
        } else { sb.append('-'); }
        sb.append(' ');

        // Building field 5: Number of half moves
        sb.append(halfMoves);
        sb.append(' ');

        // Building field 6: Number of full moves
        sb.append(fullMoves);
        return sb.toString();
    }
}
