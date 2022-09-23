package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BattleshipTest {
    @Test
    public void testInitialState() {
        Battleship b = new Battleship(10, 10);

        try {
            List<List<Cell>> p1Ships = new ArrayList<>();
            List<Cell> ship;
            ship = List.of(new Cell(0, 0), new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(2, 1), new Cell(3, 1), new Cell(4, 1), new Cell(5, 1));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 8), new Cell(7, 8), new Cell(8, 8));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 2), new Cell(6, 3), new Cell(6, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(10, 5), new Cell(10, 6));
            p1Ships.add(ship);
            List<List<Cell>> p2Ships = new ArrayList<>();
            b.setData(p1Ships, p2Ships);
            Assertions.fail();
        } catch (Exception ex) {
        }

        try {
            List<List<Cell>> p1Ships = new ArrayList<>();
            List<Cell> ship;
            ship = List.of(new Cell(0, 0), new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(2, 1), new Cell(3, 1), new Cell(4, 1), new Cell(5, 1));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 8), new Cell(7, 8), new Cell(8, 8));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 2), new Cell(6, 3), new Cell(6, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(5, 5), new Cell(5, 6));
            p1Ships.add(ship);
            List<List<Cell>> p2Ships = new ArrayList<>();
            ship = List.of(new Cell(5, 7), new Cell(6, 7), new Cell(7, 7), new Cell(8, 7), new Cell(9, 7));
            p2Ships.add(ship);
            ship = List.of(new Cell(3, 1), new Cell(3, 2), new Cell(3, 3), new Cell(3, 4));
            p2Ships.add(ship);
            ship = List.of(new Cell(9, 6), new Cell(9, 7), new Cell(9, 8));
            p2Ships.add(ship);
            ship = List.of(new Cell(1, 0), new Cell(2, 0), new Cell(3, 0));
            p2Ships.add(ship);
            b.setData(p1Ships, p2Ships);
            Assertions.fail();
        } catch (Exception ex) {
        }

        try {
            List<List<Cell>> p1Ships = new ArrayList<>();
            List<Cell> ship;
            ship = List.of(new Cell(0, 0), new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(2, 1), new Cell(3, 1), new Cell(4, 1), new Cell(5, 1));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 8), new Cell(7, 8), new Cell(8, 8));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 2), new Cell(6, 3), new Cell(6, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(5, 5), new Cell(5, 6));
            p1Ships.add(ship);
            List<List<Cell>> p2Ships = new ArrayList<>();
            ship = List.of(new Cell(5, 7), new Cell(6, 7), new Cell(7, 7), new Cell(8, 7), new Cell(9, 7));
            p2Ships.add(ship);
            ship = List.of(new Cell(3, 1), new Cell(3, 2), new Cell(3, 3), new Cell(3, 4));
            p2Ships.add(ship);
            ship = List.of(new Cell(9, 6), new Cell(9, 7), new Cell(9, 8));
            p2Ships.add(ship);
            ship = List.of(new Cell(1, 0), new Cell(2, 0), new Cell(3, 0));
            p2Ships.add(ship);
            ship = List.of(new Cell(4, 9));
            p2Ships.add(ship);
            b.setData(p1Ships, p2Ships);
            Assertions.fail();
        } catch (Exception ex) {
        }

        try {
            List<List<Cell>> p1Ships = new ArrayList<>();
            List<Cell> ship;
            ship = List.of(new Cell(0, 0), new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(2, 1), new Cell(3, 1), new Cell(4, 1), new Cell(5, 1));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 8), new Cell(7, 8), new Cell(8, 8));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 2), new Cell(6, 3), new Cell(6, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(5, 5), new Cell(5, 6));
            p1Ships.add(ship);
            List<List<Cell>> p2Ships = new ArrayList<>();
            ship = List.of(new Cell(5, 7), new Cell(6, 7), new Cell(7, 7), new Cell(8, 7), new Cell(9, 7));
            p2Ships.add(ship);
            ship = List.of(new Cell(3, 1), new Cell(3, 2), new Cell(3, 3), new Cell(3, 4));
            p2Ships.add(ship);
            ship = List.of(new Cell(9, 6), new Cell(9, 7), new Cell(9, 8));
            p2Ships.add(ship);
            ship = List.of(new Cell(1, 0), new Cell(2, 0), new Cell(3, 0));
            p2Ships.add(ship);
            ship = List.of(new Cell(4, 9), new Cell(5, 9));
            p2Ships.add(ship);
            b.setData(p1Ships, p2Ships);
            Assertions.fail();
        } catch (Exception ex) {
        }

        try {
            List<List<Cell>> p1Ships = new ArrayList<>();
            List<Cell> ship;
            ship = List.of(new Cell(0, 0), new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(2, 1), new Cell(3, 1), new Cell(4, 1), new Cell(5, 1));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 8), new Cell(7, 8), new Cell(8, 8));
            p1Ships.add(ship);
            ship = List.of(new Cell(6, 2), new Cell(6, 3), new Cell(6, 4));
            p1Ships.add(ship);
            ship = List.of(new Cell(5, 5), new Cell(5, 6));
            p1Ships.add(ship);
            List<List<Cell>> p2Ships = new ArrayList<>();
            ship = List.of(new Cell(5, 7), new Cell(6, 7), new Cell(7, 7), new Cell(8, 7), new Cell(9, 7));
            p2Ships.add(ship);
            ship = List.of(new Cell(3, 2), new Cell(3, 3), new Cell(3, 4), new Cell(3, 5));
            p2Ships.add(ship);
            ship = List.of(new Cell(9, 2), new Cell(9, 3), new Cell(9, 4));
            p2Ships.add(ship);
            ship = List.of(new Cell(1, 0), new Cell(2, 0), new Cell(3, 0));
            p2Ships.add(ship);
            ship = List.of(new Cell(4, 9), new Cell(5, 9));
            p2Ships.add(ship);
            b.setData(p1Ships, p2Ships);
        } catch (Exception ex) {
            Assertions.fail();
        }
        try {
            b.play(Player.PLAYER1, new Cell(3, 4));
            b.play(Player.PLAYER2, new Cell(5, 8));
            b.play(Player.PLAYER1, new Cell(0, 0));
        } catch (Exception ex) {
            Assertions.fail();
        }
        System.out.println(b);
    }

}
