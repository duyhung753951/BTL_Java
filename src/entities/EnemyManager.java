package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameState.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][] FierceToothArr;
	private ArrayList<FierceTooth> fierceTeeth = new ArrayList<>();
	private int xLvlOffset = (int) (6 * Game.SCALE);

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}

	private void addEnemies() {
		fierceTeeth = LoadSave.GetFierceTooth();
		System.out.println("size of Fierce Tooth: " + fierceTeeth.size());
	}

	public void update() {
		for (FierceTooth c : fierceTeeth)
			c.update();
	}

	public void draw(Graphics g) {
		drawFierceTooth(g);
	}

	private void drawFierceTooth(Graphics g) {
		for (FierceTooth c : fierceTeeth) {
			g.drawImage(FierceToothArr[c.getEnemyState()][c.getAniIndex()], (int) c.getHitBox().x - xLvlOffset, (int) c.getHitBox().y, FIERCETOOTH_WIDTH, FIERCETOOTH_HEIGHT, null);
		}
	}

	private void loadEnemyImgs() {
		FierceToothArr = new BufferedImage[7][8];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.FIERCETOOTH_SPRITE);
		for (int j = 0; j < FierceToothArr.length; j++)
			for (int i = 0; i < FierceToothArr[j].length; i++)
				FierceToothArr[j][i] = temp.getSubimage(i * 27, j * 27, FIERCETOOTH_WIDTH_DEFAULT, FIERCETOOTH_HEIGHT_DEFAULT);
	}
}
