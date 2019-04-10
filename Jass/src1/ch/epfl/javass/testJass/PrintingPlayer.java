package ch.epfl.javass.testJass;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.CardSet;
import ch.epfl.javass.jass.Player;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.Score;
import ch.epfl.javass.jass.TeamId;
import ch.epfl.javass.jass.Trick;
import ch.epfl.javass.jass.TurnState;

public final class PrintingPlayer implements Player {
    private final Player underlyingPlayer;

    public PrintingPlayer(Player underlyingPlayer) {
      this.underlyingPlayer = underlyingPlayer;
    }

    @Override
    public Card cardToPlay(TurnState state, CardSet hand) {
      System.out.print("C'est à moi de jouer... Je joue : ");
      Card c = underlyingPlayer.cardToPlay(state, hand);
      System.out.println(c);
      return c;
    } 
    
    
    public void setPlayers(PlayerId ownId, Map<PlayerId, String> playerNames) {
        Set<Map.Entry<PlayerId,String>>vu = playerNames.entrySet();
        for (Entry<PlayerId, String> s: vu) {
            System.out.print(s.getValue());
            if (s.getKey().equals(ownId)) System.out.print(" (moi)");
            System.out.println();
            
        }
        
    }
    
    public void updateHand(CardSet newHand) {
        System.out.println("Ma nouvelle main :"+newHand);
       
    }
    
    public void setTrump(Color trump) {
        System.out.println("Atout "+ trump );
    }
    
    public void updateTrick(Trick newTrick) {
        
        System.out.println("pli "+newTrick.index()+" "+newTrick.toString());
        
        
    }
    
    public void updateScore(Score score) {
       
        System.out.println("Scores " + score);
        
    }
    
    public void setWinningTeam(TeamId winningTeam) {
        System.out.println("l'équipe gagnante est "+ winningTeam);
        
    }
    

  }