package phase2version.solutions.chess.utility;

import phase2version.framework.chess.PermissionManager;

public final class CastlePermissions implements PermissionManager {
    private final boolean[][] PERMISSIONS = {
            { true, true },
            { true, true }
    };

    /**
     * Creates new castle permissions, where both teams have permissions for both castle sides.
     **/
    public CastlePermissions() {}

    private void clear() {
        PERMISSIONS[0][0] = false;
        PERMISSIONS[0][1] = false;
        PERMISSIONS[1][0] = false;
        PERMISSIONS[1][1] = false;
    }


    @Override
    public boolean has(int team, int side) {
        if (team != 0 && team != 1) { throw new IllegalArgumentException("Team number is not valid!"); }
        else if (side != 0 && side != 1) { throw new IllegalArgumentException("Castle side number is not valid!"); }
        else { return PERMISSIONS[team][side]; }
    }

    @Override
    public void recover(String snapshot) {
        if      (snapshot          == null) { throw new NullPointerException("Snapshot to restore state from is null!"); }
        else if (snapshot.length() ==    0) { throw new IllegalArgumentException("Snapshot is empty!"); }
        else if (snapshot.length()  >    4) { throw new IllegalArgumentException("Snapshot format is not valid!"); }

        clear();
        if (snapshot.charAt(0) == '-') { return; }
        for (int entry = 0; entry < snapshot.length(); entry++) {
            char c = snapshot.charAt(entry);
            switch (c) {
                case 'k' -> {
                    if (PERMISSIONS[0][0]) { throw new IllegalArgumentException("Snapshot contains duplicates!"); }
                    else                   { PERMISSIONS[0][0] = true; }
                }
                case 'K' -> {
                    if (PERMISSIONS[1][0]) { throw new IllegalArgumentException("Snapshot contains duplicates!"); }
                    else                   { PERMISSIONS[1][0] = true; }
                }
                case 'q' -> {
                    if (PERMISSIONS[0][1]) { throw new IllegalArgumentException("Snapshot contains duplicates!"); }
                    else                   { PERMISSIONS[0][1] = true; }
                }
                case 'Q' -> {
                    if (PERMISSIONS[1][1]) { throw new IllegalArgumentException("Snapshot contains duplicates!"); }
                    else                   { PERMISSIONS[1][1] = true; }
                }
                default  -> throw new IllegalArgumentException("Notation " + c + " is urecognized!");
            }
        }
    }

    @Override
    public void set(int team, int side, boolean b) {
        if (team != 0 && team != 1) { throw new IllegalArgumentException("Team number is not valid!"); }
        else if (side != 0 && side != 1) { throw new IllegalArgumentException("Castle side number is not valid!"); }
        PERMISSIONS[team][side] = b;
    }

    @Override
    public String snapshot() {
        StringBuilder sb = new StringBuilder();
        if (PERMISSIONS[1][0]) { sb.append('K'); }
        if (PERMISSIONS[1][1]) { sb.append('Q'); }
        if (PERMISSIONS[0][0]) { sb.append('k'); }
        if (PERMISSIONS[0][1]) { sb.append('q'); }
        if (sb.isEmpty())      { sb.append('-'); }
        return sb.toString();
    }
}
