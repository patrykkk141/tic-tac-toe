package pl.paiw.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import pl.paiw.exception.CellNotFoundException;
import pl.paiw.exception.InvalidMoveException;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class TicTacToe implements ITicTacToe {

  public enum GameState {
    STOP, PLAYER_1_MOVE, PLAYER_2_MOVE, PLAYER_1_WIN, PLAYER_2_WIN, DRAW
  }

  public enum Player {
    PLAYER_1("o"), PLAYER_2("x");

    private String playerSign;

    Player(String playerSign) {
      this.playerSign = playerSign;
    }

    public String getPlayerSign() {
      return playerSign;
    }
  }

  public static final long BOARD_SIZE = 3;
  public static final long BOARD_CELL_SUM = BOARD_SIZE * BOARD_SIZE;
  private final List<Cell> board;
  private GameState gameState = GameState.STOP;

  public TicTacToe() {
    board = new ArrayList<>();
  }

  @Override
  public void startGame() {
    initialiseBoard();
    gameState = GameState.PLAYER_1_MOVE;
  }

  @Override
  public void stopGame() {
    gameState = GameState.STOP;
  }

  @Override
  public void firstPlayerMove(final int row, final int col) {
    validateMove(Player.PLAYER_1);
    Cell cell = findCell(row, col);
    cell.setPlayer(Player.PLAYER_1);
    gameState = GameState.PLAYER_2_MOVE;
    checkWin();
  }

  @Override
  public void secondPlayerMove(int row, int col) {
    validateMove(Player.PLAYER_2);
    Cell cell = findCell(row, col);
    cell.setPlayer(Player.PLAYER_2);
    gameState = GameState.PLAYER_1_MOVE;
    checkWin();
  }

  @Override
  public List<Cell> getBoard() {
    return board;
  }

  @Override
  public GameState getGameState() {
    return gameState;
  }

  private void initialiseBoard() {
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        board.add(new Cell(i, j, null));
      }
    }
  }

  private Cell findCell(int row, int col) {
    return board.stream()
        .filter(c -> c.getRow() == row && c.getCol() == col)
        .findFirst().orElseThrow(() -> new CellNotFoundException(row, col));
  }

  private void validateMove(final Player player) {
    if (!isFirstPlayerMove(player) && !isSecondPlayerMove(player)) {
      throw new InvalidMoveException("Invalid move");
    }
  }

  private boolean isFirstPlayerMove(final Player player) {
    return player == Player.PLAYER_1 && gameState == GameState.PLAYER_1_MOVE;
  }

  private boolean isSecondPlayerMove(final Player player) {
    return player == Player.PLAYER_2 && gameState == GameState.PLAYER_2_MOVE;
  }

  private GameState checkRow(final int index) {
    Stream<Cell> markedCellsInRow = board.stream()
        .filter(cell -> cell.getRow() == index && cell.getPlayer() != null);
    Map<Player, Long> result = markedCellsInRow
        .collect(groupingBy(Cell::getPlayer, counting()));

    return checkPlayersPoints(result);
  }

  private GameState checkCol(final int index) {
    Stream<Cell> markedCellsInCol = board.stream()
        .filter(cell -> cell.getCol() == index && cell.getPlayer() != null);
    Map<Player, Long> result = markedCellsInCol
        .collect(groupingBy(Cell::getPlayer, counting()));

    return checkPlayersPoints(result);
  }

  private GameState checkPlayersPoints(Map<Player, Long> result) {
    Long player1Result = result.get(Player.PLAYER_1);
    Long player2Result = result.get(Player.PLAYER_2);
    if (player1Result != null && player1Result.equals(BOARD_SIZE)) {
      return GameState.PLAYER_1_WIN;
    } else if (player2Result != null && player2Result.equals(BOARD_SIZE)) {
      return GameState.PLAYER_2_WIN;
    }

    return null;
  }

  private GameState checkWin() {
    GameState currentState = checkRows();
    if (currentState != null) {
      gameState = currentState;
    } else if (isDraw()) {
      gameState = GameState.DRAW;
    }

    return gameState;
  }

  private GameState checkRows() {
    GameState currentState = null;
    for (int i = 0; i < BOARD_SIZE; i++) {
      currentState = checkRow(i);
      if (currentState != null) {
        break;
      } else {
        currentState = checkCols();
      }
    }

    return currentState;
  }

  private boolean isDraw() {
    long unmarkedCells = board.stream()
        .filter(cell -> cell.getPlayer() == null)
        .count();

    return unmarkedCells == 0;
  }

  private GameState checkCols() {
    GameState currentState = null;
    for (int j = 0; j < BOARD_SIZE; j++) {
      currentState = checkCol(j);
      if (currentState != null) {
        break;
      }
    }
    return currentState;
  }
}
