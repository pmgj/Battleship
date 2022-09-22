import Cell from "../model/Cell.js";
import State from "../model/State.js";
import AbstractPlayer from "./AbstractPlayer.js";

export default class IntermediatePlayer extends AbstractPlayer {
    constructor(grid) {
        super(grid);
    }
    play() {
        for (let i = 0; i < this.grid.length; i++) {
            for (let j = 0; j < this.grid[i].length; j++) {
                const obj = this.grid[i][j];
                if (obj.getState() === State.SHOT) {
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
}