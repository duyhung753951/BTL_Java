package levels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameState.Gamestate;
import main.Game;
import utilz.LoadSave;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex=0;
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	public void loadNextLevel(){
		lvlIndex++;
		if(lvlIndex >= levels.size()) {
			lvlIndex = 0;
			System.out.println("No more levels");
			Gamestate.state = Gamestate.MENU;
		}
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvData(newLevel.getLvData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffsetX(), newLevel.getLvlOffsetY());
	}
	private void buildAllLevels() {
		BufferedImage[] allLvevels = LoadSave.GetAllLevels();
		for(BufferedImage img : allLvevels)
		levels.add(new Level(img));

	}

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 12; i++) {
				int index = j*12 + i;
				levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
			}
		}
		
	}

	public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
		// Calculate which tiles are visible on screen based on the offset
		int firstTileX = xLvlOffset / Game.TILES_SIZE;
		int lastTileX = firstTileX + Game.TILES_IN_WIDTH + 1; // +1 to handle partial tiles

		int firstTileY = yLvlOffset / Game.TILES_SIZE;
		int lastTileY = firstTileY + Game.TILES_IN_HEIGHT + 1; // +1 to handle partial tiles

		// Make sure we don't go out of bounds
		lastTileY = Math.min(lastTileY, getCurrentLevel().getLvData().length);
		lastTileX = Math.min(lastTileX, getCurrentLevel().getLvData()[0].length);

		// Draw tiles
		for(int j = firstTileY; j < lastTileY; j++) {
			for(int i = firstTileX; i < lastTileX; i++) {
				if (i < getCurrentLevel().getLvData()[0].length && j < getCurrentLevel().getLvData().length) {
					int index = getCurrentLevel().getSpriteIndex(i, j);
					g.drawImage(levelSprite[index], i*Game.TILES_SIZE - xLvlOffset, j*Game.TILES_SIZE - yLvlOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
				}
			}
		}
	}
	
	public void update() {
		
	}

	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}
	public int getAmountOfLevels() {
		return levels.size();
	}
	public int getLevelIndex() {
		return lvlIndex;
	}
}
