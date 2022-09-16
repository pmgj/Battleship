import State from "./State.js";
import Cell from "./Cell.js";
import Ship from "./Ship.js";

export default class Grid {
    constructor(nrows, ncols) {
        this.rows = nrows;
        this.cols = ncols;
        this.ships = [];
        this.board = Array(this.rows).fill().map(() => Array(this.cols).fill(State.NONE));
    }
    placeShips(ships) {
        if (!ships.every(s => s.every(c => this.onBoard(c)))) {
            throw new Error("One ship is not on the board.");
        }
        let tempList = ships.flat();
        let tempSet = [...new Set(ships.flat())];
        if (tempList.length !== tempSet.length) {
            throw new Error("There are ships overlapping.");
        }
        this.ships = ships.map(s => new Ship(s));
    }
    placeShipsRandomly(sizes) {
        let dir = 0, xCoord = 0, yCoord = 0, flag = true, overlap = false;
        for (let shipSize of sizes) {
            flag = true;
            while (flag) {
                overlap = false;
                xCoord = this.getRandomInt(0, this.rows - 1); //get a random x coordinate
                yCoord = this.getRandomInt(0, this.cols - 1); //get a random y coordinate                
                dir = this.getRandomInt(0, 1); //get a random direction, 0 = vertical, 1 = horizontal
                if ((this.board[xCoord][yCoord] === State.NONE) && (((dir === 0) && ((xCoord + shipSize) < this.rows)) || ((dir === 1) && ((yCoord + shipSize) < this.cols)))) {
                    for (let j = 0; j < shipSize; j++) {
                        if ((dir === 0) && this.testCellPosition(new Cell(xCoord + j, yCoord))) {
                            overlap = true;
                        } else if ((dir === 1) && this.testCellPosition(new Cell(xCoord, yCoord + j))) {
                            overlap = true;
                        }
                    }
                    if (overlap === false) {
                        flag = false;
                    }
                }
            }
            let ship = [];
            for (let k = 0; k < shipSize; k++) {
                if (dir === 0) {
                    console.log(xCoord + k);
                    this.board[xCoord + k][yCoord] = State.SHIP;
                    ship.push(new Cell(xCoord + k, yCoord));
                } else {
                    console.log(yCoord + k);
                    this.board[xCoord][yCoord + k] = State.SHIP;
                    ship.push(new Cell(xCoord, yCoord + k));
                }
            }
            this.ships.push(ship);
        }
    }
    getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min + 1) + min);
    }
    testCellPosition({ x, y }) {
        let pos = [new Cell(x - 1, y - 1), new Cell(x - 1, y), new Cell(x - 1, y + 1), new Cell(x, y - 1), new Cell(x, y), new Cell(x, y + 1), new Cell(x + 1, y - 1), new Cell(x + 1, y), new Cell(x + 1, y + 1)];
        return pos.some(c => this.testCell(c));
    }
    testCell(cell) {
        if (this.onBoard(cell)) {
            let { x, y } = cell;
            return this.board[x][y] === State.SHIP;
        } else {
            return false;
        }
    }
    onBoard({ x, y }) {
        let inLimit = (value, limit) => value >= 0 && value < limit;
        return (inLimit(x, this.rows) && inLimit(y, this.cols));
    }
    endOfGame() {
        let ok = true;
        for (const ship of this.ships) {
            for (let { x, y } of ship) {
                if (this.matrix[x][y] === State.SHIP) {
                    ok = false;
                }
            }
        }
        return ok;
    }
}