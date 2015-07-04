package edu.utdallas.robotchess.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import edu.utdallas.robotchess.manager.*;
import edu.utdallas.robotchess.game.*;

public class MainFrame extends JFrame
{
    public final static int SQUARE_SIZE = 100;
    private static final long serialVersionUID = 3;

    Manager manager;
    BoardPanel boardPanel;

    JMenuBar menuBar;

    JMenu gameMenu;
    JMenu chessbotMenu;
    JMenu optionsMenu;

    JMenuItem newGameMenuItem;
    JMenuItem newChessDemoMenuItem;
    JRadioButton playWithChessbotsButton;
    JRadioButton playWithoutChessbotsButton;

    ButtonGroup chessbotButtonGroup;

    JButton showConnectedChessbotButton;
    JButton connectToXbeeButton;
    JCheckBoxMenuItem enableChessAIMenuItem;

    MenuItemListener menuListener;

    public MainFrame()
    {
        boardPanel = new BoardPanel(new NullManager());
        manager = new NullManager();

        setTitle("Robot Chess");
        setSize(8 * SQUARE_SIZE, 8 * SQUARE_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupMenuBar();
        add(boardPanel);
        setVisible(true);
    }

    private void setupMenuBar()
    {
        menuBar = new JMenuBar();
        menuListener = new MenuItemListener();
        gameMenu = new JMenu("Play Game");
        chessbotMenu = new JMenu("Chessbots");
        optionsMenu = new JMenu("Options");
        newGameMenuItem = new JMenuItem("New Chessgame");
        newChessDemoMenuItem = new JMenuItem("New Chessbot Demo");
        showConnectedChessbotButton = new JButton("Show Chessbot Info");
        playWithChessbotsButton = new JRadioButton("Play with Chessbots");
        playWithoutChessbotsButton = new JRadioButton("Play without Chessbots");
        connectToXbeeButton = new JButton("Connect to Xbee");
        enableChessAIMenuItem = new JCheckBoxMenuItem("Enable Chess AI");

        chessbotButtonGroup = new ButtonGroup();
        chessbotButtonGroup.add(playWithChessbotsButton);
        chessbotButtonGroup.add(playWithoutChessbotsButton);

        gameMenu.add(newGameMenuItem);
        gameMenu.add(newChessDemoMenuItem);
        chessbotMenu.add(playWithChessbotsButton);
        chessbotMenu.add(playWithoutChessbotsButton);
        chessbotMenu.add(showConnectedChessbotButton);
        optionsMenu.add(enableChessAIMenuItem);

        gameMenu.setEnabled(false);
        showConnectedChessbotButton.setEnabled(false);
        connectToXbeeButton.setEnabled(false);
        enableChessAIMenuItem.setEnabled(false);

        newGameMenuItem.addActionListener(menuListener);
        newChessDemoMenuItem.addActionListener(menuListener);
        showConnectedChessbotButton.addActionListener(menuListener);
        playWithChessbotsButton.addActionListener(menuListener);
        playWithoutChessbotsButton.addActionListener(menuListener);
        connectToXbeeButton.addActionListener(menuListener);
        enableChessAIMenuItem.addActionListener(menuListener);

        menuBar.add(gameMenu);
        menuBar.add(chessbotMenu);
        menuBar.add(optionsMenu);
        menuBar.add(connectToXbeeButton);

        setJMenuBar(menuBar);
    }

    private void switchManager(Manager manager) {
        manager.setComm(this.manager.getComm());
        this.manager = manager;
        boardPanel.setManager(this.manager);
        boardPanel.updateDisplay();
    }

    class MenuItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == playWithChessbotsButton) {
                gameMenu.setEnabled(true);
                connectToXbeeButton.setEnabled(true);
                enableChessAIMenuItem.setEnabled(true);
                showConnectedChessbotButton.setEnabled(true);
                newChessDemoMenuItem.setEnabled(true);
                switchManager(new NullManager());
            }

            if (e.getSource() == playWithoutChessbotsButton) {
                gameMenu.setEnabled(true);
                connectToXbeeButton.setEnabled(false);
                enableChessAIMenuItem.setEnabled(true);
                showConnectedChessbotButton.setEnabled(false);
                newChessDemoMenuItem.setEnabled(false);
                switchManager(new NullManager());
            }

            if (e.getSource() == newGameMenuItem) {
                if (playWithChessbotsButton.isSelected())
                {
                    if(manager.checkIfAllChessbotsAreConnected())
                        switchManager(new RobotChessManager());
                    else
                        JOptionPane.showMessageDialog(null, "All Chessbots need to be connected " +
                            "in order to play a chessgame with them. To check how many are " +
                            "currently connected, go to Options > Show Chessbot Info",
                            "Not enough Chessbots connected",
                            JOptionPane.WARNING_MESSAGE);

                }
                else
                    switchManager(new ChessManager());

                //We may run into problems here since we are creating new
                //managers but want to keep the same xbee object. Look at
                //Manager class to investigate

                switchManager(manager);
            }

            if (e.getSource() == enableChessAIMenuItem) {
                if (manager instanceof ChessManager) {
                    boolean state = enableChessAIMenuItem.getState();
                    ((ChessManager) manager).setComputerControlsTeam(state, Team.GREEN);
                }
                //Add dialogue later for choosing team for AI as well as
                //choosing difficulty
            }

            if (e.getSource() == newChessDemoMenuItem) {
                int[] robotsPresent = determineRobotsPresent();
                int[] initialLocations = generateInitialLocations(robotsPresent);
                manager = new RobotDemoManager(initialLocations);

                int boardRows = determineBoardRows();
                int boardColumns = determineBoardColumns();

                manager.setBoardRowCount(boardRows);
                manager.setBoardColumnCount(boardColumns);

                remove(boardPanel);
                boardPanel = new BoardPanel(manager);
                add(boardPanel);
                boardPanel.updateDisplay();
                setSize(boardColumns * SQUARE_SIZE, boardRows * SQUARE_SIZE);
            }

            if (e.getSource() == connectToXbeeButton) {

                boolean xbeeConnected = manager.isXbeeConnected();

                if(manager.isXbeeConnected())
                    JOptionPane.showMessageDialog(null, "Xbee is connected");
                else
                {
                    xbeeConnected = manager.connectToXbee();

                    if(xbeeConnected)
                        JOptionPane.showMessageDialog(null, "Successfully connected to Xbee");
                    else
                        JOptionPane.showMessageDialog(null, "Could not connect to Xbee. " +
                                "Try again after unplugging and plugging in the Xbee. " +
                                "If this does not work, restart the app.",
                                "Cannot find Xbee",
                                JOptionPane.WARNING_MESSAGE);
                }

            }
        }

        private int[] determineRobotsPresent()
        {
            String robotsPresentStr = (String) JOptionPane.showInputDialog(
                "Please enter space-separated list of robots present",
                "e.g. 1 2 4 6");
            String[] robotsPresentStrArr = robotsPresentStr.split(" ");

            int[] robotsPresentIntArr = new int[robotsPresentStrArr.length];
            for (int i = 0; i < robotsPresentIntArr.length; i++)
                robotsPresentIntArr[i] = Integer.parseInt(robotsPresentStrArr[i]);

            return robotsPresentIntArr;
        }

        private int[] generateInitialLocations(int[] robotsPresent)
        {
            int[] locations = new int[32];

            for (int i = 0; i < locations.length; i++)
                locations[i] = -1;

            for (int i = 0; i < robotsPresent.length; i++)
                locations[robotsPresent[i]] = robotsPresent[i];

            return locations;
        }

        private int determineBoardRows()
        {
            Object[] possibleDimensions = {"2", "3", "4", "5", "6", "7", "8"};
            String boardRows = (String) JOptionPane.showInputDialog(
                (Component) null,
                "Please enter the number of board rows",
                "Board Rows",
                JOptionPane.PLAIN_MESSAGE,
                (Icon) null,
                possibleDimensions,
                "8");
            int boardRowCount = Integer.parseInt(boardRows);

            return boardRowCount;
         }

        private int determineBoardColumns()
        {
            Object[] possibleDimensions = {"2", "3", "4", "5", "6", "7", "8"};
            String boardColumns = (String) JOptionPane.showInputDialog(
                (Component) null,
                "Please enter the number of board columns",
                "Board Columns",
                JOptionPane.PLAIN_MESSAGE,
                (Icon) null,
                possibleDimensions,
                "8");
            int boardColumnCount = Integer.parseInt(boardColumns);

            return boardColumnCount;
         }
    }

    public static void main(String[] args)
    {
        @SuppressWarnings("unused")
        MainFrame frame = new MainFrame();
    }
}
