import AbstractPlayer from "./AbstractPlayer.js";

export default class BeginnerPlayer extends AbstractPlayer {
    constructor(grid) {
        super(grid);
    }
    play() {
        let cell = this.getRandomCell(this.getRandomInt(0, this.rows * this.cols - 1));
        if (!cell) {
            cell = this.getRandomCell();
        }
        return cell;
    }
}