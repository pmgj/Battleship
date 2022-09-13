import Player from "./Player.js";
import Winner from "./Winner.js";
import Ship from "./Ship.js";

export default class Battleship {
    constructor() {
        this.p1Ships = null;
        this.p2Ships = null;
        this.rows = 10;
        this.cols = 10;
        this.turn = Player.PLAYER1;
        this.winner = Winner.NONE;
    }
    setData(rows, cols, p1Ships, p2Ships) {
        this.rows = rows;
        this.cols = cols;
        if (!p1Ships.every(s => s.every(c => this.onBoard(c)))) {
            throw new Error("One ship is not on the board.");
        }
        if (!p2Ships.every(s => s.every(c => this.onBoard(c)))) {
            throw new Error("One ship is not on the board.");
        }
        if (p1Ships.length != p2Ships.length) {
            throw new Exception("The number of ships of players is different.");
        }
        let is1 = p1Ships.map(s => s.length);
        let is2 = p2Ships.map(s => s.length);
        is1.sort();
        is2.sort();
        for (let i = 0; i < is1.length; i++) {
            if (is1[i] !== is2[i]) {
                throw new Error("The size of the ships of players is different.");
            }
        }
        let tempList = p1Ships.flat();
        let tempSet = p1Ships.flat();
        if (tempList.length !== tempSet.length) {
            throw new Error("There are ships overlapping.");
        }
        tempList = p2Ships.flat();
        tempSet = p2Ships.flat();
        if (tempList.length != tempSet.length) {
            throw new Exception("There are ships overlapping.");
        }
        this.p1Ships = p1Ships.map(s => new Ship(s));
        this.p2Ships = p2Ships.map(s => new Ship(s));
    }
    getTurn() {
        return this.turn;
    }
    getWinner() {
        return this.winner;
    }
    play(player, endCell) {
        if (this.winner != Winner.NONE) {
            throw new Error("This game is already finished.");
        }
        if (player !== this.turn) {
            throw new Error("It's not your turn.");
        }
        if (!this.onBoard(endCell)) {
            throw new Error("Shot is not on board.");
        }
        let ships = player == Player.PLAYER1 ? this.p1Ships : this.p2Ships;
        for (let ship of ships) {
            for (let p of ship.getPositions()) {
                if (p.getCell().equals(endCell)) {
                    p.setShot(true);
                }
            }
        }
        this.turn = this.turn == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
        this.winner = this.endOfGame();
    }
    endOfGame() {
        let b1 = this.p1Ships.every(s => s.getPositions().every(c => c.isShot()));
        if (b1) {
            return Winner.PLAYER2;
        }
        let b2 = this.p2Ships.every(s => s.getPositions().every(c => c.isShot()));
        if (b2) {
            return Winner.PLAYER1;
        }
        return Winner.NONE;
    }
    onBoard({ x, y }) {
        let inLimit = (value, limit) => value >= 0 && value < limit;
        return (inLimit(x, this.rows) && inLimit(y, this.cols));
    }
}