package controller;

import model.Cell;

public record InputMessage(MessageType type, int room, Cell cell) {

}
