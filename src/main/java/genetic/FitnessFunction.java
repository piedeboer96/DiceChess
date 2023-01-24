package genetic;

import game.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FitnessFunction {

    /**
     * returns 1 if the neighboring pawns of a pawn are ahead of it. Backward pawns are
     * the last pawn of a pawn chain and even though they are not isolated they can not be defended easily.
     * So they are considered a disadvantage.
     */
    // O(5,804)
    public int backwardPawn(int color, BoardProperties properties) {
        int count = 0; // 3
        int deltaRank; // 2
        List<Square> pawns; // 2
        switch (color) { // 1
            case 0 -> { // 1
                pawns = properties.BLACK_PAWNS; // 2
                deltaRank = 1; // 2
            }
            case 1 -> { // 1
                pawns = properties.WHITE_PAWNS; // 2
                deltaRank = -1; // 2
            }
            default -> throw new IllegalArgumentException();
        }
        Chessboard b = properties.BOARD; // 3
        for (Square s : pawns) { // 8 + extra : (3)
            if (isPawnIsolated(color, s, b)) continue; // 414
            boolean isNotBackwardPawn = false; // 3
            char upperbound = (char) (s.file() + 1); // 4
            outerLoop: // 1
            for (char file = (char) (s.file() - 1); file <= upperbound; file += 2) { // 2 + extra : (8)
                if (file < 97 || file > 104) continue; // 3
                for (int rank = s.rank(); (1 <= rank && rank <= 8); rank = rank + deltaRank) { // 7 + extra : (38)
                    Square target = Square.get(file, rank); // 3
                    ChessPiece p = b.read(target); // 3
                    isNotBackwardPawn = p != null && p.type() == 1 && p.color() == color; // 8
                    if (isNotBackwardPawn) { // 1
                        break outerLoop;
                    }
                }
            }
            if (!isNotBackwardPawn) { // 1
                count++;
            }
        }
        return count; // 1
    }

    /**
     * is the number of squares that a bishop can go to. This type of parameters are calculated
     * seperately for each bishop
     */
    // O(589)
    public int bishopMob(int color, BoardProperties properties) {
        List<Opportunity> opportunities; // 2
        switch (color) { // 1
            case 0 -> opportunities = properties.BLACK_MOVES; // 3
            case 1 -> opportunities = properties.WHITE_MOVES; // 3
            default ->  throw new IllegalArgumentException();
        }
        int count = 0; // 3
        for (Opportunity mo : opportunities) { // 16 + extra : (3)
            ChessPiece p = mo.owner(); // 3
            if (p.type() == 3 && p.color() == color) { // 5 + 2(14)
                count += mo.size();
            }
        }
        return count; // 1
    }

    /**
     * parameter returns 1 if the bishop on the given square is one of the two large diagonals
     * of the board. Bishops are stronger on the large diagonals because they have higher mobility and
     * they are reaching the two central squares simultaneously controlling the center
     */
    // O(176)
    public int bishopOnLarge(int color, BoardProperties properties) {
        List<Square> bishops; // 2
        switch (color) { // 1
            case 0 -> bishops = properties.BLACK_BISHOPS; // 3
            case 1 -> bishops = properties.WHITE_BISHOPS; // 3
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        }
        int count = 0; // 3
        for (int i = 7, j = 0; i >= 0 && j <= 7; i--, j++) { // 8 + extra : (46)
            Square s1 = Square.get((char)('a' + j), 8 - j); // 6
            Square s2 = Square.get((char)('a' + j), 8 - i); // 6
            if(bishops.contains(s1)) count++; // 1
            if(bishops.contains(s2)) count++; // 1
            if(count == 2) return count; // 1
        }
        return count; // 1
    }

    /**
     * returns 1 if Player A has two or more bishops. Bishop pairs are generally considered
     * an advantage as to bishops can together cover all possible squares regardless of the color of the
     * square. Bishop pairs are especially strong in open positions where there are no central pawns and
     * the bishops can move freely to create threats
     */
    // O(5)
    public int bishopPair(int color, BoardProperties properties) {
        return (bishopValue(color,properties) >= 2)? 1 : 0; // 5
    }

    /**
     * value is the number of bishop of the Player A
     */
    // O(4)
    public int bishopValue(int color, BoardProperties properties) {
        return switch (color) { // 1
            case 0 -> properties.BLACK_BISHOPS.size(); // 3
            case 1 -> properties.WHITE_BISHOPS.size(); // 3
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * returns 1 if a passed pawn of Player A is blocked by a piece of Player B which
     * prevents it from moving closer to promotion. This is an advantage for the blocking side. The passed
     * pawn on a4 is not blocked by any black pieces. If it is pushed to a5 however it will be considered
     * blocked because there is a black knight on a6
     */

    // O(3889)
    public int blockedPassedPawn(int color, BoardProperties properties) {
        int count = 0; // 3
        List<Square> passed = passPawns(color, properties); // 3,724
        int[] deltaRank = {-1, 1}; // 3
        Chessboard b = properties.BOARD; // 3
        for (Square s : passed) { // 8 + extra : (11)
            int rank = s.rank() + deltaRank[color]; // 5
            if (rank < 1 || rank > 8) { // 3
                continue;
            }
            Square target = Square.get(s.file(), rank); // 3
            ChessPiece p = b.read(target); // 3
            if (p != null && p.color() != color) { // 4
                count++;
            }
        }
        return count; // 1
    }

    /**
     * returns 1 if a central pawn on column e or d on its initial square is blocked by its own
     * piece which severely decreases the mobility of the pieces
     */
    // O(69)
    public int blockedPawn(int color, BoardProperties properties) {
        int count = 0; // 3
        Square[][] locations = {
                {Square.get('d', 7), Square.get('e', 7)},
                {Square.get('d', 2), Square.get('e', 2)}
        }; // 6
        int[] deltaRank = {-1, 1}; // 3
        Chessboard b = properties.BOARD; // 3
        for (Square s : locations[color]) { // 2 + extra : (5)
            ChessPiece ps = b.read(s); // 3
            if (ps == null || ps.color() != color || ps.type() != 1) continue; // 7
            Square target = Square.get(s.file(), s.rank() + deltaRank[color]); // 7
            ChessPiece pt = b.read(target); // 3
            if (pt != null && pt.color() == color) { // 4
                count++;
            }
        }
        return count; // 1
    }

    /**
     * is the number of pawns of Player A that are in squares e4,e5,d4 or d5. Center
     * pawns are important for controlling the center and decreasing enemy mobility
     */
    // O(27)
    public int centerPawnCount(int color, BoardProperties properties){
        Square[] center = {Square.get('d', 4), Square.get('d', 5), Square.get('e', 4), Square.get('e', 5)}; // 6
        int count = 0; // 3
        switch (color) { // 1
            case 0 -> { // 1
                for (Square s : center) { // 4 + extra : (3)
                    if (properties.BLACK_PAWNS.contains(s)) { // 2
                        count++; // 1
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
        return count; // 1
    }

    /**
     * returns 1 if a pawn is doubled pawn. Doubled pawns are considered a disadvantage as
     * they blocked each other, and they are vulnerable to attacks.
     */
    // O(172)
    public int doublePawn(int color, BoardProperties properties) {
        int count = 0; // 3
        List<Square> pawns; // 1
        switch (color) { // 1
            case 0 -> pawns = properties.BLACK_PAWNS; // 3
            case 1 -> pawns = properties.WHITE_PAWNS;
            default -> throw new IllegalArgumentException();
        }
        Chessboard b = properties.BOARD; // 3
        for (Square s : pawns) { // 8 + extra : (8)
            int rank = s.rank() + 1; // 4
            if (rank <= 8) { // 1
                Square target = Square.get(s.file(), rank);  // 4
                ChessPiece p = b.read(target); // 3
                if (p != null && p.type() == 1 && p.color() == color) { // 7
                    count++;
                }
            }
        }
        return count; // 1
    }

    /**
     * is the number of knights of Player B that are in the weak squares of Player A.
     * These weak squares are called outposts and best squares for knights because they can not be pushed
     * back by enemy pawns
     */
    // O(1,902)
    public int enemyKnightOnWeak(int color, BoardProperties properties) {
        int count = 0; // 3
        List<Square> weakSquares = weakSquares(color, properties); // 1,886
        List<Square> knights; // 1
        switch (color) { // 1
            case 0 -> knights = properties.BLACK_KNIGHTS; // 3
            case 1 -> knights = properties.WHITE_KNIGHTS;
            default -> throw new IllegalArgumentException();
        }
        for(Square knight : knights) { // 2 + extra : (3)
            if (weakSquares.contains(knight)) count++; // 2
        }
        return count; // 1
    }

    /**
     * returns 1 if a given pawn has no neighboring pawns of the same color. Isolated pawns
     * are generally considered as a weakness since they cannot be protected by pawns so they should be
     * protected by other more valuable pieces
     */
    // O(3,332)
    public int isoPawn(int color, BoardProperties properties) {
        int count = 0; // 3
        List<Square> pawns; // 1
        switch (color) { // 1
            case 0 -> pawns = properties.BLACK_PAWNS; // 3
            case 1 -> pawns = properties.WHITE_PAWNS;
            default -> throw new IllegalArgumentException();
        }

        for (Square s : pawns) { // 8 + extra : (3)
            if (isPawnIsolated(color, s, properties.BOARD)) { // 414
                count++; // 1
            }
        }
        return count; // 1
    }

    /**
     * returns true if the pawn on square s is isolated. Else, false.
     */
    // O(414)
    private boolean isPawnIsolated(int color, Square s, Chessboard b) {
        for (char file = (char) (s.file() - 1); file < s.file() + 2; file++) { // 3 + extra : (14)
            if (97 <= file && file <= 104) { // 3
                for (int rank = 1; rank <= 8; rank++) { // 8 + extra : (18)
                    Square target = Square.get(file, rank); // 3
                    if (target == s) { // 1
                        continue;
                    }
                    ChessPiece p = b.read(target); // 3
                    if (p != null && p.type() == 1 && p.color() == color) { // 7
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
    // O(119)
    public int kingAttacked(int color, BoardProperties properties) {
        return kingAttackedDefended(color, properties, true, false);
    }

    public int kingAttackedDefended(int color, BoardProperties properties, boolean opponentIsAttacking, boolean kingZoneIsAttacked) {
        int count = 0; // 3
        List<Square> zone; // 1
        List<Opportunity> opportunities; // 1

        switch (color) { // 1
            case 0 -> { // 1
                if(!opponentIsAttacking || kingZoneIsAttacked) zone = kingZone(properties.BLACK_KING); // 2 + extra : (217)
                else { // 1
                    zone = new ArrayList<>(); // 2
                    Square kingLocation = properties.BLACK_KING; // 3
                    zone.add(kingLocation); // 1
                }
                if (opponentIsAttacking) { // 1
                    opportunities = properties.WHITE_MOVES; // 2
                } else { // 1
                    opportunities = properties.BLACK_MOVES; // 2
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

        for (Opportunity mo : opportunities) { // 16 + extra : (3)
            for (Square s : zone) { // (8 || 1) + extra : (5)
                if (mo.options().contains(s)) { // 1
                    count++;
                    break;
                }
            }
        }

        return count; // 1
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
    // O(442)
    public int kingDefended(int color, BoardProperties properties){
        return kingAttackedDefended(color, properties, false, false);
    }

    /**
     * is the number of pawns of Player A adjacent to Player A’s king. Pawn shield is
     * an important parameter for evaluating the king safety
     */
    // O(251)
    public int kingPawnShield(int color, BoardProperties properties) {
        List<Square> zone = color == 0 ? kingZone(properties.BLACK_KING) : kingZone(properties.WHITE_KING);
        List<Square> pawns = color == 0 ? properties.BLACK_PAWNS : properties.WHITE_PAWNS;
//        List<Square> zone = Collections.synchronizedList(color == 0 ? kingZone(properties.BLACK_KING) : kingZone(properties.WHITE_KING));
//        List<Square> pawns = Collections.synchronizedList(color == 0 ? properties.BLACK_PAWNS : properties.WHITE_PAWNS);
//        return (int) zone.parallelStream().filter(pawns::contains).count(); this would make it parallel but im not 100% that is working
        return (int) zone.stream().filter(pawns::contains).count();
    }


    /**
     * value is 1 if the Player A has a king
     */
    // O(5)
    public int kingValue(int color, BoardProperties properties) {
        return switch (color) { // 1
            case 0 -> properties.BLACK_KING == null ? 0 : 1; // 4
            case 1 -> properties.WHITE_KING == null ? 0 : 1;
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * returns adjacent squares of king
     */
    // O(216)
    private List<Square> kingZone(Square kingLocation) {
        List<Square> zone = new ArrayList<>(); // 3
        for (char file = (char) (kingLocation.file() - 1); file < kingLocation.file() + 2; file++) { // 3 + extra : (17)
            for (int rank = kingLocation.rank() - 1; rank < kingLocation.rank() + 2; rank++) { // 3 + extra : (16)
                if (file < 'a' || file > 'h' || rank < 1 || rank > 8) { // 7
                    continue;
                } else if (file == kingLocation.file() && rank == kingLocation.rank()) { // 5
                    continue;
                }
                Square zoneSquare = Square.get(file, rank); // 3
                zone.add(zoneSquare); // 1
            }
        }
        return zone; // 1
    }

    /**
     *  is the number of pieces of the enemy that are acting on ones king’s adjacent
     * squares
     */
    // O(441)
    public int kingZoneAttacked(int color, BoardProperties properties) {
        return kingAttackedDefended(color, properties, true, true);
    }

    /**
     * is the number of squares that a specific knight can go to.
     */
//    public int knightMob(int color, BoardProperties properties) {
//        List<Opportunity> opportunities;
//        switch (color) {
//            case 0 -> opportunities = properties.BLACK_MOVES;
//            case 1 -> opportunities = properties.WHITE_MOVES;
//            default ->  throw new IllegalArgumentException();
//        }
//        int count = 0;
//        for (Opportunity mo : opportunities) {
//            ChessPiece p = mo.owner();
//            if (p.type() == 2 && p.color() == color) {
//                count += mo.size();
//            }
//        }
//        return count;
//    }
    public int knightMob(int color, BoardProperties properties) {
        List<Opportunity> opportunities = color == 0 ? properties.BLACK_MOVES : properties.WHITE_MOVES;
        return opportunities.stream()
                .filter(o -> o.owner().type() == 2 && o.owner().color() == color)
                .mapToInt(Opportunity::size)
                .sum();
    }

    /**
     * returns 1 if a given knight on a given square is supported by ones own pawn.
     * Supported knights are strong because they can only be backfired by pawns and since they are
     * supported they can stay in an important position for a long number of moves making it harder for
     * the opponent to play around it
     */
    // O(140)
    public int knightSupport(int color, BoardProperties properties) {
        List<Square> knights; // 1
        List<Square> pawns; // 1
        int deltaRank; // 1
        switch (color) { // 1
            case 0 ->  { // 1
                knights = properties.BLACK_KNIGHTS; // 2
                pawns = properties.BLACK_PAWNS; // 2
                deltaRank = 1; // 2
            }
            case 1 -> {
                knights = properties.WHITE_KNIGHTS;
                pawns = properties.WHITE_PAWNS;
                deltaRank = -1;
            }
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        }

        int count = 0; // 3
        for (Square s : knights) { // 2 + extra : (3)
            int rank = s.rank() + deltaRank; // 4
            if (rank < 1 || rank > 8) continue; // 3
            outerLoop: // 1
            for (char file = (char) (s.file() - 1); file < s.file() + 2; file = (char) (file + 2)) { // 2 + extra : (19)
                if (file < 97 || file > 104) continue; // 3
                Square captureLocationOfPawn = Square.get(file, rank); // 3
                for (Square s2 :  pawns) { // 8 + extra : (3)
                    if (s2 == captureLocationOfPawn) { // 1
                        count++;
                        break outerLoop;
                    }
                }
            }
        }
        return count; // 1
    }

    /*
     public int kingPawnShield(int color, BoardProperties properties) {
        List<Square> zone = color == 0 ? kingZone(properties.BLACK_KING) : kingZone(properties.WHITE_KING);
        List<Square> pawns = color == 0 ? properties.BLACK_PAWNS : properties.WHITE_PAWNS;
        return (int) zone.stream().filter(pawns::contains).count();
    }
     */
//    private int knightPeriphery(int color, BoardProperties properties, int[] bounds) {
//        List<Square> knightLocations;
//        switch (color) {
//            case 0 -> knightLocations = properties.BLACK_KNIGHTS;
//            case 1 -> knightLocations = properties.WHITE_KNIGHTS;
//            default -> throw new IllegalArgumentException();
//        }
//
//        int count = 0;
//        int rankUpperbound = 1 + bounds[1];
//        for (int rank = 1 + bounds[0]; rank <= rankUpperbound; rank += (bounds[1] - bounds[0])){
//            for(char file = (char) (97 + bounds[0]); file <= (char) (97 + bounds[1]); file++) {
//                Square s = Square.get(file, rank);
//                if (knightLocations.contains(s)) {
//                    count++;
//                    if (count == 2) {
//                        return count;
//                    }
//                }
//            }
//        }
//
//        char fileUpperbound = (char) (97 + bounds[1]);
//        for (char file = (char) (97 + bounds[0]); file <= fileUpperbound; file += (bounds[1] - bounds[0])) {
//            for (int rank = 2 + bounds[0]; rank <= bounds[1]; rank++) {
//                Square s = Square.get(file, rank);
//                if (knightLocations.contains(s)) {
//                    count++;
//                    if (count == 2) {
//                        return count;
//                    }
//                }
//            }
//        }
//        return count;
//    }
    private int knightPeriphery(int color, BoardProperties properties, int[] bounds) {
        List<Square> knightLocations = color == 0 ? properties.BLACK_KNIGHTS : properties.WHITE_KNIGHTS;
        return (int) knightLocations.stream().filter(s -> s.file() >= (char) (97 + bounds[0]) && s.file() <= (char) (97 + bounds[1])
                && (s.rank() == bounds[0] + 1 || s.rank() == bounds[1]
                || s.file() == (char) (97 + bounds[0]) || s.file() == (char) (97 + bounds[1]))).count();
    }


    /**
     *  returns 1 if a given knight is on the squares a1 to a8,a8 to h8,a1 to h1 or h1 to h8.
     * This is the outest periphery and most of the time knights on these squares are weaker
     */
    // O(299)
    public int knightPeriphery0(int color, BoardProperties properties) {
        int[] bounds = {0, 7};
        return knightPeriphery(color, properties, bounds);
    }

    /**
     *returns 1 if a given knight is on the squares b2 to b7,b7 to g7,b2 to g2 or g2 to g7.
     */
    // O(235)
    public int knightPeriphery1(int color, BoardProperties properties){
        int[] bounds = {1, 6};
        return knightPeriphery(color, properties, bounds);
    }

    /**
     *  returns 1 if a given knight is on the squares c3 to c6,c6 to f6,c3 to f3 or f3 to f6
     */
    // O(171)
    public int knightPeriphery2(int color, BoardProperties properties) {
        int[] bounds = {2, 5};
        return knightPeriphery(color, properties, bounds);
    }

    /**
     *  returns 1 if a given knight is on the squares e4, e5,d4 or d5
     */
    // O(110)
    public int knightPeriphery3(int color, BoardProperties properties){
        int[] bounds = {3, 4};
        return knightPeriphery(color, properties, bounds);
    }

    /**
     *  value is the number of knight of the Player A
     */
    // O(4)
    public int knightValue(int color, BoardProperties properties){
        return switch (color) { // 1
            case 0 -> properties.BLACK_KNIGHTS.size(); // 3
            case 1 -> properties.WHITE_KNIGHTS.size();
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * returns 1 if for a given pawn there are no opposing pawns of the enemy on the neighboring
     * columns and on the given pawn’s column ahead of the pawn. If a pawn is passed it is big threat for
     * the opponent because they are no pawns on the way to prevent it from promoting
     */
    // O(3,723)
    public int passPawn(int color, BoardProperties properties) {
        return passPawns(color, properties).size();
    }

    /**
     * returns list of squares where the pass pawns are: no opposing pawns of the enemy on the neighboring
     *      * columns and on the given pawn’s column ahead of the pawn.
     */
    // O(3,722)
    private List<Square> passPawns(int color, BoardProperties properties) {
        Chessboard b = properties.BOARD; // 3
        List<Square> passed = new ArrayList<>(); // 3
        List<Square> pawns; // 1
        int deltaRank; // 1
        switch (color) { // 1
            case 0 -> { // 1
                pawns = properties.BLACK_PAWNS; // 2
                deltaRank = -1; // 2
            }
            case 1 -> { // 1
                pawns = properties.WHITE_PAWNS; // 2
                deltaRank = 1; // 2
            }
            default -> throw new IllegalArgumentException();
        }
        for (Square s : pawns) { // 8 + extra : (11)
            boolean opponentPawnNotPassed = true; // 3
            char upperbound = (char) (s.file() + 1); // 5
            outerLoop: // 1
            for (char file = (char) (s.file() - 1); file <= upperbound; file++) { // 3 + extra : (11)
                if (file < 97 || file > 104) { continue; } // 3
                for (int rank = s.rank() + deltaRank; (1 <= rank && rank <= 8); rank = rank + deltaRank) { // 7 + extra : (39)
                    Square target = Square.get(file, rank); // 3
                    ChessPiece p = b.read(target); // 3
                    opponentPawnNotPassed = p != null && p.type() == 1 && p.color() != color; // 8
                    if (opponentPawnNotPassed) { // 1
                        break outerLoop;
                    }
                }
            }
            if (!opponentPawnNotPassed) { // 1
                passed.add(s);
            }
        }
        return passed; // 1
    }

    /**
     *  value is the number of pawns of the Player A
     */
    // O(4)
    public int pawnValue(int color, BoardProperties properties) {
        return switch (color) { // 1
            case 0 -> properties.BLACK_PAWNS.size(); // 3
            case 1 -> properties.WHITE_PAWNS.size();
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * returns the number of squares a queen can move to
     */
    // O(589)
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
    // O(4)
    public int queenValue(int color, BoardProperties properties){
        return switch (color) { // 1
            case 0 -> properties.BLACK_QUEENS.size(); // 3
            case 1 -> properties.WHITE_QUEENS.size();
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * is the rank of the passed pawn. A passed pawn on rank 7 which means the pawn
     * is one move away from promoting is a lot more dangerous compared to a passed pawn on its
     * initial square. Passed pawns with higher ranks have higher priority thus they are an advantage.
     */
    // O(3,747)
    public int rankPassedPawn(int color, BoardProperties properties) {
        List<Square> passed = passPawns(color, properties); // 3,724
        int sum = 0; // 3
        for (Square s : passed) { // 8 + extra : (3)
            sum += s.rank(); // 2
        }
        return sum; // 1
    }

    /**
     * returns 1 if a rook of the same color is behind the passed pawn. If there is a rook
     * behind a passed pawn it is easier to push to pawn forward as it is always protected by the rook and
     * rook never gets in the way
     */
    // O(3,954)
    public int rookBhdPassPawn(int color, BoardProperties properties) {
        int count = 0; // 3
        List<Square> passed = passPawns(color, properties); // 3,724
        List<Square> rooks; // 1
        switch (color) { // 1
            case 0 -> rooks = properties.BLACK_ROOKS; // 3
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        for (Square s : rooks) { // 2 + extra : (3)
            for (Square s2 : passed) { // 8 + extra : (5)
                if (s.file() == s2.file()) { // 3
                    if (color == 0 && s.rank() > s2.rank()) { // 5
                        count++;
                        break;
                    } else if (color == 1 && s.rank() < s2.rank()) { // 5
                        count++;
                        break;
                    }
                }
            }
        }
        return count; // 1
    }

    /**
     * returns 1 if a given rook is on a file with pawns from both sides. Rooks on closed
     * files are considered a disadvantage as they have lower file mobility and no access to the important
     * squares of the game especially in the middlegame and endgame.
     */
    // O(403)
    public int rookClosedFile(int color, BoardProperties properties){
        int count = 0; // 3
        List<Square> rooks; // 1
        switch (color) { // 1
            case 0 -> rooks = properties.BLACK_ROOKS; // 3
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        Chessboard b =  properties.BOARD; // 3
        for (Square s : rooks) { // 2 + extra : (3)
            boolean blackPawn = false; // 3
            boolean whitePawn = false; // 3
            for (int rank = 1; rank <= 8; rank++) { // 8 + extra : (130)
                if (rank == s.rank()) continue; // 1
                Square target = Square.get(s.file(), rank); // 3
                ChessPiece p = b.read(target); // 3
                if (!blackPawn) blackPawn = p != null && p.type() == 1 && p.color() == 0;
                if (!whitePawn) whitePawn = p != null && p.type() == 1 && p.color() == 1;
                if (blackPawn && whitePawn) break;
            }
            if (blackPawn && whitePawn) count++; // 2
        }
        return count; // 1
    }

    /**
     * returns 1 if there are no pieces between to rooks of the same color and they are on the
     * same file or on the same rank. Connected rooks defend each other to create threats in the opposition
     * area because they cannot be captured by queen or king
     */
    // O(19)
    public int rookCon(int color, BoardProperties properties) {
        List<Square> rooks; // 1
        switch (color) { // 1
            case 0 -> rooks = properties.BLACK_ROOKS; // 3
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        if (rooks.size() < 2) { // 2
            return 0;
        } else if (rooks.get(0).rank() == rooks.get(1).rank() || rooks.get(0).file() == rooks.get(1).file()) { // 11
            return 1;
        }
        return 0; // 1
    }

    /**
     * returns the number of squares a rook can move to.
     */
    // O(589)
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
    // O(23)
    public int rookOnSeventh(int color, BoardProperties properties) {
        int count = 0; // 3
        int[] targetRanks = {1, 8}; // 3
        List<Square> rooks; // 1
        switch (color) { // 1
            case 0 -> rooks = properties.BLACK_ROOKS; // 3
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        for (Square s : rooks) { // 2 + extra : (3)
            if (s.rank() == targetRanks[color]) { // 3
                count++; // 1
            }
        }
        return count; // 1
    }

    /**
     * returns 1 if a given rook is on a file with no pawns from either side. Rooks are stronger
     * on open columns because they can move freely
     */
    // O(273)
    public int rookOpenFile(int color, BoardProperties properties){
        int count = 0; // 3
        List<Square> rooks; // 1
        switch (color) { // 1
            case 0 -> rooks = properties.BLACK_ROOKS; // 3
            case 1 -> rooks = properties.WHITE_ROOKS;
            default -> throw new IllegalArgumentException();
        }
        Chessboard b = properties.BOARD; // 3
        for (Square s : rooks) { // 2 + extra : (3)
            boolean isClosed = true; // 3
            for (int rank = 1; rank <= 8; rank++) { // 8 + extra : (20)
                if (rank == s.rank()) { // 1
                    continue;
                }
                Square target = Square.get(s.file(), rank); // 3
                ChessPiece p = b.read(target); // 3
                isClosed = p != null && p.type() == 1; // 5
                if (isClosed) { // 1
                    break;
                }
            }
            if (!isClosed) { // 1
                count++; // 1
            }
        }
        return count; // 1
    }

    /**
     * returns 1 if a given rook is on a file with no pawns from its own side. Rooks are
     * strong on semi-open files as well
     */
    // O(321)
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
    // O(4)
    public int rookValue(int color, BoardProperties properties){
        return switch (color) { // 1
            case 0 -> properties.BLACK_ROOKS.size(); // 3
            case 1 -> properties.WHITE_ROOKS.size();
            default -> throw new IllegalStateException("Color is not an integer between 0 and 1.");
        };
    }

    /**
     * is the number of squares in Player A’s area that cannot be protected by Player A’s pawns.
     */
    // O(1,885)
    public int weakCount(int color, BoardProperties properties) {
        return weakSquares(color, properties).size();
    }

    /**
     * returns the list of squares that are weak squares: squares in Player A’s area that cannot be protected by Player A’s pawns.
     */
    // O(1,884)
    public List<Square> weakSquares(int color, BoardProperties properties) {
        List<Square> weakSquares = new ArrayList<>(); // 3
        int strongSquare = 0; // 3
        int[][] checkingSquares = new int[3][8]; // 3
        for (int i = 0; i < checkingSquares.length; i++) { // 3 + extra : (9)
            for (int j = 0; j < checkingSquares[0].length; j++) { // 8 + extra : (24)
                if (j == 0 || j == 7) checkingSquares[i][j] = 1; // 3
                else checkingSquares[i][j] = strongSquare; // 3
            }
        }
        Chessboard b = properties.BOARD; // 3
        boolean teamIsBlack = color == 0; // 3
        for (int fileTemp = 0; fileTemp <= 7; fileTemp++) { // 7 - extra : (79)
            boolean pawnFront = false; // 3
            for (int deltaRank = 0; deltaRank <= 2; deltaRank++) { // 3 + extra : (9)
                Square squareTemp; // 1
                int weakRankTemp; // 1
                if (teamIsBlack) { // 1
                    squareTemp = Square.get((char) (97 + fileTemp), 7 - deltaRank); // 5
                    weakRankTemp = 2 + deltaRank; // 2
                } else {
                    squareTemp = Square.get((char) (97 + fileTemp), 2 + deltaRank);
                    weakRankTemp = 5 - deltaRank;
                }
                ChessPiece piece = b.read(squareTemp); // 3
                if (piece != null && piece.type() == 1 && piece.color() == color) { // 7
                    pawnFront = true; // 2
                } else if (piece != null) pawnFront = false;
                if (!pawnFront) { // 1
                    for (int jTemp = fileTemp - 1; jTemp <= fileTemp + 1; jTemp += 2) { // 2 + extra : (9)
                        if (jTemp == -1 || jTemp == 8) continue; // 3
                        Square weakSquare = Square.get((char) (97 + jTemp), 8 - weakRankTemp); // 6
                        ChessPiece pieceOnWeak = b.read(weakSquare); // 3
                        if (pieceOnWeak != null && pieceOnWeak.type() == 1 && pieceOnWeak.color() == color) continue; // 7
                        checkingSquares[deltaRank][jTemp]++; // 2
                        if (checkingSquares[deltaRank][jTemp] == 2) { // 2
                            weakSquares.add(weakSquare);
                        }
                    }
                }
            }
        }
        return weakSquares; // 1
    }

    private double evaluationFunction(Chromosome c, int color, BoardProperties properties) {
        double[] data = c.getData();
        return  evaluationFunction(data, color, properties);
    }

    private double evaluationFunction(double[] data, int color, BoardProperties properties) {
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

    public double evaluate(Chromosome c, DiceChess game) {
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
        double currentPlayerScore = evaluationFunction(c, color, properties);
        double opponentPlayerScore = evaluationFunction(c, opponent, properties);
        return currentPlayerScore - opponentPlayerScore;
    }

    public double evaluate(DiceChess game, double[] data) {
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
        double currentPlayerScore = evaluationFunction(data, color, properties);
        double opponentPlayerScore = evaluationFunction(data, opponent, properties);
        return currentPlayerScore - opponentPlayerScore;
    }
}
