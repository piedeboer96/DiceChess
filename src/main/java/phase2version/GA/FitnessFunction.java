package phase2version.GA;

import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import chess.units.*;
import chess.utility.ChessboardSquare;

import java.util.ArrayList;
import java.util.List;

public class FitnessFunction {
    static Chromosome chromosome;

    // TODO: initialize the body of the functions

    private static boolean pieceInTeam(IChessMatch match, IChessPiece piece) {
        if(match.getPlayer() == piece.team()) return true;
        return false;
    }

    /**
     *  value is the number of pawns of the Player A
     */
    static int pawnValue(IChessMatch match) {
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Pawn && pieceInTeam(match,piece)) {
                count++;
            }
        }
        return count;
    }

    /**
     *  value is the number of knight of the Player A
     */
    static int knightValue(IChessMatch match){
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Knight && pieceInTeam(match,piece)) {
                count++;
            }
        }
        return count;
    }

    /**
     *  value is the number of bishop of the Player A
     */
    static int bishopValue(IChessMatch match) {
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Bishop && pieceInTeam(match,piece)) {
                count++;
            }
        }
        return count;
    }

    /**
     *  value is the number of rook of the Player A
     */
    static int rookValue(IChessMatch match){
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Rook && pieceInTeam(match,piece)) {
                count++;
            }
        }
        return count;
    }

    /**
     *  value is the number of queen of the Player A
     */
    static int queenValue(IChessMatch match){
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Queen && pieceInTeam(match,piece)) {
                count++;
            }
        }
        return count;
    }

    static List<IChessboardSquare> weakSquares(IChessMatch match) {
        List<IChessboardSquare> weakSquares = new ArrayList<>();
        int strongSquare = 0;
        int[][] checkingSquares = new int[3][8];
        for(int i = 0; i < checkingSquares.length; i++) {
            for(int j = 0; j < checkingSquares[0].length; j++) {
                checkingSquares[i][j] = strongSquare;
            }
        }
        int team = match.getPlayer();
        boolean teamIsBlack = team == 0;
        List<IChessPiece> pieces = match.pieces();
        for(int fileTemp = 0; fileTemp <= 7; fileTemp++) {
            boolean pawnFront = false;
            for(int deltaRank = 0; deltaRank <= 2; deltaRank++) {
                IChessboardSquare squareTemp;
                int weakRankTemp;
                if(teamIsBlack) {
                    squareTemp = new ChessboardSquare(fileTemp,1 + deltaRank);
                    weakRankTemp = 2 + deltaRank;
                } else {
                    squareTemp = new ChessboardSquare(fileTemp,6 - deltaRank);
                    weakRankTemp = 5 - deltaRank;
                }
                IChessPiece piece = match.get(squareTemp);
                if(piece instanceof Pawn) pawnFront = true;
                else if(!(piece instanceof Pawn) && piece != null) pawnFront = false;
                if(!pawnFront) {
                    for(int jTemp = fileTemp - 1; jTemp <= fileTemp + 1; jTemp += 2) {
                        if(jTemp == -1 || jTemp == 8) continue;
                        checkingSquares[deltaRank][jTemp]++;
                        if(checkingSquares[deltaRank][jTemp] == 2) {
                            IChessboardSquare weakSquare = new ChessboardSquare(jTemp, weakRankTemp);
                            weakSquares.add(weakSquare);
                        }
                    }
                }
            }
        }
        return weakSquares;
    }

    /**
     *  is the number of squares in Player A’s area that cannot be protected by Player A’s pawns.
     */
    static int Weakcount(IChessMatch match) {
        return weakSquares(match).size();
    }

    /**
     * is the number of knights of Player B that are in the weak squares of Player A.
     * These weak squares are called outposts and best squares for knights because they can not be pushed
     * back by enemy pawns
     */
    static int Enemyknightonweak(IChessMatch match) {
        int count = 0;
        List<IChessboardSquare> weakSquares = weakSquares(match);
        for(IChessboardSquare weakSquare : weakSquares) {
            IChessPiece piece = match.get(weakSquare);
            if(piece instanceof Knight && !pieceInTeam(match,piece)) {
                count++;
                if(count == 2) return count;
            }
        }
        return count;
    }


    /**
     * is the number of pawns of Player A that are in squares e4,e5,d4 or d5. Center
     * pawns are important for controlling the center and decreasing enemy mobility
     */
    static int Centerpawncount(IChessMatch match){
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        IChessboardSquare squareTemp;
        for(int fileTemp = 3; fileTemp <= 4; fileTemp++) {
            for(int rankTemp = 3; rankTemp <= 4; rankTemp++) {
                squareTemp = new ChessboardSquare(fileTemp, rankTemp);
                IChessPiece piece = match.get(squareTemp);
                if (pieceInTeam(match,piece) && piece instanceof Pawn) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * is the number of pawns of Player A adjacent to Player A’s king. Pawn shield is
     * an important parameter for evaluating the king safety
     */
    static int Kingpawnshield(IChessMatch match){
        return 0;
    }

    static int valueOfPiece(IChessPiece piece) {
        if(piece instanceof Pawn) {
            return chromosome.data[0];
        } else if(piece instanceof Knight) {
            return chromosome.data[1];
        } else if(piece instanceof Bishop) {
            return chromosome.data[2];
        } else if(piece instanceof Rook) {
            return chromosome.data[3];
        } else {
            return chromosome.data[4];
        }
    }

    /**
     * returns adjacent squares of king
     */
    private static List<IChessboardSquare> kingZone(IChessPiece king) {
        int kingRank = king.rank();
        int kingFile = king.file();
        List<IChessboardSquare> kingZone = new ArrayList<>();
        for(int rankTemp = kingRank - 1; rankTemp <= kingRank + 1; rankTemp++) {
            for (int fileTemp = kingFile - 1; fileTemp <= kingFile + 1; fileTemp++) {
                if (rankTemp < 0 || rankTemp > 7 || fileTemp < 0 || fileTemp > 7) continue;
                if (rankTemp == kingRank && fileTemp == kingFile) continue;
                IChessboardSquare squareTemp = new ChessboardSquare(fileTemp, rankTemp);
                kingZone.add(squareTemp);
            }
        }
        return kingZone;
    }

    static int KingattackedDefended(IChessMatch match, boolean isAttacking) {
        IChessPiece king = match.getKing(match.getPlayer());
        int kingRank = king.rank();
        int kingFile = king.file();
        List<IChessPiece> pieces = match.pieces();
        List<IChessboardSquare> kingZone = kingZone(king);
        int valueOfAttacks = 0;
        boolean pieceInTeam;
        for(IChessPiece piece : pieces) {
            if(isAttacking) pieceInTeam = !pieceInTeam(match,piece);
            else pieceInTeam = pieceInTeam(match,piece);
            if(pieceInTeam) {
                List<IChessMove> pieceMoves = match.generateMovesOf(piece);
                outerloop:
                for(IChessMove move : pieceMoves) {
                    for(IChessboardSquare adjacentSquare : kingZone) {
                        if(move.canReach(adjacentSquare)) {
                            valueOfAttacks += valueOfPiece(piece);
                            break outerloop;
                        }
                    }
                }
            }
        }
        return valueOfAttacks;
    }

    /**
     *  is the material value of the pieces of the enemy that are acting on ones king’s adjacent
     * squares
     */
    static int Kingattacked(IChessMatch match) {
        return KingattackedDefended(match, true);
    }

    /**
     * is the material value of the pieces of the Player A that are acting on Player A’s king’s
     * adjacent squares
     */
    static int Kingdefended(IChessMatch match){
        return KingattackedDefended(match, false);
    }

    /**
     * returns 1 if Player A is castled to measure king safety
     */
    static int Kingcastled(IChessMatch match) {
        if (match.hasCastled(match.getPlayer())) return 1;
        return 0;
    }

    /**
     * is the number of squares that a bishop can go to. This type of parameters are calculated
     * seperately for each bishop
     */
    static int Bishopmob(IChessMatch match) {
        int count = 0;
        List<IChessPiece> pieces = match.pieces();
        for(IChessPiece piece : pieces) {
            if(piece instanceof Bishop && pieceInTeam(match,piece)) {
                List<IChessMove> bishopMoves = match.generateMovesOf(piece);
                for(IChessMove bishopMove : bishopMoves) {
                    count += bishopMove.possibilities().size();
                }
            }
        }
        return count;
    }

    /**
     * parameter returns 1 if the bishop on the given square is one of the two large diagonals
     * of the board. Bishops are stronger on the large diagonals because they have higher mobility and
     * they are reaching the two central squares simultaneously controlling the center
     */
    static int Bishoponlarge(IChessMatch match) {
        int count = 0;
        int team = match.getPlayer();
        for(int i = 7, j = 0; i >= 0 && j <= 7; i--, j++) {
            IChessboardSquare square1 = new ChessboardSquare(i, j);
            IChessboardSquare square2 = new ChessboardSquare(j, i);
            IChessPiece pieceOn1 = match.get(square1);
            IChessPiece pieceOn2 = match.get(square2);
            if((pieceOn1 instanceof Bishop && pieceInTeam(match,pieceOn1)) || (pieceOn2 instanceof Bishop && pieceInTeam(match,pieceOn2)) ) {
                count++;
                if(count == 2) return count;
            }
        }
        return count;
    }

    /**
     * returns 1 if Player A has two or more bishops. Bishop pairs are generally considered
     * an advantage as to bishops can together cover all possible squares regardless of the color of the
     * square. Bishop pairs are especially strong in open positions where there are no central pawns and
     * the bishops can move freely to create threats
     */
    static int Bishoppair(IChessMatch match) {
        return (bishopValue(match) >= 2)? 1 : 0;
    }

    /**
     *  is the number of squares that a specific knight can go to.
     */
    static int Knightmob(IChessMatch match) {
        int count = 0;
        List<IChessPiece> pieces = match.pieces();
        for(IChessPiece piece : pieces) {
            if(piece instanceof Knight && pieceInTeam(match,piece)) {
                List<IChessMove> knightMoves = match.generateMovesOf(piece);
                for(IChessMove knightMove : knightMoves) {
                    count += knightMove.possibilities().size();
                }
            }
        }
        return count;
    }

    /**
     *  returns 1 if a given knight on a given square is supported by ones own pawn.
     * Supported knights are strong because they can only be backfired by pawns and since they are
     * supported they can stay in an important position for a long number of moves making it harder for
     * the opponent to play around it
     */
    static int Knightsupport(IChessMatch match){
        return 0;
    }

    static int Knightperiphery(IChessMatch match, int[] bounds) {
        int count = 0;
        int rank = bounds[0];
        while(rank != -1) {
            for(int tempFile = bounds[0]; tempFile <= bounds[1]; tempFile++) {
                IChessboardSquare squareTemp = new ChessboardSquare(tempFile, rank);
                IChessPiece piece = match.get(squareTemp);
                if(piece instanceof Knight && pieceInTeam(match,piece)) {
                    count++;
                    if(count == 2) return count;
                }
            }
            if(rank == bounds[0]) { rank = bounds[1];
            } else { rank = -1;}
        }
        int file = bounds[0];
        if(bounds[1] - bounds[0] == 1) return count;
        while(file != -1) {
            for(int tempRank = bounds[0] + 1; tempRank <= bounds[1] - 1; tempRank++) {
                IChessboardSquare squareTemp = new ChessboardSquare(file, tempRank);
                IChessPiece piece = match.get(squareTemp);
                if(piece instanceof Knight && pieceInTeam(match,piece)) {
                    count++;
                    if(count == 2) return count;
                }
            }
            if(file == bounds[0]) { file = bounds[1];
            } else { file = -1;}
        }
        return count;
    }

    /**
     *  returns 1 if a given knight is on the squares a1 to a8,a8 to h8,a1 to h1 or h1 to h8.
     * This is the outest periphery and most of the times knights on these squares are weaker
     */
    static int Knightperiphery0(IChessMatch match) {
        int[] bounds = {0, 7};
        return Knightperiphery(match, bounds);
    }

    /**
     *returns 1 if a given knight is on the squares b2 to b7,b7 to g7,b2 to g2 or g2 to g7.
     */
    static int Knightperiphery1(IChessMatch match){
        int[] bounds = {1, 6};
        return Knightperiphery(match, bounds);
    }

    /**
     *  returns 1 if a given knight is on the squares c3 to c6,c6 to f6,c3 to f3 or f3 to f6
     */
    static int Knightperiphery2(IChessMatch match) {
        int[] bounds = {2, 5};
        return Knightperiphery(match, bounds);
    }

    /**
     *  returns 1 if a given knight is on the squares e4, e5,d4 or d5
     */
    static int Knightperiphery3(IChessMatch match){
        int[] bounds = {3, 4};
        return Knightperiphery(match, bounds);
    }

    /**
     * returns 1 if a given pawn has no neighboring pawns of the same color. Isolated pawns
     * are generally considered as a weakness since they cannot be protected by pawns so they should be
     * protected by other more valuable pieces
     */
    static int Isopawn(IChessMatch match){
        return 0;
    }

    /**
     *  returns 1 if a pawn is doubled pawn. Doubled pawns are considered a disadvantage as
     * they blocked each other, and they are vulnerable to attacks
     */
    static int Doublepawn(IChessMatch match) {
        int count = 0;
        boolean[] pawnInFile = {false, false, false, false, false, false, false, false};
        List<IChessPiece> pieces = match.pieces();
        for(IChessPiece piece : pieces) {
            if(piece instanceof Pawn && pieceInTeam(match, piece)) {
                if(!pawnInFile[piece.file()]) pawnInFile[piece.file()] = true;
                else count++;
            }
        }
        return count;
    }

    /**
     *  returns 1 if for a given pawn there are no opposing pawns of the enemy on the neighboring
     * columns and on the given pawn’s column ahead of the pawn. If a pawn is passed it is big threat for
     * the opponent because they are no pawns on the way to prevent it from promoting
     */
    static int Passpawn(IChessMatch match){
        return 0;
    }

    /**
     * returns 1 if a rook of the same color is behind the passed pawn. If there is a rook
     * behind a passed pawn it is easier to push to pawn forward as it is always protected by the rook and
     * rook never gets in the way
     */
    static int Rookbhdpasspawn(IChessMatch match){
        return 0;
    }

    /**
     * returns 1 if the neighboring pawns of a pawn are ahead of it. Backward pawns are
     * the last pawn of a pawn chain and even though they are not isolated they can not be defended easily.
     * So they are considered a disadvantage
     */
    static int Backwardpawn(IChessMatch match){
        return 0;
    }

    /**
     *  is the rank of the passed pawn. A passed pawn on rank 7 which means the pawn
     * is one move away from promoting is a lot more dangerous compared to a passed pawn on its
     * initial square. Passed pawns with higher ranks have higher priority thus they are an advantage
     */
    static int Rankpassedpawn(IChessMatch match){
        return 0;
    }

    /**
     * returns 1 if a central pawn on column e or d on its initial square is blocked by its own
     * piece which severely decreases the mobility of the pieces
     */
    static int Blockedpawn(IChessMatch match) {
        int count = 0;
        boolean blackTeam = match.getPlayer() == 0
        int rank;
        if(match.getPlayer() == 0) rank = 1;
        else rank = 6;
        List<IChessboardSquare> squares = new ArrayList<>();
        squares.add(new ChessboardSquare(3, rank));
        squares.add(new ChessboardSquare(4, rank));
        for(IChessboardSquare square : squares) {
            IChessPiece piece = match.get(square);
            if(piece instanceof Pawn && pieceInTeam(match, piece) && isPawnBlocked(match, blackTeam, square.file())) count++;
        }
        return count;
    }

    static boolean isPawnBlocked(IChessMatch match, boolean blackTeam, int file) {
        int rank;
        if(blackTeam) rank = 2;
        else rank = 5;
        IChessboardSquare square = new ChessboardSquare(file, rank);
        if(match.get(square) != null) return true;
        return false;
    }

    /**
     *  returns 1 if a passed pawn of Player A is blocked by a piece of Player B which
     * prevents it from moving closer to promotion. This is an advantage for the blocking side. The passed
     * pawn on a4 is not blocked by any black pieces. If it is pushed to a5 however it will be considered
     * blocked because there is a black knight on a6
     */
    static int Blockedpassedpawn(IChessMatch match){
        return 0;
    }

    /**
     * returns 1 if a given rook is on a file with no pawns from either side. Rooks are stronger
     * on open columns because they can move freely
     */
    static int Rookopenfile(IChessMatch match){
        return 0;
    }

    /**
     * returns 1 if a given rook is on a file with no pawns from its own side. Rooks are
     * strong on semi-open files as well
     */
    static int Rooksemiopenfile(IChessMatch match) {
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if (piece instanceof Rook && pieceInTeam(match,piece)) {
                int rookFile = piece.file();
                for(int rankTemp = 0; rankTemp <= 7; rankTemp++) {
                    if(rankTemp == piece.rank()) continue;
                    IChessboardSquare squareTemp = new ChessboardSquare(rookFile, rankTemp);
                    if(!(match.get(squareTemp) instanceof Pawn) || !(pieceInTeam(match,piece))) continue;
                    count++;
                    if(count == 2) return count;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * returns 1 if a given rook is on a file with pawns from both sides. Rooks on closed
     * files are considered a disadvantage as they have lower file mobility and no access to the important
     * squares of the game especially in the middlegame and endgame.
     */
    static int Rookclosedfile(IChessMatch match){
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if (piece instanceof Rook && pieceInTeam(match,piece)) {
                int rookFile = piece.file();
                boolean pawnTeam = false;
                boolean pawnOpponent = false;
                for(int rankTemp = 0; rankTemp <= 7; rankTemp++) {
                    if(rankTemp == piece.rank()) continue;
                    IChessboardSquare squareTemp = new ChessboardSquare(rookFile, rankTemp);
                    if((match.get(squareTemp) instanceof Pawn) && pieceInTeam(match,piece)) {
                        pawnTeam = true;
                    } else if((match.get(squareTemp) instanceof Pawn) && !pieceInTeam(match,piece)) {
                        pawnOpponent = true;
                    }
                    if(pawnTeam && pawnOpponent) {
                        count++;
                        if(count == 2) return count;
                    }
                }
            }
        }
        return count;
    }

    /**
     * returns 1 if a given rook is on seventh rank from the Players perspective. For white
     * that would be the rank 7 for black rank 2. Rooks on seventh rank are dangerous and a classical
     * theme in chess for creating major threats at once
     */
    static int Rookonseventh(IChessMatch match) {
        int count = 0;
        int seventhRank;
        if(match.getPlayer() == 0) { seventhRank = 7;
        } else { seventhRank = 0;
        }
        for(int fileTemp = 0; fileTemp <= 7; fileTemp++) {
            IChessboardSquare squareTemp = new ChessboardSquare(fileTemp, seventhRank);
            IChessPiece piece = match.get(squareTemp);
            if(!pieceInTeam(match,piece) || !(piece instanceof Rook)) continue;
            count++;
            if(count == 2) return count;
        }

        return count;
    }

    /**
     * returns the number of squares a rook can move to.
     */
    static int Rookmob(IChessMatch match) {
        int count = 0;
        List<IChessPiece> pieces = match.pieces();
        for(IChessPiece piece : pieces) {
            if(piece instanceof Rook && pieceInTeam(match,piece)) {
                List<IChessMove> rookMoves = match.generateMovesOf(piece);
                for(IChessMove rookMove : rookMoves) {
                    count += rookMove.possibilities().size();
                }
            }
        }
        return count;
    }

    /**
     * returns 1 if there are no pieces between to rooks of the same color and they are on the
     * same file or on the same rank. Connected rooks defend each other to create threats in the opposition
     * area because they cannot be captured by queen or king
     */
    static int Rookcon(IChessMatch match) {
        List<IChessPiece> pieces = match.pieces();
        IChessPiece rook1 = null;
        IChessPiece rook2 = null;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Rook && pieceInTeam(match,piece)) {
                if(rook1 == null) rook1 = piece;
                else {
                    rook2 = piece;
                    break;
                }
            }
        }
        if(rook1 == null || rook2 == null) return 0;
        if(rook1.file() == rook2.file() || rook1.rank() == rook2.rank()) return 1;
        return 0;
    }

    /**
     * returns the number of squares a queen can move to
     */
    static int Queenmob(IChessMatch match) {
        int count = 0;
        List<IChessPiece> pieces = match.pieces();
        for(IChessPiece piece : pieces) {
            if(piece instanceof Queen && pieceInTeam(match,piece)) {
                List<IChessMove> queenMoves = match.generateMovesOf(piece);
                for(IChessMove queenMove : queenMoves) {
                    count += queenMove.possibilities().size();
                }
                return count;
            }
        }
        return count;
    }


    static int evaluate(IChessMatch match) {
        return pawnValue(match) * chromosome.data[0] +
                knightValue(match) * chromosome.data[1] +
                bishopValue(match) * chromosome.data[2] +
                rookValue(match) * chromosome.data[3] +
                queenValue(match) * chromosome.data[4] +
                Weakcount(match) * chromosome.data[5] +
                Enemyknightonweak(match) * chromosome.data[6] +
                Centerpawncount(match) * chromosome.data[7] +
                Kingpawnshield(match) * chromosome.data[8] +
                Kingattacked(match) * chromosome.data[9] +
                Kingdefended(match) * chromosome.data[10] +
                Kingcastled(match) * chromosome.data[11] +
                Bishopmob(match) * chromosome.data[12] +
                Bishoponlarge(match) * chromosome.data[13] +
                Bishoppair( match) * chromosome.data[14] +
                Knightmob(match) * chromosome.data[15] +
                Knightsupport(match) * chromosome.data[16] +
                Knightperiphery0(match) * chromosome.data[17] +
                Knightperiphery1(match) * chromosome.data[18] +
                Knightperiphery2(match) * chromosome.data[19] +
                Knightperiphery3(match) * chromosome.data[20] +
                Isopawn(match) * chromosome.data[21] +
                Doublepawn(match) * chromosome.data[22] +
                Passpawn(match) * chromosome.data[23] +
                Rookbhdpasspawn(match) * chromosome.data[24] +
                Backwardpawn(match) * chromosome.data[25] +
                Rankpassedpawn(match) * chromosome.data[26] +
                Blockedpawn(match) * chromosome.data[27] +
                Blockedpassedpawn(match) * chromosome.data[28] +
                Rookopenfile(match) * chromosome.data[29] +
                Rooksemiopenfile(match) * chromosome.data[30] +
                Rookclosedfile(match) * chromosome.data[31] +
                Rookonseventh(match) * chromosome.data[32] +
                Rookmob(match) * chromosome.data[33] +
                Rookcon(match) * chromosome.data[34] +
                Queenmob(match) * chromosome.data[35];
    }
}
