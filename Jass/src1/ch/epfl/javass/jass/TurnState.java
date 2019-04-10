package ch.epfl.javass.jass;

import ch.epfl.javass.Preconditions;
import ch.epfl.javass.bits.Bits32;
import ch.epfl.javass.bits.Bits64;
import ch.epfl.javass.jass.Card.Color;

public final class TurnState {
    
    private final long pkScore;
    private final long pkUnplayedCards;
    private final int pkTrick;

    private TurnState(long pkScore, long pkUnplayedCards, int pkTrick) {
        this.pkScore = pkScore;
        this.pkUnplayedCards = pkUnplayedCards;
        this.pkTrick = pkTrick;
    }

    public static TurnState initial(Color trump, Score score, PlayerId firstPlayer) {
        int pkTrick = (trump.ordinal() << 30) | (firstPlayer.ordinal() << 28)
                | (PackedCard.INVALID << 18) | (PackedCard.INVALID << 12)
                | (PackedCard.INVALID << 6) | (PackedCard.INVALID);
        
        long pkUnPlayedCards = Bits64.mask(0, 9) | Bits64.mask(16, 9) | Bits64.mask(32, 9) | Bits64.mask(48, 9)  ;
        
        return new TurnState(score.packed(),pkUnPlayedCards , pkTrick);
    }
    

    public static TurnState ofPackedComponents(long pkScore, long pkUnplayedCards, int pkTrick){
        
        Preconditions.checkArgument(PackedScore.isValid(pkScore) && PackedCardSet.isValid(pkUnplayedCards)
                && PackedTrick.isValid(pkTrick) );
        
        return new TurnState(pkScore, pkUnplayedCards, pkTrick);
    }
    

    public long packedScore() {
        return pkScore;
    }

    public long packedUnplayedCards() {
        return pkUnplayedCards;
    }

    public int packedTrick() {
        return pkTrick;
    }

    public Score score() {
        return Score.ofPacked(pkScore);
    }

    public CardSet unplayedCards() {
        return CardSet.ofPacked(pkUnplayedCards);
    }

    public Trick trick() {
        return Trick.ofPacked(pkTrick);
    }

     public boolean isTerminal() {
        return pkTrick== PackedTrick.INVALID;
    }
     

    public  PlayerId nextPlayer() {
        if(PackedTrick.isFull(pkTrick)) {
            throw new IllegalStateException();
        }
        return trick().player(
                trick().size());
    }

     public TurnState withNewCardPlayed(Card card) {
         
         if(PackedTrick.isFull(pkTrick)) {
             throw new IllegalStateException();
         }
     
         int pkTrick = PackedTrick.withAddedCard(this.pkTrick, card.packed());
         
         return new TurnState(this.pkScore,PackedCardSet.remove(this.pkUnplayedCards, card.packed()), pkTrick);
     }

       public TurnState withTrickCollected()   {
             
             if(!PackedTrick.isFull(pkTrick)) {
                 throw new IllegalStateException();
             }
             
            int pkTrick=PackedTrick.nextEmpty(this.pkTrick);
     long score = PackedScore.withAdditionalTrick(pkScore, PackedTrick.winningPlayer(this.pkTrick).team(), PackedTrick.points(this.pkTrick));
            
             return new TurnState(score, this.pkUnplayedCards, pkTrick);      
         }
     
     public TurnState withNewCardPlayedAndTrickCollected(Card card) {
         if(PackedTrick.isFull(pkTrick)) {
             throw new IllegalStateException();
         }
         
         if(PackedTrick.size(pkTrick)==3) {
        
             return withNewCardPlayed(card).withTrickCollected();
         }
         
         return withNewCardPlayed(card);
     }
 
     
}



