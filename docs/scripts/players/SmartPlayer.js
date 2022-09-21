import Cell from "../model/Cell.js";
import State from "../model/State.js";
import AbstractPlayer from "./AbstractPlayer.js";

export default class SmartPlayer extends AbstractPlayer {
    constructor(grid) {
        super(grid);
        this.lastCell = null;
        this.shipFound = false;
        this.xDir = -1;
        this.yDir = 0;
    }
    play() {
        if (this.lastShot === State.SHOT) {
            this.shipFound = true;
            let { x, y } = this.lastCell;
            let borders = [new Cell(x - 1, y), new Cell(x, y + 1), new Cell(x + 1, y), new Cell(x, y - 1)];
            let temp = borders.find(({ x: a, y: b }) => this.grid[a][b].getState() === State.NONE);
            if(temp) {
                return temp;
            }
        }
        this.lastCell = this.getRandomCell(this.getRandomInt(0, this.rows * this.cols - 1));
        if (!this.lastCell) {
            this.lastCell = this.getRandomCell();
        }
        return this.lastCell;
    }
}