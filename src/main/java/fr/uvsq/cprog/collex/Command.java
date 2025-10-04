package fr.uvsq.cprog.collex;

/**
 * Interface for DNS commands.
 */
public interface Command {

  /**
   * Executes the command and returns the result.
   *
   * @return the result string
   */
  String execute();

  /**
   * Checks if this command terminates the application.
   *
   * @return true if terminates, false otherwise
   */
  default boolean isTerminate() {
    return false;
  }
}
