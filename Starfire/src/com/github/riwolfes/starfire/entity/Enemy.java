package com.github.riwolfes.starfire.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.riwolfes.starfire.graphics.Screen;
import com.github.riwolfes.starfire.graphics.Sprite;

public class Enemy extends Entity {
	
	private int hp;
	private Sprite sprite = Sprite.enemies;
	private int spriteSize= sprite.SIZE;
	
	public List<Projectile> projectileList = new ArrayList<Projectile>();
	
	private Screen screen;
	private Random xRandom;
	private int xR, yR, count   = 0, countMax;
	private final double second = 1000000000.0;
	private double delta        = 0.0;
	private double tTime        = 0.0;
	private long lastTime       = 0;
	private double atkSpd       = 0.0;
	
	private int width, height;
	
	public Enemy(int x, int y, Screen screen){
		this.x     = x;
		this.y     = y;
		if(this.x <= 120)			this.x = 121;
		if(this.y >=screen.height-25 || this.y < 0)this.y = screen.height-50;
		this.hp    = 50;
		this.screen= screen;
		this.width = screen.width;
		this.height= screen.height;
		obj   = new Rectangle(x-sprite.SIZE/2, y-sprite.SIZE/2, sprite.SIZE, sprite.SIZE);
		new Rectangle(0,0,screen.width,screen.height);
		xRandom = new Random();
		xR = (xRandom.nextInt(2) * 2) - 1;
		yR = (xRandom.nextInt(2) * 2) - 1;
		countMax = xRandom.nextInt(2)+1;
		atkSpd = 0.75+(0.14*xRandom.nextInt(10));
	}

	public void render() {
		int xx = x-sprite.SIZE/2;
		int yy = y-sprite.SIZE/2;
		screen.renderEntity(xx, yy, sprite);
	}

	public int getHealth(){
		return hp;
	}
	
	public Rectangle getRect(){
		return obj;
	}
	
	public void update() {
		long now = System.nanoTime();
		tTime = now - lastTime;
		tTime = tTime/second;
		lastTime = now;
		delta += tTime;
		while(delta >= atkSpd){
			fire();
			count++;
			delta=0;
		}
		if(count <= countMax){
			if(x + xR <= width  && x + xR >= 120){
				if(x+xR >= width-26)xR = -1;
				if(x+xR <= 121)xR = 1;
				x += xR;
			}
			if(y + yR <= height && y + yR >= 0){
				if(y+yR >= height-25)yR = -1;
				if(y+yR <= 11)yR = 1;
				y += yR;
			}
		}else{
			xR = (xRandom.nextInt(2) * 2) - 1;
			yR = (xRandom.nextInt(2) * 2) - 1;
			count = 0;
		}
		obj   = new Rectangle(x-spriteSize/2, y-spriteSize/2, spriteSize,spriteSize);
	}

	private void fire() {
		projectileList.add( new Projectile(x,y,-10));
	}

	public void setHealth(int i) {
		this.hp = i;
	}
}
