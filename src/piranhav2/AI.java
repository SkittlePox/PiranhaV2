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
                if (boxes[x][y].getStatus() == -1) {

                    if (boxes[x][y].getCardinalStatus(0) <= 8) {
                        if (boxes[x - 1][y - 1].getChanceToGive() >= 100) {
                            boxes[x][y].mineConfirmed();
                        }
                    }
                    if (boxes[x][y].getCardinalStatus(1) <= 8) {
                        if (boxes[x][y - 1].getChanceToGive() >= 100) {
                            boxes[x][y].mineConfirmed();
                        }
                    }
                    if (boxes[x][y].getCardinalStatus(2) <= 8) {
                        if (boxes[x + 1][y - 1].getChanceToGive() >= 100) {
                            boxes[x][y].mineConfirmed();
                        }
                    }
                    if (boxes[x][y].getCardinalStatus(3) <= 8) {
                        if (boxes[x - 1][y].getChanceToGive() >= 100) {
                            boxes[x][y].mineConfirmed();
                        }
                    }
                    if (boxes[x][y].getCardinalStatus(4) <= 8) {
                        if (boxes[x + 1][y].getChanceToGive() >= 100) {
                            boxes[x][y].mineConfirmed();
                        }
                    }
                    if (boxes[x][y].getCardinalStatus(5) <= 8) {
                        if (boxes[x - 1][y + 1].getChanceToGive() >= 100) {
                            boxes[x][y].mineConfirmed();
                        }
                    }
                    if (boxes[x][y].getCardinalStatus(6) <= 8) {
                        if (boxes[x][y + 1].getChanceToGive() >= 100) {
                            boxes[x][y].mineConfirmed();
                        }
                    }
                    if (boxes[x][y].getCardinalStatus(7) <= 8) {
                        if (boxes[x + 1][y + 1].getChanceToGive() >= 100) {
                            boxes[x][y].mineConfirmed();
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
                if (boxes[x][y].isClear() && boxes[x][y].getStatus() != 0) {
                    if (boxes[x][y].getCardinalStatus(0) != 10) {
                        moveToBox(x - 1, y - 1);
                        mouse.leftClick();
                    }
                    if (boxes[x][y].getCardinalStatus(1) != 10) {
                        moveToBox(x, y - 1);
                        mouse.leftClick();
                    }
                    if (boxes[x][y].getCardinalStatus(2) != 10) {
                        moveToBox(x + 1, y - 1);
                        mouse.leftClick();
                    }
                    if (boxes[x][y].getCardinalStatus(3) != 10) {
                        moveToBox(x - 1, y);
                        mouse.leftClick();
                    }
                    if (boxes[x][y].getCardinalStatus(4) != 10) {
                        moveToBox(x + 1, y);
                        mouse.leftClick();
                    }
                    if (boxes[x][y].getCardinalStatus(5) != 10) {
                        moveToBox(x - 1, y + 1);
                        mouse.leftClick();
                    }
                    if (boxes[x][y].getCardinalStatus(6) != 10) {
                        moveToBox(x, y + 1);
                        mouse.leftClick();
                    }
                    if (boxes[x][y].getCardinalStatus(7) != 10) {
                        moveToBox(x + 1, y + 1);
                        mouse.leftClick();
                    }
                }
            }
        }
    }
}
