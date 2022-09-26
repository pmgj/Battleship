package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Battleship;
import model.Cell;
import model.Player;
import model.Winner;
import model.players.AbstractPlayer;
import model.players.AdvancedPlayer;

public class MainWindow {

    private final int rows = 10;
    private final int cols = 10;

    private Battleship game;
    private AbstractPlayer computer;

    public MainWindow() {
        JFrame frame = new JFrame("Battleship");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel playersPanel = new JPanel();
        playersPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        JLabel playersLabel = new JLabel("My Board", SwingConstants.CENTER);
        playersPanel.add(playersLabel);
        JPanel playersBoard = new JPanel(new GridLayout(rows, cols));
        playersPanel.add(playersBoard);

        JPanel opponentsPanel = new JPanel();
        opponentsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        opponentsPanel.setLayout(new BoxLayout(opponentsPanel, BoxLayout.Y_AXIS));
        JLabel opponentsLabel = new JLabel("Opponent's Board", SwingConstants.CENTER);
        opponentsPanel.add(opponentsLabel);
        JPanel opponentsBoard = new JPanel(new GridLayout(rows, cols));
        opponentsPanel.add(opponentsBoard);

        this.game = new Battleship(rows, cols);
        this.game.setRandomShips(new int[] { 5, 4, 3, 3, 2 });
        this.computer = new AdvancedPlayer(this.game.getGrid(Player.PLAYER1).getBoard());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Square b1 = new Square();
                playersBoard.add(b1);
                this.game.getGrid(Player.PLAYER1).addObserver(new Cell(i, j), b1);
                Square b2 = new Square();
                opponentsBoard.add(b2);
                this.game.getGrid(Player.PLAYER2).addObserver(new Cell(i, j), b2);
                final int x = i, y = j;
                b2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        game.play(Player.PLAYER1, new Cell(x, y));
                        var winner = game.getWinner();
                        if (winner == Winner.NONE) {
                            var cell = computer.play();
                            game.play(Player.PLAYER2, cell);
                            winner = game.getWinner();
                        }
                        if (winner != Winner.NONE) {
                            JOptionPane.showMessageDialog(frame, winner == Winner.PLAYER1 ? "You Win!" : "You lose!",
                                    "Game over!", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });
            }
        }
        playersPanel.revalidate();
        playersPanel.setPreferredSize(new Dimension(32 * cols, 32 * rows));
        opponentsPanel.revalidate();
        opponentsPanel.setPreferredSize(new Dimension(32 * cols, 32 * rows));
        mainPanel.add(playersPanel);
        mainPanel.add(opponentsPanel);

        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension d = new Dimension((int) (2 * playersPanel.getPreferredSize().getWidth()),
                (int) playersPanel.getPreferredSize().getHeight());
        frame.setSize(d);
        frame.pack();

        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
