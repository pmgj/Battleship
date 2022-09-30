package controller;

import java.util.List;

import model.Battleship;
import model.CellState;
import model.Player;
import model.Winner;

public class OutputMessage {

    private ConnectionType type;
    private Player turn;
    private List<RoomMessage> rooms;
    private Winner winner;
    private CellState[][] myGrid;
    private CellState[][] opGrid;

    public OutputMessage() {

    }

    public OutputMessage(ConnectionType type, List<RoomMessage> rooms) {
        this.type = type;
        this.rooms = rooms;
    }

    public OutputMessage(ConnectionType type, Player turn) {
        this.type = type;
        this.turn = turn;
    }

    public OutputMessage(ConnectionType type, Battleship game, Player player) {
        this.type = type;
        this.winner = game.getWinner();
        this.turn = game.getTurn();
        this.myGrid = player == Player.PLAYER1 ? game.getGrid1().getHiddenBoard() : game.getGrid2().getHiddenBoard();
        this.opGrid = player == Player.PLAYER1 ? game.getGrid2().getBoard() : game.getGrid1().getBoard();
    }

    public OutputMessage(ConnectionType type, Battleship game) {
        this.type = type;
        this.winner = game.getWinner();
        this.turn = game.getTurn();
        this.myGrid = game.getGrid1().getHiddenBoard();
        this.opGrid = game.getGrid2().getHiddenBoard();
    }

    public ConnectionType getType() {
        return type;
    }

    public void setType(ConnectionType type) {
        this.type = type;
    }

    public Player getTurn() {
        return turn;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }

    public List<RoomMessage> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomMessage> rooms) {
        this.rooms = rooms;
    }

    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }

    public CellState[][] getMyGrid() {
        return myGrid;
    }

    public void setMyGrid(CellState[][] myGrid) {
        this.myGrid = myGrid;
    }

    public CellState[][] getOpGrid() {
        return opGrid;
    }

    public void setOpGrid(CellState[][] opGrid) {
        this.opGrid = opGrid;
    }
}
