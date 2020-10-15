package pl.paiw.game;

import pl.paiw.game.TicTacToe.Player;

public class Cell {

  private int row;
  private int col;
  private Player player;

  public Cell(int row, int col, Player player) {
    this.row = row;
    this.col = col;
    this.player = player;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    if (this.player == null) {
      this.player = player;
    } else {
      throw new IllegalStateException(String.format("Cell %dx%d already used", row, col));
    }
  }
}
