package model;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Battleship {
    private List<Ship> p1Ships;
    private List<Ship> p2Ships;
    private int rows;
    private int cols;
    private Player turn = Player.PLAYER1;
    private Winner winner = Winner.NONE;

    public void setData(int rows, int cols, List<List<Cell>> p1Ships, List<List<Cell>> p2Ships) throws Exception {
        this.rows = rows;
        this.cols = cols;
        if (!p1Ships.stream().allMatch(s -> s.stream().allMatch(c -> this.onBoard(c)))) {
            throw new Exception("One ship is not on the board.");
        }
        if (!p2Ships.stream().allMatch(s -> s.stream().allMatch(c -> this.onBoard(c)))) {
            throw new Exception("One ship is not on the board.");
        }
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
        List<Cell> tempList = p1Ships.stream().flatMap(List::stream).collect(Collectors.toList());
        Set<Cell> tempSet = p1Ships.stream().flatMap(List::stream).collect(Collectors.toSet());
        if (tempList.size() != tempSet.size()) {
            throw new Exception("There are ships overlapping.");
        }
        tempList = p2Ships.stream().flatMap(List::stream).collect(Collectors.toList());
        tempSet = p2Ships.stream().flatMap(List::stream).collect(Collectors.toSet());
        if (tempList.size() != tempSet.size()) {
            throw new Exception("There are ships overlapping.");
        }
        this.p1Ships = p1Ships.stream().map(s -> new Ship(s)).collect(Collectors.toList());
        this.p2Ships = p2Ships.stream().map(s -> new Ship(s)).collect(Collectors.toList());
    }

    public Player getTurn() {
        return turn;
    }

    public Winner getWinner() {
        return winner;
    }

    public void play(Player player, Cell endCell) throws Exception {
        if (this.winner != Winner.NONE) {
            throw new Exception("This game is already finished.");
        }
        if (player != this.turn) {
            throw new Exception("It's not your turn.");
        }
        if (!this.onBoard(endCell)) {
            throw new Exception("Shot is not on board.");
        }
        List<Ship> ships = player == Player.PLAYER1 ? this.p1Ships : this.p2Ships;
        for (Ship ship : ships) {
            for (Position p : ship.getPositions()) {
                if (p.getCell().equals(endCell)) {
                    p.setShot(true);
                }
            }
        }
        this.turn = this.turn == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
        this.winner = this.endOfGame();
    }

    private Winner endOfGame() {
        boolean b1 = this.p1Ships.stream().allMatch(s -> s.getPositions().stream().allMatch(c -> c.isShot()));
        if (b1) {
            return Winner.PLAYER2;
        }
        boolean b2 = this.p2Ships.stream().allMatch(s -> s.getPositions().stream().allMatch(c -> c.isShot()));
        if (b2) {
            return Winner.PLAYER1;
        }
        return Winner.NONE;
    }

    public boolean onBoard(Cell cell) {
        BiFunction<Integer, Integer, Boolean> inLimit = (value, limit) -> value >= 0 && value < limit;
        return (inLimit.apply(cell.getX(), this.rows) && inLimit.apply(cell.getY(), this.cols));
    }

    private String printBoard(List<Ship> ships) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= this.rows * 4; i++) {
            sb.append("-");
        }
        sb.append("\n");
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                sb.append("|");
                Cell cell = new Cell(i, j);
                Position position = null;
                for (Ship ship : ships) {
                    for (Position p : ship.getPositions()) {
                        if (p.getCell().equals(cell)) {
                            position = p;
                        }
                    }
                }        
                sb.append(position == null ? "   " : position.isShot() ? " X " : " S ");
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
