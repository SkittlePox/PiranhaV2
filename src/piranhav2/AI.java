package piranhav2;

import static piranhav2.PiranhaV2.boxes;
import static piranhav2.PiranhaV2.columnsY;
import static piranhav2.PiranhaV2.mouse;
import static piranhav2.PiranhaV2.moveToBox;
import static piranhav2.PiranhaV2.print;
import static piranhav2.PiranhaV2.rowsX;
import static piranhav2.PiranhaV2.userPause;

public class AI {

    public AI() {
    }

    public void numberedBoxCheck() {
        print("Checking numbered boxes");
        for (int y = 0; y < columnsY; y++) {
            for (int x = 0; x < rowsX; x++) {
                for (int g = 0; g <= 7; g++) {
                    if (boxes[x][y].getStatus() == -1 && boxes[x][y].getCardinalStatus(g) <= 8) {
                        switch (g) {
                            case 0:
                                if (boxes[x - 1][y - 1].getChanceToGive() >= 100) {
                                    boxes[x][y].mineConfirmed();
                                }
                                break;
                            case 1:
                                if (boxes[x][y - 1].getChanceToGive() >= 100) {
                                    boxes[x][y].mineConfirmed();
                                }
                                break;
                            case 2:
                                if (boxes[x + 1][y - 1].getChanceToGive() >= 100) {
                                    boxes[x][y].mineConfirmed();
                                }
                                break;
                            case 3:
                                if (boxes[x - 1][y].getChanceToGive() >= 100) {
                                    boxes[x][y].mineConfirmed();
                                }
                                break;
                            case 4:
                                if (boxes[x + 1][y].getChanceToGive() >= 100) {
                                    boxes[x][y].mineConfirmed();
                                }
                                break;
                            case 5:
                                if (boxes[x - 1][y + 1].getChanceToGive() >= 100) {
                                    boxes[x][y].mineConfirmed();
                                }
                                break;
                            case 6:
                                if (boxes[x][y + 1].getChanceToGive() >= 100) {
                                    boxes[x][y].mineConfirmed();
                                }
                                break;
                            case 7:
                                if (boxes[x + 1][y + 1].getChanceToGive() >= 100) {
                                    boxes[x][y].mineConfirmed();
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    public void flagMines() {
        print("Flagging mines");
        for (int y = 0; y < columnsY; y++) {
            for (int x = 0; x < rowsX; x++) {
                moveToBox(x, y);
                if (boxes[x][y].isAMine() && boxes[x][y].getStatus() != 9) {
                    moveToBox(x, y);
                    mouse.rightClick();
                }
            }
        }
    }

    public void clearNums() {
        userPause(1);
        for (int y = 0; y < columnsY; y++) {
            for (int x = 0; x < rowsX; x++) {
                for (int f = 0; f <= 7; f++) {
                    if (boxes[x][y].getCardinalStatus(f) != 10 && boxes[x][y].isClear()) {
                        switch (f) {
                            case 0:
                                moveToBox(x - 1, y - 1);
                                mouse.leftClick();
                                break;
                            case 1:
                                moveToBox(x, y - 1);
                                mouse.leftClick();
                                break;
                            case 2:
                                moveToBox(x + 1, y - 1);
                                mouse.leftClick();
                                break;
                            case 3:
                                moveToBox(x - 1, y);
                                mouse.leftClick();
                                break;
                            case 4:
                                moveToBox(x + 1, y);
                                mouse.leftClick();
                                break;
                            case 5:
                                moveToBox(x - 1, y + 1);
                                mouse.leftClick();
                                break;
                            case 6:
                                moveToBox(x, y + 1);
                                mouse.leftClick();
                                break;
                            case 7:
                                moveToBox(x + 1, y + 1);
                                mouse.leftClick();
                                break;
                        }
                    }
                }
            }
        }
    }
}
