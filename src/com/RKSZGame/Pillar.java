package com.RKSZGame;

import org.newdawn.slick.*;

public class Pillar {

	int x;
	int y;
	Image pillarPicture;
	
	public Pillar(){
		
	}
	
	public Pillar(int x, int y, Image image){
		this.x = x;
		this.y = y;
		pillarPicture = image;
	}
	
	public boolean inBounds(int objX, int objY){
		if((objX > x && objX < x + 50) && (objY > y && objY < y + 50))
			return true;
		return false;
	}
	
	public void render(GameContainer gc, Graphics  g) throws SlickException{
		pillarPicture.draw(x, y);
	}
}
