class GUI {
    init() {
        let tables = document.querySelectorAll("table");
        let myBoard = tables[0];
        let opBoard = tables[1];
        let createBoard = table => {
            for (let i = 0; i < 10; i++) {
                let tr = document.createElement("tr");
                for (let j = 0; j < 10; j++) {
                    let td = document.createElement("td");
                    tr.appendChild(td);
                }
                table.appendChild(tr);
            }    
        };
        createBoard(myBoard);
        createBoard(opBoard);
    }
}
let gui = new GUI();
gui.init();