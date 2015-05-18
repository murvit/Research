package tank;

import java.util.Random;

/**
 * Created by VMurashkin on 15.07.14.
 */
public class Enemy {
    private int x;
    private int y;
    private int xOld;
    private int yOld;
    private int directionX;
    private int directionY;



    static final int SPEED = 1;
    static final int SIZE = 50;
    boolean isDead = false;
    Random rand = new Random();

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

    public Enemy() {
        this.x = rand.nextInt(GameFrame.fieldSizeX - SIZE) + GameFrame.fieldStartX;
        this.y = rand.nextInt(GameFrame.fieldSizeY - SIZE - Tank.SIZE_Y * 2 - GameFrame.fieldStartY) + GameFrame.fieldStartY;
        this.xOld = this.x;
        this.yOld = this.y;
        this.directionX = (rand.nextInt(2) == 1) ? 1 : -1;
        this.directionY = (rand.nextInt(2) == 1) ? 1 : -1;
    }

    public  void move() {

            if (x + directionX > GameFrame.fieldSizeX - SIZE)
                directionX = -directionX;
            if (x + directionX < GameFrame.fieldStartX)
                directionX = -directionX;
            if (y + directionY > GameFrame.fieldSizeY - SIZE)
                directionY = -directionY;
            if (y + directionY < GameFrame.fieldStartY)
                directionY = -directionY;
            xOld = x;
            yOld = y;
            x += directionX * SPEED;
            y += directionY * SPEED;


    }
}