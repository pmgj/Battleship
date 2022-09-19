import Cell from "./Cell.js";
import State from "./State.js";

export default class RandomPlayer {
    constructor(grid) {
        this.grid = grid;
    }
    play() {
        let rows = this.grid.length;
        let cols = this.grid[0].length;
        let x, y;
        do {
            x = this.getRandomInt(0, rows - 1);
            y = this.getRandomInt(0, cols - 1);
        } while (this.grid[x][y] !== State.NONE);
        return new Cell(x, y);
    }
    getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min + 1) + min);
    }
    setResultShot({x, y}, shot) {
        this.grid[x][y] = shot;
    }
}