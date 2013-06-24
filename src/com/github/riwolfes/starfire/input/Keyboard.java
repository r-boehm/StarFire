package com.github.riwolfes.starfire.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[150];
	public boolean up, down, left, right,space, f1, f2, esc,inventory, chat;
	
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE];
		f1 = keys[KeyEvent.VK_F1];		 //112
		f2 = keys[KeyEvent.VK_F2];
		esc= keys[KeyEvent.VK_ESCAPE];	 //27
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if(i != 113 && i != 69 && i != 10) keys[e.getKeyCode()] = true	;
		else keys[e.getKeyCode()] = !keys[e.getKeyCode()];
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		if(i != 113 && i != 69 && i != 10) keys[e.getKeyCode()] = false	;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
