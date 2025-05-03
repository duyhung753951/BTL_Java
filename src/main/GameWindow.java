package main;

import utilz.LoadSave;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.swing.JFrame;

import static utilz.LoadSave.ICON;

public class GameWindow {
	private JFrame jframe;
	public GameWindow(GamePanel gamePanel) {

		jframe = new JFrame();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		jframe.setResizable(false);
		jframe.setTitle("Adventure Time");
		jframe.setIconImage(LoadSave.GetSpriteAtlas(ICON));
		jframe.pack();
		jframe.setVisible(true);
		jframe.setLocationRelativeTo(null);
		jframe.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {		// when click out of the game window, we stop moving
				gamePanel.getGame().windowFocusLost();
				
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
