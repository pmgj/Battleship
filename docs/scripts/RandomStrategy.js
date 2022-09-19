import Cell from "./Cell";
import Player from "./Player";
import State from "./State";

export default class RandomStrategy {
    constructor(game) {
        this.game = game;
    }
    play() {
        let rows = this.game.getRows();
        let cols = this.game.getCols();
        let x, y, board;
        do {
            x = this.getRandomInt(0, rows);
            y = this.getRandomInt(0, cols);
            let grid = this.game.getGrid(Player.PLAYER2);
            board = grid.getCodifiedBoard();
        } while (board[x][y] !== State.NONE);
        return new Cell(x, y);
    }
    getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min + 1) + min);
    }
}