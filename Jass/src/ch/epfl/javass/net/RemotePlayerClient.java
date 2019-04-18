package ch.epfl.javass.net;
import java.io.BufferedReader;
import static java.nio.charset.StandardCharsets.US_ASCII;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.net.Socket;
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


public final class RemotePlayerClient implements Player, AutoCloseable{
    
    private Socket s;
    private BufferedReader r;
    private BufferedWriter w;

    public RemotePlayerClient(String hostName) throws IOException {
        s = new Socket(hostName, 5108);
        r =new BufferedReader(new InputStreamReader(s.getInputStream(),US_ASCII));
        w =new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),US_ASCII));
    }

    @Override
    public void close() throws IOException {
        s.close();
        w.close();
        r.close();
    }

    @Override
    public Card cardToPlay(TurnState state, CardSet hand) {
        Card card;
        try {
            w.write("CARD ");
            w.write(StringSerializer.combine(',', StringSerializer.serializeLong(state.packedScore()),
                    StringSerializer.serializeLong(state.packedUnplayedCards()) ,
                    StringSerializer.serializeInt(state.packedTrick())));
            
            w.write(" "+StringSerializer.serializeLong(hand.packed()));
            newLineAndFlush();
            card=Card.ofPacked(StringSerializer.deserializeInt(r.readLine()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return card;
    }

    @Override
    public void setPlayers(PlayerId ownId, Map<PlayerId, String> playerNames) {
        try {
            w.write("PLRS "+StringSerializer.serializeInt(ownId.ordinal())+" ");
            int i=0;
            for (String s : playerNames.values()) {
                if(i==playerNames.size()-1) {
                    w.write(s);
                }else {
                w.write(s+",");
                }
                i++;
            }
            newLineAndFlush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void updateHand(CardSet newHand) {
        try {
            w.write("HAND "+StringSerializer.serializeLong(newHand.packed()));
            newLineAndFlush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void setTrump(Color trump) {
        try {
            w.write("TRMP "+StringSerializer.serializeInt(trump.ordinal()));
            newLineAndFlush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void updateTrick(Trick newTrick) {
        try {
            w.write( "TRCK " +StringSerializer.serializeInt(newTrick.packed()));
            newLineAndFlush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void updateScore(Score score) {
        try {
            w.write("SCOR "+StringSerializer.serializeLong(score.packed()));
            newLineAndFlush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void setWinningTeam(TeamId winningTeam) {
        try {
            
            w.write("WINR "+StringSerializer.serializeInt(winningTeam.ordinal()));
            newLineAndFlush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void newLineAndFlush() throws IOException {
        w.write('\n');
        w.flush();
     }

}


