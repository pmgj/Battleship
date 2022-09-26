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

    public void setState(State newValue) {
        var oldValue = this.state;
        this.state = newValue;
        this.squareObservers.firePropertyChange("state", oldValue, newValue);
    }

    public void addSquareObservers(PropertyChangeListener pcl) {
        squareObservers.addPropertyChangeListener(pcl);
    }

    @Override
    public String toString() {
        return String.format("%s", this.state);
    }
}
