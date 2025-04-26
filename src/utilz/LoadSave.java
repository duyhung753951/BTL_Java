package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Crabby;
import static utilz.Constants.EnemyConstants.CRABBY;
import main.Game;

public class LoadSave {


	public static final String FINN_ATLAS = "Assets/Character/FinnSprites.png";
	public static final String LEVEL_ATLAS = "Assets/Levels/outside_sprites.png";
	public static final String LEVEL_1_DATA = "Assets/Levels/level_one_data_long.png";
	public static final String MENU_BUTTONS = "Assets/Menu/button_atlas.png";
	public static final String MENU_BACKGROUND = "Assets/Menu/menu_background.png";
	public static final String PAUSE_BACKGROUND = "Assets/Menu/pause_menu.png";
	public static final String SOUND_BUTTONS= "Assets/Menu/sound_button.png";
	public static final String URM_BUTTONS= "Assets/Menu/urm_buttons.png";
	public static final String VOLUME_BUTTONS= "Assets/Menu/volume_buttons.png";
	public static final String PLAYING_BG_IMG = "Assets/Levels/playing_bg_img.png";
	public static final String BIG_CLOUDS = "Assets/Levels/big_clouds.png";
	public static final String SMALL_CLOUDS = "Assets/Levels/small_clouds.png";
	public static final String CRABBY_SPRITE = "Assets/Monsters/Crabby/crabby_sprite.png";
	public static final String STATUS_BAR = "Assets/Menu/health_power_bar.png";
	public static final String DEATH_SCREEN = "Assets/Menu/death_screen.png";
	public static final String OPTIONS_MENU = "Assets/Menu/options_background.png";
	public static final String MENU_BACKGROUND_IMG = "Assets/Menu/menu_background_img1.jpg";
	public static final String MENU_BACKGROUND_IMG2 = "Assets/Menu/menu_background_img.jpg";
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

	public static ArrayList<Crabby> GetCrabs(){
		BufferedImage img = GetSpriteAtlas(LEVEL_1_DATA);
		ArrayList<Crabby> list = new ArrayList<>();
		for(int j = 0; j < img.getHeight(); j++){
			for(int i = 0; i < img.getWidth(); i++){
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if(value == CRABBY){
					list.add(new Crabby(i* Game.TILES_SIZE, j* Game.TILES_SIZE));
				}
			}
		}
		return list;
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
