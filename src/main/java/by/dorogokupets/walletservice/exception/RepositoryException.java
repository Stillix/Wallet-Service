package by.dorogokupets.walletservice.exception;

/**
 * This class represents a custom exception used in the wallet repositories
 * to handle repository-related errors.
 */
public class RepositoryException extends Exception {
  public RepositoryException() {
    super();
  }

  public RepositoryException(String message) {
    super(message);
  }

  public RepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public RepositoryException(Throwable cause) {
    super(cause);
  }
}
