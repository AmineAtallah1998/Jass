package ch.epfl.javass.debug;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import ch.epfl.javass.jass.JassGame;
import ch.epfl.javass.jass.Player;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.net.RemotePlayerClient;
import ch.epfl.javass.net.RemotePlayerServer;

public final class RandomJassGame {
  public static void main(String[] args) {
      
    Map<PlayerId, Player> players = new HashMap<>();
    Map<PlayerId, String> playerNames = new HashMap<>();

    for (PlayerId pId: PlayerId.ALL) {
      Player player;
      if (pId == PlayerId.PLAYER_1) {
      player = new PrintingPlayer(new RandomPlayer(2019)); 
      }
      else if (pId == PlayerId.PLAYER_2) { //Player2 is the distant player
      
      try(RemotePlayerClient p = new RemotePlayerClient("169.254.141.56")){
          RemotePlayerServer server = new RemotePlayerServer(new RandomPlayer(2019));
       System.out.println("selem");
       players.put(pId, p);
       playerNames.put(pId, pId.name());
       continue;
       
      }catch (IOException e) {
          throw new UncheckedIOException(e);
      }
      }else {
      player = new RandomPlayer(2019);
      }
      players.put(pId, player);
      playerNames.put(pId, pId.name());
    }

    JassGame g = new JassGame(2019, players, playerNames);
    while (! g.isGameOver()) {
      g.advanceToEndOfNextTrick();
      System.out.println("----");
    }
  }
}