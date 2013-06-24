package com.github.riwolfes.starfire.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

public class Screen {

	public int width;
	public int height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	public int tileSize = (int) Math.sqrt(16);
	
	public Boolean doneNotification = false;

	public int xOffset, yOffset;

	int xBackground, yBackground;
	int xx, yy, tileIndex;

	public static Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = Screen.random.nextInt(0xffffff);
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderEntity(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				if (sprite.pixels[x + y * sprite.SIZE] != 0) {
					pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];
				}
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void renderBackground() {
		int xp = 0, yp = 0;
		for(int x = 0; x < 1; x++){
			for(int y = 0; y < 10; y++){
				pixels[xp + yp * width] = 0xFFFFFF;
				xp = x + random.nextInt(width-5);
				yp = y + random.nextInt(height-10);
			}
		}

	}

	public void addOffset() {
		xBackground++;
	}

	public void screenshot() {
		try {
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			image.setRGB(0, 0, width, height, pixels, 0, width);
		    SimpleDateFormat sdfDate = new SimpleDateFormat("HH-mm-ss");
		    Date now = new Date();
			File file = new File("screenshots/"+sdfDate.format(now)+".png");
			file.mkdirs();
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void capture() {
	}

	public void renderNotification() {
		for(int x = width-150; x < width; x++){
			for(int y = 0; y < 100; y++){
				pixels[x + y * width] = 0xFFFFFF;
			}
		}
	}
}