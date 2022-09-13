package model;

import java.util.List;
import java.util.stream.Collectors;

public class Ship {
    private List<Position> positions;

    public Ship(List<Cell> cells) {
        this.positions = cells.stream().map(c -> new Position(c)).collect(Collectors.toList());
    }

    public List<Position> getPositions() {
        return positions;
    }
}
