package com.RKSZGame;

import org.newdawn.slick.*;
import org.newdawn.slick.openal.*;
import org.newdawn.slick.openal.SoundStore;

public class Bomb {

    private float x;
    private float y;
    private int time;
    private boolean isAlive;
    Audio noise;
    private SpriteSheet bombSheet;
	private Animation bombmap; 
	//Used for creating empty array of bombs
	public Bomb(){
		isAlive = false;
	}
	
	public Bomb(float x, float y, int time, Audio noise) throws SlickException{
		this.x = x;
		this.y = y;
		this.time = time;
		isAlive = true;
		this.noise = noise;
		//TODO: Get sprites 
		
		bombSheet = new SpriteSheet("res/bombmap.png", 32, 32);
		bombmap = new Animation(bombSheet, 200);
	}

	public int getTime(){
		return time;
	}
	
	public boolean getIsAlive(){
		return isAlive;
	}
	
	public boolean updated(int time){
		if(time == 100 && x != 0){
			isAlive = false;
			noise.playAsSoundEffect(1.0f, 1f, false);
		}
			this.time++;
		return isAlive;
	}

	public void render(GameContainer gc, Graphics  g) throws SlickException{
		if(isAlive){
			bombmap.draw(x, y);
		}
	}
}
