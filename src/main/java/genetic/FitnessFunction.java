package genetic;

import game.*;

import java.util.ArrayList;
import java.util.List;

public class FitnessFunction {

    /**
     * returns 1 if the neighboring pawns of a pawn are ahead of it. Backward pawns are
     * the last pawn of a pawn chain and even though they are not isolated they can not be defended easily.
     * So they are considered a disadvantage.
     */
    public int backwardPawn(int color, BoardProperties properties) {
        int count = 0;
        int deltaRank;
        List<Square> pawns;
        switch (color) {
            case 0 -> {
                pawns = properties.BLACK_PAWNS;
                deltaRank = 1;
            }
            case 1 -> {
                pawns = properties.WHITE_PAWNS;
                deltaRank = -1;
            }
            default -> throw new IllegalArgumentException();
        }
        Chessboard b = properties.BOARD;
        for (Square s : pawns) {
            if (isPawnIsolated(color, s, b)) continue;
            boolean isNotBackwardPawn = false;
            char upperbound = (char) (s.file() + 1);
            outerLoop:
            for (char file = (char) (s.file() - 1); file <= upperbound; file += 2) {
                if (file < 97 || file > 104) continue;
                for (int rank = s.rank(); (1 <= rank && rank <= 8); rank = rank + deltaRank) {
                    Square target = Square.get(file, rank);
                    ChessPiece p = b.read(target);
                    isNotBackwardPawn = p != null && p.type() == 1 && p.color() == color;
                    if (isNotBackwardPawn) {
                        break outerLoop;
                    }
                }
            }
            if (!isNotBackwardPawn) {
                count++;
            }
        }
        return count;
    }

    /**
     * is the number of squares that a bishop can go to. This type of parameters are calculated
     * seperately for each bishop
     */
    public int bishopMob(int color, BoardProperties properties) {
        List<Opportunity> opportunities;
        switch (color) {
            case 0 -> opportunities = properties.BLACK_MOVES;
            case 1 -> opportunities = properties.WHITE_MOVES;
            default ->  throw new IllegalArgumentException();
        }
        int count = 0;
        for (Opportunity mo : opportunities) {
            ChessPiece p = mo.owner();
            if (p.type() == 3 && p.color() == color) {
                count += mo.size();
            }
        }
        return count;
    }

    /**
     * parameter returns 1 if the bishop on the given square is one of the two large diagonals
     * of the board. Bishops are stronger on the large diagonals because they have higher mobility and
     * they are reaching the two central squares simultaneously controlling the center
     */
    public int bishopOnLarge(int color, BoardProperties properties) {
        List<Square> bishops;
        switch (color) {
            case 0 -> bishops = properties.BLACK_BISHOPS;
            case 1 -> bishops = properties.WHITE_BISHOPS;
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        }
        int count = 0;
        for (int i = 7, j = 0; i >= 0 && j <= 7; i--, j++) {
            Square s1 = Square.get((char)('a' + j), 8 - j);
            Square s2 = Square.get((char)('a' + j), 8 - i);
            if(bishops.contains(s1)) count++;
            if(bishops.contains(s2)) count++;
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
    public int bishopPair(int color, BoardProperties properties) {
        return (bishopValue(color,properties) >= 2)? 1 : 0;
    }

    /**
     * value is the number of bishop of the Player A
     */
    public int bishopValue(int color, BoardProperties properties) {
        return switch (color) {
            case 0 -> properties.BLACK_BISHOPS.size();
            case 1 -> properties.WHITE_BISHOPS.size();
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * returns 1 if a passed pawn of Player A is blocked by a piece of Player B which
     * prevents it from moving closer to promotion. This is an advantage for the blocking side. The passed
     * pawn on a4 is not blocked by any black pieces. If it is pushed to a5 however it will be considered
     * blocked because there is a black knight on a6
     */
    public int blockedPassedPawn(int color, BoardProperties properties) {
        int count = 0;
        List<Square> passed = passPawns(color, properties);
        int[] deltaRank = {-1, 1};
        Chessboard b = properties.BOARD;
        for (Square s : passed) {
            int rank = s.rank() + deltaRank[color];
            if (rank < 1 || rank > 8) {
                continue;
            }
            Square target = Square.get(s.file(), rank);
            ChessPiece p = b.read(target);
            if (p != null && p.color() != color) {
                count++;
            }
        }
        return count;
    }

    /**
     * returns 1 if a central pawn on column e or d on its initial square is blocked by its own
     * piece which severely decreases the mobility of the pieces
     */
    public int blockedPawn(int color, BoardProperties properties) {
        int count = 0;
        Square[][] locations = {
                {Square.get('d', 7), Square.get('e', 7)},
                {Square.get('d', 2), Square.get('e', 2)}
        };
        int[] deltaRank = {-1, 1};
        Chessboard b = properties.BOARD;
        for (Square s : locations[color]) {
            ChessPiece ps = b.read(s);
            if (ps == null || ps.color() != color || ps.type() != 1) continue;
            Square target = Square.get(s.file(), s.rank() + deltaRank[color]);
            ChessPiece pt = b.read(target);
            if (pt != null && pt.color() == color) {
                count++;
            }
        }
        return count;
    }

    /**
     * is the number of pawns of Player A that are in squares e4,e5,d4 or d5. Center
     * pawns are important for controlling the center and decreasing enemy mobility
     */
    public int centerPawnCount(int color, BoardProperties properties){
        Square[] center = {Square.get('d', 4), Square.get('d', 5), Square.get('e', 4), Square.get('e', 5)};
        int count = 0;
        switch (color) {
            case 0 -> {
                for (Square s : center) {
                    if (properties.BLACK_PAWNS.contains(s)) {
                        count++;
                    }
                }
            }
            case 1 -> {
                for (Square s : center) {
                    if (properties.WHITE_PAWNS.contains(s)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * returns 1 if a pawn is doubled pawn. Doubled pawns are considered a disadvantage as
     * they blocked each other, and they are vulnerable to attacks.
     */
    public int doublePawn(int color, BoardProperties properties) {
        int count = 0;
        List<Square> pawns;
        switch (color) {
            case 0 -> pawns = properties.BLACK_PAWNS;
            case 1 -> pawns = properties.WHITE_PAWNS;
            default -> throw new IllegalArgumentException();
        }
        Chessboard b = properties.BOARD;
        for (Square s : pawns) {
            int rank = s.rank() + 1;
            if (rank <= 8) {
                Square target = Square.get(s.file(), rank);
                ChessPiece p = b.read(target);
                if (p != null && p.type() == 1 && p.color() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * is the number of knights of Player B that are in the weak squares of Player A.
     * These weak squares are called outposts and best squares for knights because they can not be pushed
     * back by enemy pawns
     */
    public int enemyKnightOnWeak(int color, BoardProperties properties) {
        int count = 0;
        List<Square> weakSquares = weakSquares(color, properties);
        Chessboard b = properties.BOARD;
        for(Square weakSquare : weakSquares) {
            ChessPiece piece = b.read(weakSquare);
            if(piece != null && piece.type() == 2 && piece.color() != color) {
                count++;
                if(count == 2) return count;
            }
        }
        return count;
    }

    /**
     * returns 1 if a given pawn has no neighboring pawns of the same color. Isolated pawns
     * are generally considered as a weakness since they cannot be protected by pawns so they should be
     * protected by other more valuable pieces
     */
    public int isoPawn(int color, BoardProperties properties) {
        int count = 0;
        List<Square> pawns;
        switch (color) {
            case 0 -> pawns = properties.BLACK_PAWNS;
            case 1 -> pawns = properties.WHITE_PAWNS;
            default -> throw new IllegalArgumentException();
        }

        for (Square s : pawns) {
            if (isPawnIsolated(color, s, properties.BOARD)) {
                count++;
            }
        }
        return count;
    }

    /**
     * returns true if the pawn on square s is isolated. Else, false.
     */
    private boolean isPawnIsolated(int color, Square s, Chessboard b) {
        for (char file = (char) (s.file() - 1); file < s.file() + 2; file++) {
            if (97 <= file && file <= 104) {
                for (int rank = 1; rank <= 8; rank++) {
                    Square target = Square.get(file, rank);
                    if (target == s) {
                        continue;
                    }
                    ChessPiece p = b.read(target);
                    if (p != null && p.type() == 1 && p.color() == color) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *  is the number of pieces of the enemy that are acting on the king’s square
     */
    public int kingAttacked(int color, BoardProperties properties) {
        return kingAttackedDefended(color, properties, true, false);
    }

    public int kingAttackedDefended(int color, BoardProperties properties, boolean opponentIsAttacking, boolean kingZoneIsAttacked) {
        int count = 0;
        List<Square> zone;
        List<Opportunity> opportunities;

        switch (color) {
            case 0 -> {
                if(!opponentIsAttacking || kingZoneIsAttacked) zone = kingZone(properties.BLACK_KING);
                else {
                    zone = new ArrayList<>();
                    Square kingLocation = properties.BLACK_KING;
                    zone.add(kingLocation);
                }
                if (opponentIsAttacking) {
                    opportunities = properties.WHITE_MOVES;
                } else {
                    opportunities = properties.BLACK_MOVES;
                }
            }
            case 1 -> {
                if(!opponentIsAttacking || kingZoneIsAttacked) zone = kingZone(properties.WHITE_KING);
                else {
                    zone = new ArrayList<>();
                    Square kingLocation = properties.WHITE_KING;
                    zone.add(kingLocation);
                }
                if (opponentIsAttacking) {
                    opportunities = properties.BLACK_MOVES;
                } else {
                    opportunities = properties.WHITE_MOVES;
                }
            }
            default -> throw new IllegalStateException();
        }

        for (Opportunity mo : opportunities) {
            for (Square s : zone) {
                if (mo.options().contains(s)) {
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    /**
     * returns 1 if Player A is castled to measure king safety
     */
    public int kingCastled(int color, BoardProperties properties) {
        //Todo: Finish implementation when castling has been added.
        return 0;
    }

    /**
     * is the number of pieces of the Player A that are acting on Player A’s king’s
     * adjacent squares
     */
    public int kingDefended(int color, BoardProperties properties){
        return kingAttackedDefended(color, properties, false, false);
    }

    /**
     * is the number of pawns of Player A adjacent to Player A’s king. Pawn shield is
     * an important parameter for evaluating the king safety
     */
    public int kingPawnShield(int color, BoardProperties properties) {
        int count = 0;
        switch (color) {
            case 0 -> {
                List<Square> zone = kingZone(properties.BLACK_KING);
                for (Square s : zone) {
                    if (properties.BLACK_PAWNS.contains(s)) {
                        count++;
                    }
                }
            }
            case 1 -> {
                List<Square> zone = kingZone(properties.WHITE_KING);
                for (Square s : zone) {
                    if (properties.WHITE_PAWNS.contains(s)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * value is 1 if the Player A has a king
     */
    public int kingValue(int color, BoardProperties properties) {
        return switch (color) {
            case 0 -> properties.BLACK_KING == null ? 0 : 1;
            case 1 -> properties.WHITE_KING == null ? 0 : 1;
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * returns adjacent squares of king
     */
    private List<Square> kingZone(Square kingLocation) {
        List<Square> zone = new ArrayList<>();
        for (char file = (char) (kingLocation.file() - 1); file < kingLocation.file() + 2; file++) {
            for (int rank = kingLocation.rank() -1; rank < kingLocation.rank() + 2; rank++) {
                if (file < 'a' || file > 'h' || rank < 1 || rank > 8) {
                    continue;
                } else if (file == kingLocation.file() && rank == kingLocation.rank()) {
                    continue;
                }
                Square zoneSquare = Square.get(file, rank);
                zone.add(zoneSquare);
            }
        }
        return zone;
    }

    /**
     *  is the number of pieces of the enemy that are acting on ones king’s adjacent
     * squares
     */
    public int kingZoneAttacked(int color, BoardProperties properties) {
        return kingAttackedDefended(color, properties, true, true);
    }

    /**
     * is the number of squares that a specific knight can go to.
     */
    public int knightMob(int color, BoardProperties properties) {
        List<Opportunity> opportunities;
        switch (color) {
            case 0 -> opportunities = properties.BLACK_MOVES;
            case 1 -> opportunities = properties.WHITE_MOVES;
            default ->  throw new IllegalArgumentException();
        }
        int count = 0;
        for (Opportunity mo : opportunities) {
            ChessPiece p = mo.owner();
            if (p.type() == 2 && p.color() == color) {
                count += mo.size();
            }
        }
        return count;
    }

    /**
     * returns 1 if a given knight on a given square is supported by ones own pawn.
     * Supported knights are strong because they can only be backfired by pawns and since they are
     * supported they can stay in an important position for a long number of moves making it harder for
     * the opponent to play around it
     */
    public int knightSupport(int color, BoardProperties properties) {
        List<Square> knights;
        List<Square> pawns;
        int deltaRank;
        switch (color) {
            case 0 ->  {
                knights = properties.BLACK_KNIGHTS;
                pawns = properties.BLACK_PAWNS;
                deltaRank = 1;
            }
            case 1 -> {
                knights = properties.WHITE_KNIGHTS;
                pawns = properties.WHITE_PAWNS;
                deltaRank = -1;
            }
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        }

        int count = 0;
        for (Square s : knights) {
            int rank = s.rank() + deltaRank;
            if (rank < 1 || rank > 8) continue;
            outerLoop:
            for (char file = (char) (s.file() - 1); file < s.file() + 2; file = (char) (file + 2)) {
                if (file < 97 || file > 104) continue;
                Square captureLocationOfPawn = Square.get(file, rank);
                for (Square s2 :  pawns) {
                    if (s2 == captureLocationOfPawn) {
                        count++;
                        break outerLoop;
                    }
                }
            }
        }
        return count;
    }

    private int knightPeriphery(int color, BoardProperties properties, int[] bounds) {
        List<Square> knightLocations;
        switch (color) {
            case 0 -> knightLocations = properties.BLACK_KNIGHTS;
            case 1 -> knightLocations = properties.WHITE_KNIGHTS;
            default -> throw new IllegalArgumentException();
        }

        int count = 0;
        int rankUpperbound = 1 + bounds[1];
        for (int rank = 1 + bounds[0]; rank <= rankUpperbound; rank += (bounds[1] - bounds[0])){
            for(char file = (char) (97 + bounds[0]); file <= (char) (97 + bounds[1]); file++) {
                Square s = Square.get(file, rank);
                if (knightLocations.contains(s)) {
                    count++;
                    if (count == 2) {
                        return count;
                    }
                }
            }
        }

        char fileUpperbound = (char) (97 + bounds[1]);
        for (char file = (char) (97 + bounds[0]); file <= fileUpperbound; file += (bounds[1] - bounds[0])) {
            for (int rank = 2 + bounds[0]; rank <= bounds[1]; rank++) {
                Square s = Square.get(file, rank);
                if (knightLocations.contains(s)) {
                    count++;
                    if (count == 2) {
                        return count;
                    }
                }
            }
        }
        return count;
    }

    /**
     *  returns 1 if a given knight is on the squares a1 to a8,a8 to h8,a1 to h1 or h1 to h8.
     * This is the outest periphery and most of the time knights on these squares are weaker
     */
    public int knightPeriphery0(int color, BoardProperties properties) {
        int[] bounds = {0, 7};
        return knightPeriphery(color, properties, bounds);
    }

    /**
     *returns 1 if a given knight is on the squares b2 to b7,b7 to g7,b2 to g2 or g2 to g7.
     */
    public int knightPeriphery1(int color, BoardProperties properties){
        int[] bounds = {1, 6};
        return knightPeriphery(color, properties, bounds);
    }

    /**
     *  returns 1 if a given knight is on the squares c3 to c6,c6 to f6,c3 to f3 or f3 to f6
     */
    public int knightPeriphery2(int color, BoardProperties properties) {
        int[] bounds = {2, 5};
        return knightPeriphery(color, properties, bounds);
    }

    /**
     *  returns 1 if a given knight is on the squares e4, e5,d4 or d5
     */
    public int knightPeriphery3(int color, BoardProperties properties){
        int[] bounds = {3, 4};
        return knightPeriphery(color, properties, bounds);
    }

    /**
     *  value is the number of knight of the Player A
     */
    public int knightValue(int color, BoardProperties properties){
        return switch (color) {
            case 0 -> properties.BLACK_KNIGHTS.size();
            case 1 -> properties.WHITE_KNIGHTS.size();
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * returns 1 if for a given pawn there are no opposing pawns of the enemy on the neighboring
     * columns and on the given pawn’s column ahead of the pawn. If a pawn is passed it is big threat for
     * the opponent because they are no pawns on the way to prevent it from promoting
     */
    public int passPawn(int color, BoardProperties properties) {
        return passPawns(color, properties).size();
    }

    /**
     * returns list of squares where the pass pawns are: no opposing pawns of the enemy on the neighboring
     *      * columns and on the given pawn’s column ahead of the pawn.
     */
    private List<Square> passPawns(int color, BoardProperties properties) {
        Chessboard b = properties.BOARD;
        List<Square> passed = new ArrayList<>();
        List<Square> pawns;
        int deltaRank;
        switch (color) {
            case 0 -> {
                pawns = properties.BLACK_PAWNS;
                deltaRank = -1;
            }
            case 1 -> {
                pawns = properties.WHITE_PAWNS;
                deltaRank = 1;
            }
            default -> throw new IllegalArgumentException();
        }
        for (Square s : pawns) {
            boolean opponentPawnNotPassed = true;
            char upperbound = (char) (s.file() + 1);
            outerLoop:
            for (char file = (char) (s.file() - 1); file <= upperbound; file++) {
                if (file < 97 || file > 104) { continue; }
                for (int rank = s.rank() + deltaRank; (1 <= rank && rank <= 8); rank = rank + deltaRank) {
                    Square target = Square.get(file, rank);
                    ChessPiece p = b.read(target);
                    opponentPawnNotPassed = p != null && p.type() == 1 && p.color() != color;
                    if (opponentPawnNotPassed) {
                        break outerLoop;
                    }
                }
            }
            if (!opponentPawnNotPassed) {
                passed.add(s);
            }
        }
        return passed;
    }

    /**
     *  value is the number of pawns of the Player A
     */
    public int pawnValue(int color, BoardProperties properties) {
        return switch (color) {
            case 0 -> properties.BLACK_PAWNS.size();
            case 1 -> properties.WHITE_PAWNS.size();
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * returns the number of squares a queen can move to
     */
    public int queenMob(int color, BoardProperties properties) {
        List<Opportunity> opportunities;
        switch (color) {
            case 0 -> opportunities = properties.BLACK_MOVES;
            case 1 -> opportunities = properties.WHITE_MOVES;
            default ->  throw new IllegalArgumentException();
        }
        int count = 0;
        for (Opportunity mo : opportunities) {
            ChessPiece p = mo.owner();
            if (p.type() == 5 && p.color() == color) {
                count += mo.size();
            }
        }
        return count;
    }

    /**
     *  value is the number of queen of the Player A
     */
    public int queenValue(int color, BoardProperties properties){
        return switch (color) {
            case 0 -> properties.BLACK_QUEENS.size();
            case 1 -> properties.WHITE_QUEENS.size();
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * is the rank of the passed pawn. A passed pawn on rank 7 which means the pawn
     * is one move away from promoting is a lot more dangerous compared to a passed pawn on its
     * initial square. Passed pawns with higher ranks have higher priority thus they are an advantage.
     */
    public int rankPassedPawn(int color, BoardProperties properties) {
        List<Square> passed = passPawns(color, properties);
        int sum = 0;
        for (Square s : passed) {
            sum += s.rank();
        }
        return sum;
    }

    /**
     * returns 1 if a rook of the same color is behind the passed pawn. If there is a rook
     * behind a passed pawn it is easier to push to pawn forward as it is always protected by the rook and
     * rook never gets in the way
     */
    public int rookBhdPassPawn(int color, BoardProperties properties) {
        int count = 0;
        List<Square> passed = passPawns(color, properties);
        List<Square> rooks;
        switch (color) {
            case 0 -> rooks = properties.BLACK_ROOKS;
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        for (Square s : rooks) {
            for (Square s2 : passed) {
                if (s.file() == s2.file()) {
                    if (color == 0 && s.rank() > s2.rank()) {
                        count++;
                        break;
                    } else if (color == 1 && s.rank() < s2.rank()) {
                        count++;
                        break;
                    }
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
    public int rookClosedFile(int color, BoardProperties properties){
        int count = 0;
        List<Square> rooks;
        switch (color) {
            case 0 -> rooks = properties.BLACK_ROOKS;
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        Chessboard b =  properties.BOARD;
        for (Square s : rooks) {
            boolean blackPawn = false;
            boolean whitePawn = false;
            for (int rank = 1; rank <= 8; rank++) {
                if (rank == s.rank()) continue;
                Square target = Square.get(s.file(), rank);
                ChessPiece p = b.read(target);
                if (!blackPawn) blackPawn = p != null && p.type() == 1 && p.color() == 0;
                if (!whitePawn) whitePawn = p != null && p.type() == 1 && p.color() == 1;
                if (blackPawn && whitePawn) break;
            }
            if (blackPawn && whitePawn) count++;
        }
        return count;
    }

    /**
     * returns 1 if there are no pieces between to rooks of the same color and they are on the
     * same file or on the same rank. Connected rooks defend each other to create threats in the opposition
     * area because they cannot be captured by queen or king
     */
    public int rookCon(int color, BoardProperties properties) {
        List<Square> rooks;
        switch (color) {
            case 0 -> rooks = properties.BLACK_ROOKS;
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        if (rooks.size() < 2) {
            return 0;
        } else if (rooks.get(0).rank() == rooks.get(1).rank() || rooks.get(0).file() == rooks.get(1).file()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * returns the number of squares a rook can move to.
     */
    public int rookMob(int color, BoardProperties properties) {
        List<Opportunity> opportunities;
        switch (color) {
            case 0 -> opportunities = properties.BLACK_MOVES;
            case 1 -> opportunities = properties.WHITE_MOVES;
            default ->  throw new IllegalArgumentException();
        }
        int count = 0;
        for (Opportunity mo : opportunities) {
            ChessPiece p = mo.owner();
            if (p.type() == 4 && p.color() == color) {
                count += mo.size();
            }
        }
        return count;
    }

    /**
     * returns 1 if a given rook is on seventh rank from the Players perspective. For white
     * that would be the rank 7 for black rank 2. Rooks on seventh rank are dangerous and a classical
     * theme in chess for creating major threats at once
     */
    public int rookOnSeventh(int color, BoardProperties properties) {
        int count = 0;
        int[] targetRanks = {1, 8};
        List<Square> rooks;
        switch (color) {
            case 0 -> rooks = properties.BLACK_ROOKS;
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        for (Square s : rooks) {
            if (s.rank() == targetRanks[color]) {
                count++;
            }
        }
        return count;
    }

    /**
     * returns 1 if a given rook is on a file with no pawns from either side. Rooks are stronger
     * on open columns because they can move freely
     */
    public int rookOpenFile(int color, BoardProperties properties){
        int count = 0;
        List<Square> rooks;
        switch (color) {
            case 0 -> rooks = properties.BLACK_ROOKS;
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        Chessboard b =  properties.BOARD;
        for (Square s : rooks) {
            boolean isClosed = true;
            for (int rank = 1; rank <= 8; rank++) {
                if (rank == s.rank()) {
                    continue;
                }
                Square target = Square.get(s.file(), rank);
                ChessPiece p = b.read(target);
                isClosed = p != null && p.type() == 1;
                if (isClosed) {
                    break;
                }
            }
            if (!isClosed) {
                count++;
            }
        }
        return count;
    }

    /**
     * returns 1 if a given rook is on a file with no pawns from its own side. Rooks are
     * strong on semi-open files as well
     */
    public int rookSemiOpenFile(int color, BoardProperties properties) {
        int count = 0;
        List<Square> rooks;
        switch (color) {
            case 0 -> rooks = properties.BLACK_ROOKS;
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        Chessboard b =  properties.BOARD;
        for (Square s : rooks) {
            boolean isClosed = true;
            for (int rank = 1; rank <= 8; rank++) {
                if (rank == s.rank()) {
                    continue;
                }
                Square target = Square.get(s.file(), rank);
                ChessPiece p = b.read(target);
                isClosed = p != null && p.type() == 1 && p.color() == color;
                if (isClosed) {
                    break;
                }
            }
            if (!isClosed) {
                count++;
            }
        }
        return count;
    }

    /**
     *  value is the number of rook of the Player A
     */
    public int rookValue(int color, BoardProperties properties){
        return switch (color) {
            case 0 -> properties.BLACK_ROOKS.size();
            case 1 -> properties.WHITE_ROOKS.size();
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * is the number of squares in Player A’s area that cannot be protected by Player A’s pawns.
     */
    public int weakCount(int color, BoardProperties properties) {
        return weakSquares(color, properties).size();
    }

    /**
     * returns the list of squares that are weak squares: squares in Player A’s area that cannot be protected by Player A’s pawns.
     */
    public List<Square> weakSquares(int color, BoardProperties properties) {
        List<Square> weakSquares = new ArrayList<>();
        int strongSquare = 0;
        int[][] checkingSquares = new int[3][8];
        for (int i = 0; i < checkingSquares.length; i++) {
            for (int j = 0; j < checkingSquares[0].length; j++) {
                if (j == 0 || j == 7) checkingSquares[i][j] = 1;
                else checkingSquares[i][j] = strongSquare;
            }
        }
        Chessboard b = properties.BOARD;
        boolean teamIsBlack = color == 0;
        for (int fileTemp = 0; fileTemp <= 7; fileTemp++) {
            boolean pawnFront = false;
            for (int deltaRank = 0; deltaRank <= 2; deltaRank++) {
                Square squareTemp;
                int weakRankTemp;
                if (teamIsBlack) {
                    squareTemp = Square.get((char) (97 + fileTemp), 7 - deltaRank);
                    weakRankTemp = 2 + deltaRank;
                } else {
                    squareTemp = Square.get((char) (97 + fileTemp), 2 + deltaRank);
                    weakRankTemp = 5 - deltaRank;
                }
                ChessPiece piece = b.read(squareTemp);
                if (piece != null && piece.type() == 1 && piece.color() == color) {
                    pawnFront = true;
                } else if (piece != null) pawnFront = false;
                if (!pawnFront) {
                    for (int jTemp = fileTemp - 1; jTemp <= fileTemp + 1; jTemp += 2) {
                        if (jTemp == -1 || jTemp == 8) continue;
                        Square weakSquare = Square.get((char) (97 + jTemp), 8 - weakRankTemp);
                        ChessPiece pieceOnWeak = b.read(weakSquare);
                        if (pieceOnWeak != null && pieceOnWeak.type() == 1 && pieceOnWeak.color() == color) continue;
                        checkingSquares[deltaRank][jTemp]++;
                        if (checkingSquares[deltaRank][jTemp] == 2) {
                            weakSquares.add(weakSquare);
                        }
                    }
                }
            }
        }
        return weakSquares;
    }

    private int evaluationFunction(Chromosome c, int color, BoardProperties properties) {
        int[] data = c.getData();
        return  backwardPawn(color, properties) * data[0] +
                bishopMob(color, properties) * data[1] +
                bishopOnLarge(color, properties) * data[2] +
                bishopPair(color, properties) * data[3] +
                bishopValue(color, properties) * data[4] +
                blockedPassedPawn(color, properties) * data[5] +
                blockedPawn(color, properties) * data[6] +
                centerPawnCount(color, properties) * data[7] +
                doublePawn(color, properties) * data[8] +
                enemyKnightOnWeak(color, properties) * data[9] +
                isoPawn(color, properties) * data[10] +
                kingAttacked(color, properties) * data[11] +
                kingCastled(color, properties) * data[12] +
                kingDefended(color, properties) * data[13] +
                kingPawnShield(color, properties) * data[14] +
                kingValue(color, properties) * data[15] +
                kingZoneAttacked(color, properties) * data[16] +
                knightMob(color, properties) * data[17] +
                knightSupport(color, properties) * data[18] +
                knightPeriphery0(color, properties) * data[19] +
                knightPeriphery1(color, properties) * data[20] +
                knightPeriphery2(color, properties) * data[21] +
                knightPeriphery3(color, properties) * data[22] +
                knightValue(color, properties) * data[23] +
                passPawn(color, properties) * data[24] +
                pawnValue(color, properties) * data[25] +
                queenMob(color, properties) * data[26] +
                queenValue(color, properties) * data[27] +
                rankPassedPawn(color, properties) * data[28] +
                rookBhdPassPawn(color, properties) * data[29] +
                rookClosedFile(color, properties) * data[30] +
                rookCon(color, properties) * data[31] +
                rookMob(color, properties) * data[32] +
                rookOnSeventh(color, properties) * data[33] +
                rookOpenFile(color, properties) * data[34] +
                rookSemiOpenFile(color, properties) * data[35] +
                rookValue(color, properties) * data[36] +
                weakCount(color, properties) * data[37];
    }

    private int evaluationFunction(int[] data, int color, BoardProperties properties) {
        return  backwardPawn(color, properties) * data[0] +
                bishopMob(color, properties) * data[1] +
                bishopOnLarge(color, properties) * data[2] +
                bishopPair(color, properties) * data[3] +
                bishopValue(color, properties) * data[4] +
                blockedPassedPawn(color, properties) * data[5] +
                blockedPawn(color, properties) * data[6] +
                centerPawnCount(color, properties) * data[7] +
                doublePawn(color, properties) * data[8] +
                enemyKnightOnWeak(color, properties) * data[9] +
                isoPawn(color, properties) * data[10] +
                kingAttacked(color, properties) * data[11] +
                kingCastled(color, properties) * data[12] +
                kingDefended(color, properties) * data[13] +
                kingPawnShield(color, properties) * data[14] +
                kingValue(color, properties) * data[15] +
                kingZoneAttacked(color, properties) * data[16] +
                knightMob(color, properties) * data[17] +
                knightSupport(color, properties) * data[18] +
                knightPeriphery0(color, properties) * data[19] +
                knightPeriphery1(color, properties) * data[20] +
                knightPeriphery2(color, properties) * data[21] +
                knightPeriphery3(color, properties) * data[22] +
                knightValue(color, properties) * data[23] +
                passPawn(color, properties) * data[24] +
                pawnValue(color, properties) * data[25] +
                queenMob(color, properties) * data[26] +
                queenValue(color, properties) * data[27] +
                rankPassedPawn(color, properties) * data[28] +
                rookBhdPassPawn(color, properties) * data[29] +
                rookClosedFile(color, properties) * data[30] +
                rookCon(color, properties) * data[31] +
                rookMob(color, properties) * data[32] +
                rookOnSeventh(color, properties) * data[33] +
                rookOpenFile(color, properties) * data[34] +
                rookSemiOpenFile(color, properties) * data[35] +
                rookValue(color, properties) * data[36] +
                weakCount(color, properties) * data[37];

    }

    public int evaluate(Chromosome c, DiceChess game) {
        if (game.getState() != GameState.ONGOING) {
            return Integer.MIN_VALUE;
        }
        int color = game.getActiveColor();
        int opponent;
        switch(color) {
            case 0 -> opponent = 1;
            case 1 -> opponent = 0;
            default -> throw new IllegalArgumentException("Game does not contain an active color between 0 and 1.");
        }
        BoardProperties properties = new BoardProperties(game);
        int currentPlayerScore = evaluationFunction(c, color, properties);
        int opponentPlayerScore = evaluationFunction(c, opponent, properties);
        return currentPlayerScore - opponentPlayerScore;
    }

    public int evaluate(DiceChess game, int[] data) {
        if (game.getState() != GameState.ONGOING) {
            return Integer.MIN_VALUE;
        }
        int color = game.getActiveColor();
        int opponent;
        switch(color) {
            case 0 -> opponent = 1;
            case 1 -> opponent = 0;
            default -> throw new IllegalArgumentException("Game does not contain an active color between 0 and 1.");
        }
        BoardProperties properties = new BoardProperties(game);
        int currentPlayerScore = evaluationFunction(data, color, properties);
        int opponentPlayerScore = evaluationFunction(data, opponent, properties);
        return currentPlayerScore - opponentPlayerScore;
    }
}

