package model.players;

import java.util.List;

import model.Cell;
import model.CellState;
import model.State;

public class IntermediatePlayer extends AbstractPlayer {
    public IntermediatePlayer(CellState[][] grid) {
        super(grid);
    }

    public Cell play() {
        for (var i = 0; i < this.grid.length; i++) {
            for (var j = 0; j < this.grid[i].length; j++) {
                var obj = this.grid[i][j];
                if (obj.getState() == State.SHOT) {
                    var borders = List.of(new Cell(i - 1, j), new Cell(i, j + 1), new Cell(i + 1, j),
                            new Cell(i, j - 1));
                    var temp = borders.stream()
                            .filter(c -> this.onBoard(c) && this.grid[c.getX()][c.getY()].getState() == State.NONE)
                            .findFirst().get();
                    return temp;
                }
            }
        }
        var temp = this.getRandomCell(this.getRandomInt(0, this.rows * this.cols - 1));
        if (temp.isEmpty()) {
            temp = this.getRandomCell();
        }
        return temp.get();
    }

}
