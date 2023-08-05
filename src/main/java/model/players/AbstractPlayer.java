package model.players;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import model.Cell;
import model.CellState;
import model.State;

public abstract class AbstractPlayer {
    protected CellState[][] grid;
    protected int rows;
    protected int cols;

    public AbstractPlayer(CellState[][] grid) {
        this.grid = grid;
        this.rows = this.grid.length;
        this.cols = this.grid[0].length;
    }

    public abstract Cell play();

    protected Optional<Cell> getRandomCell() {
        return this.getRandomCell(0);
    }

    protected Optional<Cell> getRandomCell(int start) {
        CellState[] array = Arrays.stream(this.grid).flatMap(Stream::of).toArray(CellState[]::new);
        for (var i = start; i < array.length; i++) {
            if (array[i].getState() == State.NONE) {
                return Optional.of(new Cell(i / this.cols, i % this.cols));
            }
        }
        return Optional.empty();
    }

    protected int getRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    protected boolean onBoard(Cell cell) {
        BiFunction<Integer, Integer, Boolean> inLimit = (value, limit) -> value >= 0 && value < limit;
        return (inLimit.apply(cell.x(), this.rows) && inLimit.apply(cell.y(), this.cols));
    }
}
