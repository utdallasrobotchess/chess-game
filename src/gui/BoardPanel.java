/**
 *
 * @author Ryan J. Marcotte
 */

package gui;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import manager.UIState;
import manager.RobotState;
import robot.Motion;

public class BoardPanel extends JPanel
{
    final int TOTAL_SQUARES = 64;
    
    UIState uiState;
    RobotState robotState;

    SquareButton squares[];
    
    int rows;
    int columns;
    
    protected BoardPanel(UIState uiState, RobotState robotState, int rows, int columns)
    {
        this.uiState = uiState;
        this.robotState = robotState;
        this.rows = rows;
        this.columns = columns;
        
        setLayout(new GridLayout(rows, columns));
        
        squares = new SquareButton[TOTAL_SQUARES];

        initializeSquares();
    }

    protected void initializeSquares()
    {
        boolean whiteSquare = true;

        for (int i = 0; i < squares.length; i++) {
            Color squareColor = whiteSquare ? Color.WHITE : Color.BLACK;
            squares[i] = new SquareButton(i, squareColor);
            squares[i].addActionListener(new ButtonListener());

            if (i % 8 < columns && i / 8 < rows)
                add(squares[i]);

            if (i % 8 != 7)
                whiteSquare = !whiteSquare;
        }
    }

    protected void initializeDemo()
    {
        squares[0].setIcon(uiState.getPieceImage("green-pawn"));
    }

    class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SquareButton buttonPressed = (SquareButton) e.getSource();

            if (uiState.isDemoMode()) {
                int selectedIndex = uiState.getSelectedIndex();

                if (selectedIndex == -1 && buttonPressed.isOccupied()) {
                    uiState.setSelectedIndex(buttonPressed.getIndex());
                }

                if (selectedIndex != -1 && !buttonPressed.isOccupied()) {
                    squares[selectedIndex].setIcon(null);
                    uiState.setSelectedIndex(-1);
                    buttonPressed.setIcon(uiState.getPieceImage("green-pawn"));

                    int current[] = {selectedIndex};
                    int desired[] = {buttonPressed.getIndex()};

                    System.out.println("current: " + selectedIndex);
                    System.out.println("desired: " + buttonPressed.getIndex());
                    
                    robotState.addNewMotion(new Motion(current, desired));
                }
            }
        }
    }
}
