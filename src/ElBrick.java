/*
 Defining the shape for the ElBrick to be generated
 A. Landry & Isaac LeJeune
 10/20/2022
 */

public class ElBrick extends TetrisBrick {

    private int rotationCount = 0;

    public ElBrick() {
        super();
        initPosition();

    }

    public void initPosition() {
        int[][] brickShape = new int[4][2];
        int brick_color = 1;

        brickShape[0][0] = 5;
        brickShape[0][1] = 0;

        brickShape[1][0] = 5;
        brickShape[1][1] = 1;

        brickShape[2][0] = 5;
        brickShape[2][1] = 2;

        brickShape[3][0] = 6;
        brickShape[3][1] = 2;

        super.brickShape(brickShape);
        super.brickColor(brick_color);
    }

    @Override
    public void rotate() {
        int[][] brickShape = new int[4][2];

        switch (super.orientation) {
            case 0:
                brickShape = new int[][]{
                    {super.position[0][0] - 1, super.position[0][1] + 1},
                    {super.position[1][0], super.position[1][1]},
                    {super.position[2][0] + 1, super.position[2][1] - 1},
                    {super.position[3][0]    , super.position[3][1] - 2},};
                super.orientation += 1;
                super.brickShape(brickShape);
                break;
            case 1:
                brickShape = new int[][]{
                    {super.position[0][0] +1 , super.position[0][1] + 1},
                    {super.position[1][0], super.position[1][1]},
                    {super.position[2][0] - 1, super.position[2][1] - 1},
                    {super.position[3][0] - 2, super.position[3][1]    },};
                super.orientation += 1;
                super.brickShape(brickShape);
                break;
            case 2:
                brickShape = new int[][]{
                    {super.position[0][0] + 1, super.position[0][1] - 1},
                    {super.position[1][0], super.position[1][1]},
                    {super.position[2][0] - 1, super.position[2][1] + 1},
                    {super.position[3][0]    , super.position[3][1] + 2},};
                super.orientation += 1;
                super.brickShape(brickShape);
                break;
            case 3:
                brickShape = new int[][]{
                    {super.position[0][0] - 1, super.position[0][1] - 1},
                    {super.position[1][0], super.position[1][1]},
                    {super.position[2][0] + 1, super.position[2][1] + 1},
                    {super.position[3][0] + 2, super.position[3][1]    },};
                super.orientation -= 3;
                super.brickShape(brickShape);
                break;
        }

    }

    @Override
    public void unrotate() {
        rotate();
        rotate();
        rotate();
    }
}
