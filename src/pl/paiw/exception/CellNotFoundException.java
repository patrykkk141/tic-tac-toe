package pl.paiw.exception;

public class CellNotFoundException extends RuntimeException {

  public CellNotFoundException(final int col, final int row) {
    super(String.format("Cell %dx%d not exist", col, row));
  }
}
