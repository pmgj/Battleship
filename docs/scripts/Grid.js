import Cell from "./Cell.js";
import Ship from "./Ship.js";
import State from "./State.js";
import CellState from "./CellState.js";

export default class Grid {
    constructor(nrows, ncols) {
        this.rows = nrows;
        this.cols = ncols;
        this.ships = [];
        this.hiddenBoard = Array(this.rows).fill().map(() => Array(this.cols).fill());
        this.openBoard = null;
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
        for (let i = 0; i < this.rows; i++) {
            for (let j = 0; j < this.cols; j++) {
                this.hiddenBoard[i][j] = new CellState(State.NONE);
            }
        }
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
                    this.hiddenBoard[xCoord + k][yCoord].setState(State.SHIP);
                    ship.push(new Cell(xCoord + k, yCoord));
                } else {
                    this.hiddenBoard[xCoord][yCoord + k].setState(State.SHIP);
                    ship.push(new Cell(xCoord, yCoord + k));
                }
            }
            this.ships.push(ship);
        }
        this.openBoard = this.getCodifiedBoard();
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
            return this.hiddenBoard[x][y].getState() === State.SHIP;
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
                if (this.hiddenBoard[x][y].getState() === State.SHIP) {
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
        return this.openBoard;
    }
    shot(cell) {
        let { x, y } = cell;
        if (this.hiddenBoard[x][y].getState() === State.SHOT || this.hiddenBoard[x][y].getState() === State.WATER) {
            throw new Error("Cell already shot.");
        }
        let newstate = this.hiddenBoard[x][y].getState() === State.SHIP ? State.SHOT : State.WATER;
        this.hiddenBoard[x][y].setState(newstate);
        this.openBoard[x][y].setState(newstate);
        this.ships.filter(ship => ship.every(({ x, y }) => this.hiddenBoard[x][y].getState() !== State.SHIP)).forEach(ship => ship.forEach(p => this.shootHV(p)));
    }
    getCodifiedBoard() {
        let matrix = Array(this.rows).fill().map(() => Array(this.cols).fill(new CellState(State.NONE)));
        for (let i = 0; i < this.rows; i++) {
            for (let j = 0; j < this.cols; j++) {
                matrix[i][j] = new CellState(this.hiddenBoard[i][j].getState() === State.SHIP ? State.NONE : this.hiddenBoard[i][j].getState());
            }
        }
        return matrix;
    }
    shootHV({ x: row, y: col }) {
        let cells = [new Cell(row - 1, col - 1), new Cell(row - 1, col), new Cell(row - 1, col + 1), new Cell(row, col - 1), new Cell(row, col + 1), new Cell(row + 1, col - 1), new Cell(row + 1, col), new Cell(row + 1, col + 1)];
        cells.filter(cell => this.onBoard(cell) && this.hiddenBoard[cell.x][cell.y].getState() === State.NONE).forEach(cell => {
            this.hiddenBoard[cell.x][cell.y].setState(State.WATER);
            this.openBoard[cell.x][cell.y].setState(State.WATER);
        });
    }
    addObserver({x, y}, obj) {
        this.hiddenBoard[x][y].setObserver(obj);
    }
}