package ch.epfl.javass.net;
import java.io.BufferedReader;
import static java.nio.charset.StandardCharsets.US_ASCII;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.CardSet;
import ch.epfl.javass.jass.Player;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.Score;
import ch.epfl.javass.jass.TeamId;
import ch.epfl.javass.jass.Trick;
import ch.epfl.javass.jass.TurnState;

//JAVADOC
public final class RemotePlayerClient implements Player, AutoCloseable{
    private final String hostName;
    private final int port;
    private final Socket s;
    private final BufferedReader r;
    private final BufferedWriter w;
    
    public RemotePlayerClient(String hostName , int port) throws IOException {
        this.hostName=hostName;
        this.port=port;
        
        this.s = new Socket(hostName, port);
        this.r =
          new BufferedReader(
            new InputStreamReader(s.getInputStream(),
                      US_ASCII));
        this. w =
          new BufferedWriter(
            new OutputStreamWriter(s.getOutputStream(),
                       US_ASCII));
    }
    
    @Override
    public void close() throws Exception {
        w.close();r.close();s.close();
    }
    
    @Override
    public Card cardToPlay(TurnState state, CardSet hand) {
        return null;
    }
    
    @Override
    public void setPlayers(PlayerId ownId, Map<PlayerId, String> playerNames) {
        
    }

    @Override
    public void updateHand(CardSet newHand) {
       
    }

    @Override
    public void setTrump(Color trump) throws IOException {
        w.write("TRMP "+StringSerializer.serializeInt(trump.ordinal()));
        newLineFlush();

    }
    
    private void newLineFlush() throws IOException {
        w.write('\n');
        w.flush();
    }

    @Override
    public void updateTrick(Trick newTrick) {
        
    }

    @Override
    public void updateScore(Score score) {
       
    }

    @Override
    public void setWinningTeam(TeamId winningTeam) {
       
    }

}
