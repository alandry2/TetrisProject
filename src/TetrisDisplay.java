/*
 Designing the interactive interface for Tetris Project through Graphics and Listeners
 A. Landry & Isaac LeJeune
 10/20/2022
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel {

    TetrisGame game;
    TetrisBrick brick;

    private int start_x = 50;
    private int start_y = 30;
    private int cell_size = 23;
    private int score_increment = 0;
    private Color[] brickColor = {Color.WHITE, Color.RED, Color.CYAN, Color.GREEN,
        Color.YELLOW, Color.ORANGE, Color.PINK, Color.MAGENTA, Color.BLACK};

    private int speed = 400;
    private Timer timer;
    private boolean pause = false;

    public TetrisDisplay(TetrisGame gam) {
        game = gam;

        timer = new Timer(speed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cycleMove();
            }
        });
        timer.start();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                translateKey(e);
            }

        });

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    public void translateKey(KeyEvent ke) {
        int code = ke.getKeyCode();
        switch (code) {
            case KeyEvent.VK_LEFT:
                game.makeMove("left");
                break;
            case KeyEvent.VK_RIGHT:
                game.makeMove("right");
                break;
            case KeyEvent.VK_N:
                game.state = 0;
                timer.restart();
                game.newGame();
                break;
            case KeyEvent.VK_SPACE:
                if (pause == false) {
                    game.state += 2;
                    timer.stop();
                    pause = true;
                } else if (pause == true) {
                    game.state -= 2;
                    timer.start();
                    pause = false;
                }
                break;
            case KeyEvent.VK_UP:
                game.makeMove("rotate");
            case KeyEvent.VK_DOWN:
                game.makeMove("Down");

        }
        repaint();

    }

    private void drawWell(Graphics g) {
        // Left wall
        g.fillRect(start_x - cell_size, start_y,
                cell_size, cell_size * game.getRows());
        // Right Wall
        g.fillRect(start_x + cell_size * game.getCols(),
                start_y, cell_size,
                cell_size * game.getRows());
        // Bottom Wall
        g.fillRect(start_x - cell_size,
                start_y + game.getRows() * cell_size,
                cell_size * game.getCols() + 2 * cell_size, cell_size);
        g.setColor(brickColor[0]);
        g.fillRect(start_x, start_y, cell_size * 12, cell_size * 20);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        if (game.state == 0) //Normal
        {
            drawWell(g);
            drawBackground(g);
            drawScore(g);
            drawBrick(g);
        } else if (game.state == 1) //Game is Over
        {
            drawWell(g);
            drawBackground(g);
            drawScore(g);
            drawGameOver(g);
            timer.stop();
            game.checkScore();

        } else if (game.state == 2) //Pause
        {
            drawBackground(g);
            drawWell(g);
            drawBrick(g);
            drawScore(g);
            drawPause(g);
        }
    }

    private void cycleMove() {
        game.makeMove("Down");
        repaint();
    }

    private void drawBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        int rows = game.getRows();
        int cols = game.getCols();
        for (int backgroundCol = 0; backgroundCol < game.getCols(); backgroundCol++) {
            for (int backgroundRow = 0; backgroundRow < game.getRows(); backgroundRow++) {
                if (game.fetchBoardPosition(backgroundRow, backgroundCol) > 0) {
                    g.setColor(brickColor[game.fetchBoardPosition(backgroundRow, backgroundCol)]);
                    g.fillRect(start_x + backgroundCol * cell_size,
                            start_y + backgroundRow * cell_size, cell_size, cell_size);
                    g.setColor(brickColor[8]);
                    g.drawRect(start_x + backgroundCol * cell_size,
                            start_y + backgroundRow * cell_size, cell_size, cell_size);
                }
            }
        }
    }

    private void drawGameOver(Graphics g) {
        int bxHeight = 40;
        int bxWid = 100;
        int strokeWei = 5;
        int fontSize = 32;
        int stringX = 4;
        int stringY = 3;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(strokeWei));

        int[] xVal = new int[4];
        int[] yVal = new int[4];
        int xValAdjustment = 290;
        int yValAdjustment = 73;

        xVal[0] = (start_x + game.getCols() * cell_size / 2) - (bxWid);
        xVal[1] = (start_x + game.getCols() * cell_size / 2) + (bxWid);
        xVal[2] = (start_x + game.getCols() * cell_size / 2) + (bxWid);
        xVal[3] = (start_x + game.getCols() * cell_size / 2) - (bxWid);

        yVal[0] = (start_x + game.getCols() * cell_size / 2) + (bxHeight);
        yVal[1] = (start_x + game.getCols() * cell_size / 2) + (bxHeight);
        yVal[2] = (start_x + game.getCols() * cell_size / 2) - (bxHeight);
        yVal[3] = (start_x + game.getCols() * cell_size / 2) - (bxHeight);

        g.setColor(Color.WHITE);
        g.fillPolygon(xVal, yVal, xVal.length);
        g.setColor(Color.BLACK);
        g.drawPolygon(xVal, yVal, xVal.length);

        g.setFont(new Font("Impact", Font.BOLD, fontSize));
        g.setColor(Color.RED);
        g.drawString("GAME OVER", (start_x + game.getCols() * cell_size / 2) - yValAdjustment, (start_y + game.getRows() * cell_size - xValAdjustment));
    }

    private void drawPause(Graphics g) {
        int bxHeight = 40;
        int bxWid = 100;
        int strokeWei = 5;
        int fontSize = 32;
        int stringX = 4;
        int stringY = 3;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(strokeWei));

        int[] xVal = new int[4];
        int[] yVal = new int[4];
        int xValAdjustment = 290;
        int yValAdjustment = 60;

        xVal[0] = (start_x + game.getCols() * cell_size / 2) - (bxWid);
        xVal[1] = (start_x + game.getCols() * cell_size / 2) + (bxWid);
        xVal[2] = (start_x + game.getCols() * cell_size / 2) + (bxWid);
        xVal[3] = (start_x + game.getCols() * cell_size / 2) - (bxWid);

        yVal[0] = (start_x + game.getCols() * cell_size / 2) + (bxHeight);
        yVal[1] = (start_x + game.getCols() * cell_size / 2) + (bxHeight);
        yVal[2] = (start_x + game.getCols() * cell_size / 2) - (bxHeight);
        yVal[3] = (start_x + game.getCols() * cell_size / 2) - (bxHeight);

        g.setColor(Color.WHITE);
        g.fillPolygon(xVal, yVal, xVal.length);
        g.setColor(Color.BLACK);
        g.drawPolygon(xVal, yVal, xVal.length);

        g.setFont(new Font("Impact", Font.BOLD, fontSize));
        g.setColor(Color.BLUE);
        g.drawString(" PAUSED ", (start_x + game.getCols() * cell_size / 2) - yValAdjustment, (start_y + game.getRows() * cell_size - xValAdjustment));
    }

    private void drawScore(Graphics g) {
        int scoreBoxWidth = 4 * cell_size;
        int scoreBoxHeight = cell_size;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, scoreBoxWidth, scoreBoxHeight);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, scoreBoxWidth, scoreBoxHeight);

        int startStringX = 5;
        int startStringY = 14;
        int startScoreX = 45;
        int startScoreY = 15;

        int score = game.getScore();
        g.drawString("Score: ", startStringX, startStringY);
        g.drawString("" + score, startScoreX, startScoreY);
    }

    private void drawBrick(Graphics g) {
        for (int segmentPiece = 0; segmentPiece < game.getNumSegs(); segmentPiece++) {
            g.setColor(brickColor[game.getFallingBrickColor()]);
            g.fillRect(start_x + game.getSegCol(segmentPiece) * cell_size,
                    start_y + game.getSegRow(segmentPiece) * cell_size, cell_size, cell_size);
            g.setColor(brickColor[8]);
            g.drawRect(start_x + game.getSegCol(segmentPiece) * cell_size,
                    start_y + game.getSegRow(segmentPiece) * cell_size, cell_size, cell_size);
        }
    }
}
