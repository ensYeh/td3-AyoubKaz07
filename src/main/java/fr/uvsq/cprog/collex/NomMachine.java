
package fr.uvsq.cprog.collex;

/**
 * Represents a machine name with a qualified domain name.
 */
public class NomMachine {
  private final String qualifiedName;

  /**
   * Constructs a NomMachine with the given qualified name.
   *
   * @param qualifiedName the qualified name for the machine
   * @throws IllegalArgumentException if qualifiedName is null, empty, or doesn't contain a dot
   */
  public NomMachine(String qualifiedName) {
    if (qualifiedName == null || qualifiedName.trim().isEmpty()) {
      throw new IllegalArgumentException("Qualified name cannot be null or empty");
    }
    if (!qualifiedName.contains(".")) {
      throw new IllegalArgumentException("Qualified name must contain at least one dot");
    }
    this.qualifiedName = qualifiedName;
  }

  public String getMachineName() {
    return qualifiedName.split("\\.")[0];
  }

  public String getDomain() {
    int firstDotIndex = qualifiedName.indexOf('.');
    return qualifiedName.substring(firstDotIndex + 1);
  }

  @Override
  public String toString() {
    return qualifiedName;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    NomMachine that = (NomMachine) obj;
    return qualifiedName.equals(that.qualifiedName);
  }
}
