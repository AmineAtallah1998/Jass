package ch.epfl.javass.jass;

import java.util.Map;

import ch.epfl.javass.jass.Card.Color;

public final class PacedPlayer implements Player {
    
    private final Player underlyingPlayer;
    private final double minTime;
    
    public PacedPlayer(Player underlyingPlayer, double minTime) {
        this.underlyingPlayer=underlyingPlayer;
        this.minTime=minTime;
    }
    

    @Override
    public Card cardToPlay(TurnState state, CardSet hand) {
        
           long current=  System.currentTimeMillis();
       
           Card card =underlyingPlayer.cardToPlay(state, hand);
           long after =  System.currentTimeMillis();
           if (minTime-(after-current)>0) {
           try {
               Thread.sleep((long)(minTime- (after-current)));
             } catch (InterruptedException e) { /* ignore */ } 
           }
           return card;
    }
    
    public  void setPlayers(PlayerId ownId, Map<PlayerId, String> playerNames) {
        underlyingPlayer.setPlayers(ownId, playerNames);
    }
    
    public void updateHand(CardSet newHand) {
        underlyingPlayer.updateHand(newHand);
    }
    
    public void setTrump(Color trump) {
        underlyingPlayer.setTrump(trump);
    }
    
    public void updateTrick(Trick newTrick) {
        underlyingPlayer.updateTrick(newTrick);
    }
    
    public void updateScore(Score score) {
        underlyingPlayer.updateScore(score);
    }
    
    public void setWinningTeam(TeamId winningTeam) {
        underlyingPlayer.setWinningTeam(winningTeam);
    }
    
    
    

}

