import Battleship from "./Battleship.js";
import State from "./State.js";
import Player from "./Player.js";
import Cell from "./Cell.js";
import Winner from "./Winner.js";
import RandomPlayer from "./RandomPlayer.js";

class GUI {
    constructor() {
        this.rows = 10;
        this.cols = 10;
        this.game = new Battleship(this.rows, this.cols);
        this.game.setRandomShips([5, 4, 3, 3, 2]);
        this.computer = new RandomPlayer(this.game.getGrid(Player.PLAYER1).getCodifiedBoard());
    }
    init() {
        let tables = document.querySelectorAll("table");
        let myBoard = tables[0];
        let opBoard = tables[1];
        let createBoard = (table, values) => {
            for (let i = 0; i < this.rows; i++) {
                let tr = document.createElement("tr");
                for (let j = 0; j < this.cols; j++) {
                    let td = document.createElement("td");
                    if(values) {
                        td.className = values[i][j] == State.SHIP ? "ship" : "none";
                    } else {
                        td.className = "none";
                        td.onclick = this.play.bind(this);
                    }
                    tr.appendChild(td);
                }
                table.appendChild(tr);
            }
        };
        createBoard(myBoard, this.game.p1Ships.board);
        createBoard(opBoard);
        console.table(this.game.p2Ships.board);
    }
    play(evt) {
        let td = evt.currentTarget;
        let shot = this.game.play(Player.PLAYER1, this.coordinates(td));
        let winner = this.game.getWinner();
        td.className = shot.toLowerCase();
        if(winner === Winner.NONE) {
            let cell = this.computer.play();
            shot = this.game.play(Player.PLAYER2, cell);
            this.computer.setResultShot(cell, shot);
            let table = document.querySelector("table");
            table.rows[cell.x].cells[cell.y].className = shot.toLowerCase();
            winner = this.game.getWinner();
        }
        if(winner !== Winner.NONE) {
            let message = document.querySelector("#message");
            message.textContent = `Game over! ${winner === Player.PLAYER1 ? "You Win!" : "You lose!"}`;
        }
    }
    coordinates(cell) {
        return new Cell(cell.parentNode.rowIndex, cell.cellIndex);
    }
}
let gui = new GUI();
gui.init();