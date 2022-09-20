import Grid from "./Grid.js";
import Player from "./Player.js";
import Winner from "./Winner.js";
import State from "./State.js";

export default class Battleship {
    constructor(nrows, ncols, gui) {
        this.p1Ships = new Grid(nrows, ncols, gui);
        this.p2Ships = new Grid(nrows, ncols, gui);
        this.rows = nrows;
        this.cols = ncols;
        this.turn = Player.PLAYER1;
        this.winner = Winner.NONE;
    }
    setData(p1Ships, p2Ships) {
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
        this.p1Ships.placeShips(p1Ships);
        this.p2Ships.placeShips(p2Ships);
    }
    setRandomShips(sizes) {
        this.p1Ships.placeShipsRandomly(sizes);
        this.p2Ships.placeShipsRandomly(sizes);
    }
    getTurn() {
        return this.turn;
    }
    getWinner() {
        return this.winner;
    }
    play(player, endCell) {
        if (this.winner !== Winner.NONE) {
            throw new Error("This game is already finished.");
        }
        if (player !== this.turn) {
            throw new Error("It's not your turn.");
        }
        if (!this.onBoard(endCell)) {
            throw new Error("Shot is not on board.");
        }
        let ships = player == Player.PLAYER1 ? this.p2Ships : this.p1Ships;
        ships.shot(endCell);
        this.turn = this.turn == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
        this.winner = this.endOfGame();
    }
    getGrid(player) {
        return player === Player.PLAYER1 ? this.p1Ships : this.p2Ships;
    }
    endOfGame() {
        if (this.p1Ships.endOfGame()) {
            return Winner.PLAYER2;
        }
        if (this.p2Ships.endOfGame()) {
            return Winner.PLAYER1;
        }
        return Winner.NONE;
    }
    onBoard({ x, y }) {
        let inLimit = (value, limit) => value >= 0 && value < limit;
        return (inLimit(x, this.rows) && inLimit(y, this.cols));
    }
}