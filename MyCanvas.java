package game1;

import java.awt.Canvas;
import java.awt.Color;
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
	
	Goodguy puck = new Goodguy(385,640,30,30,"files/right.png"); // good guy class
	Attack boss = new Attack(20, 600, 60, 40, "files/boss.png"); // 2nd player class
	Net net = new Net(340, 80, 100, 40, "files/net.png"); // net class
	Background back = new Background(0, 0, 800, 680, "files/rink.png"); // background class
	Score score = new Score(225, 0, 350, 200, "files/score.png"); // score board class
	
	LinkedList badguys = new LinkedList(); // bad guys "class" 

	Timer timer = new Timer(); // timer for game timer
	Timer collision = new Timer(); // timer for collision detections
	
	// counting variables 
	int goals = 0; // good guy goals
	int hits = 0; // bad guy goals
	int time = 0; // bad guy motion time variable
	int timerSeconds = 0; // time in seconds
	int timerMinutes = 0; // time in minutes
	int tries = 0; // attempts
	boolean bossshow = false; // show 2nd player
	boolean instructions = false; // show instructions
	
		
	public MyCanvas() { 
		this.setSize(800,680); // boundaries for myCanvas 
		this.addKeyListener(this); 
		playIt("files/arena.wav"); // background sound track
		playIt("files/music.wav"); // background sound track 2

		
		// randomizer
		Random rand = new Random(); // random integer
		int winwidth = this.getWidth() - 30; // sets x boundaries as myCanvas
		int winheight = this.getHeight() - 30; // sets y boundaries as myCanvas

		// generates random coordinates for bad guys
		for (int i = 0; i<4; i++) {
			int rx = rand.nextInt(winwidth); 
			int ry = rand.nextInt(winheight);
				
			Badguy bg = new Badguy(rx, ry, 50,50,"files/villain.png"); // bad guy instantiation
			Rectangle br = new Rectangle(rx,ry,30,30); // rectangle around bad guy
				
			if (br.contains(puck.getxCoord(), puck.getyCoord())) {
				System.out.println("badguy on top of puck");
				continue;
			}
			badguys.add(bg); // add bad guys to linked list
		}
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				timerSeconds++; // add one second to timer
			}
		}, 1000, 1000);
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				timerMinutes++; // add one minute to timer
			}
		} , 60*1000, 60*1000);		
		
		collision.scheduleAtFixedRate(new TimerTask() {
			@Override 
			public void run() {
				Rectangle pr = new Rectangle(puck.getxCoord(), puck.getyCoord(), puck.getHeight(), puck.getWidth()); // rectangle around good guy
				Rectangle nr = new Rectangle(net.getxCoord(),net.getyCoord(),net.getWidth(),net.getHeight()); // rectangle around net
				if(nr.intersects(pr)) {
					puck.setxCoord(385); // good guy reset x
					puck.setyCoord(640); // good guy reset y 
					boss.setxCoord(20); // boss reset x
					boss.setyCoord(600); // boss reset y
					playIt("files/goal.wav"); // goal sound
					System.out.println(goals + " goal"); // number of goals (internal print)
					goals++; // add 1 to goals variable
					tries++; // add 1 to attempts
					
					// reset bad guys position
					for(int i = 0; i < badguys.size(); i++) {
						Badguy bg = (Badguy) badguys.get(i);
						bg.RePosition(); // reposition method
					}
				}
					
					
				for(int i = 0; i < badguys.size(); i++) {
					Badguy bg = (Badguy) badguys.get(i);
					Rectangle br = new Rectangle(bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight()); // rectangle around bad guys
					if (br.intersects(pr)) {
						playIt("files/hit.wav"); // hit sound
						playIt("files/whistle.wav"); // whistle sound
						puck.setxCoord(385); // good guy reset x
						puck.setyCoord(640); // good guy reset y
						boss.setxCoord(20); // boss reset x
						boss.setyCoord(600); // boss reset y
						hits++; // add 1 to hits variable
						tries++; // add 1 to attempts
						
						for(int j = 0; j < badguys.size(); j++) {
							Badguy bg2 = (Badguy) badguys.get(j);
							bg2.RePosition(); // reset bad guys position
						}
					}
				}
				
				for (int i = 0; i < badguys.size(); i++) {
					Rectangle bsr = new Rectangle(boss.getxCoord(), boss.getyCoord(), boss.getWidth(), boss.getHeight());
					Rectangle pr2 = new Rectangle(puck.getxCoord(), puck.getyCoord(), puck.getHeight(), puck.getWidth()); // rectangle around good guy
					if (bsr.intersects(pr2)) {
						puck.setxCoord(385); // good guy reset x
						puck.setyCoord(640); // good guy reset y
						boss.setxCoord(20); // boss rest x
						boss.setyCoord(600); // boss reset y
						tries++; // add 1 to attempts
						playIt("files/boo.wav");
						
						if (goals <= 3) { // if there are more than 3 goals
							goals = 0; // reset goals to 0
						} else { 
							goals = goals - 1; // subtract one off 0
						}
						
						for(int k = 0; k < badguys.size(); k++) {
							Badguy bg3 = (Badguy) badguys.get(k);
							bg3.RePosition(); // reset bad guys position
						}
					}
				}
			}
		}, 10, 10); // check every 10 milliseconds
		
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
		// bad guy movement time variable
		time++;
		
		// background image
		g.drawImage(back.getImg(), back.getxCoord(), back.getyCoord(), back.getWidth(), back.getHeight(), this);
		
		// good guy image
		g.drawImage(puck.getImg(), puck.getxCoord(), puck.getyCoord(), puck.getWidth(), puck.getHeight(), this);
		
		// 2nd player image
		if (bossshow) {
			g.drawImage(boss.getImg(), boss.getxCoord(), boss.getyCoord(), boss.getWidth(), boss.getWidth(), this);
		}
			
		// net image
		g.drawImage(net.getImg(), net.getxCoord(), net.getyCoord(), net.getWidth(), net.getHeight(), this);
		
		// score board image
		g.drawImage(score.getImg(), score.getxCoord(), score.getyCoord(), score.getWidth(), score.getHeight(), this);
			
		// good guy score print
		g.drawString(String.valueOf(goals), 255, 42);
		
		// bad guy score print
		g.drawString(String.valueOf(hits), 535, 42);
		
		
		// instruction button print
		if (instructions == false) {
			g.drawString("press  I  for instructions", 600, 645); // same position as minimize
			g.drawRect(637, 634, 12, 12); // rectangle around the I
		}

		// full instructions print
		if (instructions) {
			int x = 500; // initial x variable for spacing
			int y = 524; // initial y variable for spacing
		
			g.drawString("press enter for 2-player", x, y);
			g.drawString("WASD (boss) and arrows (puck)", x, y+15);
			g.drawString("puck = home player", x, y+30);
			g.drawString("boss = away player", x, y+45);
			
			g.drawString("puck + net = +1 home goal", x, y+60);
			g.drawString("puck + player = +1 away goal", x, y+75);
			
			g.drawString("if goals > 3: puck + boss = -1 home goal", x, y+90);
			g.drawString("if goals < 3: puck + boss = reset home goal", x, y+105);
			
			g.drawString("press  I  to minimize", 600, 645); // same position as instructions
			g.drawRect(637, 634, 12, 12); // rectangle around the I

		}
		
		
		// time print
		if (timerSeconds < 10) {
			g.drawString(String.valueOf(timerMinutes) + ":0" + String.valueOf(timerSeconds%60), 388, 47);
		} else {
			g.drawString(String.valueOf(timerMinutes) + ":" + String.valueOf(timerSeconds%60), 388, 47);
		}
		
		
		// scoring attempts print
		g.setColor(Color.WHITE);
		g.drawString("Scoring Attempts:" + String.valueOf(tries), 20, 15);
		
		// seconds per goal print
		if (goals == 0) {
			g.drawString("Seconds / Goal: 0", 660, 15);
		} else {
			g.drawString("Seconds / Goal:" + String.valueOf(timerSeconds / goals), 660, 15);
		}
		
		
		
		
		// artificial intelligence for bad guys
		for(int i = 0; i < badguys.size(); i++) {
			Badguy bg = (Badguy) badguys.get(i);
			Random rand = new Random();
			if (time%15==0) {
				if (bg.getxCoord() < puck.getxCoord() && rand.nextInt()%5<2) {
					bg.setxCoord(bg.getxCoord()+1);
				}
				else if (bg.getxCoord() > puck.getxCoord() && rand.nextInt()%5<2) {
					bg.setxCoord(bg.getxCoord()-1);
				}
				
				if (bg.getyCoord() < puck.getyCoord() && rand.nextInt()%5<2) {
					bg.setyCoord(bg.getyCoord()+1);
				}
				else if (bg.getyCoord() > puck.getyCoord() && rand.nextInt()%5<2) {
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
		 boss.moveIt(e.getKeyCode(), this.getWidth(), this.getHeight());

		if (e.getKeyCode() == 10) {
			if (bossshow) {
				bossshow = false;
			} else {
				bossshow = true;
			}
		}
		
		if (e.getKeyCode() == 73) {
			if (instructions) {
				instructions = false;
			} else {
				instructions = true;
			}
		}
		 			
		
	}	
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
