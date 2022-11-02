package solutions.chess.utility;

import org.junit.jupiter.api.Test;
import utility.Exam;

public final class CastlePermissionsTest implements Exam {
    @Override @Test
    public void take() {
        testHas();
        testSet();
        testRecover();
        testSnapshot();
    }

    @Test
    public void testHas() {
        CastlePermissions permissions = new CastlePermissions();
        assert permissions.has(0, 0); assert permissions.has(0, 1);
        assert permissions.has(1, 0); assert permissions.has(1, 1);
    }

    @Test
    public void testRecover() {
        CastlePermissions permissions = new CastlePermissions();
        try { permissions.recover(null); assert false; }
        catch (NullPointerException ignored) {}

        try { permissions.recover(""); assert false; }
        catch (IllegalArgumentException ignored) {}

        try { permissions.recover("kk"); assert false; }
        catch (IllegalArgumentException ignored) {}

        try { permissions.recover("KK"); assert false; }
        catch (IllegalArgumentException ignored) {}

        try { permissions.recover("qq"); assert false; }
        catch (IllegalArgumentException ignored) {}

        try { permissions.recover("QQ"); assert false; }
        catch (IllegalArgumentException ignored) {}

        try { permissions.recover("KQkq-"); assert false; }
        catch (IllegalArgumentException ignored) {}

        permissions.recover("-");
        assert !permissions.has(0, 0); assert !permissions.has(0, 1);
        assert !permissions.has(1, 0); assert !permissions.has(1, 1);

        permissions.recover("k");
        assert  permissions.has(0, 0); assert !permissions.has(0, 1);
        assert !permissions.has(1, 0); assert !permissions.has(1, 1);

        permissions.recover("K");
        assert !permissions.has(0, 0); assert !permissions.has(0, 1);
        assert  permissions.has(1, 0); assert !permissions.has(1, 1);

        permissions.recover("q");
        assert !permissions.has(0, 0); assert  permissions.has(0, 1);
        assert !permissions.has(1, 0); assert !permissions.has(1, 1);

        permissions.recover("Q");
        assert !permissions.has(0, 0); assert !permissions.has(0, 1);
        assert !permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.recover("Kk");
        assert  permissions.has(0, 0); assert !permissions.has(0, 1);
        assert  permissions.has(1, 0); assert !permissions.has(1, 1);

        permissions.recover("Kq");
        assert !permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert !permissions.has(1, 1);

        permissions.recover("KQ");
        assert !permissions.has(0, 0); assert !permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.recover("kq");
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert !permissions.has(1, 0); assert !permissions.has(1, 1);

        permissions.recover("Qkq");
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert !permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.recover("KQq");
        assert !permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.recover("KQk");
        assert  permissions.has(0, 0); assert !permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.recover("Kkq");
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert !permissions.has(1, 1);

        permissions.recover("KQkq");
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);
    }

    @Test
    public void testSet() {
        CastlePermissions permissions = new CastlePermissions();
        permissions.set(0, 0, false);
        assert !permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.set(0, 0, true);
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.set(0, 1, false);
        assert  permissions.has(0, 0); assert !permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.set(0, 1, true);
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.set(1, 0, false);
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert !permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.set(1, 0, true);
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);

        permissions.set(1, 1, false);
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert !permissions.has(1, 1);

        permissions.set(1, 1, true);
        assert  permissions.has(0, 0); assert  permissions.has(0, 1);
        assert  permissions.has(1, 0); assert  permissions.has(1, 1);
    }

    @Test
    public void testSnapshot() {
        CastlePermissions permissions = new CastlePermissions();
        permissions.recover("-");
        assert permissions.snapshot().equals("-");

        permissions.recover("k");
        assert permissions.snapshot().equals("k");

        permissions.recover("K");
        assert permissions.snapshot().equals("K");

        permissions.recover("q");
        assert permissions.snapshot().equals("q");

        permissions.recover("Q");
        assert permissions.snapshot().equals("Q");

        permissions.recover("Kk");
        assert permissions.snapshot().equals("Kk");

        permissions.recover("Kq");
        assert permissions.snapshot().equals("Kq");

        permissions.recover("KQ");
        assert permissions.snapshot().equals("KQ");

        permissions.recover("kq");
        assert permissions.snapshot().equals("kq");

        permissions.recover("Qkq");
        assert permissions.snapshot().equals("Qkq");

        permissions.recover("KQq");
        assert permissions.snapshot().equals("KQq");

        permissions.recover("KQk");
        assert permissions.snapshot().equals("KQk");

        permissions.recover("Kkq");
        assert permissions.snapshot().equals("Kkq");

        permissions.recover("KQkq");
        assert permissions.snapshot().equals("KQkq");
    }
}
