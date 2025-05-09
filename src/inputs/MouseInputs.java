package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gameState.Gamestate;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener{

	private GamePanel gamePanel;
	
	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (Gamestate.state) {
		case PLAYING:
			gamePanel.getGame().getPlaying().mouseDragged(e);
			break;
			case OPTION:
				gamePanel.getGame().getGameOptions().mouseDragged(e);
				break;
		default:
			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mousedMoved(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().mousedMoved(e);
			break;
			case OPTION:
				gamePanel.getGame().getGameOptions().mousedMoved(e);
				break;
		default:
			break;
		
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(Gamestate.state) {
		
		case PLAYING:
			gamePanel.getGame().getPlaying().mouseClicked(e);
			break;
		default:
			break;
		
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mousePressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().mousePressed(e);
			break;
			case OPTION:
				gamePanel.getGame().getGameOptions().mousePressed(e);
				break;
		default:
			break;
		
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().mouseReleased(e);
			break;
			case OPTION:
				gamePanel.getGame().getGameOptions().mouseReleased(e);
				break;
		default:
			break;
		
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
