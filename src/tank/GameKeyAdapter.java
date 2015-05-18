package tank;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by User on 17.05.2015.
 */
public class GameKeyAdapter extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent e) {
        int i = (e.getKeyCode());
//                System.out.println(i);
        switch (i) {
            case 37:
                GameFrame.tank.rotateLeft();
                break;

            case 38:
                GameFrame.tank.rotateUp();
                break;

            case 39:
                GameFrame.tank.rotateRight();
                break;

            case 40:
                GameFrame.tank.rotateDown();
                break;

            case 32:
                GameFrame.tank.shoot();
                break;

            case 88:
                System.exit(0);
        }
    }
}


