package chess.utility;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import chess.interfaces.IChessMove;
import chess.interfaces.IChessMoveInfo;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboard;
import chess.interfaces.IChessboardSquare;
import chess.units.King;
import chess.units.Pawn;
import gui.interfaces.IDrawable;

public abstract class Chessboard implements IChessboard, IDrawable
{
    /**
     * Represents the opportunity to castle on the king side.
     * Array is participated as follows { Opportunity for black, Opportunity for white }
     **/
    protected final boolean[] canCastleKingSide;

    /**
     * Represents the opportunity to castle on the queen side.
     * Array is participated as follows { Opportunity for black, Opportunity for white }
     **/
    protected final boolean[] canCastleQueenSide;

    /**
     * Represents the kings of both teams.
     * Array is participated as follows { black king, white king }
     **/
    protected final IChessPiece[] kings;

    /**
     * A 1-dimensional array representation of a chessboard (= 8 x 8 grid).
     * Every 8 consecutive entries in the array form 1 row
     * (e.g., the first row consists of indices from the set { 0, 1, 2, 3, 4, 5, 6, 7 }).
     **/
    protected final IChessPiece[] squares;

    /**
     * Represents the en-passant target square. It is one square behind a pawn when he doubled step.
     * Only usable in the very next turn by another pawn.
     **/
    protected IChessboardSquare enPassantTargetSquare;

    /**
     * Represents the 'un-captured' pieces on the board.
     **/
    protected List<IChessPiece> pieces;

    public Chessboard()
    {
        squares = new IChessPiece[64];
        pieces = new ArrayList<>();
        canCastleKingSide = new boolean[2];
        canCastleQueenSide = new boolean[2];
        kings = new IChessPiece[2];
    }

    public List<IChessMove> generateMovesOf(IChessPiece piece)
    {
        List<IChessMove> possibleMoves = new ArrayList<>();

        boolean pieceIsPawn = piece instanceof Pawn;

        // Read loop statement as 'For every movement whose information is known such as the number of steps we can take
        // and the direction we are walking (and how far we walk with each step), do some calculations.'
        for (IChessMoveInfo info : piece.movementInfo())
        {
            List<IChessboardSquare> reachableSquares = new ArrayList<>();

            // As we are iterating using steps, we can re-use the previously calculated destination square
            // to get the next destination etc. Thus, we need an iterative variable starting with our current position.
            IChessboardSquare stepDestination = piece;

            // Read loop statement as 'While we have taken fewer steps than we are maximally allowed
            // in the current direction we are walking in, do some stuff'.
            for (int stepsTaken = 0; stepsTaken < info.maxSteps(); stepsTaken++)
            {
                try
                {
                    stepDestination = new ChessboardSquare(stepDestination.file() + info.deltaFile(), stepDestination.rank() + info.deltaRank());
                    IChessPiece destinationOccupier = get(stepDestination);
                    if (destinationOccupier == null) { reachableSquares.add(stepDestination); continue; }
                    // If the destination square is occupied by an opponent and the current piece is not a pawn,
                    // then we can capture this piece to obtain the destination square.
                    else if (destinationOccupier.opponentOf(piece) && !pieceIsPawn) { reachableSquares.add(stepDestination); }
                    // Since the destination square is occupied, there is no need to look further in the current direction.
                    break;
                }
                // If it errors, then we have hit the borders of the board. So, we should stop.
                catch (IllegalArgumentException ignored) {}


            }

            // If we are a pawn, let's see if we have any captures to do.
            if (pieceIsPawn)
            {
                int step;
                if (piece.team() == 0) { step = 1; }
                else { step = -1; }

                // Just a simple loop to access both diagonal options.
                for (int k = -1; k < 2; k = k + 2)
                {
                    List<IChessboardSquare> captureList = new ArrayList<>();
                    try
                    {
                        // Since we are still at the first step, the step's rank number is the correct rank to look for captures.
                        IChessboardSquare targetSquare = new ChessboardSquare(piece.file() + k, piece.rank() + step);
                        IChessPiece squareOccupier = get(targetSquare);
                        if (squareOccupier != null)
                        {
                            // If square is occupied and the occupier is an opponent, then we can capture him as a pawn.
                            if (squareOccupier.opponentOf(piece)) { captureList.add(targetSquare); }
                        }
                        // Since there was no occupier on the square, perhaps it could be an en-passant opportunity for us.
                        else if (enPassantTargetSquare != null && targetSquare.equals(enPassantTargetSquare)) { captureList.add(enPassantTargetSquare); }
                    } catch (IllegalArgumentException ignored) { continue; }

                    // If our capture list is not empty, then we have a possible capture as a pawn.
                    if (captureList.size() > 0)
                    {
                        IChessMove possible = new ChessMove(piece, captureList);
                        possibleMoves.add(possible);
                    }
                }
            }
            // Else if we are allowed to castle as a king, then let's have a look at it.
            // First, the chess piece has to be a king, the current move iteration should not have any
            // vertical/rank influence and the possibleMoves of this iteration needs to be 1 (as this
            // implies that the square either left or right is free).
            else if (piece instanceof King && info.deltaRank() == 0 && reachableSquares.size() == 1)
            {
                // Checking whether we satisfy the conditions to look
                // if the squares between the king and the rook are free.
                // Note: Castling king sides requires 2 free spaces, whereas castling queen side requires 3 free spaces.
                if (canCastleKingSide[piece.team()] && info.deltaFile() == 1)
                {
                    // Since we have already determined the first tile right is free with the previous conditions,
                    // all that is left is to check the second tile.
                   stepDestination = new ChessboardSquare(stepDestination.file() + 1, stepDestination.rank());
                   IChessPiece occupier = get(stepDestination);

                   // If the square is empty, then we can castle king side.
                   if (occupier == null) { reachableSquares.add(stepDestination); }
                }
                else if (canCastleQueenSide[piece.team()] && info.deltaFile() == -1)
                {
                    // Checking the second square to the left.
                    stepDestination = new ChessboardSquare(stepDestination.file() - 1, stepDestination.rank());
                    IChessPiece occupier = get(stepDestination);

                    if (occupier == null)
                    {
                        // Checking the third and final square to the left.
                        IChessboardSquare finalSquare = new ChessboardSquare(stepDestination.file() - 1, stepDestination.rank());
                        occupier = get(finalSquare);

                        // If last square is null as well, we can castle queen side.
                        if (occupier == null) { reachableSquares.add(stepDestination); }
                    }
                }
            }

            // If there are squares in our result set, then we got a possible move, so add it.
            if (reachableSquares.size() > 0)
            {
                IChessMove possible = new ChessMove(piece, reachableSquares);
                possibleMoves.add(possible);
            }
        }

        return possibleMoves;
    }

    public List<IChessMove> generateMovesOf(int team)
    {
        List<IChessMove> movesOfTeam = new ArrayList<>();
        for (IChessPiece piece : pieces)
        {
            if (piece.team() == team && !piece.isHidden())
            {
                List<IChessMove> possibleMoves = generateMovesOf(piece);
                if (possibleMoves.size() > 0) { movesOfTeam.addAll(possibleMoves); }
            }
        }
        return movesOfTeam;
    }
    public List<IChessMove> filterMovesOf(int team, char[] values)
    {
        List<IChessMove> filteredMovesOfTeam = new ArrayList<>();
        List<IChessMove> movesOfTeam = legalMovesOf(team);

        if(values[0] == values[1])
        {
            return movesOfTeam;
        }
        for(int i = 0; i < movesOfTeam.size(); i++)
        {
            if(movesOfTeam.get(i).owner().toFen() == values[0] || movesOfTeam.get(i).owner().toFen() == values[1])
            {
                filteredMovesOfTeam.add(movesOfTeam.get(i));
            }
        }
        return filteredMovesOfTeam;
    }
    public IChessPiece get(IChessboardSquare square) { return squares[square.toIndex()]; }

    public IChessPiece getKing(int team) { return kings[team]; }

    public List<IChessMove> legalMovesOf(int team)
    {
        // Reminder: You must have called loadKings after the board construction, so the kings are properly tracked...
        // Else this will crash or track the king from a previous match that has been displayed on this board.
        IChessPiece king = kings[team];

        int opponent;
        if (team == 0) { opponent = 1; }
        else { opponent = 0; }

        List<IChessMove> teamMoves =  generateMovesOf(team);
        List<IChessMove> legalMoves = new ArrayList<>();

        for (IChessMove teamMove : teamMoves)
        {
            IChessPiece mate = teamMove.owner();
            IChessboardSquare actualSquare = new ChessboardSquare(mate.file(), mate.rank());
            int actualIndex = actualSquare.toIndex();
            squares[actualIndex] = null;

            List<IChessboardSquare> valid = new ArrayList<>();
            for (IChessboardSquare destination : teamMove.possibilities())
            {
                int destinationIndex = destination.toIndex();
                if(mate instanceof King && (actualIndex-destinationIndex == -2 || actualIndex-destinationIndex == 2))
                {
                    if(actualIndex-destinationIndex == -2)
                    {
                        boolean safeToPlay = true;
                        for(int i = 1; i < 3; i++)
                        {

                            IChessboardSquare additionalSquare = new ChessboardSquare(mate.file()+1, mate.rank());
                            int additionalIndex = additionalSquare.toIndex();
                            mate.ghostTo(additionalSquare);
                            IChessPiece indexOccupier = squares[additionalIndex];
                            if (indexOccupier != null) { indexOccupier.hide(); }
                            squares[additionalIndex] = mate;
                            List<IChessMove> opponentResponses = generateMovesOf(opponent);

                            for (IChessMove response : opponentResponses)
                            {
                                if (response.canReach(king))
                                {
                                    safeToPlay = false;
                                    break;
                                }
                            }

                            if (indexOccupier != null) { indexOccupier.show(); }
                            squares[additionalIndex] = indexOccupier;
                        }
                        if (safeToPlay) { valid.add(destination); }

                    }
                    else
                    {
                        boolean safeToPlay = true;
                        for(int i = 1; i < 3; i++)
                    {

                        IChessboardSquare additionalSquare = new ChessboardSquare(mate.file()-i, mate.rank());
                        int additionalIndex = additionalSquare.toIndex();
                        mate.ghostTo(additionalSquare);
                        IChessPiece indexOccupier = squares[additionalIndex];
                        if (indexOccupier != null) { indexOccupier.hide(); }
                        squares[additionalIndex] = mate;
                        List<IChessMove> opponentResponses = generateMovesOf(opponent);

                        for (IChessMove response : opponentResponses)
                        {
                            if (response.canReach(king))
                            {
                                safeToPlay = false;
                                break;
                            }
                        }
                        if (indexOccupier != null) { indexOccupier.show(); }
                        squares[additionalIndex] = indexOccupier;

                    }
                        if (safeToPlay) { valid.add(destination); }
                    }



                }
                else
                {
                    mate.ghostTo(destination);
                    IChessPiece indexOccupier = squares[destinationIndex];
                    if (indexOccupier != null) { indexOccupier.hide(); }
                    squares[destinationIndex] = mate;

                    List<IChessMove> opponentResponses = generateMovesOf(opponent);
                    boolean safeToPlay = true;
                    for (IChessMove response : opponentResponses)
                    {
                        if (response.canReach(king))
                        {
                            safeToPlay = false;
                            break;
                        }
                    }

                    if (indexOccupier != null) { indexOccupier.show(); }
                    squares[destinationIndex] = indexOccupier;
                    if (safeToPlay) { valid.add(destination); }
                }
            }

            mate.ghostTo(actualSquare);
            squares[actualIndex] = mate;
            if (valid.size() > 0)
            {
                IChessMove legal = new ChessMove(mate, valid);
                legalMoves.add(legal);
            }
        }
        return legalMoves;
    }

    /**
     * Sets a reference in the king array for the king of each time.
     * This method should be called after each new board construction from the factory.
     **/
    public void loadKings()
    {
        for (IChessPiece piece : pieces)
        {
            if (piece instanceof King)
            {
                kings[piece.team()] = piece;
            }
        }
    }

    public List<IChessPiece> pieces() { return pieces; }

    public boolean playerIsCheckMated(int team)
    {
        int opponent;
        if (team == 0) { opponent = 1; }
        else { opponent = 0; }

        List<IChessMove> opponentMoves = generateMovesOf(opponent);
        for (IChessMove opponentMove : opponentMoves)
        {
            if (opponentMove.canReach(kings[team]))
            {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
        Color c = g.getColor();
        Color cNew;
        if (c.getRed() == 120 && c.getGreen() == 120 && c.getBlue() == 120) {
            cNew = new Color(36, 36, 36);
            g.setColor(cNew);
        } else if (c.getRed() == 36 && c.getGreen() == 36 && c.getBlue() == 36) {
            cNew = new Color(120, 120, 120);
            g.setColor(cNew);
        } else {
            cNew = new Color(255, 255, 255);
            g.setColor(cNew);
        }
        g.drawString(0 + "," + 0, x + (int)(0.75*width), y + (int)(0.15*height));
    }
}
