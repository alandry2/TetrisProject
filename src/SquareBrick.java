/*
 Defining the shape for the SquareBrick to be generated
 A. Landry & Isaac LeJeune
 10/20/2022
 */

public class SquareBrick extends TetrisBrick {

    public SquareBrick() {
        super();
        initPosition();

    }

    public void initPosition() {
        int[][] brickShape = new int[4][2];
        int brick_color = 5;

        brickShape[0][0] = 5;
        brickShape[0][1] = 0;

        brickShape[1][0] = 6;
        brickShape[1][1] = 0;

        brickShape[2][0] = 5;
        brickShape[2][1] = 1;

        brickShape[3][0] = 6;
        brickShape[3][1] = 1;

        super.brickShape(brickShape);
        super.brickColor(brick_color);
    }

    @Override
    public void rotate() {

    }

    @Override
    public void unrotate() {

    }

}
