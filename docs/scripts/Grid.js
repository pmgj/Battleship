import State from "./State.js";
import Cell from "./Cell.js";
import Ship from "./Ship.js";

export default class Grid {
    constructor(nrows, ncols, gui) {
        this.rows = nrows;
        this.cols = ncols;
        this.ships = [];
        this.board = Array(this.rows).fill().map(() => Array(this.cols).fill(State.NONE));
        this.gui = gui;
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
                if ((dir === 0 && xCoord + shipSize < this.rows) || (dir === 1 && yCoord + shipSize < this.cols)) {
                    for (let j = 0; j < shipSize; j++) {
                        if (dir === 0 && this.testCellPosition(new Cell(xCoord + j, yCoord))) {
                            overlap = true;
                        } else if (dir === 1 && this.testCellPosition(new Cell(xCoord, yCoord + j))) {
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
                    this.board[xCoord + k][yCoord] = State.SHIP;
                    ship.push(new Cell(xCoord + k, yCoord));
                } else {
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
                if (this.board[x][y] === State.SHIP) {
                    ok = false;
                }
            }
        }
        return ok;
    }
    getRows() {
        return this.rows;
    }
    getCols() {
        return this.cols;
    }
    getBoard() {
        return this.board;
    }
    shot(cell) {
        let { x, y } = cell;
        if (this.board[x][y] === State.SHOT || this.board[x][y] === State.WATER) {
            throw new Error("Cell already shot.");
        }
        this.board[x][y] = this.board[x][y] === State.SHIP ? State.SHOT : State.WATER;
        this.gui.gridChange(cell, this.board[x][y]);
        this.fillUnuseful();
    }
    getCodifiedBoard() {
        let matrix = JSON.parse(JSON.stringify(this.board));
        for (let i = 0; i < matrix.length; i++) {
            for (let j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = matrix[i][j] === State.SHIP ? State.NONE : matrix[i][j];
            }
        }
        return matrix;
    }
    fillUnuseful() {
        for (let ship of this.ships) {
            if (this.testDestroyedShip(ship)) {
                for (let p of ship) {
                    this.shootHV(p);
                }
            }
        }
    }
    testDestroyedShip(ship) {
        return ship.every(({x, y}) => this.board[x][y] !== State.SHIP);
    }
    shootHV({ x: row, y: col }) {
        let cells = [new Cell(row - 1, col - 1), new Cell(row - 1, col), new Cell(row - 1, col + 1), new Cell(row, col - 1), new Cell(row, col + 1), new Cell(row + 1, col - 1), new Cell(row + 1, col), new Cell(row + 1, col + 1)];
        for (let cell of cells) {
            if (this.onBoard(cell)) {
                let { x, y } = cell;
                if (this.board[x][y] === State.NONE) {
                    this.board[x][y] = State.WATER;
                    this.gui.gridChange(cell, State.WATER);
                }
            }
        }
    }
}