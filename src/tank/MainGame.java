package tank;

import javax.swing.*;

/**
 * Created by vmurashkin on 18.05.15.
 */
public class MainGame implements Runnable{
    public static void main(String[] args) {

        MainGame mainGame = new MainGame();
        mainGame.run();

    }

    @Override
    public void run() {
        GameFrame game = new GameFrame();
        game.add(new LoadImageApp());
        game.setTitle("Tanks 0.8.2");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setResizable(false);
    }
}
