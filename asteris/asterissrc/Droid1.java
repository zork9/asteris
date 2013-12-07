package asterissrc;
/*
Copyright (C) 2012-2013 Johan Ceuppens

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

class Droid1 extends Enemy 
{
protected int hitpoints = 2;
protected int maxhitpoints = 2;
protected StateImageLibrary rightimages = new StateImageLibrary();
protected StateImageLibrary leftimages = new StateImageLibrary();
private String direction = "right";//left right stop
private int x, y, w, h;
private boolean moving  = false;
protected StateImageLibrary staticleftimages = new StateImageLibrary();
protected StateImageLibrary staticrightimages = new StateImageLibrary();

public Droid1(int startx, int starty, int startw, int starth)
{
	super(startx, starty, startw, starth);
}

public void move()
{
	direction = "right";
	x+=1;
}

};
