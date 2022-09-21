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
        let index = this.firstIndex(array, this.getRandomInt(0, rows * cols - 1));
        if (index === -1) {
            index = this.firstIndex(array);
            if (index === -1) {
                throw new Error("The grid does not have any empty spaces.");
            }
        }
        return new Cell(Math.floor(index / cols), index % cols);
    }
    firstIndex(array, start = 0) {
        for(let i = start; i < array.length; i++) {
            if(array[i].getState() === State.NONE) {
                return i;
            }
        }
        return -1;
    }
    getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min + 1) + min);
    }
}