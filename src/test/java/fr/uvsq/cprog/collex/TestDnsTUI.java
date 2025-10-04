package fr.uvsq.cprog.collex;

import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class TestDnsTUI {

  @Test
  public void testCommandDispatch() throws IOException, IllegalArgumentException {
    Dns dns = new Dns();
    DnsTUI tui = new DnsTUI(dns);

    Command command = tui.nextCommande("ls hofedz.com");
    assertTrue(command instanceof ListDomainCommand);

    // Test quit command
    command = tui.nextCommande("quit");
    assertTrue(command instanceof QuitCommand);

    // Test empty input
    command = tui.nextCommande("");
    assertTrue(command instanceof QuitCommand);

    // Test add command valid
    command = tui.nextCommande("add 192.168.1.1 api.hofedz.com");
    assertTrue(command instanceof AddItemCommand);

    // Test ls with -a flag
    command = tui.nextCommande("ls -a hofedz.com");
    assertTrue(command instanceof ListDomainCommand);

    // Test ls without domain
    command = tui.nextCommande("ls");
    assertNull(command);

    // Test invalid add (too few args)
    command = tui.nextCommande("add 192.168.1.1");
    assertNull(command);

    // Test invalid add (invalid IP)
    command = tui.nextCommande("add invalid.ip api.com");
    assertNull(command);

    // Test IP lookup
    command = tui.nextCommande("192.168.1.1");
    assertTrue(command instanceof LookupIPCommand);

    // Test name lookup
    command = tui.nextCommande("machine.example.com");
    assertTrue(command instanceof LookupNameCommand);
  }
}
