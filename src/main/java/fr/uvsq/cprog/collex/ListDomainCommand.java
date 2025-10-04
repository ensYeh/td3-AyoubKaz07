package fr.uvsq.cprog.collex;

import java.util.List;

/**
 * Command to list machines for a domain.
 */
public class ListDomainCommand implements Command {
  private final String domain;
  private final boolean sortByAddress;
  private final Dns dns;

  /**
   * Constructs a ListDomainCommand.
   *
   * @param domain the domain name
   * @param sortByAddress if true, sort by address; otherwise, sort by name
   * @param dns the DNS instance
   */
  public ListDomainCommand(String domain, boolean sortByAddress, Dns dns) {
    this.domain = domain;
    this.sortByAddress = sortByAddress;
    this.dns = dns;
  }

  @Override
  public String execute() {
    List<DnsItem> items = dns.getItems(domain, sortByAddress);
    if (items.isEmpty()) {
      return "No machines found";
    }
    StringBuilder sb = new StringBuilder();
    for (DnsItem item : items) {
      sb.append(item.getIp().toString()).append(" ").append(item.getName().toString()).append("\n");
    }
    return sb.toString();
  }
}
