package ch.epfl.javass.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EnumMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.US_ASCII;

import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.CardSet;
import ch.epfl.javass.jass.Player;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.Score;
import ch.epfl.javass.jass.TeamId;
import ch.epfl.javass.jass.Trick;
import ch.epfl.javass.jass.TurnState;

public final class RemotePlayerServer {
    private final Player player;

    public RemotePlayerServer(Player player) {
        this.player=player;
    }
   
    public void run() {
        
        try (ServerSocket s0 = new ServerSocket(5108);
                Socket s = s0.accept();
                BufferedReader r = new BufferedReader(
                        new InputStreamReader(s.getInputStream(), US_ASCII));
                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
                        s.getOutputStream(), US_ASCII)))
        {
            
                while (true) {
                    
                    String string = r.readLine();
                    String[] tab = StringSerializer.split(' ', string);

                    switch (tab[0]) {
                    case "TRMP":
                        player.setTrump(Card.Color.values()[StringSerializer
                                                            .deserializeInt(tab[1])]);
                        
                        break;
                    case "HAND":
                        player.updateHand(CardSet.ofPacked(
                                StringSerializer.deserializeLong(tab[1])));
                        break;
                    case "SCOR":
                        player.updateScore(Score.ofPacked(
                                StringSerializer.deserializeLong(tab[1])));
                        break;
                    case "TRCK":
                        player.updateTrick(Trick
                                .ofPacked(StringSerializer.deserializeInt(tab[1])));
                        break;
                    case "WINR":
                        player.setWinningTeam(TeamId.values()[StringSerializer
                                                             .deserializeInt(tab[1])]);
                        break;

                    case "PLRS":
                        PlayerId ownId = PlayerId.values()[StringSerializer
                                                           .deserializeInt(tab[1])];
                        String[] players = StringSerializer.split(',', tab[2]);
                        Map<PlayerId, String> map = new EnumMap<>(PlayerId.class);
                        for (int i = 0; i < 4; i++) {

                            map.put(PlayerId.values()[i],
                                    StringSerializer.deserializeString(players[i]));
                        }
                        player.setPlayers(ownId, map);
                        break;

                    case "CARD":
                        CardSet hand = CardSet
                        .ofPacked(StringSerializer.deserializeLong(tab[2]));
                        String[] stateArguments = StringSerializer.split(',',
                                tab[1]);
                        TurnState state = TurnState.ofPackedComponents(
                                StringSerializer.deserializeLong(stateArguments[0]),
                                StringSerializer.deserializeLong(stateArguments[1]),
                                StringSerializer.deserializeInt(stateArguments[2]));

                        w.write(StringSerializer.serializeInt(player.cardToPlay(state, hand).packed()));
                        w.write('\n');
                        w.flush();
                        break;

                    }//end of switch
                   
                }//end while
                
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

}


