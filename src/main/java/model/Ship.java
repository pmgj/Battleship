package model;

import java.util.List;

public class Ship {
    private List<Cell> positions;

    public Ship(List<Cell> cells) {
        this.positions = cells;
    }

    public List<Cell> getPositions() {
        return positions;
    }
}
