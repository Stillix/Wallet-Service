package by.dorogokupets.walletservice.exception;

/**
 * This class represents a custom exception used in the wallet services
 * to handle service-related errors.
 */
public class ServiceException extends Exception {
  public ServiceException() {
    super();
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }
}
