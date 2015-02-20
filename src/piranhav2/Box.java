package piranhav2;

import static piranhav2.PiranhaV2.print;

public class Box {

    private double chance = 0;
    private double unaccountedBoxes, surroundingBoxes, chanceToGive;
    private boolean isAMine, isAccountedFor = false;

    private int x, y, number, status;
    private int[] cardinals = new int[8];   //nw = 0, n = 1, ne = 2, w = 3, e = 4, sw = 5, s = 6, se = 7

    /*
     For variable 'status':
     -1 is unchecked
     0 is zero mines nearby
     1 is one mine nearby
     2 is two mines nearby
     3 is three mines nearby
     4 is four mines nearby
     5 is five mines nearby
     6 is six mines nearby
     7 is seven mines nearby
     8 is eight mines nearby
     9 is a mine that is flagged
    
     10 is a non-existent mine (Only for the surrounding boxes that reside on the edges of the field)
     */
    public Box(int xCoordinate, int yCoordinate, int boxNumber, int boxStatus) {
        x = xCoordinate;
        y = yCoordinate;
        number = boxNumber;
        status = boxStatus;
        for (int a = 0; a < 8; a++) {
            cardinals[a] = 10;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int s) {
        status = s;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNum() {
        return number;
    }
    
    public void flagCheck() {
        unaccountedBoxes = status;
        for(int d = 0; d <= 7; d++) {
            if(cardinals[d] == 9) {
                unaccountedBoxes--;
            }
        }
    }
    
    public int getSurroundingBoxes() {
        int a = 0;
        for(int b = 0; b < 8; b++) {
            if(cardinals[b]==-1) {
                a++;
            }
        }
        return a;
    }
    
    public double getChanceToGive() {
        flagCheck();
        surroundingBoxes = getSurroundingBoxes();
        chanceToGive = (unaccountedBoxes / surroundingBoxes) * 100;
        if (unaccountedBoxes == 0 || status == 9) {
            return 0;
        } else {
            return chanceToGive;
        }
    }

    public void setCardinalStatus(int direction, int status) {
        cardinals[direction] = status;
    }

    public int getCardinalStatus(int direction) {
        return cardinals[direction];
    }
    
    public void mineConfirmed() {
        isAMine = true;
    }
    
    public boolean isAMine() {
        return isAMine;
    }
    
    public boolean isClear() {
        flagCheck();
        return unaccountedBoxes == 0 && status != -1;
    }
}
