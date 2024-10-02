/*
 Designing standard settings for window design and interface
 Andrew Landry & Isaac LeJeune
 10/20/2022
 */

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TetrisWindow extends JFrame implements ActionListener {

    private int winWid = 400;
    private int winHei = 580;
    private int rows = 20;
    private int cols = 12;
    private String saveIncrement = "1";

    // Date for SaveFile
    Date gameSaveDate = new Date();
    SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy - hh.mm");
    private String currentDate = timeFormat.format(gameSaveDate);
    private String fileName = "gameSave" + currentDate + ".txt";

    private TetrisDisplay display;
    private TetrisGame game;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu leaderboardMenu;
    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem newGameItem;
    JMenuItem clearLeaderboardItem;
    JMenuItem displayLeaderboardItem;

    public TetrisWindow() {
        this.setTitle("Tetris Game");
        this.setSize(winWid, winHei);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new TetrisGame(rows, cols);
        display = new TetrisDisplay(game);

        this.add(display);

        menuBar = new JMenuBar();
        leaderboardMenu = new JMenu("Leaderboard");
        fileMenu = new JMenu("File");

        loadItem = new JMenuItem("Load Game");
        saveItem = new JMenuItem("Save Game");
        exitItem = new JMenuItem("Exit");
        newGameItem = new JMenuItem("New Game");

        clearLeaderboardItem = new JMenuItem("Clear Leaderboard");
        displayLeaderboardItem = new JMenuItem("Display Leaderboard");

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        newGameItem.addActionListener(this);

        clearLeaderboardItem.addActionListener(this);
        displayLeaderboardItem.addActionListener(this);

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(newGameItem);
        fileMenu.add(exitItem);

        leaderboardMenu.add(displayLeaderboardItem);
        leaderboardMenu.add(clearLeaderboardItem);

        menuBar.add(leaderboardMenu);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JFileChooser fileChooser = new JFileChooser();

        if (ae.getSource() == loadItem) {
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                fileName = (fileChooser.getSelectedFile().getAbsolutePath());
            }
            game.retrieveFromFile(fileName);
        }
        if (ae.getSource() == saveItem) {
            game.saveToFile(fileName);
        }
        if (ae.getSource() == exitItem) {
            System.exit(0);
        }
        if (ae.getSource() == newGameItem) {
            game.newGame();
        }
        if (ae.getSource() == displayLeaderboardItem) {
            drawLeaderBoard();
        }
        if (ae.getSource() == clearLeaderboardItem) {
            clearLeaderBoard();
        }
    }

    private void drawLeaderBoard() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> scores = new ArrayList<>();
        String fileName = "HighScoreLeaderboard.txt";
        File inFile = new File(fileName);

        if (!inFile.exists()) {
            String errorMessage = "ERROR !! \n"
                    + "Cannot find file " + fileName + "\n"
                    + "Confirm that " + fileName + " is readable.";
            JOptionPane.showMessageDialog(null, errorMessage, "An error has occurred", 0);
        }
        try {
            Scanner inScan = new Scanner(inFile);

            while (inScan.hasNext()) {
                names.add(inScan.next());
                scores.add(inScan.next());
            }
        } catch (IOException ioe) {
            String errorMsg = "Error!\n Trouble reading the file: '+fileName+'\n";
            JOptionPane.showMessageDialog(null, errorMsg, "Error", 0);
        }

        String leaderBoard = "";
        for (int dex = 0; dex < names.size(); dex++) {
            leaderBoard += names.get(dex) + " " + scores.get(dex) + "\n";
        }

        JOptionPane.showMessageDialog(null, "High Scores: \n\n" + leaderBoard, "Leader Board", 1);
    }

    private void clearLeaderBoard() {
        String fileName = "HighScoreLeaderboard.txt";
        File inFile = new File(fileName);

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> scores = new ArrayList<>();
        int scoreListSize = 10;

        boolean append = false;
        String text = " ";
        if (!inFile.exists()) {
            String errorMessage = "ERROR !! \n"
                    + "Cannot find file " + fileName + "\n"
                    + "Confirm that " + fileName + " is readable.";
            JOptionPane.showMessageDialog(null, errorMessage, "An error has occurred", 0);
        }
        try {
            Scanner inScan = new Scanner(inFile);
            for (int iteration = 0; iteration < scoreListSize; iteration++) {
                names.add("AAA");
                scores.add("0");
            }

            for (int iteration = 0; iteration < names.size(); iteration++) {
                text += names.get(iteration) + " " + scores.get(iteration) + "\n";
            }
            FileWriter outWriter = new FileWriter(inFile);
            outWriter.write(text);
            outWriter.close();

        } catch (IOException ioe) {
            String errorMsg = "Error!\n Trouble reading the file: " + fileName + "\n";
            JOptionPane.showMessageDialog(null, errorMsg, "Error", 0);
        }
    }

    public static void main(String[] args) {
        TetrisWindow myWindow = new TetrisWindow();
    }

}
