package main;

import java.awt.Graphics;

import entities.Player;

public class Game implements Runnable{
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;	// frame rate: xu li hinh anh cua game
	private final int UPS_SET = 200;	// Tick rate: xu li mat logic, su kien cua game
	
	private Player player;
	
	public Game() {
		initClasses();		// initiate
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();
		
	}
	
	private void initClasses() {
		player = new Player(200, 200);
		
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		player.update();
	}
	
	public void render(Graphics g) {
		player.render(g);
	}

	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET;	// 1 000 000 000 nanoSecond = 1 Second
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;	// delta time for update
		double deltaF = 0;  // delta time for frame
		
		// using delta time to keep time we waste from previous update and reuse in update
		// exp deltaU += 1.1 second so 0.1 second that we save it to use in next update
		
		while(true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	public void windowFocusLost() {
		player.resetDirBoolean();	// reset moving boolean to false
		
	}
	
	public Player getPlayer() {
		return player;
	}

	
}
