package expecti;

public interface Evaluation
{
    // hardcoded all the evaluation weights
    final int backwardPawn = 6;
    final int bishopMob = 2;
    final int bishopOnLarge = 75;
    final int bishopPair = 10;
    final int bishopValue = 303;
    final int blockedPassedPawn = -45;
    final int blockedPawn = -47;
    final int centerPawnCount = 23;
    final int doublePawn = -15;
    final int enemyKnightOnWeak = -27;
    final int isoPawn = -14;
    final int kingAttacked = -68;
    final int kingCastled = 58;
    final int kingDefended = 25;
    final int kingPawnShield = 24;
    final int knightMob = 3;
    final int knightSupport = 28;
    final int knightPeriphery0 = -24;
    final int knightPeriphery1 = -50;
    final int knightPeriphery2 = -4;
    final int knightPeriphery3 = 48;
    final int knightValue = 261;
    final int passPawn = 85;
    final int pawnValue = 100;
    final int queenMob = 1;
    final int queenValue = 805;
    final int rankPassedPawn = -13;
    final int rookBhdPassPawn = 7;
    final int rookClosedFile = 2;
    final int rookCon = 22;
    final int rookMob = 9;
    final int rookOnSeventh = 39;
    final int rookOpenFile = 63;
    final int rookSemiOpenFile = 25;
    final int rookValue = 595;
    final int weakCount = 0;
    final int kingEaten = -2*queenValue;


    /**
     * array containing all the evaluation weights
     */
    final int[] eval = {backwardPawn, bishopMob, bishopOnLarge,bishopPair, bishopValue , blockedPassedPawn,
            blockedPawn , centerPawnCount, doublePawn,  enemyKnightOnWeak, isoPawn ,
            kingAttacked, kingCastled, kingDefended,  kingPawnShield, knightMob,
            knightSupport , knightPeriphery0 , knightPeriphery1 , knightPeriphery2,
            knightPeriphery3,  knightValue , passPawn , pawnValue , queenMob ,queenValue,
            rankPassedPawn , rookBhdPassPawn , rookClosedFile , rookCon , rookMob , rookOnSeventh,
            rookOpenFile , rookSemiOpenFile , rookValue ,  weakCount , kingEaten};

    /**
     * The getEvaluation() method returns the evaluation of the game
     * @return int representing the evaluation function result
     */
    public int getEvaluation();

    /**
     * This method returns the value of the queen, useful for calculating the value of the king
     * (multiplication of the queen value)
     * @return the evaluation value of a queen piece
     */
    public int getQueenValue();


}
