/*
 Creating abstract super class to control other sub-bricks
 A. Landry & Isaac LeJeune
 10/20/2022
 */

public abstract class TetrisBrick {

    int numSegments = 4;
    int orientation = 0;
    int colorNum;
    int[][] position = new int[numSegments][2];

    public void moveLeft() {
        for (int segmentPiece = 0; segmentPiece < 4; segmentPiece++) {
            position[segmentPiece][0]--;
        }
    }

    public void moveRight() {
        for (int segmentPiece = 0; segmentPiece < 4; segmentPiece++) {
            position[segmentPiece][0]++;
        }
    }

    public void moveDown() {
        for (int segmentPiece = 0; segmentPiece < 4; segmentPiece++) {
            position[segmentPiece][1]++;
        }
    }

    public void moveUp() {
        for (int segmentPiece = 0; segmentPiece < 4; segmentPiece++) {
            position[segmentPiece][1]--;
        }
    }
    
    public String toString() {
        return null;
        
    }

    public abstract void rotate();

    public abstract void unrotate();

    public int getColorNum() {
        return colorNum;
    }

    public int getnumSegment() {
        return numSegments;
    }

    public int[][] getPosition() {
        return position;
    }

    public abstract void initPosition();

    public void brickShape(int[][] brickShape) {
        this.position = brickShape;
    }

    public void brickColor(int brickColor) {
        this.colorNum = brickColor;
    }

}
