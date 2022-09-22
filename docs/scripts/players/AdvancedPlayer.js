import Cell from "../model/Cell.js";
import State from "../model/State.js";
import AbstractPlayer from "./AbstractPlayer.js";

export default class AdvancedPlayer extends AbstractPlayer {
    constructor(grid) {
        super(grid);
    }
    play() {
        for (let i = 0; i < this.grid.length; i++) {
            for (let j = 0; j < this.grid[i].length; j++) {
                const obj = this.grid[i][j];
                if (obj.getState() === State.SHOT) {
                    let cell = this.shipDestroyed(new Cell(i, j));
                    if (cell) {
                        return cell;
                    }
                    let borders = [new Cell(i - 1, j), new Cell(i, j + 1), new Cell(i + 1, j), new Cell(i, j - 1)];
                    let temp = borders.find(c => this.onBoard(c) && this.grid[c.x][c.y].getState() === State.NONE);
                    if (temp) {
                        return temp;
                    }
                }
            }
        }
        let temp = this.getRandomCell(this.getRandomInt(0, this.rows * this.cols - 1));
        if (!temp) {
            temp = this.getRandomCell();
        }
        return temp;
    }
    shipDestroyed({ x, y }) {
        if (this.onBoard(new Cell(x, y + 1)) && this.grid[x][y + 1].getState() === State.SHOT) {
            let z = 0;
            while (this.onBoard(new Cell(x, y + z + 1)) && this.grid[x][y + ++z].getState() === State.SHOT);
            if (this.grid[x][y + z].getState() === State.NONE) {
                return new Cell(x, y + z);
            }
            z = 0;
            while (this.onBoard(new Cell(x, y + z - 1)) && this.grid[x][y + --z].getState() === State.SHOT);
            if (this.grid[x][y + z].getState() === State.NONE) {
                return new Cell(x, y + z);
            }
        }
        if (this.onBoard(new Cell(x + 1, y)) && this.grid[x + 1][y].getState() === State.SHOT) {
            let z = 0;
            while (this.onBoard(new Cell(x + z + 1, y)) && this.grid[x + ++z][y].getState() === State.SHOT);
            if (this.grid[x + z][y].getState() === State.NONE) {
                return new Cell(x + z, y);
            }
            z = 0;
            while (this.onBoard(new Cell(x + z - 1, y)) && this.grid[x + --z][y].getState() === State.SHOT);
            if (this.grid[x + z][y].getState() === State.NONE) {
                return new Cell(x + z, y);
            }
        }
    }
}