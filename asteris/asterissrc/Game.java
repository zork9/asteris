package asterissrc;
/*
Copyright (C) 2012-2013 Johan Ceuppens

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
import java.awt.*;
import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.*;
import java.util.Random;

public class Game extends JPanel implements ActionListener {
private String fileprefix = "./pics/";
private String musicfileprefix = "./music/";
private int SCREENWIDTH = 320;
private int SCREENHEIGHT = 200;
private Player player = new Player(100,100,40,40);
private LinkedList boundingboxes = new LinkedList();
private LinkedList gateways = new LinkedList();
private Gateway currentgateway;//FIXME default value
private LinkedList bullets = new LinkedList();
private LinkedList enemies = new LinkedList();

private SimpleMidiPlayer midiplayer = new SimpleMidiPlayer();

///private Map map = new Map(0,0,320,200,new ImageIcon(fileprefix+"map-level1-320x200-1.png").getImage());
private Map map = new Map(0,0,400,2000,new ImageIcon(fileprefix+"map-level1-320x200-1.png").getImage());
    public Game() {
	
	Timer timer;

        addKeyListener(new TAdapter());
        setFocusable(true);
	
        setBackground(Color.white);
        setDoubleBuffered(true);

        timer = new Timer(40, this);
        timer.start();

	loadlevel1000();

    }

    public void addNotify() {
        super.addNotify();
        GameInit();
    }

    public void loadlevel1000()
    {
	gateways.clear();
	bullets.clear();
	enemies.clear();
	boundingboxes.clear();	

	////BFIX midiplayer.playfile("music/" + "level1000.mid", 3);//repeat song 3 times 
	map = new Map(0,0,400,200,new ImageIcon(fileprefix+"map-level1-400x200-1.png").getImage());
	boundingboxes.add(new BoundingBox(0,160,400,6));
	///boundingboxes.add(new BoundingBox(-1000,160,2000,50));
	gateways.add(new Gateway(400-48,160-48,48,48,new ImageIcon(fileprefix+"door-48x48-1.png").getImage(),0,0,100,160-player.geth(),2000));
    }

    public void loadlevel2000()
    {
	gateways.clear();
	bullets.clear();
	enemies.clear();
	boundingboxes.clear();	

	map = new Map(0,0,800,200,new ImageIcon(fileprefix+"map-level2-320x200-1.png").getImage());
	boundingboxes.add(new BoundingBox(0,160,400,6));
	///boundingboxes.add(new BoundingBox(-1000,160,2000,50));
	//gateways.add(new Gateway(400-48,160-48,48,48,new ImageIcon(fileprefix+"gateways-48x48-1.png").getImage(),100,0));
    
	enemies.add(new Droid1(0,0,48,48));

	}

    public void GameInit() {
        LevelInit();
    }


    public void LevelInit() {

    }

/*
 * Collision detection code
 */
    public boolean DoGatewayCollision()
    {
	for (int i = 0; i < gateways.size(); i++) {
		Object o = gateways.get(i);
		Gateway bo = (Gateway)o;
		if (collision(player.getx()-map.getx(),player.gety()-map.gety(),player.getw(),player.geth(),bo.getx(),bo.gety(),bo.getw(),bo.geth())) {

			currentgateway = bo;

			return true;
		}
	}
	return false;
    }	


    public boolean collisionforfall(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        if (x1 > x2 && y1 + h1 > y2 && x1 < x2 + w2 && y1 < y2 + h2)//FIXME
                return true;
        else
                return false;
    }

    public boolean collision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        if (x1 > x2 && y1 > y2 && x1 < x2 + w2 && y1 < y2 + h2)//FIXME
                return true;
        else
                return false;
    }

    public boolean DoBulletCollision()
    {
	
	for (int i = 0; i < bullets.size(); i++) {
		Object oo = bullets.get(i);
		Bullet bo = (Bullet)oo;
		for (int j = 0; j < enemies.size(); j++) {
			Object o = enemies.get(j);
			Enemy e = (Enemy)o;
			if (collision(bo.getx(),bo.gety(),bo.getw(),bo.geth(),e.getx(),e.gety(),e.getw(),e.geth())) {//FIXME enemy instaed of player x and gety
				return true;
			}
		}
	}

	return false;

    }

    public void DoFallDown()
    {

	player.jump();	
	for (int i = 0; i < boundingboxes.size(); i++) {
		Object o = boundingboxes.get(i);
		BoundingBox bo = (BoundingBox)o;
		if (collisionforfall(player.getx(),player.gety(),player.getw(),player.geth(),bo.getx(),bo.gety(),bo.getw(),bo.geth())) {
			return;
		}
	}
	player.fall();
    }

    public void DoMoveEnemies()
    {
	for (int i = 0; i < enemies.size(); i++) {
		Object o = enemies.get(i);
		Enemy eo = (Enemy)o;
		eo.move();
	}
    }	

    //garbage collect bullets which are offscreen
    public void DoMoveBullets()
    {
	for (int i = 0; i < bullets.size(); i++) {
		Object o = bullets.get(i);
		Bullet bo = (Bullet)o;
		bo.move();
	}
    }	

    //garbage collect bullets which are offscreen
    public void DoGarbageCollectBullets()
    {
	for (int i = 0; i < bullets.size(); i++) {
		Object o = bullets.get(i);
		Bullet bo = (Bullet)o;
		if (bo.getx() < 0 || bo.getx() > SCREENWIDTH)
			bullets.remove(i);
	}
    }	


/*
 * Drawing
 */ 
    public void DrawGateways(Graphics2D g2d)
    {
	for (int i = 0; i < gateways.size(); i++) {
		Object o = gateways.get(i);
		Gateway bo = (Gateway)o;
		g2d.drawImage(bo.getImage(), bo.getx()+map.getx(), bo.gety()+map.gety(), this);
	}
    }	

    public void DrawBullets(Graphics2D g2d)
    {
	for (int i = 0; i < bullets.size(); i++) {
		Object o = bullets.get(i);
		Bullet bo = (Bullet)o;
		g2d.drawImage(bo.getImage(), bo.getx(), bo.gety(), this);
	}
    }	



    public void DrawPlayer(Graphics2D g2d) {
	g2d.drawImage(player.getImage(), player.getx(), player.gety(), this);
    }
	
    public void DrawMap(Graphics2D g2d) {
	g2d.drawImage(map.getImage(), map.getx(), map.gety(), this);
    }
	
    public void paint(Graphics g)
    {
      Graphics2D g2d = (Graphics2D) g;

      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, 320, 200);

      DrawMap(g2d);
      DrawGateways(g2d);
      DrawBullets(g2d);
      DrawPlayer(g2d);
      DoFallDown();

      DoGarbageCollectBullets();

      DoMoveEnemies(); 

      boolean bulletcollision = DoBulletCollision();
    
      boolean gatewaycollision = DoGatewayCollision();
      if (gatewaycollision) {

	player.setxy(currentgateway.getplayerx(),currentgateway.getplayery());
	map.setxy(currentgateway.getmapx(),currentgateway.getmapy());

	switch(currentgateway.getnewlevel()) {
		case 1000:
			loadlevel1000();
			break;
		case 2000:
			loadlevel2000();
			break;
		default:
			loadlevel1000();
			break;
		}
	}

      DoMoveBullets();

	//g2d.drawImage("pics/player-right-40x40-1.png",100,100,this);
      Toolkit.getDefaultToolkit().sync();
      g.dispose();

    }

    class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
		player.settonotmoving();
	} 
      
        public void keyPressed(KeyEvent e) {

          int key = e.getKeyCode();

	   	if (key == KeyEvent.VK_LEFT) {
			player.settomoving();
			if (map.getx() >= 0) {
				if (player.getx() < SCREENWIDTH/2 +1) { 
					player.moveleft();
				}
			}
			else if (map.getx() < - map.getw() + SCREENWIDTH+1) {
				if (player.getx() > SCREENWIDTH/2 +1) { 
					player.moveleft();
				} else {
					map.moveright();
				}
			}
			 else {
				map.moveright();
			}

	   	}
	   	if (key == KeyEvent.VK_RIGHT) {
			player.settomoving();
			if (map.getx() >= 0) {
				if (player.getx() < SCREENWIDTH/2 +1) {
					player.moveright();
				} else {
					map.moveleft();
				}
			}
			else if (map.getx() < - map.getw() + SCREENWIDTH+1) { 
					player.moveright();
			 
			}  else {
				map.moveleft();
			}
	   	}
	   	if (key == KeyEvent.VK_UP) {

	   	}
	   	if (key == KeyEvent.VK_DOWN) {
		}	
	   	if (key == KeyEvent.VK_X) {
			Bullet b = new Bullet(player.getx()+player.getw()/2,player.gety()+5,20,20,player.getdirection());
			bullets.add(b);
	   	}
	   	if (key == KeyEvent.VK_Z) {//go back to history of talkmodes
	   		player.settojumping();	
		}
	   	if (key == KeyEvent.VK_SPACE) {//go back to history of talkmodes
	   	}
		if (key == KeyEvent.VK_ESCAPE) {
	   	}
	}
    }

    public void actionPerformed(ActionEvent e) {
        repaint();  
    }

}
