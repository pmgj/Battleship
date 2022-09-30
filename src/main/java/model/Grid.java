package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import view.Square;

public class Grid {
    private int rows;
    private int cols;
    private List<Ship> ships;
    private CellState[][] hiddenBoard;
    private CellState[][] openBoard;

    public Grid(int nrows, int ncols) {
        this.rows = nrows;
        this.cols = ncols;
        this.ships = new ArrayList<>();
        this.hiddenBoard = new CellState[this.rows][this.cols];
        this.openBoard = new CellState[this.rows][this.cols];
    }

    public void placeShips(List<List<Cell>> ships) {
        if(!this.ships.isEmpty()) {
            throw new IllegalArgumentException("There are already ships in the board.");
        }
        if (!ships.stream().allMatch(s -> s.stream().allMatch(c -> this.onBoard(c)))) {
            throw new IllegalArgumentException("One ship is not on the board.");
        }
        List<Cell> tempList = ships.stream().flatMap(List::stream).collect(Collectors.toList());
        Set<Cell> tempSet = ships.stream().flatMap(List::stream).collect(Collectors.toSet());
        if (tempList.size() != tempSet.size()) {
            throw new IllegalArgumentException("There are ships overlapping.");
        }
        this.ships = ships.stream().map(s -> new Ship(s)).collect(Collectors.toList());
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.hiddenBoard[i][j] = new CellState(State.NONE);
                this.openBoard[i][j] = new CellState(State.NONE);
            }
        }
        for(var ship : this.ships) {
            for(var cell : ship.getPositions()) {
                this.hiddenBoard[cell.getX()][cell.getY()] = new CellState(State.SHIP);
                this.openBoard[cell.getX()][cell.getY()] = new CellState(State.NONE);
            }
        }
    }

    private boolean onBoard(Cell cell) {
        BiFunction<Integer, Integer, Boolean> inLimit = (value, limit) -> value >= 0 && value < limit;
        return (inLimit.apply(cell.getX(), this.rows) && inLimit.apply(cell.getY(), this.cols));
    }

    private int getRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    public void placeShipsRandomly(int[] sizes) {
        if(!this.ships.isEmpty()) {
            throw new IllegalArgumentException("There are already ships in the board.");
        }
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.hiddenBoard[i][j] = new CellState(State.NONE);
                this.openBoard[i][j] = new CellState(State.NONE);
            }
        }
        int dir = 0, xCoord = 0, yCoord = 0;
        boolean flag = true, overlap = false;
        for (int shipSize : sizes) {
            flag = true;
            while (flag) {
                overlap = false;
                xCoord = this.getRandomInt(0, this.rows - 1); //get a random x coordinate
                yCoord = this.getRandomInt(0, this.cols - 1); //get a random y coordinate                
                dir = this.getRandomInt(0, 1); //get a random direction, 0 = vertical, 1 = horizontal
                if ((dir == 0 && xCoord + shipSize < this.rows) || (dir == 1 && yCoord + shipSize < this.cols)) {
                    for (int j = 0; j < shipSize; j++) {
                        if (dir == 0 && this.testCellPosition(new Cell(xCoord + j, yCoord))) {
                            overlap = true;
                        } else if (dir == 1 && this.testCellPosition(new Cell(xCoord, yCoord + j))) {
                            overlap = true;
                        }
                    }
                    if (overlap == false) {
                        flag = false;
                    }
                }
            }
            List<Cell> cells = new ArrayList<>();
            for (int k = 0; k < shipSize; k++) {
                if (dir == 0) {
                    this.hiddenBoard[xCoord + k][yCoord].setState(State.SHIP);
                    cells.add(new Cell(xCoord + k, yCoord));
                } else {
                    this.hiddenBoard[xCoord][yCoord + k].setState(State.SHIP);
                    cells.add(new Cell(xCoord, yCoord + k));
                }
            }
            this.ships.add(new Ship(cells));
        }
    }

    private boolean testCellPosition(Cell cell) {
        int x = cell.getX(), y = cell.getY();
        var pos = List.of(new Cell(x - 1, y - 1), new Cell(x - 1, y), new Cell(x - 1, y + 1), new Cell(x, y - 1),
                new Cell(x, y), new Cell(x, y + 1), new Cell(x + 1, y - 1), new Cell(x + 1, y), new Cell(x + 1, y + 1));
        return pos.stream().anyMatch(c -> this.testCell(c));
    }

    private boolean testCell(Cell cell) {
        if (this.onBoard(cell)) {
            int x = cell.getX(), y = cell.getY();
            return this.hiddenBoard[x][y].getState() == State.SHIP;
        } else {
            return false;
        }
    }

    public boolean endOfGame() {
        var ok = true;
        for (var ship : this.ships) {
            for (var cell : ship.getPositions()) {
                int x = cell.getX(), y = cell.getY();
                if (this.hiddenBoard[x][y].getState() == State.SHIP) {
                    ok = false;
                }
            }
        }
        return ok;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public CellState[][] getBoard() {
        return openBoard;
    }

    public CellState[][] getHiddenBoard() {
        return hiddenBoard;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void shot(Cell cell) {
        int x = cell.getX(), y = cell.getY();
        if (this.hiddenBoard[x][y].getState() == State.SHOT || this.hiddenBoard[x][y].getState() == State.WATER) {
            throw new IllegalArgumentException("Cell already shot.");
        }
        if (!this.onBoard(cell)) {
            throw new IllegalArgumentException("Shot is not on board.");
        }
        var newstate = this.hiddenBoard[x][y].getState() == State.SHIP ? State.SHOT : State.WATER;
        this.hiddenBoard[x][y].setState(newstate);
        this.openBoard[x][y].setState(newstate);
        this.ships.stream().filter(ship -> ship.getPositions().stream().allMatch(c -> this.hiddenBoard[c.getX()][c.getY()].getState() != State.SHIP)).forEach(ship2 -> ship2.getPositions().stream().forEach(p -> this.shootHV(p)));
    }

    private void shootHV(Cell cell) {
        int row = cell.getX(), col = cell.getY();
        var cells = List.of(new Cell(row - 1, col - 1), new Cell(row - 1, col), new Cell(row - 1, col + 1), new Cell(row, col - 1), new Cell(row, col + 1), new Cell(row + 1, col - 1), new Cell(row + 1, col), new Cell(row + 1, col + 1));
        cells.stream().filter(c1 -> this.onBoard(c1) && this.hiddenBoard[c1.getX()][c1.getY()].getState() == State.NONE).forEach(c2 -> {
            this.hiddenBoard[c2.getX()][c2.getY()].setState(State.WATER);
            this.openBoard[c2.getX()][c2.getY()].setState(State.WATER);
        });
    }

    public void addObserver(Cell cell, Square square) {
        this.hiddenBoard[cell.getX()][cell.getY()].addSquareObservers(square);
    }
}
