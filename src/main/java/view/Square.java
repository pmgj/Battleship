package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

public class Square extends JButton implements PropertyChangeListener {
    public Square() {
        this.setBackground(Color.DARK_GRAY);
        this.setSize(new Dimension(32, 32));
        this.setFont(new Font("Arial", Font.PLAIN, 12));
        this.setMargin(new Insets(0, 0, 0, 0));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Aqui!");
    }    
}
