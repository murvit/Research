package tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by VMurashkin on 15.07.14.
 */
public class LoadImageApp extends Component {
    public LoadImageApp() {
        try {
            GameFrame.monster = ImageIO.read(getClass().getResource("/res/monster3.png"));
            GameFrame.tankUp = ImageIO.read(getClass().getResource("/res/tankup.png"));
            GameFrame.tankDown = ImageIO.read(getClass().getResource("/res/tankdown.png"));
            GameFrame.tankLeft = ImageIO.read(getClass().getResource("/res/tankleft.png"));
            GameFrame.tankRight = ImageIO.read(getClass().getResource("/res/tankright.png"));
//                clear = ImageIO.read(getClass().getResource("/res/clear.png"));

            Tank.IMAGES.put("up", GameFrame.tankUp);
            Tank.IMAGES.put("down", GameFrame.tankDown);
            Tank.IMAGES.put("left", GameFrame.tankLeft);
            Tank.IMAGES.put("right", GameFrame.tankRight);


        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
