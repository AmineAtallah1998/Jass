package ch.epfl.javass.net;

import java.net.ServerSocket;
import ch.epfl.javass.jass.Player;

public final class RemotePlayerServer {
    private final Player player;
    
    public RemotePlayerServer(Player player) {
        this.player=player;
    }

   public void run() {
       while(true) {
           //OUvrir socket qui va faire (comme serveur exemple)
           //try with resource et dedans while avec true 
       }
   }

}
