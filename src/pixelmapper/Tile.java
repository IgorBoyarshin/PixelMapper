package pixelmapper;

import java.awt.*;

/**
 * Created:  14.07.2014 19:51.
 */
public class Tile {
    private Color color;
    private final int x;
    private final int y;

    public Tile(int x, int y) {
        this.color = Color.gray;
        this.x = x;
        this.y = y;
    }

    public Tile(int x, int y, Color color) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
