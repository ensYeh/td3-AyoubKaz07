package fr.uvsq.cprog.collex;

/**
 * Command to look up the machine name for an IP address.
 */
public class LookupIPCommand implements Command {
  private final AdresseIP ip;
  private final Dns dns;

  /**
   * Constructs a LookupIPCommand.
   *
   * @param ip the IP address
   * @param dns the DNS instance
   */
  public LookupIPCommand(AdresseIP ip, Dns dns) {
    this.ip = ip;
    this.dns = dns;
  }

  @Override
  public String execute() {
    DnsItem item = dns.getItem(ip);
    if (item == null) {
      return "Not found";
    }
    return item.getName().toString();
  }
}
