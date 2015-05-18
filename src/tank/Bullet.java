package tank;

import java.util.List;

/**
 * Created by VMurashkin on 15.07.14.
 */
public class Bullet {
    private int x;
    private int y;
    private int xOld;
    private int yOld;
    private int directionX;
    private int directionY;
    boolean isDead = false;
    Tank tank;
    static final int SPEED = 2;

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

    public Bullet(Tank tank) {

        x = tank.getX() + tank.getDirectionX() * Tank.SIZE_X /2;
        y = tank.getY() + tank.getDirectionY() * Tank.SIZE_Y /2;
        directionX = tank.getDirectionX();
        directionY = tank.getDirectionY();
    }

    public Bullet fly() {

            this.xOld = this.x;
            this.yOld = this.y;
            this.x += this.directionX * SPEED;
            this.y += this.directionY * SPEED;
            return this;

    }

    public void isDead() {
        if (this.y < GameFrame.fieldStartY || this.y > GameFrame.fieldSizeY || this.x < GameFrame.fieldStartX || this.x > GameFrame.fieldSizeX)
                this.isDead = true;

    }


    public void isShoot(List<Enemy> enemies) {


            for (int i = enemies.size() - 1; i > -1; i--) {

                Enemy enemy = enemies.get(i);
                if (enemy.getX() < this.x && (enemy.getX() + Enemy.SIZE) > this.x && enemy.getY() < this.y && (enemy.getY() + Enemy.SIZE) > this.y) {
                    enemy.isDead = true;
                }
            }

    }

}