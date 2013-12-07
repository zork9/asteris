package asterissrc;
/*
Copyright (C) 2012 Johan Ceuppens

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.*;

class Enemy 
{
protected int hitpoints = 10;
protected int maxhitpoints = 10;
protected StateImageLibrary rightimages = new StateImageLibrary();
protected StateImageLibrary leftimages = new StateImageLibrary();
private String direction = "right";//left right stop
private int x, y, w, h;
private boolean moving  = false;
protected StateImageLibrary staticleftimages = new StateImageLibrary();
protected StateImageLibrary staticrightimages = new StateImageLibrary();
public Enemy(int startx, int starty, int startw, int starth)
{
	direction = "right";

	x = startx;
	y = starty;

	w = startw;
	h = starth;

	staticleftimages.addImage("player-left-static-40x40-1.png");
	staticrightimages.addImage("player-right-static-40x40-1.png");

	addLeftImage("player-left-40x40-1.png");
	addLeftImage("player-left-40x40-1.png");
	addLeftImage("player-left-40x40-2.png");
	addLeftImage("player-left-40x40-3.png");
	addLeftImage("player-left-40x40-4.png");
	addLeftImage("player-left-40x40-5.png");
	addLeftImage("player-left-40x40-6.png");
	addLeftImage("player-left-40x40-7.png");
	addLeftImage("player-left-40x40-8.png");
	addLeftImage("player-left-40x40-9.png");
	addLeftImage("player-left-40x40-10.png");

	addRightImage("player-right-40x40-1.png");
	addRightImage("player-right-40x40-2.png");
	addRightImage("player-right-40x40-3.png");
	addRightImage("player-right-40x40-4.png");
	addRightImage("player-right-40x40-5.png");
	addRightImage("player-right-40x40-6.png");
	addRightImage("player-right-40x40-7.png");
	addRightImage("player-right-40x40-8.png");
	addRightImage("player-right-40x40-9.png");
	addRightImage("player-right-40x40-10.png");

}

public void addLeftImage(String rightimage)
{
	leftimages.addImage(rightimage);
}

public void addRightImage(String leftimage)
{
	rightimages.addImage(leftimage);
}

public Image getImage()
{
	if (direction == "left") {
		if (!moving)
			return staticleftimages.getImage();
		else
			return leftimages.getImage();
	} else if (direction == "right") {
		if (!moving)
			return staticrightimages.getImage();
		else 
			return rightimages.getImage();
	}

	return rightimages.getImage(2);
}

public int getx()
{
	return x;
}

public int gety()
{
	return y;
}

public int getw()
{
	return w;
}

public int geth()
{
	return h;
}

public void fall()
{
	y+=5;
}

public void moveleft()
{
	direction = "left";
	x-=5;
}

public void moveright()
{
	direction = "right";
	x+=5;
}

public void settomoving()
{
	moving = true;
}

public void settonotmoving()
{
	moving = false;
}

public String getdirection()
{
	return direction;
}
};
