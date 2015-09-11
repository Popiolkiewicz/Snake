import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {

    private static final long serialVersionUID = -1015558200090253783L;
    private static final int sP = 20;
    private static final int wP = 20;

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        redrawPanel(graphics);
        redrawSnake(graphics);
        redrawCherry(graphics);
    }

    private void redrawPanel(Graphics graphics) {
        graphics.setColor(Color.LIGHT_GRAY);
        for (int i = 0 ; i <= 600 ; i += sP)
            graphics.drawLine(i, 0, i, 600);
        for (int j = 0 ; j <= 600 ; j += wP)
            graphics.drawLine(0, j, 600, j);
    }

    private void redrawSnake(Graphics graphics) {
        Snake snake = Snake.getInstance();
        for (Point x : snake.getSections()) {
            graphics.setColor(Color.GREEN);
            graphics.fillRect((int) x.getX() * sP, (int) x.getY() * wP, sP, wP);
        }
    }

    private void redrawCherry(Graphics graphics) {
        Point cherry = Snake.getCherry();
        graphics.setColor(Color.RED);
        graphics.fillRect((int) cherry.getX() * sP, (int) cherry.getY() * wP, sP, wP);
    }
}
