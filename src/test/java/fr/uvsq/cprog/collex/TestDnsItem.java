package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestDnsItem {

    @Test
    public void testConstructor() {
        AdresseIP ip = new AdresseIP("192.168.1.1");
        NomMachine name = new NomMachine("www.uvsq.fr");
        DnsItem item = new DnsItem(ip, name);
        assertEquals(ip, item.getIp());
        assertEquals(name, item.getName());
    }

    @Test
    public void testToString() {
        AdresseIP ip = new AdresseIP("10.0.0.1");
        NomMachine name = new NomMachine("test.example.com");
        DnsItem item = new DnsItem(ip, name);
        assertEquals("10.0.0.1 test.example.com", item.toString());
    }

    @Test
    public void testEquals() {
        AdresseIP ip1 = new AdresseIP("192.168.1.1");
        NomMachine name1 = new NomMachine("www.uvsq.fr");
        DnsItem item1 = new DnsItem(ip1, name1);
        DnsItem item2 = new DnsItem(ip1, name1);
        DnsItem item3 = new DnsItem(new AdresseIP("192.168.1.2"), name1);
        assertEquals(item1, item2);
        assertNotEquals(item1, item3);
    }
}
