package utilz;

import entities.Crabby;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.CRABBY;

public class HelpMethods {
    /**
     * Kiểm tra thực thể có thể di chuyển tới vị trí được chỉ định hay không dựa trên va chạm với các tiles.
     * @param x Tọa độ X dự định.
     * @param y Tọa độ Y dự định.
     * @param width Chiều rộng hitbox.
     * @param height Chiều cao hitbox.
     * @param lvData Mảng dữ liệu bản đồ.
     * @return Trả về true nếu di chuyển hợp lệ.
     */
    public static boolean CanMoveHere(float x, float y, float width, float height, int [][] lvData){
        return !IsSolid(x, y, lvData)                  // top left
                && !IsSolid(x + width, y, lvData)      // top right
                && !IsSolid(x, y + height, lvData)     // bottom left
                && !IsSolid(x + width, y + height, lvData); // bottom right

    }
    /**
     * Kiểm tra xem ô tile tại vị trí xác định có là vật cản không.
     * @param x Tọa độ pixel X.
     * @param y Tọa độ pixel Y.
     * @param lvData Mảng dữ liệu bản đồ.
     * @return Trả về true nếu ô đó là vật cản.
     */
    public static boolean IsSolid(float x, float y, int[][] lvData){
        int maxWidth = lvData[0].length * Game.TILES_SIZE;
        int maxHeight = lvData.length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth || y < 0 || y >= maxHeight)    // check x, y có vượt ngoài phạm vi game
            return true;

        float xIndex = x / Game.TILES_SIZE;     // tọa độ x trong mảng level data
        float yIndex = y / Game.TILES_SIZE;     // tọa độ y

        // Additional bounds check to avoid ArrayIndexOutOfBoundsException
        if (yIndex >= lvData.length || xIndex >= lvData[0].length)
            return true;

        int value = lvData[(int) yIndex][(int) xIndex]; // lấy giá trị

        if(value >= 48 || value < 0 || value != 11)     // nếu ứng với solid return true
            return true;
        return false;
    }

    /**
     * Trả về tọa độ X kế bên bức tường sau khi va chạm (để thực thể không xuyên qua bức tường).
     * @param hitbox Hitbox của thực thể.
     * @param xSpeed Tốc độ ngang (x-axis).
     * @return Tọa độ X mới ngay sát bức tường.
     */
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if(xSpeed > 0){
            //Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;              // trả lại tọa độ đặt hitbox sát trái tường bên phải
        }else{
            //Left
            return currentTile * Game.TILES_SIZE;       // trả về tọa độ sát phải bức tường bên trái
        }
    }

    /**
     * Xác định tọa độ Y mới sau khi va chạm trần hoặc sàn để đảm bảo nhân vật đứng trên mặt sàn hoặc ngay dưới trần nhà.
     * @param hitbox Hitbox của thực thể.
     * @param airSpeed Tốc độ theo chiều dọc (dương: rơi xuống, âm: nhảy lên).
     * @param lvData Dữ liệu cấp độ bản đồ.
     * @return Tọa độ Y tối ưu sau khi xử lý va chạm.
     */
    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed, int [][] lvData) {
        int currentTile;
        if (airSpeed > 0) {
            // Falling - chạm đất (Floor)
            currentTile = (int) ((hitbox.y + hitbox.height) / Game.TILES_SIZE);
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (hitbox.height);
            if (IsSolid(hitbox.x, tileYPos, lvData) || IsSolid(hitbox.x + hitbox.width, tileYPos, lvData)) {
                return tileYPos - yOffset - 1;
            }
        } else {
            // Rising - chạm trần (Roof)
            currentTile = (int) (hitbox.y / Game.TILES_SIZE);
            int tileYPos = currentTile * Game.TILES_SIZE;
            if (IsSolid(hitbox.x, tileYPos, lvData) || IsSolid(hitbox.x + hitbox.width, tileYPos, lvData)) {
                return tileYPos + Game.TILES_SIZE;
            }
        }
        return hitbox.y;
    }


    /**
     * Kiểm tra xem nhân vật có đang đứng trên mặt sàn hay không.
     * @param hitbox Hitbox của nhân vật.
     * @param lvData Mảng dữ liệu bản đồ.
     * @return Trả về true nếu nhân vật trên mặt sàn, ngược lại false.
     */
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvData) {
        if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvData))
            if(!IsSolid(hitbox.x + hitbox.width,hitbox.y + hitbox.height + 1, lvData))
                return false;
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0)
        return IsSolid(hitbox.x + hitbox.width+ xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }

        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);

    }
    public static int[][] GetLevelData(BufferedImage img){
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
    public static ArrayList<Crabby> GetCrabs(BufferedImage img){
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
    public static Point GetPlayerSpawn(BufferedImage img) {
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
            }
        return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
    }

}
