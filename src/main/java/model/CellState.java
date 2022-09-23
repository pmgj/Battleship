package model;

public class CellState {
    private State state;
    private Object observer;

    public CellState(State newstate) {
        this.state = newstate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setObserver(Object observer) {
        this.observer = observer;
    }
}
