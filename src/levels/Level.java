package levels;


import entities.Crabby;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.*;

public class Level {
	private int[][] lvlData;
	private BufferedImage img ;
	private ArrayList<Crabby> crabs;

	private  int lvlTilesWide;		// chieu rong tiles cua level
	private  int maxTilesOffset ;	// so luong tiles du ra man hinh (phan co the cuon)
	private  int maxLvlOffsetX  ;		// maxTilesOffset theo pixel
	private Point playerSpawn;
	public Level(BufferedImage img ) {
		this.img= img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		calcPlayerSpawn();
	}
	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}
	private void createEnemies() {
		crabs = GetCrabs(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public ArrayList<Crabby> getCrabs() {
		return crabs;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLvData() {
		return lvlData;
	}
}
