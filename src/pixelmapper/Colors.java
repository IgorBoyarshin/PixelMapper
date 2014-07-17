package pixelmapper;

import java.awt.*;

/**
 * Created:  14.07.2014 19:56.
 */
public enum Colors {
    WHITE(Color.white), BLACK(Color.black), ORANGE(Color.orange),
    YELLOW(Color.yellow), GREEN(Color.green), BLUE(Color.blue),
    MAGENTA(Color.magenta), CYAN(Color.cyan);

    private final Color color;


    Colors(Color color) {
        this.color = color;
    }

    // TODO: redo
    public static Colors getInstanceByData(Color color) {
        if (color.equals(Color.white)) {
            return WHITE;
        } else if (color.equals(Color.black)) {
            return BLACK;
        } else if (color.equals(Color.orange)) {
            return ORANGE;
        } else if (color.equals(Color.yellow)) {
            return YELLOW;
        } else if (color.equals(Color.green)) {
            return GREEN;
        } else if (color.equals(Color.magenta)) {
            return MAGENTA;
        } else if (color.equals(Color.cyan)) {
            return CYAN;
        } else if (color.equals(Color.blue)) {
            return BLUE;
        } else {
            return null;
        }
    }

    public static Colors getInstanceByName(String color) {
        return Colors.valueOf(color);
    }

    public Color getColor() {
        return color;
    }
}
