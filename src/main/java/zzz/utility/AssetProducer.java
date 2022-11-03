package zzz.utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class AssetProducer {
    public static void main(String[] args) {
        String   chessAssetDirectory = "src/main/java/assets/chess";
        String   chessAssetType      = "png";
        String[] chessAssetNames     = { "bp", "bn", "bb", "br", "bq", "bk", "wp", "wn", "wb", "wr", "wq", "wk" };
        int[]    chessCharacterCPs   = { 9823, 9822, 9821, 9820, 9819, 9818, 9817, 9816, 9815, 9814, 9813, 9812 };
        Font     chessAssetFont      = new Font("FreeSerif", Font.PLAIN, 360);
        Color    chessAssetColor     = Color.BLACK;

        for (int i = 0; i < 12; i++) {
            try {
                BufferedImage image = ImageFromCharacter.produce(chessCharacterCPs[i], chessAssetColor, chessAssetFont);
                String path = chessAssetDirectory + '/' + chessAssetNames[i] + '.' + chessAssetType;
                ImageIO.write(image, chessAssetType, new File(path));
            } catch (IOException ignored) { System.out.println("I crashed!");}
        }
    }
}
