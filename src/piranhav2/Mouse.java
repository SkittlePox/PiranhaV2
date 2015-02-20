package piranhav2;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.*;

public class Mouse {

    public Robot robot;
    int x;
    int y;

    public Mouse() throws AWTException {
        robot = new Robot();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImage(Rectangle captureSize) {
        return robot.createScreenCapture(captureSize);
    }

    public void move(int xx, int yy) {
        robot.mouseMove(xx, yy);
    }

    public void leftClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void rightClick() {
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }
}
