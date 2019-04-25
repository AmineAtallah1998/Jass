package ch.epfl.javass.net;

import org.junit.jupiter.api.Test;

import ch.epfl.javass.jass.MctsPlayer;
import ch.epfl.javass.net.RemotePlayerServer;

public class Server {
    @Test
    public void test() {
        RemotePlayerServer r = new RemotePlayerServer(new MctsPlayer(null, 1L, 10000)) ;
        r.run(); 
    
    }
}