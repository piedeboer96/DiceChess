package die.interfaces;

/**
 * Provides a generic die.
 **/
public interface IDie
{
    /**
     * Rolls the die.
     * @param team The team for which the die is thrown.
     * @return A fen character representing the type of the chess piece from the team that is allowed to move.
     **/
    char roll(int team);
}
