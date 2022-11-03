package phase2version.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Provides image creation from unicode character code points.
 **/
public final class ImageFromCharacter {
    /**
     * Produces the image of a unicode character point.
     * @param cp The code point of a unicode character.
     * @param c The color the image representation of the character should have.
     * @param f The font the image representation of the character should have.
     * @return The image representation of the character denoted in the code point.
     * @exception NullPointerException Thrown when null is provided as a color or as a font.
     **/
    public static BufferedImage produce(int cp, Color c, Font f) {
        if      (c == null) { throw new NullPointerException("Graphics color is null!"); }
        else if (f == null) { throw new NullPointerException("Graphics font is null!"); }

        String content = Character.toString(cp);
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(f);
        FontMetrics metrics = g2d.getFontMetrics();
        int imageWidth = metrics.stringWidth(content);
        int imageHeight = metrics.getHeight();
        g2d.dispose();

        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setColor(c);
        g2d.setFont(f);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.drawString(content, 0, metrics.getAscent());
        g2d.dispose();
        return image;
    }
}
