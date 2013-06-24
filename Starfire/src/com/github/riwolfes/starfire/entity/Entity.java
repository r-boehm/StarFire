package com.github.riwolfes.starfire.entity;

import java.awt.Rectangle;

import com.github.riwolfes.starfire.graphics.Sprite;

public abstract class Entity {
	
	protected Sprite sprite;
	protected int x, y, dir;
	protected boolean enemie = false, removed = false;
	protected Rectangle obj;
	
	public Entity(){
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void move(){
	}
}
