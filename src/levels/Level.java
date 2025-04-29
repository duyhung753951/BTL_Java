package levels;


import entities.Boss;
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
	private ArrayList<Boss> bosses;

	private  int lvlTilesWide;		// chieu rong tiles cua level
	private  int maxTilesOffset ;	// so luong tiles du ra man hinh (phan co the cuon)
	private  int maxLvlOffsetX  ;		// maxTilesOffset theo pixel

	private int lvlTilesLength;		// chieu dai tiles cua level
	private int maxTilesOffsetY;	// so luong tiles du ra man hinh (phan co the cuon)
	private int maxLvlOffsetY;

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

		lvlTilesLength = img.getHeight();
		maxTilesOffsetY = lvlTilesLength - Game.TILES_IN_HEIGHT;
		maxLvlOffsetY = Game.TILES_SIZE * maxTilesOffsetY;
	}
	private void createEnemies() {
		crabs = GetCrabs(img);
		bosses = GetBoss(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}
	public int getLvlOffsetX() {
		return maxLvlOffsetX;
	}

	public int getLvlOffsetY() {return maxLvlOffsetY;}

	public ArrayList<Crabby> getCrabs() {
		return crabs;
	}

	public ArrayList<Boss> getBosses() {
		return bosses;
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
