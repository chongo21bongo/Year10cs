package game1;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import sun.audio.*;

public class MyCanvas extends Canvas implements KeyListener {
	
	Goodguy puck = new Goodguy(385,650,30,30,"files/right.png");
	LinkedList badguys = new LinkedList();
	Net net = new Net(340, 80, 100, 40, "files/net.png");
	Background back = new Background(0, 0, 800, 680, "files/rink.png");
	Score score = new Score(225, 0, 350, 200, "files/score.png");
	Timer timer = new Timer();
	
	// counting variables 
	int goals = 0;
	int hits = 0;
	int time = 0;
	int timerSeconds = 0;
	int timerMinutes = 0;


	
	public MyCanvas() { 
		this.setSize(800,680); // boundaries for myCanvas 
		this.addKeyListener(this); 
		playIt("files/arena.wav"); // background sound track
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				timerSeconds++;
				// System.out.println(String.valueOf(timerSeconds%60) + " seconds");
			}
		}, 1000, 1000);
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				timerMinutes++;
				// System.out.println(String.valueOf(timerMinutes) + " minutes");
			}
		} , 60*1000, 60*1000);
		
		// randomizer
		Random rand = new Random();
		int winwidth = this.getWidth(); // sets x boundaries as myCanvas
		int winheight = this.getHeight(); // sets y boundaries as myCanvas
		// System.out.println("winwidth " + String.valueOf(winwidth)); 
		// System.out.println("winheight " + String.valueOf(winheight));

		
		// generates random coordinates for bad guys
		for (int i = 0; i<5; i++) {
			int rx = rand.nextInt(winwidth); 
			int ry = rand.nextInt(winheight);
			// System.out.println("rx = " + String.valueOf(rx));
			// System.out.println("ry = " + String.valueOf(ry));
				
			Badguy bg = new Badguy(rx, ry, 50,50,"files/villain.png"); // bad guy instantiation
			Rectangle br = new Rectangle(rx,ry,30,30); // rectangle around bad guy
				
				
			if (br.contains(puck.getxCoord(), puck.getyCoord())) {
				System.out.println("badguy on top of puck");
				continue;
			}
			badguys.add(bg); // add bad guys to linked list
		}
	}

	public void playIt(String filename) {
		try {
			InputStream in = new FileInputStream(filename);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		time++;
		
		// background image
		g.drawImage(back.getImg(), back.getxCoord(), back.getyCoord(), back.getWidth(), back.getHeight(), this);
		
		// good guy image
		g.drawImage(puck.getImg(), puck.getxCoord(), puck.getyCoord(), puck.getWidth(), puck.getHeight(), this);
		
		// net image
		g.drawImage(net.getImg(), net.getxCoord(), net.getyCoord(), net.getWidth(), net.getHeight(), this);
		
		// score board image
		g.drawImage(score.getImg(), score.getxCoord(), score.getyCoord(), score.getWidth(), score.getHeight(), this);
		
		// good guy score print
		g.drawString(String.valueOf(goals), 256, 42);
		
		// bad guy score print
		g.drawString(String.valueOf(hits), 537, 42);
		
		// time print
		if (timerSeconds < 10) {
			g.drawString(String.valueOf(timerMinutes) + ":0" + String.valueOf(timerSeconds%60), 388, 47);
		} else {
			g.drawString(String.valueOf(timerMinutes) + ":" + String.valueOf(timerSeconds%60), 388, 47);
		}
		
		// artificial intelligence for bad guys
		for(int i = 0; i < badguys.size(); i++) {
			Badguy bg = (Badguy) badguys.get(i);
			Random rand = new Random();
			if (time%10==0) {
				if (bg.getxCoord()<puck.getxCoord() && rand.nextInt()%5<2) {
					bg.setxCoord(bg.getxCoord()+1);
				}
				else if (bg.getxCoord()>puck.getxCoord() && rand.nextInt()%5<2) {
					bg.setxCoord(bg.getxCoord()-1);
				}
				
				if (bg.getyCoord()<puck.getyCoord() && rand.nextInt()%5<2) {
					bg.setyCoord(bg.getyCoord()+1);
				}
				else if (bg.getyCoord()>puck.getyCoord() && rand.nextInt()%5<2) {
					bg.setyCoord(bg.getyCoord()-1);
				}
			}
			
			badguys.set(i, bg);
			
			g.drawImage(bg.getImg(), bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight(), this);
			repaint();
		}
	}
		
	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) { // key listener 
		// System.out.println(e);
		puck.moveIt(e.getKeyCode(), this.getWidth(), this.getHeight());
		
		Rectangle pr = new Rectangle(puck.getxCoord(), puck.getyCoord(), puck.getHeight(), puck.getWidth()); // rectangle around good guy
		Rectangle nr = new Rectangle(net.getxCoord(),net.getyCoord(),net.getWidth(),net.getHeight()); // rectangle around net
		if(nr.intersects(pr)) {
			puck.setxCoord(385); // good guy reset x
			puck.setyCoord(650); // good guy reset y 
			playIt("files/goal.wav"); // goal sound
			System.out.println(goals + " goal"); // number of goals (internal print)
			goals++; // add 1 to goals variable
			
			for(int i = 0; i < badguys.size(); i++) {
				Badguy bg = (Badguy) badguys.get(i);
				bg.RePosition(); // reset bad guys position
				
			}
		}
			
			
		for(int i = 0; i < badguys.size(); i++) {
			Badguy bg = (Badguy) badguys.get(i);
			Rectangle br = new Rectangle(bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight()); // rectangle around bad guys
			if (br.intersects(pr)) {
				playIt("files/hit.wav"); // hit sound
				playIt("files/whistle.wav"); // whistle sound
				puck.setxCoord(385); // good guy reset x
				puck.setyCoord(650); // good guy reset y
				hits++; // add 1 to hits variable
				System.out.println(hits + " hits"); // number of hits (internal print)
				
				for(int j = 0; j < badguys.size(); j++) {
					Badguy bg2 = (Badguy) badguys.get(j);
					bg2.RePosition(); // reset bad guys position
				}
			}
		}
	}	
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
