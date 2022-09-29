package model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Battleship {
    private Grid p1Ships;
    private Grid p2Ships;
    private int rows;
    private int cols;
    private Player turn;
    private Winner winner;

    public Battleship(int nrows, int ncols) {
        this.p1Ships = new Grid(nrows, ncols);
        this.p2Ships = new Grid(nrows, ncols);
        this.rows = nrows;
        this.cols = ncols;
        this.turn = Player.PLAYER1;
        this.winner = Winner.NONE;
    }

    public void setData(List<List<Cell>> p1Ships, List<List<Cell>> p2Ships) throws Exception {
        if (p1Ships.size() != p2Ships.size()) {
            throw new Exception("The number of ships of players is different.");
        }
        List<Integer> is1 = p1Ships.stream().map(s -> s.size()).collect(Collectors.toList());
        List<Integer> is2 = p2Ships.stream().map(s -> s.size()).collect(Collectors.toList());
        Collections.sort(is1);
        Collections.sort(is2);
        for (int i = 0; i < is1.size(); i++) {
            if (is1.get(i) != is2.get(i)) {
                throw new Exception("The size of the ships of players is different.");
            }
        }
        this.p1Ships.placeShips(p1Ships);
        this.p2Ships.placeShips(p2Ships);
    }

    public void setWinner(Winner winner) {
        if(this.winner != Winner.NONE) {
            return;
        }
        this.winner = winner;
    }

    public void setRandomShips(int[] sizes) {
        this.p1Ships.placeShipsRandomly(sizes);
        this.p2Ships.placeShipsRandomly(sizes);
    }

    public Player getTurn() {
        return turn;
    }

    public Winner getWinner() {
        return winner;
    }

    public void play(Player player, Cell endCell) {
        if (this.winner != Winner.NONE) {
            throw new IllegalArgumentException("This game is already finished.");
        }
        if (player != this.turn) {
            throw new IllegalArgumentException("It's not your turn.");
        }
        Grid grid = player == Player.PLAYER1 ? this.p2Ships : this.p1Ships;
        grid.shot(endCell);
        this.turn = this.turn == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
        this.winner = this.endOfGame();
    }

    public Grid getGrid(Player player) {
        return player == Player.PLAYER1 ? this.p1Ships : this.p2Ships;
    }

    private Winner endOfGame() {
        if (this.p1Ships.endOfGame()) {
            return Winner.PLAYER2;
        }
        if (this.p2Ships.endOfGame()) {
            return Winner.PLAYER1;
        }
        return Winner.NONE;
    }

    private String printBoard(Grid grid) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= this.rows * 4; i++) {
            sb.append("-");
        }
        sb.append("\n");
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                sb.append("|");
                Cell position = null;
                sb.append(position == null ? "   " : grid.getBoard()[i][j].getState() == State.SHOT ? " X " : " S ");
            }
            sb.append("|\n");
            for (int j = 0; j <= this.rows * 4; j++) {
                sb.append("-");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.printBoard(this.p1Ships) + this.printBoard(this.p2Ships);
    }
}
