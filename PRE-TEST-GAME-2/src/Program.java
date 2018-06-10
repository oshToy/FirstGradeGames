import java.awt.BorderLayout;
import java.awt.Color;
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
		GameFrame frame=new GameFrame(700,700);
	}

}
class GameFrame extends JFrame {
	protected static  ArrayList <FireBall> FireBallArray = new ArrayList<FireBall>();
	protected static  ArrayList <Brick> bricksArray= new ArrayList<Brick>();
	protected static  PlaneBody plane;
	protected int width;
	protected int height;
	public static GamePanel panel;
	public static int brickMakerDelay=1500 ;
	public GameFrame(int width,int height){
		this.width=width;
		this.height=height;  
		setSize(width,height);
		panel=new GamePanel();
		ControlPanel controlPan=new ControlPanel();
		add(panel,BorderLayout.CENTER);		
		add(controlPan,BorderLayout.SOUTH);
        
		FrameBody();

	}
	void FrameBody(){
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}


	public  class GamePanel extends JPanel{

		private int speedBrick;
		private int radiusBrick;
		private boolean firstTime=true;
		private Timer brickMakerTimer;

		public GamePanel(){		
			plane= new PlaneBody();
			setFocusable(true);
			plane.setColorPlane(Color.RED);
			plane.setRadiusPlane(20);
			addKeyListener(plane);
			 brickMakerTimer=new Timer(((int)(Math.random()*brickMakerDelay)+1500),new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					brickMakerTimer.setDelay(((int)(Math.random()*brickMakerDelay)+1000));
					
					int randomWidth=(int)(Math.random()*(width-50));
					bricksArray.add(new Brick(randomWidth,0));

				}
			});
			brickMakerTimer.start();
		}
		@Override
		protected void paintComponent(Graphics g) {

			if(firstTime){
				plane.setPlanePoint(new Point(getWidth()/2,getHeight()));
				firstTime=false;
			}
			super.paintComponent(g);
			for(int i=0;i<FireBallArray.size();i++){
				if(FireBallArray.get(i).y<0){
					FireBallArray.remove(i);
				}
				else {
					FireBallArray.get(i).draw(g);
				}

			}	 
			for(int i=0;i<bricksArray.size();i++){
				if(bricksArray.get(i).y>getHeight()){
					bricksArray.remove(i) ;
				}
				else{
					bricksArray.get(i).draw(g);
				}
				
			}

			plane.draw(g);

		}
	}
	public class Brick{
		private int x;
		private int y;
		public Brick(int x1 , int y1){
			this.x=x1;
			this.y=y1;
			Timer BrickFallTimer=new Timer(250,new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					y+=10;

				}
			});
			BrickFallTimer.start();
		}
		public void draw(Graphics g){
			g.setColor(plane.colorPlane);
			g.fillRect(x, y, 40, 20);
		}
	}

	public class PlaneBody extends KeyAdapter{
		private int moveX=-10;
		private Color colorPlane;
		private int radiusPlane;
		private Point planePoint=new Point(0,0);
		private int planeDelay=50;
		private int arcCounter;
		private Timer planeTimer;
		public PlaneBody(){
			

			planeTimer=new Timer(50,new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					planeTimer.setDelay(planeDelay);
					if(planePoint.x<0){
						moveX=moveX*-1;				
					}		
					else if(planePoint.x>getWidth()-radiusPlane*2){
						moveX=moveX*-1;						
					}

					planePoint.x=planePoint.x+moveX;
					arcCounter++;
					panel.repaint();

				}
			});
			planeTimer.start();	 

		}
		public int getPlaneDelay() {
			return planeDelay;
		}
		public void setPlaneDelay(int planeDelay) {
			this.planeDelay = planeDelay;
		}	  
		public Point getPlanePoint() {
			return planePoint;
		}
		public void setPlanePoint(Point planePoint) {
			this.planePoint = planePoint;
		}
		public Color getColorPlane() {
			return colorPlane;
		}
		public void setColorPlane(Color colorPlane) {
			this.colorPlane = colorPlane;
		}
		public int getRadiusPlane() {
			return radiusPlane;
		}
		public void setRadiusPlane(int radiusPlane) {
			this.radiusPlane = radiusPlane;
		}
		void draw(Graphics g){
			
			g.setColor(colorPlane);		 
			g.fillRoundRect(planePoint.x+radiusPlane, planePoint.y-radiusPlane*5, radiusPlane, radiusPlane*4, radiusPlane, radiusPlane*3);
			g.setColor(Color.BLACK);
			for(int i=0;i<7;i++){
				g.fillArc(planePoint.x, planePoint.y-radiusPlane*2, radiusPlane*3, radiusPlane*2, 48*(arcCounter+i), 20);
			}

		}

		@Override
		public void keyPressed(KeyEvent e) {


			if(e.getKeyChar()==KeyEvent.VK_SPACE){
				GameFrame.FireBallArray.add(new FireBall(planePoint.x+radiusPlane, planePoint.y-radiusPlane*5));

			}
		}


	}
	public class FireBall extends JPanel  {
		private int x;
		private int y;
		private int radius=5;
		private Color color=new Color(10);
		private int ballDelay=50;
		public FireBall(int x1, int y1) {
			this.x=x1;
			this.y=y1;
			Timer timer=new Timer(ballDelay,new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					y=y-15;
					for(int i=0;i<bricksArray.size();i++){						
							 if(y>=bricksArray.get(i).y-10
										&&y<=bricksArray.get(i).y+20&&
										x>=bricksArray.get(i).x-5&&
										x<=bricksArray.get(i).x+40){
								 bricksArray.remove(i) ;	
								 y=-5;
								 FireBallArray.remove(this);
								 
							}  
						
							 
						}
					repaint();

				}
			});
			timer.start();  
		}
		public void draw(Graphics g){
			g.setColor(plane.colorPlane);
			g.fillOval(x, y, radius*2, radius*2);			  

		}
		public Color getColor() {
			return color;
		}
		public void setColor(Color color) {
			this.color = color;
		}
		public int getRadius() {
			return radius;
		}
		public void setRadius(int radius) {
			this.radius = radius;
		}

	}
}