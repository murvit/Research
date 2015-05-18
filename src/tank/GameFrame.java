package tank;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;


/**
 * Created by VMurashkin on 18.06.14.
 */
public class GameFrame extends JFrame {

    static BufferedImage monster, tankUp, tankDown, tankLeft, tankRight, picTank, clear;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int fieldStartX = 0;
    static int fieldStartY = 50;
    static int fieldSizeX = screenSize.width / 2;
    static int fieldSizeY = screenSize.height / 2 + 50;
    static int tankSizeX = 50;
    static int tankSizeY = 50;
    int level = 1;
    int score = 0;
    int hiScore;


    static Tank tank = new Tank();
    static List<Enemy> enemies = new ArrayList<Enemy>();

    Color pureGray = new Color(211, 211, 211);


    int numberOfMonsters;
    boolean win;
    boolean stop;
    boolean tankDead;
    boolean begin;
    boolean delay;
    int delayCounter;
    String sScore;
    String sLevel;


//================================ КОНСТРУКТОР ====================================


    public GameFrame() {

        initialize();
        this.setBackground(pureGray);
        this.setSize(fieldSizeX, fieldSizeY + 50);
        this.setLocation(100, 100);
        //            this.setUndecorated(true);
        this.setVisible(true);

        this.addKeyListener(new GameKeyAdapter());
        Timer timer = new Timer(10, new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                repaint();

                for (Bullet bullet : GameFrame.tank.bullets) {
                    bullet.fly();
                }

                for (Bullet bullet : GameFrame.tank.bullets) {
                    bullet.isDead();
                }

                GameFrame.tank.move();

                for (Bullet bullet : GameFrame.tank.bullets) {
                    bullet.isShoot(GameFrame.enemies);
                }

                for (Enemy enemy : GameFrame.enemies) {
                    enemy.move();
                }

                if (enemies.size() == 0) {
                    win = true;
                    stop = true;
                }

                if (isMonsterEat()) {
                    stop = true;
                    if (score > hiScore)
                        hiScore = score;
                }

                if (isTankOut()) {
                    tankDead = true;
                    stop = true;
                    if (score > hiScore)
                        hiScore = score;
                }

            }
        }

        );
        timer.start();

    }


// ===================================== Рисование ==================================

    @Override
    public void paint(Graphics g) {

        g.drawLine(fieldStartX, fieldStartY - 1, fieldSizeX, fieldStartY - 1);
        g.drawLine(fieldStartX, fieldSizeY, fieldSizeX, fieldSizeY);

        g.setColor(pureGray);
        g.drawRect(tank.getxOld() - 26, tank.getyOld() - 26, tankSizeX + 1, tankSizeY + 1);
        picTank = Tank.IMAGES.get(tank.getDirection());
        g.drawImage(picTank, tank.getX() - 25, tank.getY() - 25, null);

        for (int i = enemies.size() - 1; i > -1; i--) {
            Enemy enemy = enemies.get(i);
            g.drawImage(clear, enemy.getxOld(), enemy.getyOld(), null);
            g.drawImage(monster, enemy.getX(), enemy.getY(), null);
        }

        g.setColor(pureGray);
        for (int i = tank.bullets.size() - 1; i > -1; i--) {
            Bullet bullet = tank.bullets.get(i);
            g.drawRect(bullet.getxOld(), bullet.getyOld(), 1, 1);
        }

        g.setColor(Color.RED);
        for (int i = tank.bullets.size() - 1; i > -1; i--) {
            Bullet bullet = tank.bullets.get(i);
            g.drawRect(bullet.getX(), bullet.getY(), 1, 1);
        }
    }


// ========================================= Инициализация ========================================


    public void initialize() {

        tank.bullets.clear();
        enemies.clear();
        tank = new Tank();

        numberOfMonsters = level + 5;

        win = false;
        stop = false;
        tankDead = false;
        begin = true;

        delay = true;
        delayCounter = 3;

        makeEnemies(numberOfMonsters);
    }

    public static void makeEnemies(int num) {
        for (int i = 0; i < num; i++) {
            Enemy enemy = new Enemy();
            enemies.add(enemy);
        }
    }


    public boolean isMonsterEat() {

        for (Enemy enemy : enemies) {

            tankDead = tankDead || (enemy.getX() + Enemy.SIZE > tank.getX() - Tank.SIZE_X / 2 && enemy.getX() < tank.getX() + Tank.SIZE_X / 2 && enemy.getY() + Enemy.SIZE > tank.getY() - Tank.SIZE_Y / 2 && enemy.getY() < tank.getY() + Tank.SIZE_Y / 2);

        }
        return tankDead;
    }

    public boolean isTankOut() {
        return tank.getX() + tank.getDirectionX() * 25 < fieldStartX || tank.getX() + tank.getDirectionX() * 25 > fieldSizeX || tank.getY() + tank.getDirectionY() * 25 < fieldStartY || tank.getY() + tank.getDirectionY() * 25 > fieldSizeY;
    }


// ====================================================== MAIN ===============================================


}
