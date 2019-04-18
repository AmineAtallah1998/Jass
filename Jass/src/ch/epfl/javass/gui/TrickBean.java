package ch.epfl.javass.gui;

import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.Trick;
import javafx.collections.*;

public final class TrickBean {

    private Color trump;
    private PlayerId winningPlayer;
    private ObservableMap<PlayerId, Card> trick;

    public ObservableMap<PlayerId, Card> trick() {
        return FXCollections.unmodifiableObservableMap(trick);       
    }

    public void setTrick(Trick newTrick) {
        this.winningPlayer=newTrick.winningPlayer();
        ObservableMap<PlayerId,Card>map = FXCollections.observableHashMap();
        for (int i=0 ; i<newTrick.size() ; i++) {
            map.put(newTrick.player(i), newTrick.card(i));
        }
        for (int i=0 ; i<4 ; i++) {
            if(!map.containsKey(PlayerId.values()[i])) {
                map.put(PlayerId.values()[i], null);
            }
        }
        this.trick=map;
    }

    public Color trump() {
        return trump;
    }
    public PlayerId winningPlayer() {
        return winningPlayer;
    }
    public void setTrump(Color trump) {
        this.trump=trump;
    }

}
