package game1;

import java.awt.Image;
import java.awt.Toolkit;

public class Attack {
	
	private int xCoord = 0;
	private int yCoord = 0;
	private int width = 10;
	private int height = 10;
	private Image img;
	

	public Attack() {
	setxCoord(350);
	setyCoord(250);
	setWidth(30);
	setHeight(30);
	setImg("../files/boss.png");
}

public Attack(int x, int y, int w, int h, String imgpath) {
	setxCoord(x);
	setyCoord(y);
	setWidth(w);
	setHeight(h);
	setImg(imgpath);
}

public void twoPlayer(int direction) {
	boolean mode = false;
	if (direction == 13) {
		mode = true;
	} 
} 

public void moveIt(int direction, int w, int h) {
	int speed = 15;
	int x = getxCoord();
	int y = getyCoord();
	
	if (direction == 68) {
		x = x + speed;
		if (x > w) { x = x - speed * 3;}
		setxCoord(x);
		// System.out.println("move right");
	} else if (direction == 65) {
		if (x < 0) { x = x + speed * 3;}
		x = x - speed;
		setxCoord(x);
		// System.out.println("move left");
	} else if (direction == 83) {
		if (y > h - 10) { y = y - speed * 3;}
		y = y + speed;
		setyCoord(y);
		// System.out.println("move down");
	} else if (direction == 87) {
		if (y < 0) { y = y + speed * 3; }
		y = y - speed;
		setyCoord(y);
		// System.out.println("move up");
	}
}

public void setImg(String imgpath) {
	this.img = Toolkit.getDefaultToolkit().getImage(imgpath);
}

public int getxCoord() {
	return xCoord;
}

public void setxCoord(int xCoord) {
	this.xCoord = xCoord;
}

public int getyCoord() {
	return yCoord;
}

public void setyCoord(int yCoord) {
	this.yCoord = yCoord;
}

public int getWidth() {
	return width;
}

public void setWidth(int width) {
	this.width = width;
}

public int getHeight() {
	return height;
}

public void setHeight(int height) {
	this.height = height;
}

public Image getImg() {
	return img;
}

public void setImg(Image img) {
	this.img = img;
}

}


	