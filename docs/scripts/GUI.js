import Battleship from "./model/Battleship.js";
import State from "./model/State.js";
import Player from "./model/Player.js";
import Cell from "./model/Cell.js";
import Winner from "./model/Winner.js";
import AdvancedPlayer from "./players/AdvancedPlayer.js";

class GUI {
    constructor() {
        this.rows = 10;
        this.cols = 10;
    }
    registerEvents() {
        this.init();
        let iniciar = document.querySelector("input[type='button']");
        iniciar.onclick = this.init.bind(this);
    }
    init() {
        this.game = new Battleship(this.rows, this.cols);
        this.game.setRandomShips([5, 4, 3, 3, 2]);
        this.computer = new AdvancedPlayer(this.game.getGrid(Player.PLAYER1).getBoard());
        let tables = document.querySelectorAll("table");
        let myBoard = tables[0];
        myBoard.tBodies[0].innerHTML = "";
        let opBoard = tables[1];
        opBoard.tBodies[0].innerHTML = "";
        let createBoard = (table, values) => {
            for (let i = 0; i < this.rows; i++) {
                let tr = document.createElement("tr");
                for (let j = 0; j < this.cols; j++) {
                    let td = document.createElement("td");
                    td.execute = state => td.className = state;
                    if (values) {
                        td.className = values[i][j].getState() == State.SHIP ? "SHIP" : "NONE";
                        this.game.p1Ships.addObserver(new Cell(i, j), td);
                    } else {
                        td.className = "NONE";
                        td.onclick = this.play.bind(this);
                        this.game.p2Ships.addObserver(new Cell(i, j), td);
                    }
                    tr.appendChild(td);
                }
                table.tBodies[0].appendChild(tr);
            }
        };
        createBoard(myBoard, this.game.p1Ships.hiddenBoard);
        createBoard(opBoard);
    }
    play(evt) {
        try {
            let td = evt.currentTarget;
            this.game.play(Player.PLAYER1, this.coordinates(td));
            let winner = this.game.getWinner();
            if (winner === Winner.NONE) {
                let cell = this.computer.play();
                this.game.play(Player.PLAYER2, cell);
                winner = this.game.getWinner();
            }
            if (winner !== Winner.NONE) {
                let message = document.querySelector("#message");
                message.textContent = `Game over! ${winner === Winner.PLAYER1 ? "You Win!" : "You lose!"}`;
            }
        } catch (ex) {
            console.log(ex.message);
        }
    }
    coordinates(cell) {
        return new Cell(cell.parentNode.rowIndex, cell.cellIndex);
    }
}
let gui = new GUI();
gui.registerEvents();