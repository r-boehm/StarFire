package com.github.riwolfes.starfire.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.github.riwolfes.starfire.graphics.Screen;
import com.github.riwolfes.starfire.graphics.Sprite;
import com.github.riwolfes.starfire.input.Keyboard;

public class Player extends Entity {

	private int hp;
	private int score;
	
	private Sprite sprite = Sprite.player;
	private Sprite sprite_up = Sprite.player_up;
	private Sprite sprite_down = Sprite.player_down;
	private Sprite now = sprite;
	private int spriteSize = sprite.SIZE;
	
	private final double second = 1000000000.0;
	private double delta        = 0.0;
	private double tTime        = 0.0;
	private long lastTime       = 0;
	private double atkSpd       = 0.2;
	
	public List<Projectile> projectileList = new ArrayList<Projectile>();
	
	private Keyboard input;
	private Rectangle border;
	private Screen screen;
	
	
	public Player(int x, int y, Keyboard input, Screen screen){
		this.x     = x;
		this.y     = y;
		this.score = 0; 
		this.hp    = 100;
		this.input = input;
		this.screen= screen;
		obj   = new Rectangle(x-sprite.SIZE/2, y-sprite.SIZE/2, sprite.SIZE, sprite.SIZE);
		border= new Rectangle(0,0,screen.width,screen.height);
	}
	
	public int getHealth(){
		return hp;
	}
	
	public void addScore(int i){
		this.score += i;
	}
	
	public int getScore(){
		return this.score;
	}

	public Rectangle getRect(){
		return obj;
	}
	
	public void render() {
		int xx = x-sprite.SIZE/2;
		int yy = y-sprite.SIZE/2;
		screen.renderEntity(xx, yy, now);
	}
	
	public void update() {
		long tNow = System.nanoTime();
		tTime = tNow - lastTime;
		tTime = tTime/second;
		lastTime = tNow;
		delta += tTime;
		while(delta >= atkSpd){
			if(input.space)fire();
			delta=0;
		}
		if(input.left && border.contains(obj.x-1, obj.y, obj.width, obj.height) )x--;
		if(input.right&& border.contains(obj.x+1, obj.y, obj.width, obj.height) )x++;
		if(input.up   && border.contains(obj.x, obj.y-1, obj.width, obj.height) ){
			y--;
			now = sprite_up;
		}
		if(input.down && border.contains(obj.x, obj.y+1, obj.width, obj.height) ){
			y++;
			now = sprite_down;
		}
		if(!input.down && !input.up) now = sprite;
		obj.setBounds(x-spriteSize/2, y-spriteSize/2, spriteSize, spriteSize);
	}

	private void fire() {
		projectileList.add( new Projectile(x-4,y-4,10));
	}

	public void setHealth(int i) {
		this.hp = i;
		
	}
	
	
}
