package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestAdresseIP {

    @Test
    public void testConstructorValidIP() {
        AdresseIP ip = new AdresseIP("192.168.1.1");
        assertEquals("192.168.1.1", ip.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidIP() {
        new AdresseIP("999.999.999.999");
    }

    @Test
    public void testToString() {
        AdresseIP ip = new AdresseIP("10.0.0.1");
        assertEquals("10.0.0.1", ip.toString());
    }

    @Test
    public void testEquals() {
        AdresseIP ip1 = new AdresseIP("192.168.1.1");
        AdresseIP ip2 = new AdresseIP("192.168.1.1");
        AdresseIP ip3 = new AdresseIP("192.168.1.2");
        assertEquals(ip1, ip2);
        assertNotEquals(ip1, ip3);
    }

    @Test
    public void testCompareTo() {
        AdresseIP ip1 = new AdresseIP("192.168.1.1");
        AdresseIP ip2 = new AdresseIP("192.168.1.2");
        assertTrue(ip1.compareTo(ip2) < 0);
        assertTrue(ip2.compareTo(ip1) > 0);
        assertEquals(0, ip1.compareTo(ip1));
    }
}
