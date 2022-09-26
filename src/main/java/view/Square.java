package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import model.CellState;

public class Square extends JButton implements PropertyChangeListener {
    public Square() {
        this.setBackground(Color.DARK_GRAY);
        this.setSize(new Dimension(32, 32));
        this.setFont(new Font("Arial", Font.PLAIN, 12));
        this.setMargin(new Insets(0, 0, 0, 0));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CellState cellState = (CellState) evt.getSource();
        this.setForeground(Color.WHITE);
        this.setFont(this.getFont().deriveFont(Font.BOLD));
        switch(cellState.getState()) {
            case NONE:
            this.setBackground(Color.BLUE);
            this.setText(" ");
            break;
            case SHIP:
            this.setBackground(Color.RED);
            this.setText("S");
            break;
            case SHOT:
            this.setBackground(Color.RED);
            this.setText("S");
            break;
            case WATER:
            this.setBackground(Color.BLUE);
            this.setText(" ");
            break;
        }
        this.setFocusPainted(false);
    }
}
