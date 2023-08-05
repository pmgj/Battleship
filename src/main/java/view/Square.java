package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import model.CellState;

public class Square extends JButton implements PropertyChangeListener {
    public Square() {
        float[] hsb = Color.RGBtoHSB(180, 180, 255, null);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(hsb[0], hsb[1], hsb[2])));
        this.setSize(new Dimension(32, 32));
        this.setFont(new Font("Arial", Font.PLAIN, 12));
        this.setMargin(new Insets(0, 0, 0, 0));
    }

    public void setShip() {
        float[] hsb = Color.RGBtoHSB(180, 180, 255, null);
        this.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CellState cellState = (CellState) evt.getSource();
        this.setForeground(Color.WHITE);
        this.setFont(this.getFont().deriveFont(Font.BOLD));
        switch (cellState.getState()) {
            case WATER, NONE -> {
                this.setBackground(Color.BLUE.brighter());
            }
            case SHIP, SHOT -> {
                this.setBackground(Color.RED);
                this.setText("S");
            }
        }
        this.setFocusPainted(false);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(Color.RGBtoHSB(180, 180, 255, null)));
    }
}
