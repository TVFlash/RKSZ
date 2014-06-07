package com.RKSZGame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Bomb {

    private float x;
    private float y;
    private int time;
    private boolean isAlive;
	private Animation bomb;
	private int[] duration = {14, 15};
	private SpriteSheet bombSheet;
	private Animation bombmap; 
	//Used for creating empty array of bombs
	public Bomb(){
		isAlive = false;
	}
	
	public Bomb(float x, float y, int time) throws SlickException{
		this.x = x;
		this.y = y;
		this.time = time;
		isAlive = true;
		
		//TODO: Get sprites 
		Image[] bombBurst = {new Image("res/block.png"), new Image("res/block2.png")};
		bomb = new Animation(bombBurst, duration, false);
		
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
		if(time == 60){
			isAlive = false;
			return false;
		}
		
		else
			this.time++;
		return true;
	}
	public void render(GameContainer gc, Graphics  g) throws SlickException{
		if(isAlive){
			bombmap.draw(x, y);
			//bomb.draw(x, y);
		}
	}
}
