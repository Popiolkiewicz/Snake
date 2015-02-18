import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;


public class RenderPanel extends JPanel
{
	
	private static final long serialVersionUID = -1015558200090253783L;
	
	/**
	 * Szerokoœæ pojedynczego pola.
	 */
	private static final int sP = 20;
	/**
	 * Wysokoœæ pojedynczego pola.
	 */
	private static final int wP = 20;
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		
		for(int i = 0; i<=600; i+=sP)
			g.drawLine(i, 0, i, 600);
		
		for(int j = 0; j<=600; j+=wP)
			g.drawLine(0, j , 600, j);
		
		SimpleSnake snake = SimpleSnake.snake;
		
		for(Point x: snake.segmenty)
		{
			g.setColor(Color.GREEN);
			g.fillRect((int)x.getX() * sP, (int)x.getY() * wP, sP, wP);
		}
		
		Point cherry = SimpleSnake.cherry;
		
		g.setColor(Color.RED);
		g.fillRect((int)cherry.getX() * sP, (int)cherry.getY() * wP, sP, wP);
	}
}
