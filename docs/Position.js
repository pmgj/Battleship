export default class Position {
    constructor(cell) {
        this.cell = cell;
        this.shot = false;
    }
    getCell() {
        return this.cell;
    }
    isShot() {
        return this.shot;
    }
    setCell(cell) {
        this.cell = cell;
    }
    setShot(shot) {
        this.shot = shot;
    }
}