package fr.uvsq.cprog.collex;

/**
 * Command to quit the application.
 */
public class QuitCommand implements Command {

  /**
   * Constructs a QuitCommand.
   */
  public QuitCommand() {
  }

  @Override
  public String execute() {
    return "";
  }

  @Override
  public boolean isTerminate() {
    return true;
  }
}
