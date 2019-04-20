package ch.epfl.javass.gui;

import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.Trick;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.*;


/**
 * @author Mohamed Ali Dhraief (283509) 
 * @author Amine Atallah (284592)
 *
 */public final class TrickBean {

    private Color trump;
    private PlayerId winningPlayer; // lecture simple 
    private ObservableMap<PlayerId, Card> trick = FXCollections.observableHashMap();

    
    
    /**
     * @return la propriété de l'atout 
     */
    public ObjectProperty<Color> trumpProperty(){
        return new SimpleObjectProperty<>(trump);
        
    }
    /**
     * @return la propriété du joueur gagnant 
     */
    public ReadOnlyObjectProperty<PlayerId> winningPlayerProperty(){
        return new SimpleObjectProperty<>(winningPlayer);

    }

    /**
     * @return la propriété du trick  
     */
    public ObjectProperty<ObservableMap<PlayerId, Card>> trickProperty() {
        return new SimpleObjectProperty<>(trick());
    }
    /**
     * @return une version non modifiable du pli 
     */
    public ObservableMap<PlayerId, Card> trick() {
        return FXCollections.unmodifiableObservableMap(trick);       
    }

    /**
     * @param newTrick
     * Il affecte dans la map les cartes jouées au joueurs correspendants et null aux joueurs qui n'ont pas joué
     */
    public void setTrick(Trick newTrick) {
        this.winningPlayer=newTrick.winningPlayer();
        ObservableMap<PlayerId,Card>map = FXCollections.observableHashMap();
        for (int i=0 ; i<newTrick.size() ; i++) {
            map.put(newTrick.player(i), newTrick.card(i));
        }
        for (int i= newTrick.size();i<4; i++) {
            map.put(newTrick.player(i), null);
        }
       /* for (int i=0 ; i<4 ; i++) {
            if(!map.containsKey(PlayerId.values()[i])) {
                map.put(PlayerId.values()[i], null);
            }
        }*/
        this.trick=map;
    }

    /**
     * @return l'atout
     */
    public Color trump() {
        return trump;
    }
    /**
     * @return le joueur gagant
     */
    public PlayerId winningPlayer() {
        return winningPlayer;
    }
    /**
     * @param trump
     * setter de Trump
     */
    public void setTrump(Color trump) {
        this.trump=trump;
    }
    

}




