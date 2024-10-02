/*
 Designing a class to control the logical procedures behind Tetris Project
 A. Landry & Isaac LeJeune
 10/20/2022
 */

import java.util.*;
import java.io.*;
import javax.swing.*;

public class TetrisGame {

    private int rows;
    private int cols;
    private int[][] background;
    private boolean newHighScore = false;
    public int state = 0;
    private int score = 0;
    Random randGen = new Random();

    public TetrisBrick fallingBrick;
    public TetrisDisplay brickDisplay;
    public TetrisWindow window;

    public TetrisGame(int rs, int cs) {
        rows = rs;
        cols = cs;

        newGame();
    }

    public void initBoard(int rs, int cs) {
        int rows = rs;
        int cols = cs;
        background = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; cols++) {
                background[row][col] = 0;
            }
        }

    }

    public void newGame() {
        initBoard(rows, cols);
        score = 0;
        spawnBrick();
    }

    public void makeMove(String direction) {
        if (Objects.equals(direction, "Down")) {
            fallingBrick.moveDown();
            clearCheck();
            if (validateMove() == 0) {
                fallingBrick.moveUp();
                transferColor();
                spawnBrick();
            }
        }
        if (Objects.equals(direction, "left")) {
            fallingBrick.moveLeft();
            if (validateMove() == 0) {
                fallingBrick.moveRight();
            }
        } else if (Objects.equals(direction, "right")) {
            fallingBrick.moveRight();
            if (validateMove() == 0) {
                fallingBrick.moveLeft();
            }
        }
        if (Objects.equals(direction, "rotate")) {
            fallingBrick.rotate();

            if (validateMove() == 0) {
                fallingBrick.unrotate();
            }
        }
        if (blockOutOfBounds()) {
            System.out.println("Game Over");
        }
    }

    public int fetchBoardPosition(int rows, int cols) {
        return background[rows][cols];
    }

    private void transferColor() {
        for (int brickSeg = 0; brickSeg < getNumSegs(); brickSeg++) {
            background[getSegRow(brickSeg)][getSegCol(brickSeg)] = getFallingBrickColor();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getNumSegs() {
        return fallingBrick.getnumSegment();
    }

    public int getSegCol(int segmentIndex) {
        return fallingBrick.getPosition()[segmentIndex][0];
    }

    public int getSegRow(int segmentIndex) {
        return fallingBrick.getPosition()[segmentIndex][1];
    }

    public int getFallingBrickColor() {
        return fallingBrick.getColorNum();
    }

    private int validateMove() {

        for (int segmentPiece = 0; segmentPiece < getNumSegs(); segmentPiece++) {
            if (getSegCol(segmentPiece) < 0) {
                return 0;
            }
            if (getSegCol(segmentPiece) > cols - 1) {
                return 0;
            }
            if (getSegRow(segmentPiece) > rows - 1) {
                return 0;
            }
            if (background[getSegRow(segmentPiece)][getSegCol(segmentPiece)] != 0) {
                return 0;
            }
        }
        return 1;
    }

    private int validate_left() {
        for (int segmentPiece = 0; segmentPiece < getNumSegs(); segmentPiece++) {
            if (getSegCol(segmentPiece) <= 0) {
                return 0;
            }
        }
        return 1;
    }

    private int validate_right() {
        for (int segmentPiece = 0; segmentPiece < getNumSegs(); segmentPiece++) {
            if (getSegCol(segmentPiece) >= 11) {
                return 0;
            }
        }
        return 1;
    }

    public void spawnBrick() {
        int randBrick = randGen.nextInt(7);
        switch (randBrick) {
            case 0:
                fallingBrick = new ElBrick();
                break;
            case 1:
                fallingBrick = new JayBrick();
                break;
            case 2:
                fallingBrick = new EssBrick();
                break;
            case 3:
                fallingBrick = new ZeeBrick();
                break;
            case 4:
                fallingBrick = new SquareBrick();
                break;
            case 5:
                fallingBrick = new LongBrick();
                break;
            case 6:
                fallingBrick = new StackBrick();
                break;
        }
        for (int segmentPiece = 0; segmentPiece < getNumSegs(); segmentPiece++) {
            int checkSegY = getSegRow(segmentPiece);
            int checkSegX = getSegCol(segmentPiece);

            if (background[getSegRow(segmentPiece)][getSegCol(segmentPiece)] > 0) {
                state = 1;
            }
        }

        fallingBrick.getPosition();
    }

    public int getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    private boolean blockOutOfBounds() {
        for (int segmentPiece = 0; segmentPiece < getNumSegs(); segmentPiece++) {
            if (getSegRow(segmentPiece) > rows - 1) {
                return true;
            }
        }
        return false;
    }

    private void clearCheck() {
        int brokenRows = 0;
        boolean lineFilled;

        for (int rw = rows - 1; rw >= 0; rw--) {
            lineFilled = true;

            for (int cl = 0; cl < cols; cl++) {
                if (background[rw][cl] == 0) {
                    lineFilled = false;
                    break;
                }
            }

            if (lineFilled) {
                clearRow(rw);
                lineDown(rw);
                clearRow(0);

                //adding one prevents skipping lines
                rw++;
                brokenRows++;
            }
        }
        switch (brokenRows) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 600;
                break;
            case 4:
                score += 1200;
                break;
            default:
                score += 0;
        }

    }

    private void clearRow(int rw) {
        for (int i = 0; i < cols; i++) {
            background[rw][i] = 0;
        }
    }

    private void lineDown(int rw) {
        for (int r = rw; r > 0; r--) {
            for (int col = 0; col < cols; col++) {
                background[r][col] = background[r - 1][col];
            }
        }
    }

    public String toString() {
        String boardSave = "" + background.length + " " + background[0].length + "\n";
        for (int row = 0; row < background.length; row++) {
            for (int col = 0; col < background[0].length; col++) {
                boardSave += "" + background[row][col] + " ";
            }
            boardSave += "\n";
        }
        boardSave = boardSave.substring(0, boardSave.length() - 1);
        return boardSave;
    }

    public void saveToFile(String fileName) {
        File fileConnection = new File(fileName);
        if (fileConnection.exists() && !fileConnection.canWrite()) {
            System.err.print(" Trouble opening the file: " + fileName);
            return;
        }
        try {
            FileWriter outWriter
                    = new FileWriter(fileConnection);
            outWriter.write(this.toString());
            outWriter.close();

        } catch (IOException ioe) {
            System.err.print(" Trouble writing to file: " + fileName);
        }

    }

    public void checkScore() {
        Scanner keyboard = new Scanner(System.in);
        int gameScore = getScore();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();
        String fName = "HighScoreLeaderboard.txt";
        File inFile = new File(fName);

        if (!inFile.exists()) {
            String errorMessage = "ERROR:\n Cannot find file: " + fName + ".";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", 0);
        }

        try {
            Scanner inScan = new Scanner(inFile);

            while (inScan.hasNext()) {
                names.add(inScan.next());
                scores.add(inScan.nextInt());
            }
        } catch (IOException ioe) {
            String errorMessage = "ERROR: \n Cannot find file: " + fName + ".";
            JOptionPane.showMessageDialog(null, errorMessage, "Error 2", 0);
        }

        for (int iteration = 0; iteration < scores.size(); iteration++) {
            if (gameScore > scores.get(iteration)) {
                scores.remove(scores.size() - 1);
                names.remove(names.size() - 1);
                scores.add(iteration, gameScore);

                String nameText = "Congratulations!! You have made it on the "
                        + "leaderboard!\n Please enter your name.";
                String newName = JOptionPane.showInputDialog(null, nameText, "High Score", 1);
                newHighScore = true;
                names.add(iteration, newName);
                break;
            }
        }

        String textField = "";

        for (int iteration = 0; iteration < names.size(); iteration++) {
            textField += names.get(iteration) + "   " + scores.get(iteration) + " \n";
        }
        try {
            FileWriter outWriter = new FileWriter(inFile);
            outWriter.write(textField);
            outWriter.close();

            ImageIcon tetrisIcon = new ImageIcon("tetrisImage.png");

            if (newHighScore == true) {
                JOptionPane.showMessageDialog(null, "You can now press the 'N' key "
                        + "to begin a new game\n or exit the program using the "
                        + "file tab after the game has begun.\n Thank you for playing!", "Thank you for playing", 0, tetrisIcon);
                newGame();
                state -= 1;
                newHighScore = false;
            }

        } catch (IOException ioe) {
            String errorMessage = "ERROR: \n Cannot find file: " + fName + ".";
            JOptionPane.showMessageDialog(null, errorMessage, "Error 2", 0);
        }

    }

    public void retrieveFromFile(String fileName) {
        initBoard(rows, cols);
        try {
            File fileConnection = new File(fileName);
            Scanner inScan = new Scanner(fileConnection);
            int row = inScan.nextInt();
            int col = inScan.nextInt();
            background = new int[row][col];

            for (int backgroundRow = 0; backgroundRow < row; backgroundRow++) {
                for (int backgroundCol = 0; backgroundCol < col; backgroundCol++) {
                    background[backgroundRow][backgroundCol] = inScan.nextInt();
                }
            }

        } catch (FileNotFoundException fnfe) {
            System.err.print("Trouble opening file to read: " + fileName);
            return;
        } catch (Exception e) {
            System.err.print("Error occured while reading file.");
        }

        spawnBrick();

    }

}
