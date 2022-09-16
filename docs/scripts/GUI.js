import Battleship from "./Battleship.js";
import State from "./State.js";

class GUI {
    constructor() {
        this.rows = 10;
        this.cols = 10;
        this.game = new Battleship(this.rows, this.cols);
        this.game.setRandomShips([5, 4, 3, 3, 2]);
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
                    td.className = values && values[i][j] == State.SHIP ? "ship" : "none";
                    tr.appendChild(td);
                }
                table.appendChild(tr);
            }
        };
        createBoard(myBoard, this.game.p1Ships.board);
        createBoard(opBoard);
        console.table(this.game.p2Ships.board);
    }
}
let gui = new GUI();
gui.init();