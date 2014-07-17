package pixelmapper;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created:  14.07.2014 19:50.
 */
public class Field {
    private List<Tile> field;

    public Field() {
        field = new ArrayList<Tile>();
//        field.add(new Tile(0, 0, Color.green));
    }

    public void drawField(Graphics g, int startX, int startY, int tileSize) {
        for (Tile tile : field) {
            int x = tile.getX();
            int y = tile.getY();
            Color color = tile.getColor();

            g.setColor(color);
            g.fillRect((startX + x) * tileSize + 1, (startY + y) * tileSize + 1, tileSize - 1, tileSize - 1);
        }
    }

    public void writeToFile(PrintWriter out) {
        for (Tile tile : field) {
            out.println(tile.getX());
            out.println(tile.getY());
            out.println(Colors.getInstanceByData(tile.getColor()));
        }
    }

    public void loadFromFile(BufferedReader in) {
        try {
            while (in.ready()) {
                int x = Integer.parseInt(in.readLine());
                int y = Integer.parseInt(in.readLine());
                String textColor = in.readLine();
                System.out.println("Texcolor:" + textColor + ".");
                Color color = Color.white;
                try {
                    color = Colors.getInstanceByName(textColor).getColor();
                } catch (NullPointerException n) {
                    System.out.println("--NULL--");
                }

                System.out.println("LOADING:" + x + "_" + y + "_" + Colors.getInstanceByData(color) + ".");

                field.add(new Tile(x, y, color));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintTileWithColor(int x, int y, Color color) {
        Tile tile = exist(x, y);
        if (tile != null) {
            tile.setColor(color);
        } else {
            field.add(new Tile(x, y, color));
        }
    }

    private Tile exist(int x, int y) {
        for (Tile tile : field) {
            if ((tile.getX() == x) && (tile.getY() == y)) {
                return tile;
            }
        }

        return null;
    }
}
