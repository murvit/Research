package tank;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VMurashkin on 16.07.14.
 */
public class Tank {

    private int x;
    private int y;
    private int xOld;
    private int yOld;
    private int directionX;
    private int directionY;
    private String direction;
    private int move;
    static final int SIZE_X = 50;
    static final int SIZE_Y = 50;
    List<Bullet> bullets = new ArrayList<Bullet>();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getxOld() {
        return xOld;
    }

    public int getyOld() {
        return yOld;
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public String getDirection() {
        return direction;
    }

    static final Map<String, BufferedImage> IMAGES = new HashMap<String, BufferedImage>();

    Tank() {
        x = GameFrame.fieldSizeX / 2 - SIZE_X / 2;
        y = GameFrame.fieldSizeY - SIZE_Y - 10;
        directionX = 0;
        directionY = -1;
        move = 0;
        direction = "up";
    }


    public void rotateLeft() {
        move = 1;
        direction = "left";
        directionX = -1;
        directionY = 0;
    }

    public void rotateRight() {
        direction = "right";
        move = 1;
        directionX = 1;
        directionY = 0;
    }

    public void rotateUp() {
        direction = "up";
        move = 1;
        directionX = 0;
        directionY = -1;
    }

    public void rotateDown() {
        direction = "down";
        move = 1;
        directionX = 0;
        directionY = 1;
    }


    public void move() {
        xOld = x;
        yOld = y;
        x += directionX * move;
        y += directionY * move;
    }

    public void shoot() {
        Bullet bullet = new Bullet(this);
        bullets.add(bullet);

    }


}
