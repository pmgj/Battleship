package model.players;

import model.Cell;
import model.CellState;

public class BeginnerPlayer extends AbstractPlayer {
    public BeginnerPlayer(CellState[][] grid) {
        super(grid);
    }

    public Cell play() {
        var cell = this.getRandomCell(this.getRandomInt(0, this.rows * this.cols - 1));
        if (cell.isEmpty()) {
            cell = this.getRandomCell();
        }
        return cell.get();
    }
}
