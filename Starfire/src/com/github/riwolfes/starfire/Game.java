package com.github.riwolfes.starfire;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import com.github.riwolfes.starfire.graphics.Screen;
import com.github.riwolfes.starfire.input.Keyboard;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int width = 300;
	private static int height= width / 16 * 9;
	private static int scale = 3;
	
	private static String TITLE = "Game";
	
	private long lastTime = System.nanoTime();
	private long timer = System.currentTimeMillis();
	private long tTime = 0;
	private double FPS = 60.0;
	private final double ns    =  1000000000.0 / FPS;
	private final double second=  1000000000.0;
	private double delta = 0.0;		
	private double zed   = 0.0;
	private boolean once;
	private int frames = 0;
	private int updates = 0;
	
	private Thread thread;
	private JFrame jFrame;
	private Keyboard key;
	
	private boolean running   = false;
	private boolean gameover = false;
	private boolean keyReset = false;
	
	private Screen screen;
	private EventHandler handler;
	private Font font;
	
	private BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); 
		
	public Game(){
		Dimension size = new Dimension(width * scale,height * scale);
		setPreferredSize(size);
		screen= new Screen(width,height);
		jFrame = new JFrame(); 
		key = new Keyboard();		
		addKeyListener(key);
		//handler = new EventHandler(key, screen);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font/pixel.ttf"));
			font = font.deriveFont(24f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void start(){
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop(){
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while(running){
			requestFocus();
			long now = System.nanoTime();
			tTime = now - lastTime;
			delta += tTime / ns;
			zed   += tTime /second;
			lastTime = now;
			while(delta >= 1){
				update();
				updates++;
				delta--;
			}
			while(zed >= 1){
				once = true;
				zed--;;
			}
			if(once&&key.f1)screen.screenshot();
			if(gameover&&key.space && keyReset){
				handler.reset();
				gameover = false;
			}
			if(key.space)keyReset = false;
			else keyReset = true;
			once = false;
			if(handler!= null)render();
			else startMenu();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				jFrame.setTitle(Game.TITLE + " " + updates + "  ups, " + frames + "fps");
				updates = 0 ;
				frames = 0;
			}
		}
	}

	private void startMenu() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){createBufferStrategy(2);return;}		
		screen.clear();	
		screen.renderBackground();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setFont(font);
		g.drawString(Game.TITLE, screen.width/2, screen.height/2);
		g.drawString("Press Space to Start", screen.width/2, screen.height/2+150);
		if(key.space)handler = new EventHandler(key, screen);
		for(int i = 0; i<pixels.length; i++)pixels[i] = screen.pixels[i];	
		g.dispose();
		bs.show();
	}

	public void update(){
		key.update();
		if(handler!=null){
			handler.update();
			gameover = handler.getGameOver();
		}
		
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){createBufferStrategy(2);return;}		
		screen.clear();	
		renderEntities();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		renderImage(g);
		for(int i = 0; i<pixels.length; i++)pixels[i] = screen.pixels[i];	
		g.dispose();
		bs.show();
	}
	
	private void renderEntities() {
		if(!gameover){
			screen.renderBackground();
			handler.getPlayer().render();
			if(!handler.getPlayer().projectileList.isEmpty()){
				for(int i = 0; i<handler.getPlayer().projectileList.size();i++){
					handler.getPlayer().projectileList.get(i).render(screen);
				}
			}
			if(!handler.getEnemies().isEmpty()){
				for(int i = 0; i < handler.getEnemies().size(); i++){
					if(!handler.getEnemies().get(i).projectileList.isEmpty()){
						handler.getEnemies().get(i).projectileList.get(0).render(screen);
					}
				}
			}
		}
	}
	
	private void renderImage(Graphics g) {
		g.setColor(Color.WHITE);
		if(!gameover){
			g.drawString(Integer.toString(handler.getPlayer().getHealth()), (handler.getPlayer().getX()*3)-20, (handler.getPlayer().getY()*3)-20);
			if(!handler.getEnemies().isEmpty()){
				for(int i = 0; i<handler.getEnemies().size();i++){
					handler.getEnemies().get(i).render();
					g.drawString(Integer.toString(handler.getEnemies().get(i).getHealth()), (handler.getEnemies().get(i).getX()*scale)-30, (handler.getEnemies().get(i).getY()*scale)-20);
				}
			}
		}else{
			g.setFont(font);
			g.drawString("GAME OVER", width/2, height/2);
			g.setColor(Color.RED);
			g.drawString("SCORE: "+handler.getPlayer().getScore(), width/2, height/2+50);
			g.drawString("Press Space to Restart", screen.width/2,screen.height*3-50);
		}
	}


	public static void main(String[] args){
		Game game = new Game();
		game.jFrame.setResizable(false);
		game.jFrame.setTitle(Game.TITLE + " 0 ups, 0 fps");
		game.jFrame.add(game);
		game.jFrame.pack();
		game.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.jFrame.setLocationRelativeTo(null);
		game.jFrame.setVisible(true);
		game.start();
	}
	
}
