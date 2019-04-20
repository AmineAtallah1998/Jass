package ch.epfl.javass.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.CardSet;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

/**
 * @author Mohamed Ali Dhraief (283509) 
 * @author Amine Atallah (284592)
 */
public final class HandBean {
    private ObservableList<Card> hand = initHand() ;
    private ObservableSet<Card> playableCards = FXCollections.observableSet();

    /**
     * @return la propriété correspendant de la main du joueur 
     */
    public ObjectProperty<ObservableList<Card>> handProperty(){
        return new SimpleObjectProperty<>(hand);
    }
    /**
     * @return la propriété correspendant de les cartes jouables de la main  du joueur 
     */
    public ObjectProperty<ObservableSet<Card>> playableCardsProperty(){
        return new SimpleObjectProperty<>(playableCards);
    }

    /**
     * @param newHand
     * Si le joueur vient tout juste de recevoir la main, hand contient tout les cartes
     * Si cela est une mise à jour, nous remplaçons les cartes jouées par un null
     */
    public void setHand(CardSet newHand){
        if (newHand.size()==9) {
            for (int i=0; i<9;i++) {
                hand.set(i, newHand.get(i));

            }
        }
        else
        {
            for (int i=0; i<9 ; i++ )
            {    if (hand.get(i)!=null)
                if ( !newHand.contains(hand.get(i))) {
                    hand.set(i, null);
                }
            }    
        }
    }



    /**
     * @param newPlayableCards
     * Met les cartes jouables sous la forme d'une ObservableSet :
     */
    public void setPlayableCards(CardSet newPlayableCards){
        Set<Card> cards = new HashSet<>();
        for (int i=0 ; i<newPlayableCards.size() ; i++) {
            cards.add(newPlayableCards.get(i));
        }
        playableCards.addAll(cards);
    }

    /**
     * @return les cartes jouables 
     */
    public ObservableSet<Card> playableCards(){
        return playableCards;
    }
    /**
     * @return la main du joueur
     */
    public ObservableList<Card> hand(){
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








