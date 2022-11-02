package utility;

import javax.swing.*;

public final class ImageIconCache {
    private static final ImageIcon[][] CHESS_ICONS = {
            {},
            { new ImageIcon("src/main/assets/chess/bp.png"), new ImageIcon("src/main/assets/chess/wp.png") },
            { new ImageIcon("src/main/assets/chess/bn.png"), new ImageIcon("src/main/assets/chess/wn.png") },
            { new ImageIcon("src/main/assets/chess/bb.png"), new ImageIcon("src/main/assets/chess/wb.png") },
            { new ImageIcon("src/main/assets/chess/br.png"), new ImageIcon("src/main/assets/chess/wr.png") },
            { new ImageIcon("src/main/assets/chess/bq.png"), new ImageIcon("src/main/assets/chess/wq.png") },
            { new ImageIcon("src/main/assets/chess/bk.png"), new ImageIcon("src/main/assets/chess/wk.png") }
    };

    public static ImageIcon getChessIcon(int type, int team) {
        return CHESS_ICONS[type][team];
    }
}
