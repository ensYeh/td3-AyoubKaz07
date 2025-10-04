package fr.uvsq.cprog.collex;

/**
 * Command to look up the IP address for a machine name.
 */
public class LookupNameCommand implements Command {
  private final NomMachine name;
  private final Dns dns;

  /**
   * Constructs a LookupNameCommand.
   *
   * @param name the machine name
   * @param dns the DNS instance
   */
  public LookupNameCommand(NomMachine name, Dns dns) {
    this.name = name;
    this.dns = dns;
  }

  @Override
  public String execute() {
    DnsItem item = dns.getItem(name);
    if (item == null) {
      return "Not found";
    }
    return item.getIp().toString();
  }
}
