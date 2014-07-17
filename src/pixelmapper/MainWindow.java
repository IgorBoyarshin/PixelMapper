package pixelmapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * Created:  14.07.2014 19:29.
 */
public class MainWindow extends JFrame {
    private DrawCanvas canvas;
    private final int startWidth = 500;
    private final int startHeight = 500;
    private int width = startWidth;
    private int height = startHeight;

    private Field field;
    private int tileSize = 50;
    private Color cursorColor = Color.red;

    private Colors currentColor;
    private int cursorOffsetX = 3;
    private int cursorOffsetY = 3;
    private int xOriginOffset = cursorOffsetX;
    private int yOriginOffset = cursorOffsetY;
    private int currentX;
    private int currentY;

    public MainWindow() {
        currentColor = Colors.BLACK;
        currentX = 0;
        currentY = 0;
        field = new Field();
        loadFromFile("default.fld");

        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(width, height));

        Container cp = getContentPane();
        cp.add(canvas);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        this.pack();              // Either pack() the components; or setSize()
        this.setTitle("PixelMap");  // this JFrame sets the title
        this.setVisible(true);    // this JFrame show
//        this.setResizable(false);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                processInput(evt.getKeyCode());
            }
        });
    }

    private void changeCurrentColorToOpposite() {
        if (currentColor.equals(Colors.BLACK)) {
            currentColor = Colors.WHITE;
        } else {
            currentColor = Colors.BLACK;
        }
    }

    private void changeCurrentColorByIndex(int index) {
        switch (index) {
            case 1:
                currentColor = Colors.WHITE;
                break;
            case 2:
                currentColor = Colors.BLACK;
                break;
            case 3:
                currentColor = Colors.ORANGE;
                break;
            case 4:
                currentColor = Colors.YELLOW;
                break;
            case 5:
                currentColor = Colors.GREEN;
                break;
            case 6:
                currentColor = Colors.BLUE;
                break;
            case 7:
                currentColor = Colors.MAGENTA;
                break;
            case 8:
                currentColor = Colors.CYAN;
                break;
        }
    }

    private void processInput(final int code) {
        switch (code) {
            case KeyEvent.VK_LEFT:
                System.out.println("Keyboard: Left");
                currentX--;
                if (cursorOffsetX > 1) {
                    cursorOffsetX--;
                } else {
                    xOriginOffset++;
                }
//                repaint();
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Keyboard: Right");
                currentX++;
                if (cursorOffsetX < this.getWidth() / tileSize - 2) {
                    cursorOffsetX++;
                } else {
                    xOriginOffset--;
                }
//                repaint();
                break;
            case KeyEvent.VK_UP:
                System.out.println("Keyboard: Up");
                currentY--;
                if (cursorOffsetY > 1) {
                    cursorOffsetY--;
                } else {
                    yOriginOffset++;
                }
//                repaint();
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Keyboard: Down");
                currentY++;
                if (cursorOffsetY < this.getHeight() / tileSize - 2) {
                    cursorOffsetY++;
                } else {
                    yOriginOffset--;
                }
//                repaint();
                break;
            case KeyEvent.VK_1:
                changeCurrentColorByIndex(1);
                break;
            case KeyEvent.VK_2:
                changeCurrentColorByIndex(2);
                break;
            case KeyEvent.VK_3:
                changeCurrentColorByIndex(3);
                break;
            case KeyEvent.VK_4:
                changeCurrentColorByIndex(4);
                break;
            case KeyEvent.VK_5:
                changeCurrentColorByIndex(5);
                break;
            case KeyEvent.VK_6:
                changeCurrentColorByIndex(6);
                break;
            case KeyEvent.VK_7:
                changeCurrentColorByIndex(7);
                break;
            case KeyEvent.VK_8:
                changeCurrentColorByIndex(8);
                break;
            case KeyEvent.VK_Q:
                changeCurrentColorToOpposite();
                break;
            case KeyEvent.VK_SPACE:
                field.paintTileWithColor(currentX, currentY, currentColor.getColor());
                break;
            case KeyEvent.VK_C: // clear the field
                field = new Field();
                break;
            case KeyEvent.VK_PLUS:
            case  KeyEvent.VK_EQUALS:
                if (tileSize < 80) {
                    tileSize += 5;
                }
                break;
            case KeyEvent.VK_MINUS:
                if (tileSize > 20) {
                    tileSize -= 5;
                }
                break;
//            case KeyEvent.VK_O: // set cursor to origin
//                currentX = 0;
//                currentY = 0;
//                break;
            case KeyEvent.VK_ESCAPE:
                saveToFile("default.fld");
                setVisible(false); //you can't see me!
                dispose(); //Destroy the JFrame object
                break;
        }
        repaint();
    }

    private void loadFromFile(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            field.loadFromFile(in);
            in.close();
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            System.out.println("LOADING: COULD NOT FIND FILE '" + path + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(String path) {
        try {
            File file = new File(path);
            file.delete();

            PrintWriter out = new PrintWriter(new FileWriter(path));
            field.writeToFile(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            setBackground(Color.lightGray);

            drawBlankField(g);
            drawField(g);
            drawCursor(g);
            drawCursorPosition(g);
            drawMarkers(g);
        }
    }

    private void drawMarkers(Graphics g) {
        g.setColor(currentColor.getColor());
        g.fillRect(90, 10, 12, 12);
    }

    private void drawCursorPosition(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Monospaced", Font.PLAIN, 15));
        g.drawString("X:" + currentX + " Y:" + currentY, 5, 20);
    }

    private void drawCursor(Graphics g) {
        g.setColor(cursorColor);

//        g.drawLine(cursorOffsetX * tileSize, cursorOffsetY * tileSize,
//                (cursorOffsetX + 1) * tileSize, (cursorOffsetY + 1) * tileSize);
        g.fillRect(cursorOffsetX * tileSize - 2, cursorOffsetY * tileSize - 2, tileSize + 4, 4);
        g.fillRect((cursorOffsetX + 1) * tileSize - 2, cursorOffsetY * tileSize - 2, 4, tileSize + 4);
        g.fillRect(cursorOffsetX * tileSize - 2, cursorOffsetY * tileSize - 2, 4, tileSize + 4);
        g.fillRect(cursorOffsetX * tileSize - 2, (cursorOffsetY + 1) * tileSize - 2, tileSize + 4, 4);

    }

    private void drawField(Graphics g) {
        field.drawField(g, xOriginOffset, yOriginOffset, tileSize);
    }

    private void drawBlankField(Graphics g) {
        g.setColor(Color.gray);

        for (int i = 0; i <= this.getWidth() / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, this.getHeight());
        }

        for (int i = 0; i <= this.getHeight() / tileSize; i++) {
            g.drawLine(0, i * tileSize, this.getWidth(), i * tileSize);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow();
        });
    }
}
