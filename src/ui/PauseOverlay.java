package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameState.Gamestate;
import gameState.Playing;
import main.Game;
import utilz.Constants.UI.URMButtons;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;
public class PauseOverlay {
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX,bgY,bgWidth,bgHeight;
	private AudioOptions audioOptions;
	private UrmButton menuB,replayB,unpauseB;

	public PauseOverlay(Playing playing) {
		this.playing=playing;
		loadBackground();
		audioOptions=playing.getGame().getAudioOptions();
		createUrmButtons();

		
	}

	private void createUrmButtons() {
		int menuX=(int)(313*Game.SCALE);
		int replayX=(int)(387*Game.SCALE);
		int unpauseX=(int)(462*Game.SCALE);
		int bY=(int)(325*Game.SCALE);
		
		menuB=new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB=new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB=new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
	}

	private void loadBackground() {
		backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgWidth=(int)(backgroundImg.getWidth()*Game.SCALE);
		bgHeight=(int)(backgroundImg.getHeight()*Game.SCALE);
		bgX=Game.GAME_WIDTH/2-bgWidth/2;
		bgY=(int) (25*Game.SCALE);
		
	}
	public void update() {


		menuB.update();
		unpauseB.update();
		replayB.update();

		audioOptions.update();
	}
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);

		//UrmButtons
		menuB.draw(g);
		unpauseB.draw(g);
		replayB.draw(g);
		audioOptions.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);

	}
	
	public void mousePressed(MouseEvent e) {
	 if(isIn(e, menuB)){
			menuB.setMousePressed(true);
		}else if(isIn(e, replayB)){
			replayB.setMousePressed(true);
		}else if(isIn(e, unpauseB)){
			unpauseB.setMousePressed(true);
		}else audioOptions.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
	 if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) 
				//Gamestate.state=Gamestate.MENU;
				playing.setGamestate(Gamestate.MENU);
			playing.unpauseGame();
		}else if (isIn(e, replayB)) { 
			if (replayB.isMousePressed()) {
				playing.resetAll();
				playing.unpauseGame();
			}

	    }else if (isIn(e, unpauseB)) { 
			if (unpauseB.isMousePressed()) 
			playing.unpauseGame();
	    }else audioOptions.mouseReleased(e);
		
			

			menuB.resetBools();
			replayB.resetBools();
			unpauseB.resetBools();

	}

	public void mousedMoved(MouseEvent e) {
		menuB.setMouseOver(false);;
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);

		 if (isIn(e, menuB)){
			menuB.setMouseOver(true);
			}else if (isIn(e, unpauseB)) {
				unpauseB.setMouseOver(true);
			}else if (isIn(e, replayB)) {
				replayB.setMouseOver(true);
			}else audioOptions.mouseMoved(e);
			
			
		}
		
	
	private boolean isIn(MouseEvent e,PauseButton b) {
		 return (b.getBounds().contains(e.getX(),e.getY())) ;
		
	}
}
