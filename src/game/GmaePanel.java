package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.lang.annotation.Target;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.Snake;

public class GmaePanel extends JPanel implements Runnable, KeyListener {


public static final int WIDTH=400;
public static final int HEIGHT=400;
private	Graphics2D g2d;
	private BufferedImage image;
  private Thread thread;
  private boolean running;
  private long tarketTime;
  private final int SIZE=10;
  

private int dx,dy;
private boolean up,right,down,left,start;


private Snake head,apple;
private ArrayList<Snake> snake;
private int score,level;
private boolean gameover;
     public GmaePanel()
     {
    	 setPreferredSize(new Dimension(WIDTH,HEIGHT));
    	 setFocusable(true);
    	 requestFocus();
    	 addKeyListener(this);
    	 
     }
	@Override
	
	public void addNotify()
	{
		super.addNotify();
		thread =new Thread(this);
		thread.start();
	}
	
	private void setFPS(int fps)
	{
		tarketTime=1000/fps;
	}
	public void run() {
		// TODO Auto-generated method stub

		
		if(running)
			return;
		init();
		long startTime;
		long elapsed;
		long wait;
		while(running)
		{
			startTime=System.nanoTime();
			
			update();
			requestRender();
			elapsed=System.nanoTime()-startTime;
			wait=tarketTime-elapsed/100000000;
			if(wait>0)
			{
				try {
					
					thread.sleep(wait);
					
				} catch (Exception e) {
					e.getStackTrace();// TODO: handle exception
				}
			}
			
			
		}
		
		
		
	}
	private void update() {
		// TODO Auto-generated method stub
		//up=true;
		
	if(gameover)
		{
			if(start)
			{
				setUplevel();
			}
			
		}
	if(up&&dy==00)
		{
			dy=-SIZE;
			dx=0;
		}
		if(down&&dy==00)
		{
			dy=SIZE;
			dx=0;
		}
		if(left&&dx==00)
		{
			dy=0;
			dx=-SIZE;
		}
		if(right&&dx==00&&dy!=0)
		{
			dy=0;
			dx=SIZE;
		}
		
		if(dx!=0||dy!=0)
		{ 
			for (int i = snake.size()-1; i>0; i--) {
				
				snake.get(i).setPosition(snake.get(i-1).getX(), snake.get(i-1).getY());
				
			}
			
					head.move(dx, dy);
			
			
		}
		
		
		for(Snake e:snake)
		{
			if(e.isCollsion(head))
			{
				gameover=true;
				break;
			}
		}
		if(apple.isCollsion(head))
		{
			score++;
			setApple();
			Snake e=new Snake(SIZE);
			e.setPosition(-100,-100);
			snake.add(e);
			if(score%10==10)
			{
				level++;
				if(level>10) 
					level=10;
				setFPS(level*10);
			}

		}
			
		
		if(head.getX()<0) head.setX(WIDTH);
		if(head.getY()<0) head.setY(HEIGHT);
		if(head.getX()>WIDTH) head.setX(0);
		if(head.getY()>HEIGHT) head.setY(0);
		
		
	}
	private void requestRender() {
		// TODO Auto-generated method stub
		render(g2d);
		
		Graphics g=getGraphics();
		g.drawImage(image,0,0,null);
		g.dispose();
		
		
	}
	
	public void render(Graphics2D g2d)
	{
		g2d.clearRect(0, 0, WIDTH, HEIGHT);
		g2d.setColor(Color.GREEN);
		for(Snake e:snake)
		{
			e.render(g2d);
		}
		
		g2d.setColor( Color.red);
		apple.render(g2d);
		
		if(gameover)
		{
			g2d.drawString("", 150, 200);
				
		}
		g2d.setColor(Color.WHITE);
		g2d.drawString("score:"+score+"level: "+level, 10, 10);
		if(dx==0&&dy==0)
		{
			g2d.drawString("READY!!", 150, 200);
				
		}
		
	}
	private void init() {
		// TODO Auto-generated method stub
		image =new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g2d=image.createGraphics();
		running=true; 

		gameover=false;
		setUplevel();
		}
private void setUplevel()
{
	snake=new ArrayList<Snake>();
	gameover=false;
	head=new Snake(SIZE);
	head.setPosition(WIDTH/2, HEIGHT/2 );
	snake.add(head);
	for (int i = 0; i < 3; i++) {
		Snake e=new Snake(SIZE);
		e.setPosition(head.getX()+(i*SIZE), head.getY());
		snake.add(e);
	}
	apple=new Snake(SIZE);
	setApple();
	score=0;

	gameover=false;
	level=1;
	dx=dy=0;
	setFPS(level*10);

	
	
}


public void setApple()
{
	int x=(int) (Math.random()*(WIDTH-SIZE));

	int y=(int) (Math.random()*(HEIGHT-SIZE));
    x=x-(x% SIZE);
    y=y-(y% SIZE);
    
    apple.setPosition(x, y);
}
	
	
//move
 
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
   int k=e.getKeyCode();
   
		if (k == KeyEvent.VK_UP)
			up = true;

		if (k == KeyEvent.VK_DOWN)
			down = true;

		if (k == KeyEvent.VK_LEFT)
			left = true;

		if (k == KeyEvent.VK_RIGHT)
			right = true;
		if (k == KeyEvent.VK_ENTER)
			start = true;

}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
  int k=e.getKeyCode();
	if (k == KeyEvent.VK_UP)
		up = false;

	if (k == KeyEvent.VK_DOWN)
		down = false;

	if (k == KeyEvent.VK_LEFT)
		left = false;

	if (k == KeyEvent.VK_RIGHT)
		right = false;
	if (k == KeyEvent.VK_ENTER)
		start = false;

	
}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub

	   int k=e.getKeyCode();
	   
			if (k == KeyEvent.VK_W)
				up = true;

			if (k == KeyEvent.VK_S)
				down = true;

			if (k == KeyEvent.VK_A)
				left = true;

			if (k == KeyEvent.VK_D)
				right = true;
			if (k == KeyEvent.VK_ENTER)
				start = true;

	
}

	}
