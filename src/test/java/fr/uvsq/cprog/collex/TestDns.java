package fr.uvsq.cprog.collex;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import static org.junit.Assert.*;

public class TestDns {
  private static Path originalDbFileBackup;
  private static Path dbFilePath = Paths.get("dns.txt");

  @BeforeClass
  public static void setUpClass() throws IOException {
    if (Files.exists(dbFilePath)) {
      originalDbFileBackup = Files.createTempFile("dns_backup", ".txt");
      Files.copy(dbFilePath, originalDbFileBackup, StandardCopyOption.REPLACE_EXISTING);
    }
  }

  @AfterClass
  public static void tearDownClass() throws IOException {
    if (originalDbFileBackup != null && Files.exists(originalDbFileBackup)) {
      Files.copy(originalDbFileBackup, dbFilePath, StandardCopyOption.REPLACE_EXISTING);
      Files.delete(originalDbFileBackup);
    } else if (!Files.exists(dbFilePath)) {
      Files.createFile(dbFilePath);
    }
  }

  @Before
  public void setUp() throws IOException {
    // Write test data to dns.txt
    String testData = "192.168.1.1 www.example.com\n" +
        "10.0.0.1 test.uvsq.fr\n" +
        "192.168.1.2 localhost.example.com\n" +
        "172.16.0.1 mail.example.com\n";
    Files.writeString(dbFilePath, testData);
  }

  @Test
  public void testConstructorLoadsDatabase() throws IOException {
    Dns dns = new Dns();

    AdresseIP ip1 = new AdresseIP("192.168.1.1");
    DnsItem item1 = dns.getItem(ip1);
    assertNotNull(item1);
    assertEquals(ip1, item1.getIp());
    assertEquals(new NomMachine("www.example.com"), item1.getName());

    AdresseIP ip2 = new AdresseIP("10.0.0.1");
    DnsItem item2 = dns.getItem(ip2);
    assertNotNull(item2);
    assertEquals(ip2, item2.getIp());
    assertEquals(new NomMachine("test.uvsq.fr"), item2.getName());
  }

  @Test
  public void testGetItemByIp() throws IOException {
    Dns dns = new Dns();

    AdresseIP ip = new AdresseIP("192.168.1.1");
    DnsItem item = dns.getItem(ip);
    assertNotNull(item);
    assertEquals(ip, item.getIp());
    assertEquals(new NomMachine("www.example.com"), item.getName());

    AdresseIP nonExistingIp = new AdresseIP("1.1.1.1");
    assertNull(dns.getItem(nonExistingIp));
  }

  @Test
  public void testGetItemByName() throws IOException {
    Dns dns = new Dns();

    NomMachine name = new NomMachine("www.example.com");
    DnsItem item = dns.getItem(name);
    assertNotNull(item);
    assertEquals(name, item.getName());
    assertEquals(new AdresseIP("192.168.1.1"), item.getIp());

    NomMachine nonExistingName = new NomMachine("nonexistent.com");
    assertNull(dns.getItem(nonExistingName));
  }

  @Test
  public void testGetItemsByDomain() throws IOException {
    Dns dns = new Dns();

    List<DnsItem> items = dns.getItems("uvsq.fr");
    assertEquals(1, items.size());
    assertEquals(new NomMachine("test.uvsq.fr"), items.get(0).getName());

    List<DnsItem> emptyItems = dns.getItems("nonexistent.com");
    assertTrue(emptyItems.isEmpty());
  }

  @Test
  public void testGetItemsWithSortByAddress() throws IOException {
    Dns dns = new Dns();

    List<DnsItem> items = dns.getItems("example.com", true);
    assertEquals(3, items.size());
    assertEquals("172.16.0.1", items.get(0).getIp().toString());
    assertEquals("192.168.1.1", items.get(1).getIp().toString());
    assertEquals("192.168.1.2", items.get(2).getIp().toString());
  }

  @Test
  public void testGetItemsWithSortByName() throws IOException {
    Dns dns = new Dns();

    List<DnsItem> items = dns.getItems("example.com", false);
    assertEquals(3, items.size());
    assertEquals("localhost.example.com", items.get(0).getName().toString());
    assertEquals("mail.example.com", items.get(1).getName().toString());
    assertEquals("www.example.com", items.get(2).getName().toString());
  }

  @Test
  public void testAddItem() throws IOException {
    Dns dns = new Dns();

    AdresseIP newIp = new AdresseIP("192.168.1.100");
    NomMachine newName = new NomMachine("new.ase.com");

    dns.addItem(newIp, newName);

    DnsItem addedItem = dns.getItem(newIp);
    assertNotNull(addedItem);
    assertEquals(newIp, addedItem.getIp());
    assertEquals(newName, addedItem.getName());

    DnsItem addedItemByName = dns.getItem(newName);
    assertEquals(addedItem, addedItemByName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddItemExistingIp() throws IOException {
    Dns dns = new Dns();

    AdresseIP existingIp = new AdresseIP("192.168.1.1");
    NomMachine anotherName = new NomMachine("duplicate.uvsq.fr");

    dns.addItem(existingIp, anotherName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddItemExistingName() throws IOException {
    Dns dns = new Dns();

    AdresseIP newIp = new AdresseIP("192.168.2.1");
    NomMachine existingName = new NomMachine("www.example.com");

    dns.addItem(newIp, existingName);
  }
}
