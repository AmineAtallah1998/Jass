package ch.epfl.javass.net;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import ch.epfl.javass.jass.JassGame;
import ch.epfl.javass.jass.MctsPlayer;
import ch.epfl.javass.jass.Player;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.net.RemotePlayerClient;

class Client {
    @Test
    void test() throws IOException {
        int seed = 2018;
        Map<PlayerId, Player> players = new EnumMap<>(PlayerId.class);
        Map<PlayerId, String> playerNames = new EnumMap<>(PlayerId.class);

        for (PlayerId pId: PlayerId.ALL) {
            Player player = new MctsPlayer(pId, seed, 10_000);

            if (pId == PlayerId.PLAYER_1) player = new RemotePlayerClient("128.179.162.164");
//            if (pId == PlayerId.PLAYER_2) player = new RemotePlayerClient("");
//            if (pId == PlayerId.PLAYER_3) player = new RemotePlayerClient("");
//            if (pId == PlayerId.PLAYER_4) player = new RemotePlayerClient("");

            players.put(pId, player);
            playerNames.put(pId, pId.name());
        }

        JassGame g = new JassGame(seed, players, playerNames);
        while (!g.isGameOver())
            g.advanceToEndOfNextTrick();
        if(players.get(PlayerId.PLAYER_1) instanceof RemotePlayerClient) ((RemotePlayerClient)players.get(PlayerId.PLAYER_1)).close();
        if(players.get(PlayerId.PLAYER_2) instanceof RemotePlayerClient) ((RemotePlayerClient)players.get(PlayerId.PLAYER_2)).close();
        if(players.get(PlayerId.PLAYER_3) instanceof RemotePlayerClient) ((RemotePlayerClient)players.get(PlayerId.PLAYER_3)).close();
        if(players.get(PlayerId.PLAYER_4) instanceof RemotePlayerClient) ((RemotePlayerClient)players.get(PlayerId.PLAYER_4)).close();
    }
}