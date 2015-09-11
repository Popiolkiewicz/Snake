import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener {

    private static char LEFT = 'a', RIGHT = 'd', DOWN = 's', UP = 'w', direction = DOWN;
    private static Point cherry;
    private static Snake snake;
    private static Random random = new Random();
    private ArrayList<Point> sections = new ArrayList<Point>();
    private JFrame jFrame;
    private RenderPanel renderPanel;
    private Timer timer = new Timer(10, this);
    private Point head, beforeHead;
    private int speedInterval = 0;

    public Snake() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame = new JFrame("Snake");
        jFrame.setSize(605, 625);
        jFrame.setLocation(dimension.width / 2 - 300, dimension.height / 2 - 300);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(renderPanel = new RenderPanel());
        jFrame.addKeyListener(this);

        sections.add(new Point(3, 2));
        head = new Point(3, 3);
        sections.add(head);
        throwCherry(sections);
    }

    public static void restart(ArrayList<Point> sections) {
        sections.removeAll(sections);
        sections.add(new Point(3, 2));
        snake.head = new Point(3, 3);
        sections.add(snake.head);
        direction = DOWN;
        snake.timer.start();
    }

    public static void throwCherry(ArrayList<Point> sections) {
        cherry = new Point(random.nextInt(30), random.nextInt(30));
        for (Point x : sections)
            if (cherry.getX() == x.getX() && cherry.getY() == x.getY())
                throwCherry(sections);
    }

    public static void main(String [] args) {
        snake = new Snake();
        JOptionPane.showMessageDialog(null, "\t\tWitaj w grze SNAKE!"
            + "\nU¿ywaj nastêpuj¹cych klawiszy aby zmieniæ kierunek ruchu wê¿a:"
            + "\n W - do góry"
            + "\n S - w dó³ "
            + "\n A - w lewo"
            + "\n D - w prawo."
            + "\n POWODZENIA!");
        snake.timer.start();
    }

    public void actionPerformed(ActionEvent arg0) {
        renderPanel.repaint();
        speedInterval++;
        if (speedInterval % 4 == 0) {
            move();
            checkCherry();
            checkIfGameEnds();
        }
    }

    private void move() {
        if (direction == DOWN)
            beforeHead = new Point((int) head.getX(), (int) head.getY() + 1);
        if (direction == UP)
            beforeHead = new Point((int) head.getX(), (int) head.getY() - 1);
        if (direction == LEFT)
            beforeHead = new Point((int) head.getX() - 1, (int) head.getY());
        if (direction == RIGHT)
            beforeHead = new Point((int) head.getX() + 1, (int) head.getY());
        sections.add(beforeHead);
        Point vicarial = head;
        head = beforeHead;
        beforeHead = vicarial;
    }

    private void checkCherry() {
        if (head.getX() == cherry.getX() && head.getY() == cherry.getY())
            throwCherry(sections);
        else
            sections.remove(0);
    }

    private void checkIfGameEnds() {
        for (Point x : sections)
            if (head.getX() == x.getX() && head.getY() == x.getY() && x != head
                || head.getX() >= 30 || head.getX() < 0 || head.getY() >= 30 || head.getY() < 0) {
                timer.stop();
                JOptionPane.showMessageDialog(null, "\t\tGra skoñczona! Naciœnij OK aby spróbowaæ ponownie.");
                restart(sections);
                break;
            }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A && direction != RIGHT)
            direction = LEFT;
        if (e.getKeyCode() == KeyEvent.VK_S && direction != UP)
            direction = DOWN;
        if (e.getKeyCode() == KeyEvent.VK_D && direction != LEFT)
            direction = RIGHT;
        if (e.getKeyCode() == KeyEvent.VK_W && direction != DOWN)
            direction = UP;
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static Snake getInstance() {
        return snake;
    }

    public static Point getCherry() {
        return cherry;
    }

    public ArrayList<Point> getSections() {
        return sections;
    }
}
