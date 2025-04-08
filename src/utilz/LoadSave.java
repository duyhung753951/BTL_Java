package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.FierceTooth;
import main.Game;

import static utilz.Constants.EnemyConstants.FIERCETOOTH;

public class LoadSave {

//	public static final String PLAYER_ATLAS = "Assets/Character/FantasyWarrior/sprites.png";
	public static final String FINN_ATLAS = "Assets/Character/FinnSprites.png";
	public static final String LEVEL_ATLAS = "Assets/Levels/outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "Assets/Levels/level_one_data.png";
	public static final String MENU_BUTTONS = "Assets/Menu/button_atlas.png";
	public static final String MENU_BACKGROUND = "Assets/Menu/menu_background.png";
	public static final String PAUSE_BACKGROUND = "Assets/Menu/pause_menu.png";
	public static final String  SOUND_BUTTONS= "Assets/Menu/sound_button.png";
	public static final String  URM_BUTTONS= "Assets/Menu/urm_buttons.png";
	public static final String  VOLUME_BUTTONS= "Assets/Menu/volume_buttons.png";
	// enemy sprites
	public static final String  FIERCETOOTH_SPRITE= "Assets/Monsters/FierceTooth/ToothSprites.png";
	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		
		try {
			img = ImageIO.read(is);		

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			}catch (IOException e) {
				e.printStackTrace();
			}catch (NullPointerException e) {
	            System.err.println("Error: Sai dia chi anh.");
			}
		}
		return img;
	}

	public static ArrayList<FierceTooth> GetFierceTooth() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<FierceTooth> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == FIERCETOOTH)
					list.add(new FierceTooth(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
			}
		return list;
	}
	
	public static int[][] GetLevelData(){
		int[][] lvData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		
		for(int j = 0; j < img.getHeight(); j++){
			for(int i = 0; i < img.getWidth(); i++){
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if(value >= 48) value = 0;		// incase we have more than 48 red value (index that doesn't exist)
				lvData[j][i] = value;
			}
		}
		return lvData;
	}
	
}
