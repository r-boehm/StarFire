package com.github.riwolfes.starfire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.riwolfes.starfire.entity.Enemy;
import com.github.riwolfes.starfire.entity.Player;
import com.github.riwolfes.starfire.graphics.Screen;
import com.github.riwolfes.starfire.input.Keyboard;

public class EventHandler {

	private Player player;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Random random = new Random();
	private boolean gameover = false;
	
	private Keyboard key;
	private Screen screen;
	
	public EventHandler(Keyboard pKey, Screen pScreen){
		this.key    = pKey;
		this.screen = pScreen;
		init();
	}

	public void update(){
		player.update();
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
		}
		if(!player.projectileList.isEmpty()){
			for(int i = 0; i<player.projectileList.size();i++){
				player.projectileList.get(i).update();
				if(player.projectileList.get(i).getX()>=screen.width)player.projectileList.remove(i);
				if(!enemies.isEmpty()){
					for(int y = 0; y < enemies.size(); y++){
						if(i<player.projectileList.size()&& player.projectileList.get(i).getRect().intersects(enemies.get(y).getRect())){
							enemies.get(y).setHealth(enemies.get(y).getHealth()-10);
							if(enemies.get(y).getHealth()<=0){
								enemies.remove(y);
								enemies.add(new Enemy(150+random.nextInt(screen.width-150), 50+random.nextInt(screen.height-50), screen));
								player.setHealth(player.getHealth()+5);
								player.addScore(10);
							}
							player.projectileList.remove(i);
						}
					}
				}
			}
		}
		if(!enemies.isEmpty()){
			for(int i = 0; i < enemies.size(); i++){
				if(!enemies.get(i).projectileList.isEmpty()){
					enemies.get(i).projectileList.get(0).update();
					if(enemies.get(i).projectileList.get(0).getX() <= 0)enemies.get(i).projectileList.remove(0);
					if(!enemies.get(i).projectileList.isEmpty() && enemies.get(i).projectileList.get(0).getRect().intersects(player.getRect())){
						player.setHealth(player.getHealth()-10);
						if(player.getHealth()<=0)gameover = true;
						enemies.get(i).projectileList.remove(0);
					}
				}
			}
		}
	}
	
	private void init() {
		this.player = new Player(50 ,50, key, screen);
		enemies.add(new Enemy(random.nextInt(screen.width)*2-screen.width,random.nextInt(screen.height)*2-screen.height,screen));
		enemies.add(new Enemy(random.nextInt(screen.width)*2-screen.width,random.nextInt(screen.height)*2-screen.height,screen));
		enemies.add(new Enemy(random.nextInt(screen.width)*2-screen.width,random.nextInt(screen.height)*2-screen.height,screen));
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public List<Enemy> getEnemies(){
		return this.enemies;
	}

	public boolean getGameOver() {
		return gameover;
		
	}

	public void reset() {
		gameover = false;
		enemies  = new ArrayList<Enemy>();
		init();
	}
}
