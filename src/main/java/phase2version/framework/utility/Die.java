package phase2version.framework.utility;

public interface Die {
    /**
     * Gets the result of the roll.
     * @return An integer between 1 and 6.
     **/
    int result();

    /**
     * Rolls the die.
     **/
    void roll();
}
