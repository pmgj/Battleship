package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CellState {
    private State state;
    private final PropertyChangeSupport squareObservers = new PropertyChangeSupport(this);

    public CellState(State newstate) {
        this.state = newstate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addSquareObservers(PropertyChangeListener pcl) {
        squareObservers.addPropertyChangeListener(pcl);
    }
}
