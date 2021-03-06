package tank;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
    static List<Enemy> enemies = new ArrayList<>();

    Color pureGray = new Color(211, 211, 211);


    int numberOfMonsters;
    boolean win;
    boolean stop;
    boolean tankDead;
    boolean begin;

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

        KeyAdapter adapter = new GameKeyAdapter();
        this.addKeyListener(adapter);

        Timer timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                work();
            }
        }
        );
        timer.start();

    }
//=============== Work ================

    public void work() {
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
            tankDead = true;
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


// ===================================== Рисование ==================================

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.RED);
        Font fontHi = new Font("Serif", Font.BOLD, 24);
        FontMetrics sizeFontHi = getFontMetrics(fontHi);
        g.setFont(fontHi);
        sLevel = "Level: " + level;
        sScore = "Score: " + score + "  |  HiScore: " + hiScore;
        int yHiText = (fieldStartY) / 2 + sizeFontHi.getHeight() / 2;
        int xLevel = 15;
        int xScore = fieldSizeX - sizeFontHi.stringWidth(sScore) - 15;

        if (score == hiScore)
            g.clearRect(0, 0, fieldSizeX, fieldStartY - 1);
        g.drawString(sLevel, xLevel, yHiText);
        g.drawString(sScore, xScore, yHiText);

        g.setColor(pureGray);
        g.drawRect(tank.getxOld() - 25, tank.getyOld() - 25, tankSizeX, tankSizeY);
        switch (tank.getDirectionX()) {
            case 1:
                g.drawImage(tankRight, tank.getX() - 25, tank.getY() - 25, null);
                break;

            case -1:
                g.drawImage(tankLeft, tank.getX() - 25, tank.getY() - 25, null);
                break;
        }
        switch (tank.getDirectionY()) {
            case 1:
                g.drawImage(tankDown, tank.getX() - 25, tank.getY() - 25, null);
                break;

            case -1:
                g.drawImage(tankUp, tank.getX() - 25, tank.getY() - 25, null);
                break;
        }

        for (int i = enemies.size() - 1; i > -1; i--) {
            Enemy enemy = enemies.get(i);
            g.clearRect(enemy.getxOld(), enemy.getyOld(), Enemy.SIZE, Enemy.SIZE);
        }

        for (int i = enemies.size() - 1; i > -1; i--) {
            Enemy enemy = enemies.get(i);
            if (!enemy.isDead)
                g.drawImage(monster, enemy.getX(), enemy.getY(), null);
            else {
                g.clearRect(enemy.getxOld(), enemy.getyOld(), Enemy.SIZE, Enemy.SIZE);
                enemies.remove(i);
                score += 100;
                g.clearRect(0, 0, fieldSizeX, fieldStartY - 1);
            }
        }

        for (int i = tank.bullets.size() - 1; i > -1; i--) {
            Bullet bullet = tank.bullets.get(i);
            g.setColor(pureGray);
            g.drawRect(bullet.getxOld(), bullet.getyOld(), 1, 1);
            if (!bullet.isDead) {
                g.setColor(Color.red);
                g.drawRect(bullet.getX(), bullet.getY(), 1, 1);
            } else {
                g.setColor(pureGray);
                g.drawRect(bullet.getxOld(), bullet.getyOld(), 1, 1);
                tank.bullets.remove(i);
            }
        }

        if (tankDead) {
            //g.clearRect(0,0,fieldSizeX, fieldSizeY);
            g.setColor(Color.RED);
            Font fontLoose = new Font("Serif", Font.BOLD, 24);
            FontMetrics sizeFontLoose = getFontMetrics(fontLoose);
            g.setFont(fontLoose);
            String sLoose = "Game over! Press Enter to continue or X to exit";
            int xLoose = fieldSizeX / 2 - sizeFontLoose.stringWidth(sLoose) / 2;
            int yLoose = fieldSizeY + 50 - sizeFontLoose.getHeight() / 2;
            g.clearRect(0, fieldSizeY + 1, fieldSizeX, 50);
            g.drawString(sLoose, xLoose, yLoose);
            level = 1;

        }

        if (win) {

            g.setColor(Color.RED);
            Font fontWin = new Font("Serif", Font.BOLD, 24);
            FontMetrics sizeFontWin = getFontMetrics(fontWin);
            g.setFont(fontWin);
            String sWin = "You win! Press Enter for next level";
            int xWin = fieldSizeX / 2 - sizeFontWin.stringWidth(sWin) / 2;
            int yWin = fieldSizeY + 50 - sizeFontWin.getHeight() / 2;
            g.clearRect(0, fieldSizeY + 1, fieldSizeX, 50);
            g.drawString(sWin, xWin, yWin);
            level++;
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


}
