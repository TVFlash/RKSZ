package com.RKSZGame;

import java.io.IOException;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.openal.*;
import org.newdawn.slick.openal.SoundStore;

public class Play extends BasicGameState{
	private static int MAX_BOMBS = 2;
	private Animation player, moveUp, moveDown, moveLeft, moveRight;
	
	private Audio stageMusic;
	private Audio damage;
	private Audio bombSet;

	private int[] duration = {200, 200};
	private Bomb[] bombs;
	int cooldown;
	
	private float playerX = 0;
	private float playerY = 0;
	
	private boolean firstPass;
	
	int health;
	int activeBombs;
	
	public Play(int state){
		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//TODO: Get sprites 
		Image[] mUP = {new Image("res/Zornespritefs.png"), new Image("res/Zornespritefs.png")};
		Image[] mDown = {new Image("res/Zornespritefs.png"), new Image("res/Zornespritefs.png")};
		Image[] mLeft = {new Image("res/ZornespritefsL1.png"), new Image("res/ZornespritefsL1.png")};
		Image[] mRight = {new Image("res/Zornespritefs.png"), new Image("res/Zornespritefs.png")};
		
		moveUp = new Animation(mUP, duration, false);
		moveDown = new Animation(mDown, duration, false);
		moveLeft = new Animation(mLeft, duration, false);
		moveRight = new Animation(mRight, duration, false);
		
		player = moveDown;

		firstPass = true;
		health = 100;
		activeBombs = 0;
		cooldown = 0;
		
		bombs = new Bomb[3];
		for(int i = 0; i < bombs.length; i++)
			bombs[i] = new Bomb();
		
		/** Load IMG assets **/
	    try {
	    	//Load Stage music from file
			stageMusic = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("res/FreudiasStage.ogg"));
			//Load damage noise from file
			damage =  AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/hit.wav"));
			//Load bomb set noise from file
			bombSet =  AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/bom_set.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		player.draw(playerX, playerY);
		
		for(Bomb b: bombs)
			b.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		//Load in stage music iff first pass
		if(firstPass){
			stageMusic.playAsMusic(1.0f, 1.0f, true);
			firstPass = false;
		}
		
		if(activeBombs < MAX_BOMBS && input.isKeyDown(Input.KEY_X) && cooldown == 0){
			bombs[activeBombs] = new Bomb(playerX, playerY, 0);
			activeBombs++;
			bombs[0].updated(delta);

			cooldown = 15;//May need change on final release
			bombSet.playAsSoundEffect(1.0f, 1f, false);
		}
		if(input.isKeyDown(Input.KEY_UP)){
			playerY-= 2;
			//if(playerY > topOfMap)
				//playerY += .25;
		}
		else if(input.isKeyDown(Input.KEY_DOWN)){
			playerY+= 2;
		}
		else if(input.isKeyDown(Input.KEY_LEFT)){
			player = moveLeft;
			playerX-= 2;
		}
		else if(input.isKeyDown(Input.KEY_RIGHT)){
			playerX+= 2;
		}

		else
			player = moveDown;
		
		if(input.isKeyDown(Input.KEY_ESCAPE))
			sbg.enterState(0);
		
		for(Bomb b: bombs){
			if(b.updated(b.getTime()+1) == false && activeBombs > 0)
				activeBombs--;
		}
		
		SoundStore.get().poll(0);
		if(cooldown > 0)
			cooldown--;
	}

	@Override
	public int getID() {
		return 1;
	}
}
