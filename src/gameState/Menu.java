package gameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg,backgroundImgBlue;
	private int menuX,menuY,menuWitdh,menuHeight;
	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
		backgroundImgBlue=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
		// TODO Auto-generated constructor stub
	}

	private void loadBackground() {
		backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);

		menuWitdh=(int) (backgroundImg.getWidth()*Game.SCALE);
		menuHeight=(int) (backgroundImg.getHeight()*Game.SCALE);
		menuX=Game.GAME_WIDTH/2-menuWitdh/2;
		menuY=Game.GAME_HEIGHT/2-menuHeight/2;
	}

	private void loadButtons() {
		buttons[0]=new MenuButton(Game.GAME_WIDTH/2,(int)(100*Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1]=new MenuButton(Game.GAME_WIDTH/2,(int)(170*Game.SCALE), 1, Gamestate.OPTION);
		buttons[2]=new MenuButton(Game.GAME_WIDTH/2,(int)(240*Game.SCALE), 2, Gamestate.QUIT);

	}

	@Override
	public void update() {
		for (MenuButton mb : buttons) {
			mb.update();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImgBlue, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(backgroundImg, menuX, menuY-80, menuWitdh, menuHeight, null);
		for (MenuButton mb : buttons) {
			mb.draw(g);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				if (mb.getState() == Gamestate.PLAYING)
					game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
				break;
			}
		}
		resetButtons();
	}

	private void resetButtons() {
		for (MenuButton mb : buttons) {
			mb.resetBools();
		}
		
	}

	@Override
	public void mousedMoved(MouseEvent e) {
		for (MenuButton mb : buttons) {
			
				mb.setMouseOver(false);
			
		}
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
