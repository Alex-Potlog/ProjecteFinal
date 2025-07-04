package excepcions;

public class ChatException extends Exception {
    private static final long serialVersionUID = 1L;
    private String codi;

  public ChatException(String codi) {
    this.codi = codi;
  }

  public ChatException(String message, String codi) {
    super(message);
    this.codi = codi;
  }

  public ChatException(String message, Throwable cause, String codi) {
    super(message, cause);
    this.codi = codi;
  }

  public ChatException(Throwable cause, String codi) {
    super(cause);
    this.codi = codi;
  }

  public ChatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String codi) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.codi = codi;
  }
}
