package com.RKSZGame;

import java.io.IOException;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.openal.*;

public class Play extends BasicGameState{
	private static int MAX_BOMBS = 2;
	private static int GAME_WIDTH = 1000;
	private static int GAME_HEIGHT = 550;
	private Animation player, moveUp, moveDown, moveLeft, moveRight;
	Image board, block, background;
	
	private Audio stageMusic, damage, bombSet, bombExplode;

	private int[] duration = {200, 200};
	private Bomb[] bombs;
	int cooldown;
	
	private float playerX = 215;
	private float playerY = 85;
	
	private boolean firstPass;
	
	int health;
	int activeBombs;
	int currentBomb;
	
	public Play(int state){
		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//TODO: Get sprites 
		Image[] mUP = {new Image("res/Zornespritefs.png"), new Image("res/Zornespritefs.png")};
		Image[] mDown = {new Image("res/Zornespritefs.png"), new Image("res/Zornespritefs.png")};
		Image[] mLeft = {new Image("res/ZornespritefsL1.png"), new Image("res/ZornespritefsL1.png")};
		Image[] mRight = {new Image("res/Zornespritefs.png"), new Image("res/Zornespritefs.png")};
		
		board = new Image("res/boardbig.png");
		block = new Image("res/peg.png");
		background = new Image("res/bg.png");
		
		moveUp = new Animation(mUP, duration, false);
		moveDown = new Animation(mDown, duration, false);
		moveLeft = new Animation(mLeft, duration, false);
		moveRight = new Animation(mRight, duration, false);
		
		player = moveDown;

		firstPass = true;
		health = 100;
		activeBombs = 0;
		currentBomb = 0;
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
			//load bomb explode noise from file
			bombExplode =  AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/bom_last.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw();
		board.draw(215, 85);
		for(Bomb b: bombs)
			b.render(gc, g);
		player.draw(playerX, playerY);
		
		for(int col = 135; col <= GAME_HEIGHT; col+= 100)
			for(int row = 265; row <= GAME_WIDTH; row+= 100)
				block.draw(row, col);
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
			currentBomb = (activeBombs == 0) ? 1 : 2;
			if(bombs[currentBomb].getIsAlive() == false){
				bombs[currentBomb] = new Bomb(playerX, playerY, 0, bombExplode);
				activeBombs++;
				cooldown = 15;
				bombSet.playAsSoundEffect(1.0f, 1f, false);
			}
			else
				currentBomb = (currentBomb == 1) ? 2 : 1;

			if(bombs[currentBomb].getIsAlive() == false){
				bombs[currentBomb] = new Bomb(playerX, playerY, 0, bombExplode);
				activeBombs++;
				cooldown = 20;
				bombSet.playAsSoundEffect(1.0f, 1f, false);
			}
		}
		if(input.isKeyDown(Input.KEY_UP)){
			if(playerY > 85){
				playerY-= 2.5;
			}
		}
		else if(input.isKeyDown(Input.KEY_DOWN)){
			if(playerY < 575){
				playerY+= 2.5;
			}
		}
		else if(input.isKeyDown(Input.KEY_LEFT)){
			if(playerX > 215){
				player = moveLeft;
				playerX-= 2.5;
			}
		}
		else if(input.isKeyDown(Input.KEY_RIGHT)){
			if(playerX < 1004){
				playerX+= 2.5;
			}
		}

		else
			player = moveDown;
		
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			sbg.enterState(0);
		}
		
		for(Bomb b: bombs){
			if(b.updated(b.getTime()+1) == false && activeBombs > 0){
				activeBombs = (activeBombs == 2) ? 1 : 0;
				
			}
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
