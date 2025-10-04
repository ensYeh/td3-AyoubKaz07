package fr.uvsq.cprog.collex;

import java.io.IOException;

/**
 * Command to add a new DNS item.
 */
public class AddItemCommand implements Command {
  private final AdresseIP ip;
  private final NomMachine name;
  private final Dns dns;

  /**
   * Constructs an AddItemCommand.
   *
   * @param ip the IP address
   * @param name the machine name
   * @param dns the DNS instance
   */
  public AddItemCommand(AdresseIP ip, NomMachine name, Dns dns) {
    this.ip = ip;
    this.name = name;
    this.dns = dns;
  }

  @Override
  public String execute() {
    try {
      dns.addItem(ip, name);
      return "";
    } catch (IllegalArgumentException | IOException e) {
      return "ERREUR : " + e.getMessage();
    }
  }
}
