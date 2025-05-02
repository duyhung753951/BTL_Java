package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Crabby;
import static utilz.Constants.EnemyConstants.CRABBY;
import main.Game;

public class LoadSave {


	public static final String FINN_ATLAS = "Assets/Character/FinnSprites.png";
	public static final String LEVEL_ATLAS = "Assets/Levels/outside_sprites.png";
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
	public static final String COMPLETED_IMG = "Assets/Menu/completed_sprite.png";
	public static final String BOSS_SPRITE = "Assets/BOSS/boss_sprite.png";
	public static final String BOSS_HEALTH_BAR = "Assets/BOSS/boss_health_bar.png";
	public static final String ICON = "Assets/Menu/iconGame.png";

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
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		if (url == null) {
			throw new IllegalArgumentException("Resource directory '/res/lvls' not found in the classpath.");
		}

		File file = null;
		try{
			file = new File(url.toURI());
		}catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (file == null || !file.exists()) {
			throw new IllegalArgumentException("The level" +
					" files directory does not exist: " + url);
		}

		File[] files= file.listFiles();
		if (files == null || files.length == 0) {
			throw new IllegalArgumentException("No level files found " +
					"in the directory: " + file.getAbsolutePath());
		}

		File[]  fileSorted = new File[files.length];
		// sắp xep lại thứ tự map theo tên file của level map 1-->2-->3
		for(int i = 0; i < fileSorted.length; i++) {
			for(int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i+1)+".png")){
					fileSorted[i] = files[j];
				}
			}

		}
		BufferedImage[] imgs = new BufferedImage[fileSorted.length];
		for(int i=0; i<imgs.length; i++) {
			try {
				imgs[i] = ImageIO.read(fileSorted[i]);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imgs;
	}

	

	
}
