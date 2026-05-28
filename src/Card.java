import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card {
    private String suit;
    private String val;
    private String fileName;
    private BufferedImage image;
    private Rectangle hitbox;
    private Boolean showHitbox;
    private int value;

    public Card(String s, String v) {
        suit = s;
        val = v;
        fileName = "card_" + suit + "_" + val + ".png";
        this.image = readImage();
        this.hitbox = new Rectangle(-10, -10, image.getWidth(), image.getHeight());
        showHitbox = false;
        if (v.equals("J")) {
            value = 15;
        } else if (v.equals("Q")) {
            value = 16;
        } else if (v.equals("K")) {
            value = 17;
        } else if (v.equals("A")) {
            value = 1;
        } else if (v.equals("10")) {
            value = 10;
         } else {
            value = Integer.parseInt(v.substring(1));
        }
    }

    public int getValue() {
        return value;
    }

    public Boolean getShowHitbox() {
        return showHitbox;
    }

    public void setShowHitbox(Boolean showHitbox) {
        this.showHitbox = showHitbox;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String toString() {
        return val + " of " + suit;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage readImage() {
        try {
            BufferedImage image;
            image = ImageIO.read(new File("images/" + fileName));
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}
