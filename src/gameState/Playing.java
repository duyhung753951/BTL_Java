package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

import static main.Game.SCALE;
import static utilz.Constants.Environment.*;

public class Playing extends State implements Statemethods{
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private boolean paused=false;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;

	private int xLvlOffset;
	/*
	chia doi ranh gioi trai va phai 5:5 de giu nvat o giua man hinh
	*/
	private final int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private final int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private final int lvlTilesWide = LoadSave.GetLevelData()[0].length;		// chieu rong tiles cua level
	private final int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;	// so luong tiles du ra man hinh (phan co the cuon)
	private final int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;		// maxTilesOffset theo pixel

	private BufferedImage backgroundImg, bigCloudImg, smallCloudImg;
	private int[] smallCloudsPos;
	private Random rand = new Random();

	private boolean gameOver;
	private boolean playerDying;
	public Playing(Game game) {
		super(game);
		initClasses();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		bigCloudImg = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
		smallCloudImg = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
		smallCloudsPos = new int[8];
		for(int i = 0; i < smallCloudsPos.length; i++){
			smallCloudsPos[i] = (int)(70 * Game.SCALE) + rand.nextInt((int) (100 * Game.SCALE));
		}
	}
	
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(200, 180, (int) (32*SCALE), (int) (32*SCALE), this);
		player.loadLvData(levelManager.getCurrentLevel().getLvData());
		pauseOverlay=new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
	}
	


	@Override
	public void update() {
		if (!paused) {
			levelManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLvData(), player);
			checkCloseToBorder();
		}else {
			pauseOverlay.update();
		}
		if (gameOver) {
			gameOverOverlay.update();

		}else if (playerDying) {
			player.update();
		}
		
		
		
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitBox().x;
		int diff = playerX - xLvlOffset; // vị trí hiện tại của player trên màn hình (luôn được đảm bảo nằm trong các border)

		if (diff > rightBorder) {
			xLvlOffset += (diff - rightBorder);
		}else if (diff < leftBorder) {
			xLvlOffset += (diff - leftBorder);
		}
		if(xLvlOffset > maxLvlOffsetX){
			xLvlOffset = maxLvlOffsetX;
		}else if(xLvlOffset < 0){
			xLvlOffset = 0;
		}
	}


	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg,0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		drawClouds(g);
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (gameOver)
			gameOverOverlay.draw(g);
		
		
	}

	private void drawClouds(Graphics g) {
		for(int i = 0; i < 5; i++){
			g.drawImage(bigCloudImg,0+i*BIG_CLOUD_WIDTH - (int)(xLvlOffset * 0.3),(int)(204 * SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		}
		for(int i = 0; i < 5; i++) {
			g.drawImage(smallCloudImg, SMALL_CLOUD_WIDTH * 4 * i - (int)(xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
		}
	}

	//Hoang
	public void resetAll() {
		playerDying = false;
		gameOver = false;
		paused = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver)
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAttacking(true);
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mousePressed(e);

		}else {
			gameOverOverlay.mousePressed(e);
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseReleased(e);
		}else {
			gameOverOverlay.mouseReleased(e);
		}

	}

	@Override
	public void mousedMoved(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mousedMoved(e);
		}else {
			gameOverOverlay.mouseMoved(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
			switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					player.setLeft(true);
					break;
				case KeyEvent.VK_D:
					player.setRight(true);
					break;
				case KeyEvent.VK_W, KeyEvent.VK_SPACE:
					player.setJump(true);
					break;
                case KeyEvent.VK_ESCAPE:
					paused = !paused;
					break;
			}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					player.setLeft(false);
					break;
				case KeyEvent.VK_D:
					player.setRight(false);
					break;
				case KeyEvent.VK_W, KeyEvent.VK_SPACE:
					player.setJump(false);
					break;
			}

	}

	public void mouseDragged(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mouseDragged(e);
	}

	public void unpauseGame() {
		paused=false;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayerDying(boolean playerDying) {
		this.playerDying = playerDying;

	}

}
