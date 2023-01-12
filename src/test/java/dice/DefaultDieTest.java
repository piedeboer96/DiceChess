package dice;

import org.junit.jupiter.api.Test;

final class DefaultDieTest {
    @Test
    public void testRoll() {
        Die d = new DefaultDie();
        int[] results = new int[7];
        for (int rolls = 0; rolls < 100; rolls++) {
            int roll = d.roll();
            results[roll]++;
        }
        assert results[0] == 0;
        for (int side = 1; side < 7; side++) {
            assert results[side] > 0;
        }
    }
}
