package com.RKSZGame;

import org.newdawn.slick.*;

public class Bomb {

    private float x;
    private float y;
    private int time;
    int bombID;
    private boolean isAlive;
	private Animation bomb;
	private SpriteSheet bombSheet;
	private Animation bombmap; 
	//Used for creating empty array of bombs
	public Bomb(){
		isAlive = false;
		bombID = 0;
	}
	
	public Bomb(float x, float y, int time, int id) throws SlickException{
		this.x = x;
		this.y = y;
		this.time = time;
		bombID = id;
		isAlive = true;
		
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
		if(time == 60){
			isAlive = false;
		}
		else
			this.time++;
		return isAlive;
	}

	public void render(GameContainer gc, Graphics  g) throws SlickException{
		if(isAlive){
			bombmap.draw(x, y);
			//bomb.draw(x, y);
		}
	}
}
