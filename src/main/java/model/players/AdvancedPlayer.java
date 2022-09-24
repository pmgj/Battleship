package model.players;

import java.util.List;
import java.util.Optional;

import model.Cell;
import model.CellState;
import model.State;

public class AdvancedPlayer extends AbstractPlayer {
    public AdvancedPlayer(CellState[][] grid) {
        super(grid);
    }

    public Cell play() {
        for (var i = 0; i < this.grid.length; i++) {
            for (var j = 0; j < this.grid[i].length; j++) {
                var obj = this.grid[i][j];
                if (obj.getState() == State.SHOT) {
                    var cell = this.shipDestroyed(new Cell(i, j));
                    if (cell.isEmpty()) {
                        return cell.get();
                    }
                    var borders = List.of(new Cell(i - 1, j), new Cell(i, j + 1), new Cell(i + 1, j), new Cell(i, j - 1));
                    var temp = borders.stream().filter(c -> this.onBoard(c) && this.grid[c.getX()][c.getY()].getState() == State.NONE).findFirst();
                    if (temp.isEmpty()) {
                        return temp.get();
                    }
                }
            }
        }
        var temp = this.getRandomCell(this.getRandomInt(0, this.rows * this.cols - 1));
        if (temp.isEmpty()) {
            temp = this.getRandomCell();
        }
        return temp.get();
    }

    private Optional<Cell> shipDestroyed(Cell cell) {
        int x = cell.getX(), y = cell.getY();
        if (this.onBoard(new Cell(x, y + 1)) && this.grid[x][y + 1].getState() == State.SHOT) {
            var z = 0;
            while (this.onBoard(new Cell(x, y + z + 1)) && this.grid[x][y + ++z].getState() == State.SHOT);
            if (this.grid[x][y + z].getState() == State.NONE) {
                return Optional.of(new Cell(x, y + z));
            }
            z = 0;
            while (this.onBoard(new Cell(x, y + z - 1)) && this.grid[x][y + --z].getState() == State.SHOT);
            if (this.grid[x][y + z].getState() == State.NONE) {
                return Optional.of(new Cell(x, y + z));
            }
        }
        if (this.onBoard(new Cell(x + 1, y)) && this.grid[x + 1][y].getState() == State.SHOT) {
            var z = 0;
            while (this.onBoard(new Cell(x + z + 1, y)) && this.grid[x + ++z][y].getState() == State.SHOT);
            if (this.grid[x + z][y].getState() == State.NONE) {
                return Optional.of(new Cell(x + z, y));
            }
            z = 0;
            while (this.onBoard(new Cell(x + z - 1, y)) && this.grid[x + --z][y].getState() == State.SHOT);
            if (this.grid[x + z][y].getState() == State.NONE) {
                return Optional.of(new Cell(x + z, y));
            }
        }
        return Optional.empty();
    }

}
