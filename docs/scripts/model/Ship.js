export default class Ship {
    constructor(cells) {
        this.positions = cells;
    }
    getPositions() {
        return this.positions;
    }
}