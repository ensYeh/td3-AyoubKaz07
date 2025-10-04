package fr.uvsq.cprog.collex;

import java.util.Scanner;

/**
 * Text-based user interface for DNS operations.
 * Supports commands to lookup by IP or name, add items, list domain entries, and quit.
 */
public class DnsTUI {
  private final Dns dns;
  private final Scanner scanner;

  /**
   * Constructs a DnsTUI with the given Dns instance.
   *
   * @param dns the Dns instance
   */
  public DnsTUI(Dns dns) {
    this.dns = dns;
    this.scanner = new Scanner(System.in);
  }

  /**
   * Parses the input string and returns the corresponding Command object.
   *
   * @param input User input string.
   * @return Command object or null if input is invalid.
   */

  public Command nextCommande(String input) {
    String trimmed = input.trim();
    if (trimmed.isEmpty()) {
      return new QuitCommand();
    }

    // Check for commands
    if (trimmed.equals("quit")) {
      return new QuitCommand();
    }
    if (trimmed.startsWith("add ")) {
      String[] parts = trimmed.split("\\s+");
      if (parts.length == 3) {
        try {
          AdresseIP ip = new AdresseIP(parts[1]);
          NomMachine name = new NomMachine(parts[2]);
          return new AddItemCommand(ip, name, dns);
        } catch (IllegalArgumentException e) {
          // Invalid add command
        }
      }
      return null; // Invalid add syntax
    }
    if (trimmed.startsWith("ls")) {
      String[] parts = trimmed.split("\\s+");
      if (parts.length == 2 || (parts.length == 3 && "-a".equals(parts[1]))) {
        String domain = parts.length == 2 ? parts[1] : parts[2];
        boolean sortByAddress = parts.length == 3;
        return new ListDomainCommand(domain, sortByAddress, dns);
      }
      return null; // Invalid ls syntax
    }

    // If not a command, try to interpret as IP or name
    try {
      AdresseIP ip = new AdresseIP(trimmed);
      return new LookupIPCommand(ip, dns);
    } catch (IllegalArgumentException e) {
      // Not an IP address
    }
    try {
      NomMachine name = new NomMachine(trimmed);
      return new LookupNameCommand(name, dns);
    } catch (IllegalArgumentException e) {
      // Not a machine name
    }

    return null;
  }

  /**
   * Displays the result string to the user.
   *
   * @param result the result string to display
   */
  public void affiche(String result) {
    if (result != null && !result.isEmpty()) {
      System.out.println(result);
    }
  }

  public String nextLine() {
    System.out.print("> ");
    return scanner.nextLine();
  }
}
