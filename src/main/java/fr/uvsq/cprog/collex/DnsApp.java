package fr.uvsq.cprog.collex;

import java.io.IOException;

/**
 * The main application class for the DNS management system.
 */
public class DnsApp {
  private final DnsTUI tui;

  /**
   * Constructs a DnsApp with the given Text User Interface.
   *
   * @param tui the Text User Interface instance
   */
  public DnsApp(DnsTUI tui) {
    this.tui = tui;
  }

  /**
   * Runs the main application loop, processing user commands.
   */
  public void run() {
    while (true) {
      String input = tui.nextLine();
      Command command = tui.nextCommande(input);
      if (command != null) {
        if (command.isTerminate()) {
          break;
        }
        String result = command.execute();
        tui.affiche(result);
      } else {
        tui.affiche("Invalid command");
      }
    }
  }

  /**
   * The main entry point of the application.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    try {
      Dns dns = new Dns();
      DnsTUI tui = new DnsTUI(dns);
      DnsApp app = new DnsApp(tui);
      app.run();
    } catch (IOException e) {
      System.err.println("Error initializing DNS: " + e.getMessage());
    }
  }
}
