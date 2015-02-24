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

public class Snake implements ActionListener, KeyListener
{
	ArrayList<Point> segmentySnake = new ArrayList<Point>();
	
	public JFrame jFrame;
	
	public RenderPanel renderPanel;
	
	public static Snake snake;
	
	public Timer timer = new Timer(10, this);

	static char LEFT = 'a', RIGHT = 'd', DOWN = 's', UP = 'w', direction = DOWN;
	
	int ruch = 0;
	
	Point head, beforeHead;
	
	static Point cherry;
	
	Random random = new Random();
	
	public Snake()
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		jFrame = new JFrame("Snake");
		jFrame.setSize(605,625);
		jFrame.setLocation(dimension.width/2 - 300, dimension.height/2 - 300);
		jFrame.setResizable(false);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.add(renderPanel = new RenderPanel());
		jFrame.addKeyListener(this);
		
		segmentySnake.add(new Point(3, 2));
		head = new Point(3, 3);
		segmentySnake.add(head);
		throwCherry(random, segmentySnake);

		timer.start();
	}

	public static void restartGry(ArrayList<Point> vSegmenty)
	{
		vSegmenty.removeAll(vSegmenty);
		vSegmenty.add(new Point(3, 2));
		snake.head = new Point(3, 3);
		vSegmenty.add(snake.head);
		
		snake.timer.start();
		direction = DOWN;
	}
	
	public static void throwCherry(Random vRandom, ArrayList<Point> vSegmenty)
	{
		cherry = new Point(vRandom.nextInt(30), vRandom.nextInt(30));
		for(Point x : vSegmenty)	
			if(cherry.getX() == x.getX() && cherry.getY() == x.getY())
				throwCherry(vRandom, vSegmenty);
	}
	
	public static void main(String[] args)
	{
		snake = new Snake();
		
		snake.timer.stop();
		JOptionPane.showMessageDialog(null, "\t\tWitaj w grze SNAKE!"
				+ "\nU¿ywaj nastêpuj¹cych klawiszy aby zmieniæ kierunek ruchu wê¿a:"
				+ "\n W - do góry"
				+ "\n S - w dó³ "
				+ "\n A - w lewo"
				+ "\n D - w prawo."
				+ "\n POWODZENIA!");
		snake.timer.start();
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		renderPanel.repaint();
		ruch++;
		
		if(ruch % 4 == 0)
		{
			if(direction == DOWN)
				beforeHead = new Point((int)head.getX(), (int)head.getY() + 1);
			if(direction == UP)
				beforeHead = new Point((int)head.getX(), (int)head.getY() - 1);
			if(direction == LEFT)
				beforeHead = new Point((int)head.getX() - 1, (int)head.getY());
			if(direction == RIGHT)
				beforeHead = new Point((int)head.getX() + 1, (int)head.getY());
			segmentySnake.add(beforeHead);
			Point zastepczy = head;
			head = beforeHead;
			beforeHead = zastepczy;
			
			if(head.getX() == cherry.getX() && head.getY() == cherry.getY())
			{
				throwCherry(random, segmentySnake);
			}
			else
			{
			segmentySnake.remove(0);
			}

			for(Point x: segmentySnake)
				if(head.getX() == x.getX() && head.getY() == x.getY() && x != head
						|| head.getX() >=30 || head.getX() < 0 || head.getY() >=30 || head.getY() < 0 )
				{
					timer.stop();
					JOptionPane.showMessageDialog(null, "\t\tGra skoñczona! Naciœnij OK aby spróbowaæ ponownie.");
					restartGry(segmentySnake);
					break;
				}
		}
	}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_A && direction!= RIGHT)
			direction = LEFT;
		if(e.getKeyCode() == KeyEvent.VK_S && direction!= UP)
			direction = DOWN;
		if(e.getKeyCode() == KeyEvent.VK_D && direction!= LEFT)
			direction = RIGHT;
		if(e.getKeyCode() == KeyEvent.VK_W && direction!= DOWN)
			direction = UP;
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
