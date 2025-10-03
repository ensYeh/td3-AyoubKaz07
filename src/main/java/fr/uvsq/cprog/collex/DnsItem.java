
package fr.uvsq.cprog.collex;

/**
 * Represents a DNS item with an IP address and machine name.
 */
public class DnsItem {
  private final AdresseIP ip;
  private final NomMachine name;

  public DnsItem(AdresseIP ip, NomMachine name) {
    this.ip = ip;
    this.name = name;
  }

  public AdresseIP getIp() {
    return ip;
  }

  public NomMachine getName() {
    return name;
  }

  @Override
  public String toString() {
    return ip.toString() + " " + name.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DnsItem that = (DnsItem) obj;
    return ip.equals(that.ip) && name.equals(that.name);
  }
}
