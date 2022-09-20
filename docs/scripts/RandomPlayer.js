import Cell from "./Cell.js";
import State from "./State.js";

export default class RandomPlayer {
    constructor(grid) {
        this.grid = grid;
    }
    play() {
        let rows = this.grid.length;
        let cols = this.grid[0].length;
        let array = this.grid.flat();
        let index = array.indexOf(State.NONE, this.getRandomInt(0, rows * cols - 1));
        if (index === -1) {
            index = array.indexOf(State.NONE);
            if (index === -1) {
                throw new Error("The grid does not have any empty spaces.");
            }
        }
        return new Cell(Math.floor(index / cols), index % cols);
    }
    getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min + 1) + min);
    }
    setResultShot({ x, y }, shot) {
        this.grid[x][y] = shot;
    }
}