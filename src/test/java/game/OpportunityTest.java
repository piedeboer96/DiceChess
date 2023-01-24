package game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

final class OpportunityTest {
    @Test
    public void testOwner() {
        ChessPiece p = ChessPiece.get(1, 0);
        Opportunity o = new Opportunity(p, null, null);
        assert o.owner() == p;
    }

    @Test
    public void testOrigin() {
        Square s = Square.get(0, 0);
        Opportunity o = new Opportunity(null, s, null);
        assert o.origin() == s;
    }

    @Test
    public void testOptions() {
        List<Square> l = new ArrayList<>();
        Opportunity o = new Opportunity(null, null, l);
        assert o.options() == l;
    }

    @Test
    public void testSize() {
        Square s = Square.get(0, 0);
        List<Square> l = new ArrayList<>(1);
        l.add(s);
        Opportunity o = new Opportunity(null, null, l);
        assert o.size() == 1;
    }

    @Test
    public void testSelect() {
        Square s0 = Square.get(0, 0);
        Square s1 = Square.get(7, 7);
        List<Square> l = new ArrayList<>(1);
        l.add(s1);
        Opportunity o = new Opportunity(null, s0, l);
        Movement m = o.select(0);
        assert m.origin() == s0;
        assert m.endpoint() == s1;
    }
}
