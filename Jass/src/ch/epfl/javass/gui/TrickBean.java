package ch.epfl.javass.gui;

import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.Trick;
import javafx.collections.*;

public final class TrickBean {

    private Color trump;
    private PlayerId winningPlayer;

    public ObservableMap<PlayerId, Card> trick() {
        ObservableMap<PlayerId, Card> map = FXCollections.observableHashMap();


       

        return FXCollections.unmodifiableObservableMap(map);       
    }

    void setTrick(Trick newTrick) {

    }

}
