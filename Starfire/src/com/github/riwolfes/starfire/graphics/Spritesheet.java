package com.github.riwolfes.starfire.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	public String path;
	public final int SIZE;
	public int[] pixels;
	
	public static Spritesheet enemies     = new Spritesheet("/textures/enemies.png", 256);
	public static Spritesheet player      = new Spritesheet("/textures/player.png", 256);
	public static Spritesheet projectiles = new Spritesheet("/textures/projectiles.png",256);
	public static Spritesheet background = new Spritesheet("/textures/background.png",300,168);
	
	public Spritesheet(String path, int size){
		this.path = path;
		this.SIZE = size;
		pixels 	  = new int[getSIZE() * getSIZE()];
	    load();
	}
	
	public Spritesheet(String path, int x, int y){
		this.path = path;
		this.SIZE = x;
		pixels 	  = new int[x * y];
	    load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(Spritesheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public int getSIZE() {
		return SIZE;
	}
}
