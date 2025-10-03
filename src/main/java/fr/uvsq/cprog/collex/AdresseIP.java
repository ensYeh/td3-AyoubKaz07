package fr.uvsq.cprog.collex;

/**
 * Represents an IP address.
 */
public class AdresseIP implements Comparable<AdresseIP> {
  private final String ip;

  /**
   * Constructs an AdresseIP with the given IP string.
   *
   * @param ipString the IP address as a string
   * @throws IllegalArgumentException if ipString is invalid
   */
  public AdresseIP(String ipString) {
    if (ipString == null || ipString.trim().isEmpty()) {
      throw new IllegalArgumentException("IP address cannot be null or empty");
    }
    String[] parts = ipString.split("\\.");
    if (parts.length != 4) {
      throw new IllegalArgumentException("IP address must have 4 octets separated by dots");
    }
    for (String part : parts) {
      try {
        int value = Integer.parseInt(part);
        if (value < 0 || value > 255) {
          throw new IllegalArgumentException("Each octet must be between 0 and 255");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Each octet must be a number");
      }
    }
    this.ip = ipString;
  }

  @Override
  public String toString() {
    return ip;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    AdresseIP that = (AdresseIP) obj;
    return ip.equals(that.ip);
  }

  @Override
  public int compareTo(AdresseIP other) {
    String[] thisParts = this.ip.split("\\.");
    String[] otherParts = other.ip.split("\\.");
    for (int i = 0; i < 4; i++) {
      int thisPart = Integer.parseInt(thisParts[i]);
      int otherPart = Integer.parseInt(otherParts[i]);
      int cmp = Integer.compare(thisPart, otherPart);
      if (cmp != 0) {
        return cmp;
      }
    }
    return 0;
  }
}
