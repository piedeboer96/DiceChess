package game;

import org.junit.jupiter.api.Test;

final class MovementTest {
    @Test
    public void testOrigin() {
        Square s = Square.get(0, 0);
        Movement m = new Movement(s, null);
        assert m.origin() == s;
    }

    @Test
    public void testEndpoint() {
        Square s = Square.get(0, 0);
        Movement m = new Movement(null, s);
        assert m.endpoint() == s;
    }
}
