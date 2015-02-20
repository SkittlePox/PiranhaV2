package piranhav2;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.*;
import java.util.*;

public class PiranhaV2 {

    public static Scanner scan = new Scanner(System.in);
    public static Random rand = new Random();
    static String input;

    static int rowsX = 9, columnsY = 9;
    static Box[][] boxes = new Box[rowsX][columnsY];

    static int ScreenWidth, ScreenHeight;

    static AI ai;
    static Mouse mouse;
    static BufferedImage image;

    static int homeX, homeY;
    static int[] firstBox = new int[2];
    static int[] rootColor = {128, 128, 128};
    static int[] color90 = {192, 192, 192};
    static int[] color0 = {128, 128, 128};
    static int[] color1 = {0, 0, 255};
    static int[] color2 = {0, 128, 0};
    static int[] color3 = {255, 0, 0};
    static int[] color4 = {0, 0, 128};
    static int[] color5 = {128, 0, 0};
    static int[] color6 = {0, 128, 128};
    static int[] color7b = {0, 0, 0};
    static int[] color7 = {192, 192, 192};
    static int[] color8 = {128, 128, 128};
    static int[] color9 = {255, 0, 0};

    public static void main(String[] args) throws Throwable {
        ai = new AI();
        mouse = new Mouse();
        callibrateScreenSize();
        print("Taking a screenshot in 5 seconds");
        userPause(5);
        image = takeScreenShot();
        print("Screenshot taken");
        findMinesweeper();
        //guess();
        analyze();
        ai.flagMines();
        analyze();
        ai.clearNums();
        analyze();
        printStatus();
    }

    public static void callibrateScreenSize() { //Simply gets screen size
        print("Callibrating Screen Size");
        try {
            Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            ScreenWidth = captureSize.width;
            ScreenHeight = captureSize.height;
            print("Width is " + ScreenWidth);
            print("Height is " + ScreenHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage takeScreenShot() {  //Takes a screeenshot
        Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = mouse.getImage(captureSize);
        return image;
    }

    public static void findMinesweeper() {
        print("Searching for the root color");
        userPause(1);
        boolean found = false;

        for (int y = 0; y < 300; y++) {   //Repeats for each y pixel
            for (int x = 0; x < 200; x++) {
                int color = image.getRGB(x, y);
                int blue = color & 0xFF;          // mask first 8 bits
                int green = (color >> 8) & 0xFF;  // shift right by 8 bits, then mask first 8 bits
                int red = (color >> 16) & 0xFF;   // shift right by 16 bits, then mask first 8 bits

                print(x + " " + y + " Red: " + red + " Green: " + green + "  Blue: " + blue);

                if (isSameColor(red, green, blue, rootColor[0], rootColor[1], rootColor[2])) {
                    print("Root color found at " + x + " " + y);
                    userPause(1);
                    firstBox[0] = x + 12;
                    firstBox[1] = y + 54;
                    print("Root square planted at " + firstBox[0] + " " + firstBox[1]);
                    userPause(1);
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        if (found) {
            int c = 0;
            int[] map = {firstBox[0], firstBox[1]};
            for (int b = 0; b < columnsY; b++) {
                map[0] = firstBox[0];
                for (int a = 0; a < rowsX; a++) {
                    boxes[a][b] = new Box(map[0], map[1], c, -1);
                    userPause(.05);
                    print("Box " + boxes[a][b].getNum() + " at " + boxes[a][b].getX() + " " + boxes[a][b].getY() + " with status of " + boxes[a][b].getStatus());
                    mouse.move(boxes[a][b].getX(), boxes[a][b].getY()); //Moves mouse to newly created box
                    c++;
                    map[0] += 16;
                }
                map[1] += 16;
            }
            print("Grid Planted");
        }
    }

    public static void printStatus() {
        System.out.println();
        for (int d = 0; d < rowsX + 1; d++) {
            System.out.print("---");
        }
        System.out.println();
        for (int c = 0; c < columnsY; c++) {
            System.out.print("|");
            for (int g = 0; g < rowsX; g++) {
                if (boxes[g][c].getStatus() == -1) {
                    System.out.print("[ ]");
                } else if (boxes[g][c].getStatus() == 9) {
                    System.out.print("[F]");
                } else if (boxes[g][c].getStatus() == 0) {
                    System.out.print("   ");
                } else {
                    System.out.print("[" + boxes[g][c].getStatus() + "]");
                }
            }
            System.out.print("|\n");
        }
        for (int d = 0; d < rowsX + 1; d++) {
            System.out.print("---");
        }
    }

    public static void analyze() {
        print("Analyzing board");
        userPause(1);
        image = takeScreenShot();

        for (int y = 0; y < columnsY; y++) {
            for (int x = 0; x < rowsX; x++) {
                int color = image.getRGB(boxes[x][y].getX(), boxes[x][y].getY());
                int blue = color & 0xFF;          // mask first 8 bits
                int green = (color >> 8) & 0xFF;  // shift right by 8 bits, then mask first 8 bits
                int red = (color >> 16) & 0xFF;   // shift right by 16 bits, then mask first 8 bits

                print("Testing Box " + boxes[x][y].getNum() + " at " + boxes[x][y].getX() + " " + boxes[x][y].getY() + " with colors " + red + " " + green + " " + blue);

                if (isSameColor(red, green, blue, color90[0], color90[1], color90[2])) {
                    color = image.getRGB(boxes[x][y].getX() - 9, boxes[x][y].getY());
                    blue = color & 0xFF;          // mask first 8 bits
                    green = (color >> 8) & 0xFF;  // shift right by 8 bits, then mask first 8 bits
                    red = (color >> 16) & 0xFF;   // shift right by 16 bits, then mask first 8 bits
                    if (isSameColor(red, green, blue, color0[0], color0[1], color0[2])) {
                        boxes[x][y].setStatus(0);
                    } else {
                        color = image.getRGB(boxes[x][y].getX() - 1, boxes[x][y].getY() - 1);
                        blue = color & 0xFF;          // mask first 8 bits
                        green = (color >> 8) & 0xFF;  // shift right by 8 bits, then mask first 8 bits
                        red = (color >> 16) & 0xFF;   // shift right by 16 bits, then mask first 8 bits
                        if (isSameColor(red, green, blue, color9[0], color9[1], color9[2])) {
                            boxes[x][y].setStatus(9);
                        }
                    }
                } else if (isSameColor(red, green, blue, color1[0], color1[1], color1[2])) {
                    boxes[x][y].setStatus(1);
                } else if (isSameColor(red, green, blue, color2[0], color2[1], color2[2])) {
                    boxes[x][y].setStatus(2);
                } else if (isSameColor(red, green, blue, color3[0], color3[1], color3[2])) {
                    boxes[x][y].setStatus(3);
                } else if (isSameColor(red, green, blue, color4[0], color4[1], color4[2])) {
                    boxes[x][y].setStatus(4);
                } else if (isSameColor(red, green, blue, color5[0], color5[1], color5[2])) {
                    boxes[x][y].setStatus(5);
                } else if (isSameColor(red, green, blue, color6[0], color6[1], color6[2])) {
                    boxes[x][y].setStatus(6);
                } else if (isSameColor(red, green, blue, color7b[0], color7b[1], color7b[2])) {
                    color = image.getRGB(boxes[x][y].getX() - 1, boxes[x][y].getY() - 1);
                    blue = color & 0xFF;          // mask first 8 bits
                    green = (color >> 8) & 0xFF;  // shift right by 8 bits, then mask first 8 bits
                    red = (color >> 16) & 0xFF;   // shift right by 16 bits, then mask first 8 bits
                    if (isSameColor(red, green, blue, color7[0], color7[1], color7[2])) {
                        boxes[x][y].setStatus(7);
                    }
                } else if (isSameColor(red, green, blue, color8[0], color8[1], color8[2])) {
                    boxes[x][y].setStatus(8);
                }
            }
        }
        for (int y = 0; y < columnsY; y++) {
            for (int x = 0; x < rowsX; x++) {
                if (y >= 1) {
                    boxes[x][y].setCardinalStatus(1, boxes[x][y - 1].getStatus());
                }
                if (y <= columnsY - 2) {
                    boxes[x][y].setCardinalStatus(6, boxes[x][y + 1].getStatus());
                }
                if (x <= rowsX - 2) {
                    boxes[x][y].setCardinalStatus(4, boxes[x + 1][y].getStatus());
                }
                if (x >= 1) {
                    boxes[x][y].setCardinalStatus(3, boxes[x - 1][y].getStatus());
                }
                if (x <= rowsX - 2 && y >= 1) {
                    boxes[x][y].setCardinalStatus(2, boxes[x + 1][y - 1].getStatus());
                }
                if (x >= 1 && y >= 1) {
                    boxes[x][y].setCardinalStatus(0, boxes[x - 1][y - 1].getStatus());
                }
                if (x <= rowsX - 2 && y <= columnsY - 2) {
                    boxes[x][y].setCardinalStatus(7, boxes[x + 1][y + 1].getStatus());
                }
                if (x >= 1 && y <= columnsY - 2) {
                    boxes[x][y].setCardinalStatus(5, boxes[x - 1][y + 1].getStatus());
                }
            }
        }
        ai.numberedBoxCheck();
    }

    public static void guess() {
        print("Taking a guess");

        int r = rand.nextInt(rowsX);    //Gets random x
        int c = rand.nextInt(columnsY); //Gets random y

        if (boxes[r][c].getStatus() == -1) {
            userPause(1);
            moveToBox(r, c);
            mouse.leftClick();
            System.out.println(r + " " + c + " was guessed");
        } else {
            guess();
        }
    }

    public static void moveToBox(int x, int y) {
        mouse.move(boxes[x][y].getX(), boxes[x][y].getY());
    }

    public static boolean isSameColor(int r1, int g1, int b1, int r2, int g2, int b2) {
        return (r1 == r2) && (g1 == g2) && (b1 == b2);
    }

    public static void print(String info) {
        System.out.println(info);
    }

    public static void userPause(double seconds) {
        seconds = seconds * 1000;
        long secondsL = (long) seconds;
        try {
            Thread.sleep(secondsL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void systemPause(double seconds) {
        seconds = seconds * 1000;
        long secondsL = (long) seconds;
        try {
            Thread.sleep(secondsL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
