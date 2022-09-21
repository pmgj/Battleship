export default class CellState {
    constructor(state) {
        this.state = state;
        this.observer = null;
    }
    setState(newstate) {
        this.state = newstate;
        if(this.observer) {
            this.observer.execute(newstate);
        }
    }
    setObserver(obj) {
        this.observer = obj;
    }
    getState() {
        return this.state;
    }
    equals(obj) {
        return obj.getState() === this.state;
    }
}