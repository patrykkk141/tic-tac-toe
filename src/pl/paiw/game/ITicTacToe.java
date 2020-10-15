package pl.paiw.game;

import java.util.List;
import pl.paiw.game.TicTacToe.GameState;

public interface ITicTacToe {

  void startGame();

  void stopGame();

  void firstPlayerMove(final int row, final int col);

  void secondPlayerMove(final int row, final int col);

  List<Cell> getBoard();

  GameState getGameState();

}
