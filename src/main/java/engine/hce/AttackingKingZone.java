package engine.hce;

public class AttackingKingZone {
    int kingZoneDistance = 2;
    private int attackingPiecesCount = 0;
    private int valueOfAttacks;

    private int[] attackWeight = {0, 50, 75, 88, 94, 97, 99};

    public void setAttackingPiecesCount(int attacking) {
        attackingPiecesCount += attacking;
    }

    public int getAttackingPiecesCount() {
        return attackingPiecesCount;
    }



    public double getAttackScore() {
        return valueOfAttacks * attackWeight[attackingPiecesCount] / 100;
    }
}
