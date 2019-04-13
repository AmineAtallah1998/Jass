package ch.epfl.javass.net;
import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.CardSet;
import ch.epfl.javass.jass.Player;
import ch.epfl.javass.jass.TurnState;

public final class RemotePlayerClient implements Player, AutoCloseable{
    private String s;
    private int n;
    
    public RemotePlayerClient(String s , int n) {
        this.s=s;
        this.n=n;
    }
    
    @Override
    public Card cardToPlay(TurnState state, CardSet hand) {
        return null;
    }

    @Override
    public void close() throws Exception {
        
    }

}
