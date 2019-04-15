package ch.epfl.javass.net;

import ch.epfl.javass.debug.RandomPlayer;
import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Player;
import ch.epfl.javass.net.JassCommand;
import ch.epfl.javass.net.RemotePlayerClient;
import ch.epfl.javass.net.RemotePlayerServer;
import ch.epfl.javass.net.StringSerializer;

public final class Signature_Checks_7 {
    private Signature_Checks_7() {
    }

    void checkStringSerializer() {
        String s = "";
        String[] a = new String[0];
        int i = 0;
        long l = 0L;
        char c = ' ';

        s = StringSerializer.serializeInt(i);
        i = StringSerializer.deserializeInt(s);
        s = StringSerializer.serializeLong(l);
        l = StringSerializer.deserializeLong(s);
        s = StringSerializer.serializeString(s);
        s = StringSerializer.deserializeString(s);
        s = StringSerializer.combine(c, a);
        a = StringSerializer.split(c, s);
    }

    @SuppressWarnings("unused")
    void checkJassCommand() {
        JassCommand c;

        c = JassCommand.PLRS;
        c = JassCommand.TRMP;
        c = JassCommand.HAND;
        c = JassCommand.TRCK;
        c = JassCommand.CARD;
        c = JassCommand.SCOR;
        c = JassCommand.WINR;
    }

    void checkRemotePlayerServer() {
        Player p = new RandomPlayer(0);
        RemotePlayerServer s;

        s = new RemotePlayerServer(p);
        s.run();
    }

    @SuppressWarnings("unused")
    void checkRemotePlayerClient() {
        RemotePlayerClient c;
        String s = "";
        int i = 0;
        Card card;

        c = new RemotePlayerClient(s, i);
        AutoCloseable a = c;
        Player p = c;
        card = c.cardToPlay(null, null);
        c.setPlayers(null, null);
        c.updateHand(null);
        c.setTrump(null);
        c.updateTrick(null);
        c.updateScore(null);
        c.setWinningTeam(null);
        try {
            c.close();
        } catch (Exception e) {
        }
    }
}