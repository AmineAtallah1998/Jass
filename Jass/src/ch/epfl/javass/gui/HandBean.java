package ch.epfl.javass.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.CardSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

public final class HandBean {
    private ObservableList<Card> hand = initHand() ;
    private ObservableSet<Card> playableCards;


    void setHand(CardSet newHand){
       CardSet copyNewHand= CardSet.ofPacked(newHand.packed());
       
        for (int i=0 ; i<9 ; i++) {
                if(copyNewHand.size()>0) {
                    hand.set(i, copyNewHand.get(0));
                    copyNewHand = copyNewHand.remove(copyNewHand.get(0));
                }else {
                    hand.set(i, null);
                }
                
                //FAUX A REFAIRE
                
        }

    }

    void setPlayableCards(CardSet newPlayableCards){
        Set<Card> cards = new HashSet<>();
        for (int i=0 ; i<newPlayableCards.size() ; i++) {
            cards.add(newPlayableCards.get(i));
        }
        playableCards.addAll(cards);
    }


    ObservableSet<Card> playableCards(){
        return playableCards;
    }
    ObservableList<Card> hand(){
        return hand; 
    }

    //initialisation des hand à null
    private ObservableList<Card> initHand() {
        List<Card> cards = new ArrayList<>();
        ObservableList<Card> init = FXCollections.observableArrayList();
        for (int i=0 ; i<9 ; i++) {
            cards.add(null);
        }
        init.addAll(cards);
        return init;
    }


}
