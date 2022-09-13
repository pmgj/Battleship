package model;

public class Position {
    private Cell cell;
    private boolean shot;

    public Position(Cell cell) {
        this.cell = cell;
        this.shot = false;
    }

    public Cell getCell() {
        return cell;
    }

    public boolean isShot() {
        return shot;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }
}
