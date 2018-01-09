package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener,MouseListener {
	
	public static FlappyBird flappyBird;
	
	public final int HEIGHT=600,WIDTH=600;
	
	public Renderer renderer;
	
	public Rectangle bird;
	public int ticks,yMotion,score;
	public ArrayList<Rectangle> columns;
	public Random rand;
	public boolean gameOver,started=false;
	public FlappyBird()
	{
		JFrame jframe=new JFrame();
		Timer timer=new Timer(20,this);
		renderer=new Renderer();
		rand=new Random();
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH,HEIGHT);
		jframe.addMouseListener(this);
		jframe.setTitle("Flappy Bird");
		jframe.setResizable(false);
		
		
		bird=new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
		columns=new ArrayList<Rectangle>();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		timer.start();
		jframe.setVisible(true);
		
		
	}
	public void addColumn(boolean start)
	{
		int space=250;
		int width=100;
		int height=40 + rand.nextInt(250);
		if(start)
		{
			columns.add(new Rectangle(WIDTH+width+columns.size()*250,HEIGHT-height-100,width,height));
			columns.add(new Rectangle(WIDTH+width+(columns.size()-1)*250,0,width,HEIGHT-height-space));
	
		}
		else
		{
			columns.add(new Rectangle(columns.get(columns.size()-1).x+450,HEIGHT-height-100,width,height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x,0,width,HEIGHT-height-space)); 
			
		}
	}
	public void paintColumn(Graphics g,Rectangle column)
	{
		g.setColor(Color.green.darker());
		g.fillRect(column.x,column.y,column.width,column.height);
	}
	public void jump()
	{
		if(gameOver)
		{
			bird=new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
			columns.clear();
			yMotion=0;
			score=0;
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			gameOver=false;
		}
		if(!started)
		{
			started=true;
		}
		else if (!gameOver)
		{
			if(yMotion>0)
			{
				yMotion=0;
			}
			yMotion-=10;
		}
	}
	public void actionPerformed(ActionEvent e) {
		int speed=5;
		ticks++;
		if(started)
		{
		for(int i=0;i<columns.size();i++)
		{
			Rectangle column=columns.get(i);
			column.x-=speed;
		}
		if(ticks%2==0 && yMotion<15)
		{
			yMotion+=2;
			
		}
		for(int i=0;i<columns.size();i++)
		{
			Rectangle column=columns.get(i);
			if(column.x+column.width<0)
			{
				columns.remove(column);
				if(column.y==0)
				{
					addColumn(false);
				}
			}
		}
		bird.y+=yMotion;
		for(Rectangle column:columns)
		{	if(column.y==0 && bird.x+bird.width/2>column.x+column.width/2-5 && bird.x + bird.width/2<column.x+column.width/2+5)
		{
			score++;
		}
			
			if(column.intersects(bird)) 
			{
			gameOver=true;
			bird.x=column.x-bird.width;
			}
		}
		if(bird.y>HEIGHT-120 || bird.y<0)
		{
			bird.y=HEIGHT-120-bird.height;
			gameOver=true;
		}
		if(bird.y+yMotion>=HEIGHT-100)
		{
			bird.y=HEIGHT-100-bird.height;
		}
		}
		renderer.repaint();
	}
	public void repaint(Graphics g) {
		
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.green);
		g.fillRect(0,WIDTH-115, HEIGHT, 15);
		g.setColor(Color.orange);
		g.fillRect(0,WIDTH-100,HEIGHT,100);
		g.setColor(Color.red);
		g.fillRect(bird.x,bird.y,bird.width,bird.height);
		for(Rectangle column:columns)
		{
			paintColumn(g,column);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,100));
		if(!started)
		{
			g.drawString("Click",170,HEIGHT/2-20);
		}
		if(gameOver)
		{
			g.drawString("GameOver",0,HEIGHT/2-20);
		}
		if(!gameOver && started)
		{
			g.drawString(String.valueOf(score),WIDTH/2-20,100);
		}
	}
	public static void main(String args[])
	{
		flappyBird=new FlappyBird();
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		jump();
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		
	}
	

}
