package utility;

import game.Square;
import org.junit.jupiter.api.Test;

final class VectorTest {
    @Test
    public void testGetX() {
        Vector v = new Vector(1, 0);
        assert v.getX() == 1;
    }

    @Test
    public void testGetY() {
        Vector v = new Vector(0, 1);
        assert v.getY() == 1;
    }

    @Test
    public void testTranslate() {
        Square s = Square.get(3, 3);
        Vector v = new Vector(1, 1);
        Square t = v.translate(s);
        assert t == Square.get(4, 2);
    }

    @Test
    public void testTranslateFailure() {
        Square s = Square.get(3, 0);
        Vector v = new Vector(1, 1);
        Square t = v.translate(s);
        assert t == null;
    }
}
