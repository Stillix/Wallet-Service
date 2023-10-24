package by.dorogokupets.walletservice.infrastructure.in;


import java.math.BigDecimal;
import java.util.Scanner;

/**
 * A class for reading user input from the console.
 */
public class ConsoleInput {
  private static final Scanner scanner = new Scanner(System.in);

  /**
   * Read a line of text from the console.
   *
   * @return The string read from the console.
   */

  public String readString() {
    return scanner.nextLine();
  }

  /**
   * Read a BigDecimal from the console.
   *
   * @return The BigDecimal read from the console, or BigDecimal(-1) if the input is invalid.
   */

  public BigDecimal readBigDecimal() {
    if (scanner.hasNextBigDecimal()) {
      return scanner.nextBigDecimal();
    }
    return new BigDecimal(-1);
  }

  public boolean hasNextLine() {
    return scanner.hasNextLine();
  }

  /**
   * Read an integer from the console.
   *
   * @return The integer read from the console, or -1 if the input is not a valid integer.
   */

  public int readInt() {
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      scanner.nextLine();
      return -1;
    }
  }
}
