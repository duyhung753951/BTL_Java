package utilz;

import main.Game;

public class Constants {
	public static final int ANI_SPEED = 25;
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

	public static class Environment {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	}

	public static class EnemyConstants {
		public static final int CRABBY = 0;
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;

		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;

		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);

		public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

		public static final int BOSS = 10;

		public static final int BOSS_IDLE = 0;
		public static final int BOSS_RUNNING = 1;
		public static final int BOSS_ATTACK = 2;
		public static final int BOSS_HIT = 3;
		public static final int BOSS_DEAD = 4;
		public static final int BOSS_WIDTH_DEFAULT = 250;
		public static final int BOSS_HEIGHT_DEFAULT = 250;

		public static final int BOSS_WIDTH = (int) (BOSS_WIDTH_DEFAULT * Game.SCALE);
		public static final int BOSS_HEIGHT = (int) (BOSS_HEIGHT_DEFAULT * Game.SCALE);

		public static final int BOSS_DRAWOFFSET_X = (int) (98 * Game.SCALE);
		public static final int BOSS_DRAWOFFSET_Y = (int) (76 * Game.SCALE);

		public static int GetSpriteAmount(int enemy_type, int enemy_state) {

			switch (enemy_type) {
				case CRABBY:
					switch (enemy_state) {
						case IDLE:
							return 9;
						case RUNNING:
							return 6;
						case ATTACK:
							return 7;
						case HIT:
							return 4;
						case DEAD:
							return 5;
					}
				case BOSS: {
					switch (enemy_state) {
						case BOSS_IDLE, BOSS_RUNNING, BOSS_ATTACK:
							return 8;
						case BOSS_HIT:
							return 3;
						case BOSS_DEAD:
							return 7;
					}
				}
			}
			return 0;

		}
		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
				case CRABBY:
					return 10;
				case BOSS:
					return 100;
				default:
					return 1;
			}
		}

		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
				case CRABBY:
					return 10;
				case BOSS:
					return 30;
				default:
					return 0;
			}

		}

	}
	
}
