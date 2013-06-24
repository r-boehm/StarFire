package com.github.riwolfes.starfire.entity;

import java.awt.Rectangle;

import com.github.riwolfes.starfire.graphics.Screen;
import com.github.riwolfes.starfire.graphics.Sprite;

public class Projectile extends Entity {

	private Sprite sprite = Sprite.projectile;
	private int velocity = 0;

	public Projectile(int x, int y, int velocity) {
		this.x = x;
		this.y = y;
		this.velocity = velocity;
		obj = new Rectangle(this.x-sprite.SIZE/2,this. y-sprite.SIZE/2, sprite.SIZE, sprite.SIZE);
	}

	public void render(Screen screen) {
		screen.renderEntity(this.x, this.y, sprite);
		obj.setBounds(this.x-sprite.SIZE/2, this.y-sprite.SIZE/2, sprite.SIZE, sprite.SIZE);
	}
	
	public Rectangle getRect(){
		return obj;
	}
	
	public void update(){
		x += velocity;
	}
}
