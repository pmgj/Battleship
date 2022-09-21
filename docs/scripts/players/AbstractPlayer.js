import State from "../model/State.js";
import Cell from "../model/Cell.js";

export default class AbstractPlayer {
    constructor(grid) {
        this.grid = grid;
        this.rows = this.grid.length;
        this.cols = this.grid[0].length;
        this.lastShot = State.NONE;
    }
    getRandomCell(start = 0) {
        let array = this.grid.flat();
        for (let i = start; i < array.length; i++) {
            if (array[i].getState() === State.NONE) {
                return new Cell(Math.floor(i / this.cols), i % this.cols);
            }
        }
    }
    getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min + 1) + min);
    }
    setLastShot(state) {
        this.lastShot = state;
    }
}