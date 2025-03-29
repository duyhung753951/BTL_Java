package utilz;

import main.Game;

public class Constants {
	public static class UI{
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT=140;
			public static final int B_HEIGHT_DEFAULT=56;
			public static final int B_HEIGHT=(int) (B_HEIGHT_DEFAULT*Game.SCALE);

			public static final int B_WIDTH=(int) (B_WIDTH_DEFAULT*Game.SCALE);
		}
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT=42;
			public static final int SOUND_SIZE=(int)(SOUND_SIZE_DEFAULT*Game.SCALE);
			

		}
	
	
	public static class URMButtons{
		public static final int URM_DEFAULT_SIZE=56;
		public static final int URM_SIZE=(int)(URM_DEFAULT_SIZE*Game.SCALE);
		

		}
	public static class VolumeButtons{
		public static final int VOLUME_DEFAULT_WIDTH=28;
		public static final int VOLUME_DEFAULT_HEIGHT=44;
		public static final int SLIDER_DEFAULT_WIDTH=215;

		public static final int VOLUME_WIDTH=(int)(VOLUME_DEFAULT_WIDTH*Game.SCALE);
		public static final int VOLUME_HEIGHT=(int)(VOLUME_DEFAULT_HEIGHT*Game.SCALE);
		public static final int SLIDER_WIDTH=(int)(SLIDER_DEFAULT_WIDTH*Game.SCALE);


		}
}
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants{
		//FINN
		public static final int ATTACK_1 = 0;
		public static final int DEATH = 1;
		public static final int TAKE_HIT = 2;
		public static final int IDLE = 3;
		public static final int JUMP = 4;
		public static final int RUNNING = 5;
		
		public static int getSpriteAmount(int player_action) {
			
			//FINN
			switch (player_action) {
			case ATTACK_1:
				return 5;
			case JUMP:
				return 1;
			case TAKE_HIT:
				return 3;
			case IDLE:
				return 9;
			case DEATH:
				return 4;
			case RUNNING:
				return 6;
			default:
				return 1;
			}
			
		}
		
	}
	
}
