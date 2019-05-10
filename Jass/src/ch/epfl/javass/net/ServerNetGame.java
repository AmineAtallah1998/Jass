package ch.epfl.javass.net;

import ch.epfl.javass.jass.Player;

import java.io.IOException;

import ch.epfl.javass.debug.RandomPlayer;
import ch.epfl.javass.net.RemotePlayerServer;

/**
 * test thus non Jdoc 
 * 
 * IMPORTANT : LANCER D'ABORD servernetgame PUIS netjassgame
 *
 *

 */
public class ServerNetGame {
    public static void main(String[] args) throws IOException {
        Player player = new RandomPlayer(2019);
        RemotePlayerServer gali =  new RemotePlayerServer(player);
        gali.run();
        System.out.println("on devrais pas arriver la");
    }
}


