package controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.websocket.Session;
import model.Battleship;
import model.Grid;

public class Room {
    private Session s1;
    private Session s2;
    private Battleship game;
    private List<Session> visitors = new ArrayList<>();

    public Room() {
        this.game = null;
        visitors.clear();
    }

    public void reset() {
        this.s1 = null;
        this.s2 = null;
        this.game = null;
        visitors.clear();
    }

    public List<Session> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<Session> visitors) {
        this.visitors = visitors;
    }

    public Session getS1() {
        return s1;
    }

    public void setS1(Session s1) {
        this.s1 = s1;
    }

    public Session getS2() {
        return s2;
    }

    public void setS2(Session s2) {
        this.s2 = s2;
    }

    public Battleship getGame() {
        return game;
    }

    public void createGame() throws Exception {
        int rows = 10, cols = 10;
        this.game = new Battleship();
        Grid grid1 = new Grid(rows, cols);
        Grid grid2 = new Grid(rows, cols);
        int[] qtt = new int[] { 5, 4, 3, 3, 2 };
        grid1.placeShipsRandomly(qtt);
        grid2.placeShipsRandomly(qtt);
        this.game.setShips(grid1, grid2);
    }
}
