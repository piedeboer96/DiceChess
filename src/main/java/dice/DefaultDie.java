package dice;

import java.util.Random;

/**
 * Defines a default six-sided die implementation.
 **/
public final class DefaultDie implements Die {
    private final Random RND = new Random();

    @Override
    public int roll() {
        return RND.nextInt(1, 7);
    }
}
