package gameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utilz.LoadSave;

import static main.Game.SCALE;

public class Playing extends State implements Statemethods{
	private Player player;
	private LevelManager levelManager;
	private boolean paused=false;
	private PauseOverlay pauseOverlay;

	private int xLvlOffset;
	/*
	chia doi ranh gioi trai va phai 5:5 de giu nvat o giua man hinh
	*/
	private int leftBorder = (int) (0.5 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.5 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;		// chieu rong tiles cua level
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;	// so luong tiles du ra man hinh (phan co the cuon)
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;		// maxTilesOffset theo pixel

	public Playing(Game game) {
		super(game);
		initClasses();
	}
	
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		player = new Player(200, 180, (int) (32*SCALE), (int) (32*SCALE));
		player.loadLvData(levelManager.getCurrentLevel().getLvData());
		pauseOverlay=new PauseOverlay(this);
		
	}
	


	@Override
	public void update() {
		if (!paused) {
			levelManager.update();
			player.update();
			checkCloseToBorder();
		}else {
			pauseOverlay.update();
		}
		
		
		
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitBox().x;
		int diff = playerX - xLvlOffset;

		if (diff > leftBorder) {
			xLvlOffset += diff - rightBorder;
		}else if (diff < leftBorder) {
			xLvlOffset += diff - leftBorder;
		}
		if(xLvlOffset > maxLvlOffsetX){
			xLvlOffset = maxLvlOffsetX;
		}else if(xLvlOffset < 0){
			xLvlOffset = 0;
		}
	}


	@Override
	public void draw(Graphics g) {
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		if (paused) {
			pauseOverlay.draw(g);
		}
		
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {	//button 1 is left mouse
			player.setAttacking(true);
		}
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if (paused) {
			pauseOverlay.mousePressed(e);
		}
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (paused) {
			pauseOverlay.mouseReleased(e);
		}
		
	}


	@Override
	public void mousedMoved(MouseEvent e) {
		if (paused) {
			pauseOverlay.mousedMoved(e);
		}
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setJump(true);
			break;
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_S:
			player.setDown(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		case KeyEvent.VK_ESCAPE:
			paused=!paused;
			
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setJump(false);
			break;
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_S:
			player.setDown(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
		}
		
		
	}
	public void mouseDragged(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseDragged(e);
	}

	public void unpauseGame() {
		paused=false;
	}
	public Player getPlayer() {
		return player;
	}
}
