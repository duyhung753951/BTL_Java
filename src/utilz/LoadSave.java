package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {


	public static final String FINN_ATLAS = "Assets/Character/FinnSprites.png";
	public static final String LEVEL_ATLAS = "Assets/Levels/outside_sprites.png";
//	public static final String LEVEL_1_DATA = "Assets/Levels/level_one_data.png";
	public static final String LEVEL_1_DATA = "Assets/Levels/level_one_data_long.png";
	public static final String MENU_BUTTONS = "Assets/Menu/button_atlas.png";
	public static final String MENU_BACKGROUND = "Assets/Menu/menu_background.png";
	public static final String PAUSE_BACKGROUND = "Assets/Menu/pause_menu.png";
	public static final String  SOUND_BUTTONS= "Assets/Menu/sound_button.png";
	public static final String  URM_BUTTONS= "Assets/Menu/urm_buttons.png";
	public static final String  VOLUME_BUTTONS= "Assets/Menu/volume_buttons.png";

	
	
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
	
	public static int[][] GetLevelData(){

		BufferedImage img = GetSpriteAtlas(LEVEL_1_DATA);
		int[][] lvData = new int[img.getHeight()][img.getWidth()];
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
