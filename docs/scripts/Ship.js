import Position from "./Position.js";

export default class Ship {
    constructor(cells) {
        this.positions = cells.map(c => new Position(c));
    }
    getPositions() {
        return this.positions;
    }
}