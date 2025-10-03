package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestNomMachine {

    @Test
    public void testConstructorValidName() {
        NomMachine name = new NomMachine("www.uvsq.fr");
        assertEquals("www.uvsq.fr", name.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidName() {
        new NomMachine("no-dote");
    }

    @Test
    public void testToString() {
        NomMachine name = new NomMachine("test.example.com");
        assertEquals("test.example.com", name.toString());
    }

    @Test
    public void testGetMachineName() {
        NomMachine name = new NomMachine("machine.domain.tld");
        assertEquals("machine", name.getMachineName());
        assertEquals("domain.tld", name.getDomain());
    }

    @Test
    public void testEquals() {
        NomMachine n1 = new NomMachine("www.uvsq.fr");
        NomMachine n2 = new NomMachine("www.uvsq.fr");
        NomMachine n3 = new NomMachine("mail.uvsq.fr");
        assertEquals(n1, n2);
        assertNotEquals(n1, n3);
    }
}
