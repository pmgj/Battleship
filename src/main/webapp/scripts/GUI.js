import ConnectionType from "./model/ConnectionType.js";
import Player from "./model/Player.js";
import Cell from "./model/Cell.js";
import Winner from "./model/Winner.js";
import MessageType from "./model/MessageType.js";

class GUI {
    constructor() {
        this.ws = null;
        this.player = null;
        this.rows = 10;
        this.cols = 10;
    }
    coordinates(cell) {
        return new Cell(cell.parentNode.rowIndex, cell.cellIndex);
    }
    setMessage(msg) {
        let message = document.getElementById("message");
        message.innerHTML = msg;
    }
    play(event) {
        let cellDestino = event.currentTarget;
        let dCell = this.coordinates(cellDestino);
        this.ws.send(JSON.stringify({ type: MessageType.MOVE_PIECE, room: -1, cell: dCell }));
    }
    printBoard(table, matrix) {
        for (let i = 0; i < matrix.length; i++) {
            for (let j = 0; j < matrix[i].length; j++) {
                let td = table.rows[i].cells[j];
                td.className = matrix[i][j].state;
                td.onclick = this.play.bind(this);
            }
        }
    }
    setButtonText(message) {
        let button = document.querySelector("#quit");
        button.value = message;
    }
    clearBoard() {
        let cells = document.querySelectorAll("td");
        cells.forEach(td => {
            td.innerHTML = "";
            td.className = "";
            td.onclick = undefined;
        });
    }
    unsetEvents() {
        let cells = document.querySelectorAll("td");
        cells.forEach(td => td.onclick = undefined);
    }
    enterRoom(evt) {
        let input = evt.currentTarget;
        let obj = { type: MessageType.ENTER_ROOM, room: parseInt(input.dataset.room), cell: null };
        this.ws.send(JSON.stringify(obj));
    }
    watchRoom(evt) {
        let input = evt.currentTarget;
        let obj = { type: MessageType.WATCH_ROOM, room: parseInt(input.dataset.room), cell: null };
        this.ws.send(JSON.stringify(obj));
    }
    exitRoom() {
        let obj = { type: MessageType.EXIT_ROOM, room: -1, cell: null };
        this.ws.send(JSON.stringify(obj));
        this.showRoom(false);
    }
    readData(evt) {
        let data = JSON.parse(evt.data), table;
        switch (data.type) {
            case ConnectionType.GET_ROOMS:
                let s = "";
                for (let i = 0; i < data.rooms.length; i++) {
                    let room = data.rooms[i];
                    s += `<fieldset><legend>Room ${i + 1}</legend>
                    <input type="button" value="Play" data-room="${i}" ${room.s1 && room.s2 ? "disabled='disabled'" : ""} />
                    <input type="button" value="Watch" data-room="${i}" ${!room.s1 || !room.s2 ? "disabled='disabled'" : ""} /></fieldset>`;
                }
                let rooms = document.querySelector(".rooms");
                rooms.innerHTML = s;
                let bPlay = document.querySelectorAll("input[value='Play']");
                bPlay.forEach(b => b.onclick = this.enterRoom.bind(this));
                let bWatch = document.querySelectorAll("input[value='Watch']");
                bWatch.forEach(b => b.onclick = this.watchRoom.bind(this));
                break;
            case ConnectionType.OPEN:
                this.showRoom(true);
                this.player = data.turn;
                this.setMessage("");
                this.clearBoard();
                break;
            case ConnectionType.MESSAGE:
                table = document.querySelector("table");
                this.printBoard(table, data.myGrid);
                table = document.querySelector("table + table");
                this.printBoard(table, data.opGrid);
                if (this.player === Player.VISITOR) {
                    this.setMessage("");
                } else {
                    this.setMessage(data.turn === this.player ? "Your turn." : "Opponent's turn.");
                }
                break;
            case ConnectionType.ENDGAME:
                table = document.querySelector("table");
                this.printBoard(table, data.myGrid);
                table = document.querySelector("table + table");
                this.printBoard(table, data.opGrid);
                this.closeConnection(data.winner);
                break;
        }
    }
    closeConnection(winner) {
        this.unsetEvents();
        if (this.player === Player.VISITOR) {
            if (winner) {
                this.setMessage(`Game Over!`);
            }
        } else {
            this.setMessage(`Game Over! ${(winner === Winner.DRAW) ? "Draw!" : (winner === this.player ? "You win!" : "You lose!")}`);
        }
    }
    startGame() {
        this.ws = new WebSocket(`ws://${document.location.host}${document.location.pathname}battleship`);
        this.ws.onmessage = this.readData.bind(this);
        this.showRoom(false);
    }
    showRoom(show) {
        let rooms = document.querySelector(".rooms");
        let room = document.querySelector(".room");
        if (show) {
            rooms.classList.add("hide");
            room.classList.remove("hide");
        } else {
            rooms.classList.remove("hide");
            room.classList.add("hide");
        }
    }
    createBoard() {
        let tbody1 = document.querySelector("tbody");
        let tbody2 = document.querySelector("table + table tbody");
        for (let i = 0; i < this.rows; i++) {
            let tr1 = document.createElement("tr");
            let tr2 = document.createElement("tr");
            for (let j = 0; j < this.cols; j++) {
                let td1 = document.createElement("td");
                tr1.appendChild(td1);
                let td2 = document.createElement("td");
                tr2.appendChild(td2);
            }
            tbody1.appendChild(tr1);
            tbody2.appendChild(tr2);
        }
    }
    init() {
        let button = document.querySelector("#quit");
        button.onclick = this.exitRoom.bind(this);
        this.createBoard();
        this.startGame();
    }
}
let gui = new GUI();
gui.init();