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
    //static Chromosome chromosome = new Chromosome();


    // TODO: initialize the body of the functions

    private static boolean pieceInTeam(IChessMatch match, IChessPiece piece) {
        if(piece == null) return false;
        if(match.getPlayer() == piece.team()) return true;
        return false;
    }

    /**
     *  value is the number of pawns of the Player A
     */
    public static int pawnValue(IChessMatch match) {
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
    public static int knightValue(IChessMatch match){
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
    public static int bishopValue(IChessMatch match) {
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
    public static int rookValue(IChessMatch match){
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
    public static int queenValue(IChessMatch match){
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Queen && pieceInTeam(match,piece)) {
                count++;
            }
        }
        return count;
    }

    public static List<IChessboardSquare> weakSquares(IChessMatch match) {
        List<IChessboardSquare> weakSquares = new ArrayList<>();
        int strongSquare = 0;
        int[][] checkingSquares = new int[3][8];
        for(int i = 0; i < checkingSquares.length; i++) {
            for(int j = 0; j < checkingSquares[0].length; j++) {
                if(j == 0 || j == 7) checkingSquares[i][j] = 1;
                else checkingSquares[i][j] = strongSquare;
            }
        }
        boolean teamIsBlack = match.getPlayer() == 0;
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
                if(piece instanceof Pawn && pieceInTeam(match,piece)) {
                    pawnFront = true;
                    if(piece.file() == 0 || piece.file() == 7) checkingSquares[deltaRank][piece.file()]--;
                }
                else if(piece != null) pawnFront = false;
                if(!pawnFront) {
                    for(int jTemp = fileTemp - 1; jTemp <= fileTemp + 1; jTemp += 2) {
                        if(jTemp == -1 || jTemp == 8) continue;
                        IChessboardSquare weakSquare = new ChessboardSquare(jTemp, weakRankTemp);
                        if(match.get(weakSquare) instanceof Pawn && pieceInTeam(match,piece)) continue;
                        checkingSquares[deltaRank][jTemp]++;
                        if(checkingSquares[deltaRank][jTemp] == 2) {
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
    public static int weakCount(IChessMatch match) {
        if(weakSquares(match) == null) return 0;
        return weakSquares(match).size();
    }

    /**
     * is the number of knights of Player B that are in the weak squares of Player A.
     * These weak squares are called outposts and best squares for knights because they can not be pushed
     * back by enemy pawns
     */
    public static int enemyKnightOnWeak(IChessMatch match) {
        int count = 0;
        if(weakSquares(match) == null) return 0;
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
    public static int centerPawnCount(IChessMatch match){
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        IChessboardSquare squareTemp;
        for(int fileTemp = 3; fileTemp <= 4; fileTemp++) {
            for(int rankTemp = 3; rankTemp <= 4; rankTemp++) {
                squareTemp = new ChessboardSquare(fileTemp, rankTemp);
                IChessPiece piece = match.get(squareTemp);
                if (piece instanceof Pawn && pieceInTeam(match, piece)) {
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
    public static int kingPawnShield(IChessMatch match) {
        int count = 0;
        IChessPiece king = match.getKing(match.getPlayer());
        List<IChessboardSquare> zone = kingZone(king);
        for (IChessboardSquare square : zone) {
            IChessPiece piece = match.get(square);
            if(piece instanceof Pawn && pieceInTeam(match,piece)) {
                count++;
            }
        }
        return count;
    }

    public static int valueOfPiece(IChessPiece piece) {
        /*
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
        }*/
        return 0;
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

    public static int kingAttackedDefended(IChessMatch match, boolean isAttacking) {
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
    public static int kingAttacked(IChessMatch match) {
        return kingAttackedDefended(match, true);
    }

    /**
     * is the material value of the pieces of the Player A that are acting on Player A’s king’s
     * adjacent squares
     */
    public static int kingDefended(IChessMatch match){
        return kingAttackedDefended(match, false);
    }

    /**
     * returns 1 if Player A is castled to measure king safety
     */
    public static int kingCastled(IChessMatch match) {
        if (match.hasCastled(match.getPlayer())) return 1;
        return 0;
    }

    /**
     * is the number of squares that a bishop can go to. This type of parameters are calculated
     * seperately for each bishop
     */
    public static int bishopMob(IChessMatch match) {
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
    public static int bishopOnLarge(IChessMatch match) {
        int count = 0;
        for(int i = 7, j = 0; i >= 0 && j <= 7; i--, j++) {
            IChessboardSquare square1 = new ChessboardSquare(j, j);
            IChessboardSquare square2 = new ChessboardSquare(j, i);
            IChessPiece pieceOn1 = match.get(square1);
            IChessPiece pieceOn2 = match.get(square2);
            if((pieceOn1 instanceof Bishop && pieceInTeam(match,pieceOn1))) count++;
            if(pieceOn2 instanceof Bishop && pieceInTeam(match,pieceOn2)) count++;
            if(count == 2) return count;
        }
        return count;
    }

    /**
     * returns 1 if Player A has two or more bishops. Bishop pairs are generally considered
     * an advantage as to bishops can together cover all possible squares regardless of the color of the
     * square. Bishop pairs are especially strong in open positions where there are no central pawns and
     * the bishops can move freely to create threats
     */
    public static int bishopPair(IChessMatch match) {
        return (bishopValue(match) >= 2)? 1 : 0;
    }

    /**
     *  is the number of squares that a specific knight can go to.
     */
    public static int knightMob(IChessMatch match) {
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
    public static int knightSupport(IChessMatch match) {
        List<IChessPiece> pieces = match.pieces();
        boolean isTeamBlack = match.getPlayer() == 0;
        int count = 0;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Knight && pieceInTeam(match,piece)) {
                if(isTeamBlack) count += knightProtected(match, piece, -1);
                else count += knightProtected(match, piece, 1);
            }
        }
        return count;
    }

    /**
     * @return 1 if knight is protected by pawn and 0 otherwise
     */
    public static int knightProtected(IChessMatch match, IChessPiece piece, int deltaRank) {
        IChessboardSquare square1 = null;
        IChessboardSquare square2 = null;
        int count = 0;
        if(piece.file() + 1 <= 7 && piece.rank() + deltaRank >= 0 && piece.rank() + deltaRank <= 7) {
            square1 = new ChessboardSquare(piece.file() + 1, piece.rank()  + deltaRank);
        }
        if(piece.file() - 1 >= 0 && piece.rank() + deltaRank >= 0 && piece.rank() + deltaRank <= 7) {
            square2 = new ChessboardSquare(piece.file() + 1, piece.rank()  + deltaRank);
        }
        if(square1 != null && match.get(square1) instanceof Pawn && pieceInTeam(match,match.get(square1))) count++;
        else if(square2 != null && match.get(square2) instanceof Pawn && pieceInTeam(match,match.get(square2))) count++;
        return count;
    }

    private static int knightPeriphery(IChessMatch match, int[] bounds) {
        int count = 0;
        int rank = bounds[0];
        while(rank != -1) {
            for(int tempFile = bounds[0]; tempFile <= bounds[1]; tempFile++) {
                IChessboardSquare squareTemp = new ChessboardSquare(tempFile, rank);
                IChessPiece piece = match.get(squareTemp);
                if(piece == null) continue;
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
                if(piece == null) continue;
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
    public static int knightPeriphery0(IChessMatch match) {
        int[] bounds = {0, 7};
        return knightPeriphery(match, bounds);
    }

    /**
     *returns 1 if a given knight is on the squares b2 to b7,b7 to g7,b2 to g2 or g2 to g7.
     */
    public static int knightPeriphery1(IChessMatch match){
        int[] bounds = {1, 6};
        return knightPeriphery(match, bounds);
    }

    /**
     *  returns 1 if a given knight is on the squares c3 to c6,c6 to f6,c3 to f3 or f3 to f6
     */
    public static int knightPeriphery2(IChessMatch match) {
        int[] bounds = {2, 5};
        return knightPeriphery(match, bounds);
    }

    /**
     *  returns 1 if a given knight is on the squares e4, e5,d4 or d5
     */
    public static int knightPeriphery3(IChessMatch match){
        int[] bounds = {3, 4};
        return knightPeriphery(match, bounds);
    }

    /**
     * returns 1 if a given pawn has no neighboring pawns of the same color. Isolated pawns
     * are generally considered as a weakness since they cannot be protected by pawns so they should be
     * protected by other more valuable pieces
     */
    public static int isoPawn(IChessMatch match) {
        int count = 0;
        List<IChessPiece> pieces = match.pieces();
        List<IChessPiece> pawnPieces = match.pieces();
        boolean isTeamBlack = match.getPlayer() == 0;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Pawn && pieceInTeam(match,piece)) {
                boolean hasNeighborPawn = false;
                for(int tempFile = piece.file() - 1; tempFile <= piece.file() + 1; tempFile++) {
                    if(tempFile < 0 || tempFile > 7 || tempFile == piece.file()) continue;
                    for(IChessPiece pawnPiece : pawnPieces) {
                        if(pawnPiece instanceof Pawn && pieceInTeam(match,pawnPiece) && pawnPiece.file() == tempFile) hasNeighborPawn = true;
                    }
                }
                if(!hasNeighborPawn) count++;
            }
        }
        return count;
    }

    /**
     *  returns 1 if a pawn is doubled pawn. Doubled pawns are considered a disadvantage as
     * they blocked each other, and they are vulnerable to attacks
     */
    public static int doublePawn(IChessMatch match) {
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

    private static List<IChessPiece> passPawns(IChessMatch match) {
        List<IChessPiece> passPawns = new ArrayList<>();
        boolean isTeamBlack = match.getPlayer() == 0;
        List<IChessPiece> pieces = match.pieces();
        List<IChessPiece> enemyPawnPieces = match.pieces();
        for(IChessPiece piece : pieces) {
            if(piece instanceof Pawn && pieceInTeam(match, piece)) {
                boolean isPassPawn = true;
                for(IChessPiece enemyPawnPiece : enemyPawnPieces) {
                    if(isTeamBlack) {
                        if(enemyPawnPiece instanceof Pawn && !pieceInTeam(match, enemyPawnPiece) && enemyPawnPiece.rank() > piece.rank() && (enemyPawnPiece.file() >= piece.file() - 1 && enemyPawnPiece.file() <= piece.file() + 1)) isPassPawn = false;
                    } else {
                        if(enemyPawnPiece instanceof Pawn && !pieceInTeam(match, enemyPawnPiece) && enemyPawnPiece.rank() < piece.rank() && (enemyPawnPiece.file() >= piece.file() - 1 && enemyPawnPiece.file() <= piece.file() + 1)) isPassPawn = false;
                    }
                }
                if(isPassPawn) passPawns.add(piece);
            }
        }
        return passPawns;
    }

    /**
     *  returns 1 if for a given pawn there are no opposing pawns of the enemy on the neighboring
     * columns and on the given pawn’s column ahead of the pawn. If a pawn is passed it is big threat for
     * the opponent because they are no pawns on the way to prevent it from promoting
     */
    public static int passPawn(IChessMatch match) {
        if(passPawns(match) == null) return 0;
        return passPawns(match).size();
    }

    /**
     * returns 1 if a rook of the same color is behind the passed pawn. If there is a rook
     * behind a passed pawn it is easier to push to pawn forward as it is always protected by the rook and
     * rook never gets in the way
     */
    public static int rookBhdPassPawn(IChessMatch match) {
        if(passPawns(match) == null) return 0;
        int count = 0;
        List<IChessPiece> passPawns = passPawns(match);
        List<IChessPiece> pieces = match.pieces();
        boolean isTeamBlack = match.getPlayer() == 0;
        for(IChessPiece piece : pieces) {
            if(piece instanceof Rook && pieceInTeam(match,piece)) {
                for(IChessPiece passPawn : passPawns) {
                    if(piece.file() == passPawn.file()) {
                        if(isTeamBlack) {
                            if(piece.rank() < passPawn.rank()) count++;
                        } else {
                            if(piece.rank() > passPawn.rank()) count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * returns 1 if the neighboring pawns of a pawn are ahead of it. Backward pawns are
     * the last pawn of a pawn chain and even though they are not isolated they can not be defended easily.
     * So they are considered a disadvantage
     */
    public static int backwardPawn(IChessMatch match){
        return 0;
    }

    /**
     *  is the rank of the passed pawn. A passed pawn on rank 7 which means the pawn
     * is one move away from promoting is a lot more dangerous compared to a passed pawn on its
     * initial square. Passed pawns with higher ranks have higher priority thus they are an advantage
     */
    public static int rankPassedPawn(IChessMatch match){
        return 0;
    }

    /**
     * returns 1 if a central pawn on column e or d on its initial square is blocked by its own
     * piece which severely decreases the mobility of the pieces
     */
    public static int blockedPawn(IChessMatch match) {
        int count = 0;
        boolean blackTeam = match.getPlayer() == 0;
        int rank;
        if(blackTeam) rank = 1;
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

    public static boolean isPawnBlocked(IChessMatch match, boolean blackTeam, int file) {
        int rank;
        if(blackTeam) rank = 2;
        else rank = 5;
        IChessboardSquare square = new ChessboardSquare(file, rank);
        IChessPiece pieceOnSquare = match.get(square);
        if(pieceOnSquare != null && pieceInTeam(match, pieceOnSquare)) return true;
        return false;
    }

    /**
     *  returns 1 if a passed pawn of Player A is blocked by a piece of Player B which
     * prevents it from moving closer to promotion. This is an advantage for the blocking side. The passed
     * pawn on a4 is not blocked by any black pieces. If it is pushed to a5 however it will be considered
     * blocked because there is a black knight on a6
     */
    public static int blockedPassedPawn(IChessMatch match) {
        int count = 0;
        boolean isTeamBlack = match.getPlayer() == 0;
        List<IChessPiece> passPawns = passPawns(match);
        for(IChessPiece passPawn : passPawns) {
            if(isTeamBlack && passPawn.rank() != 7) {
                IChessboardSquare square = new ChessboardSquare(passPawn.file(), passPawn.rank() + 1);
                IChessPiece pieceOnSquare = match.get(square);
                if(pieceOnSquare != null && !pieceInTeam(match, pieceOnSquare)) count++;
            } else if(!isTeamBlack && passPawn.rank() != 0) {
                IChessboardSquare square = new ChessboardSquare(passPawn.file(), passPawn.rank() - 1);
                IChessPiece pieceOnSquare = match.get(square);
                if(pieceOnSquare != null && !pieceInTeam(match, pieceOnSquare)) count++;
            }
        }
        return count;
    }

    /**
     * returns 1 if a given rook is on a file with no pawns from either side. Rooks are stronger
     * on open columns because they can move freely
     */
    public static int rookOpenFile(IChessMatch match){
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if (piece instanceof Rook && pieceInTeam(match,piece)) {
                int rookFile = piece.file();
                boolean rookOnOpenFile = true;
                for(int rankTemp = 0; rankTemp <= 7; rankTemp++) {
                    if(rankTemp == piece.rank()) continue;
                    IChessboardSquare squareTemp = new ChessboardSquare(rookFile, rankTemp);
                    if(match.get(squareTemp) instanceof Pawn) {
                        rookOnOpenFile = false;
                        break;
                    }
                }
                if(rookOnOpenFile) {
                    count++;
                    if(count == 2) return count;
                }
            }
        }
        return count;
    }

    /**
     * returns 1 if a given rook is on a file with no pawns from its own side. Rooks are
     * strong on semi-open files as well
     */
    public static int rookSemiOpenFile(IChessMatch match) {
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if (piece instanceof Rook && pieceInTeam(match,piece)) {
                int rookFile = piece.file();
                boolean rookOnSemiOpenFile = true;
                for(int rankTemp = 0; rankTemp <= 7; rankTemp++) {
                    if(rankTemp == piece.rank()) continue;
                    IChessboardSquare squareTemp = new ChessboardSquare(rookFile, rankTemp);
                    if(match.get(squareTemp) instanceof Pawn && pieceInTeam(match,match.get(squareTemp))) {
                        rookOnSemiOpenFile = false;
                        break;
                    }
                }
                if(rookOnSemiOpenFile) {
                    count++;
                    if(count == 2) return count;
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
    public static int rookClosedFile(IChessMatch match){
        List<IChessPiece> pieces = match.pieces();
        int count = 0;
        for(IChessPiece piece : pieces) {
            if (piece instanceof Rook && pieceInTeam(match,piece)) {
                boolean friendPawnOnfile = false;
                boolean enemyPawnOnfile = false;
                for(int rankTemp = 0; rankTemp <= 7; rankTemp++) {
                    if(rankTemp == piece.rank()) continue;
                    IChessboardSquare squareTemp = new ChessboardSquare(piece.file(), rankTemp);
                    IChessPiece pieceOnSquare = match.get(squareTemp);
                    if(pieceOnSquare instanceof Pawn) {
                        if(pieceInTeam(match,pieceOnSquare)) friendPawnOnfile = true;
                        else enemyPawnOnfile = true;
                        if(friendPawnOnfile && enemyPawnOnfile) {
                            count++;
                            break;
                        }
                    }
                }
                if(count == 2) return count;
            }
        }
        return count;
    }

    /**
     * returns 1 if a given rook is on seventh rank from the Players perspective. For white
     * that would be the rank 7 for black rank 2. Rooks on seventh rank are dangerous and a classical
     * theme in chess for creating major threats at once
     */
    public static int rookOnSeventh(IChessMatch match) {
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
    public static int rookMob(IChessMatch match) {
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
    public static int rookCon(IChessMatch match) {
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
    public static int queenMob(IChessMatch match) {
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


    /*
    static int evaluate(IChessMatch match) {
        return pawnValue(match) * chromosome.data[0] +
                knightValue(match) * chromosome.data[1] +
                bishopValue(match) * chromosome.data[2] +
                rookValue(match) * chromosome.data[3] +
                queenValue(match) * chromosome.data[4] +
                bishopMob(match) * chromosome.data[5] +
                rookMob(match) * chromosome.data[6] +
                passPawn(match) * chromosome.data[7] +
                kingPawnShield(match) * chromosome.data[8] +
                bishopOnLarge(match) * chromosome.data[9] +
                rookSemiOpenFile(match) * chromosome.data[10] +
                knightMob(match) * chromosome.data[11] +
                knightPeriphery0(match) * chromosome.data[12] +
                rookClosedFile(match) * chromosome.data[13] +
                knightPeriphery2(match) * chromosome.data[14] +
                queenMob(match) * chromosome.data[15] +
                rookOnSeventh(match) * chromosome.data[16] +
                kingAttacked(match) * chromosome.data[17] +
                knightSupport(match) * chromosome.data[18] +
                kingCastled(match) * chromosome.data[19];
    } */
}