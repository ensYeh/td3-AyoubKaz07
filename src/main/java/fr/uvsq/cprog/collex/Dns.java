package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Represents a simple DNS system Class that maps IP addresses to machine names and the inverse
 * The DNS data is stored in a the file specified in the dns.properties file.
 */
public class Dns {
  private final Map<AdresseIP, DnsItem> ipToItem = new HashMap<>();
  private final Map<NomMachine, DnsItem> nameToItem = new HashMap<>();
  private final Path dbFile;

  /**
   * Constructs a Dns instance.
   * Loads the database from the file specified in dns.properties.
   *
   * @throws IOException if the properties file cannot be loaded or not set correctly
   */
  public Dns() throws IOException {
    Properties props = new Properties();
    try (var in = getClass().getClassLoader().getResourceAsStream("dns.properties")) {
      if (in == null) {
        throw new IOException("dns.properties not found");
      }
      props.load(in);
    }
    String dbFileName = props.getProperty("dbfile");
    if (dbFileName == null) {
      throw new IOException("dbfile property not set");
    }
    this.dbFile = Paths.get(dbFileName);
    loadDatabase();
  }

  private void loadDatabase() throws IOException {
    if (Files.exists(dbFile)) {
      List<String> lines = Files.readAllLines(dbFile);
      for (String line : lines) {
        String[] parts = line.split("\\s+");
        if (parts.length == 2) {
          AdresseIP ip = new AdresseIP(parts[0]);
          NomMachine name = new NomMachine(parts[1]);
          DnsItem item = new DnsItem(ip, name);
          ipToItem.put(ip, item);
          nameToItem.put(name, item);
        }
      }
    }
  }

  /**
   * Gets the DNS item for the given IP address.
   *
   * @param ip the IP address
   * @return the DNS item or null if not found
   */
  public DnsItem getItem(AdresseIP ip) {
    return ipToItem.get(ip);
  }

  /**
   * Gets the DNS item for the given machine name.
   *
   * @param name the machine name
   * @return the DNS item or null if not found
   */
  public DnsItem getItem(NomMachine name) {
    return nameToItem.get(name);
  }

  /**
   * Gets the list of DNS items for the given domain, unsorted.
   *
   * @param domain the domain
   * @return the list of DNS items
   */
  public List<DnsItem> getItems(String domain) {
    return getItems(domain, false);
  }

  /**
   * Gets the list of DNS items for the given domain, optionally sorted by address.
   *
   * @param domain the domain
   * @param sortByAddress true to sort by IP address, false to sort by name
   * @return the list of DNS items
   */
  public List<DnsItem> getItems(String domain, boolean sortByAddress) {
    List<DnsItem> items = new ArrayList<>();
    for (DnsItem item : nameToItem.values()) {
      if (item.getName().getDomain().equals(domain)) {
        items.add(item);
      }
    }
    if (sortByAddress) {
      Collections.sort(items, Comparator.comparing(DnsItem::getIp));
    } else {
      Collections.sort(items, Comparator.comparing(item -> item.getName().toString()));
    }
    return items;
  }

  /**
   * Adds a new DNS item with the given IP and machine name.
   *
   * @param ip the IP address
   * @param name the machine name
   * @throws IOException if writing to the database file fails
   * @throws IllegalArgumentException if the IP or name already exists
   */
  public void addItem(AdresseIP ip, NomMachine name) throws IOException {
    if (ipToItem.containsKey(ip)) {
      throw new IllegalArgumentException("IP address already exists!");
    }
    if (nameToItem.containsKey(name)) {
      throw new IllegalArgumentException("Machine name already exists!");
    }
    DnsItem item = new DnsItem(ip, name);
    ipToItem.put(ip, item);
    nameToItem.put(name, item);
    Files.writeString(
        dbFile,
        item.toString() + "\n",
        StandardOpenOption.APPEND,
        StandardOpenOption.CREATE
    );
  }
}
