package com.RKSZGame;//Slick 8

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame {
	
	public static final String gamename = "RKSZ";
	public static final int menu = 0;
	public static final int game = 1;
	public static final int boss = 2;
	
	public Game(String title){
		super(title);
		this.addState(new Menu(menu));
		this.addState(new Play(game));
		this.addState(new Boss(boss));
	}
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
		this.getState(game).init(gc, this);
		this.getState(boss).init(gc, this);
		
		this.enterState(menu);
	}

	public static void main(String[] args) {
		AppGameContainer gc;
		try{
			gc = new AppGameContainer(new Game(gamename));
			gc.setDisplayMode(1280, 720, false);
			gc.setTargetFrameRate(59);
			gc.start();
		} catch(SlickException e){
			e.printStackTrace();
		}
	}
}
