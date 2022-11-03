package utility;

import javax.swing.*;

public final class ImageIconCache {
    private static final ImageIcon[][] CHESS_ICONS = {
            {},
            { new ImageIcon("src/main/java/assets/chess/bp.png"), new ImageIcon("src/main/java/assets/chess/wp.png") },
            { new ImageIcon("src/main/java/assets/chess/bn.png"), new ImageIcon("src/main/java/assets/chess/wn.png") },
            { new ImageIcon("src/main/java/assets/chess/bb.png"), new ImageIcon("src/main/java/assets/chess/wb.png") },
            { new ImageIcon("src/main/java/assets/chess/br.png"), new ImageIcon("src/main/java/assets/chess/wr.png") },
            { new ImageIcon("src/main/java/assets/chess/bq.png"), new ImageIcon("src/main/java/assets/chess/wq.png") },
            { new ImageIcon("src/main/java/assets/chess/bk.png"), new ImageIcon("src/main/java/assets/chess/wk.png") }
    };

    public static ImageIcon getChessIcon(int type, int team) {
        return CHESS_ICONS[type][team];
    }
}
