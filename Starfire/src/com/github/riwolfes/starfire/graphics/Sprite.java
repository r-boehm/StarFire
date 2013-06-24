package com.github.riwolfes.starfire.graphics;

public class Sprite {

	public final int SIZE;
	private int x,y;
	public int[] pixels;
	public Spritesheet sheet;
	
	public static Sprite player       = new Sprite(32,1,0,Spritesheet.player);
	public static Sprite player_up    = new Sprite(32,0,1,Spritesheet.player);
	public static Sprite player_down  = new Sprite(32,1,1,Spritesheet.player);
	public static Sprite projectile   = new Sprite(16,0,0,Spritesheet.projectiles);
	public static Sprite enemies 	  = new Sprite(32,0,0,Spritesheet.enemies);
	
	public Sprite(int size, int color){
		this.SIZE = size;
		this.pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	public Sprite(int x, int y, Spritesheet sheet){
		this.pixels = new int[x * y];
		this.sheet  = sheet;
		this.SIZE   = x;
		load();
	}
	
	public Sprite(int size, int x, int y, Spritesheet sheet){
		this.SIZE = size;
		this.pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	private void setColor(int color){
		for(int i=0; i < SIZE * SIZE; i++){
			pixels[i] = color;
		}
	}
	
	private void load(){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x< SIZE; x++){
				if(sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE] != 0xffff00ff ){
					pixels[x+y*SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];}
			}
		}
	}
}
