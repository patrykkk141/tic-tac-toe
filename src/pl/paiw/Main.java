package pl.paiw;

import static pl.paiw.game.TicTacToe.GameState.PLAYER_1_MOVE;

import java.util.List;
import java.util.Scanner;
import pl.paiw.game.Cell;
import pl.paiw.game.TicTacToe;
import pl.paiw.game.TicTacToe.GameState;

public class Main {

  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    TicTacToe game = new TicTacToe();
    game.startGame();

    printBoard(game.getBoard());
    while (game.getGameState() == PLAYER_1_MOVE || game.getGameState() == GameState.PLAYER_2_MOVE) {
      chooseUserAction(game);
      printBoard(game.getBoard());
      printGameStatusMessage(game.getGameState());
    }

    printBoard(game.getBoard());
  }

  private static void chooseUserAction(TicTacToe game) {
    try {
      int row, col;
      switch (game.getGameState()) {
        case PLAYER_1_MOVE:
          System.out.println("Provide coordinates, row: ");
          row = getUserCoordinatesInput();
          System.out.println("col: ");
          col = getUserCoordinatesInput();
          game.firstPlayerMove(row, col);
          break;
        case PLAYER_2_MOVE:
          System.out.println("Provide coordinates, row: ");
          row = getUserCoordinatesInput();
          System.out.println("col: ");
          col = getUserCoordinatesInput();
          game.secondPlayerMove(row, col);
      }
    } catch (Exception e) {
      System.out.println("Something went wrong, try again - " + e.getMessage());
    }
  }

  public static void printGameStatusMessage(GameState state) {
    switch (state) {
      case PLAYER_1_MOVE:
        System.out.println("Player 1 move");
        break;
      case PLAYER_2_MOVE:
        System.out.println("Player 2 move");
        break;
      case PLAYER_1_WIN:
        System.out.println("Player 1 win!");
        break;
      case PLAYER_2_WIN:
        System.out.println("Player 2 win!");
        break;
      case DRAW:
        System.out.println("Draw, try again!");
    }
  }

  private static int getUserCoordinatesInput() {
    return sc.nextInt();
  }

  public static void printHorizontalLine() {
    System.out.println("------");
  }

  public static void printBoard(List<Cell> board) {
    printHorizontalLine();
    for (int i = 0; i < TicTacToe.BOARD_CELL_SUM; i += 3) {
      List<Cell> row = board.subList(i, (int) (TicTacToe.BOARD_SIZE + i));
      row.forEach(c ->
          System.out.print(
              c != null && c.getPlayer() != null ? c.getPlayer().getPlayerSign() + " " : "- "));
      System.out.println();
    }
    printHorizontalLine();
  }
}
