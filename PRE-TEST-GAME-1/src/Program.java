import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Program {
	public static void main(String[] args) {
		GameFrame frame=new GameFrame(1000,1000);

	}
}
class GameFrame extends JFrame{
	private int width;
	private int height;
	private GamePanel panel=new GamePanel();
	public GameFrame(int height,int width ){
		this.height=height;
		this.width=width;

		setSize(height, width);
		panel.setFocusable(true);
		add(panel);

		FrameBuild();
	}
	void FrameBuild(){
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	class GamePanel extends JPanel{

		private Point ball = new Point(0,0);
		private int dx = 2; // Increment on ball's x-coordinate
		private int dy = 2; // Increment on ball's y-coordinate
		private int numOfdeath=3;
		private Timer timer;
		final int speed=15;
		final int radius =8;
		private int score;
		private boolean firstTime=true;
		private Point fill3DRectPoint;
		private ArrayList<Point> bricksArray=new ArrayList<Point>();
		public GamePanel(){

			setBackground(Color.BLACK);
			timer=new Timer(6,new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					repaint();

				}
			});
			timer.start();
			addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_RIGHT){
						fill3DRectPoint.x+=50;
						if(fill3DRectPoint.x>getWidth())
							fill3DRectPoint.x-=getWidth()+15;
						repaint();
					}
					if(e.getKeyCode()==KeyEvent.VK_LEFT){
						fill3DRectPoint.x-=50;
						if(fill3DRectPoint.x<-30)
							fill3DRectPoint.x+=getWidth();
						repaint();
					}

				}
			});
		}
		@Override
		protected void paintComponent(Graphics g) {
			setFocusable(true);

			if(firstTime==true){
				super.paintComponent(g);
				ball.move(0, getHeight()/2);
				fill3DRectPoint=new Point(getWidth()/2-(2*radius),getHeight()-(2*radius));
				g.fill3DRect(fill3DRectPoint.x,fill3DRectPoint.y, radius*7, radius*2, true);

				for(int i=1;i<10;i++){
					for(int j=1;j<Math.random()*3+20;j++){
						int random=(int) (Math.random()*3);           
						if(random>0){
							bricksArray.add(new Point(j*100,i*radius * 10));
						}              
					}                   
				}


				firstTime=false;
			}
			else{
				super.paintComponent(g);
				g.setColor(Color.ORANGE);
				g.fillRoundRect(fill3DRectPoint.x,fill3DRectPoint.y, radius*8, radius*2,6,10);
			}

			if (ball.x < radius) dx = Math.abs(dx);  // Check boundaries
			if (ball.x > getWidth() - radius) dx = -Math.abs(dx);
			if (ball.y < radius) dy = Math.abs(dy);
			if (ball.y > getHeight() - (2.8*radius)) dy = -Math.abs(dy);
			ball.x += dx;  // Adjust ball position
			ball.y += dy;
			g.setColor(Color.RED); //ball print
			g.fillOval(ball.x - radius, ball.y - radius, radius * 2, radius * 2);

			//bricks  print
			g.setColor(Color.GREEN);
			for(int i=0;i<bricksArray.size();i++){
				//g.fillRect(bricksArray.get(i).x, bricksArray.get(i).y, radius * 7, radius * 2);
				g.fillRoundRect(bricksArray.get(i).x, bricksArray.get(i).y, radius * 8, radius * 2
						, 100, 100);
			}

			//hit the manual-brick
			if(ball.y>fill3DRectPoint.y-(0.8*radius)&&
					(ball.x<fill3DRectPoint.x+(radius*9)&&ball.x>fill3DRectPoint.x-(radius*2))){   	
				if (ball.x > fill3DRectPoint.x+(radius*6)&&ball.x < fill3DRectPoint.x+(radius*8)){
					 dx = Math.abs(dx);
					 dy = -Math.abs(dy);
				}
				else if(ball.x<fill3DRectPoint.x+radius*1){
					 dx = -Math.abs(dx);
					 dy = -Math.abs(dy);
				}
				//score++;



			}//ball fall
			else if(ball.y>fill3DRectPoint.y-(0.8*radius)&&
					(ball.x>fill3DRectPoint.x+(radius*8)||ball.x<fill3DRectPoint.x-(radius*2))){   
				ball.move(0, getHeight()/2);
				numOfdeath--;
				

			}
			for(int i=0;i<bricksArray.size();i++){
				if(ball.y<bricksArray.get(i).y+(radius * 2)&&ball.y>bricksArray.get(i).y&&
						(ball.x<bricksArray.get(i).x+(radius*8)&&ball.x>bricksArray.get(i).x)){
					if (ball.x < bricksArray.get(i).x+(radius*8)&&ball.x > bricksArray.get(i).x+(radius*6)){
						if(ball.y>bricksArray.get(i).y+radius){//lower half
							if(dy<0){//from down
							 dx = Math.abs(dx);
							 dy = Math.abs(dy);
							}
							else if(dy>0){//from up
								dx = Math.abs(dx);
							}
							
						}
						else if(ball.y<bricksArray.get(i).y+radius){
							if(dy<0){//from down
								 dx = Math.abs(dx);
								
							}
							 else if(dy>0){//from up
								 dx = Math.abs(dx);
								 dy = -Math.abs(dy);
							 }
						}
							 dx = Math.abs(dx);
							 dy = -Math.abs(dy); } // Check boundaries
							 else if (ball.x > bricksArray.get(i).x&&ball.x < bricksArray.get(i).x+(radius*1.5)) {
						dx = -Math.abs(dx);
					dy = Math.abs(dy);}
							 else	if (dy<0)  {
						dy = Math.abs(dy);						
						}
					else if (dy>0) dy = -Math.abs(dy);
					
					score++;
				bricksArray.remove(i);
				}
			}
			
				
				
				
				
			g.setColor(Color.WHITE);
			g.setFont(new Font("ARIEL",Font.BOLD,18));
			g.drawString("score " + score , 5, 18);
			g.drawString("Deaths Left " + numOfdeath , getWidth()-117, 18);
		}
	}
}


