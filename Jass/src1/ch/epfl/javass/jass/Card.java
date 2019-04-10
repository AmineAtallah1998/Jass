package ch.epfl.javass.jass;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.epfl.javass.bits.Bits32;

public final class Card {
    
    private Card(int c ) {
        pkCard=c;
    } 
    
    private final int pkCard;
    
    public enum Color{
        SPADE , HEART , DIAMOND , CLUB;
        
         public static final List<Color> ALL =Collections.unmodifiableList(Arrays.asList(values()));
         public static final int COUNT =4;
        
        @Override
        public String toString() {
            switch(this) {
            
            case SPADE : return "\u2660" ; 
            case  HEART : return "\u2665" ; 
            case DIAMOND : return "\u2666";
            
            }
            
            return "\u2663";
                
            
        }
        
    }
    
    public enum Rank {
        SIX , SEVEN , EIGHT , NINE ,TEN , JACK , QUEEN , KING , ACE;
        
        public static final List<Rank> ALL =Collections.unmodifiableList(Arrays.asList(values()));
        public static final int COUNT = 9;
        
       public int trumpOrdinal() {
            switch(this) {
            
            case SIX: return 0;
            case SEVEN: return 1;
            case EIGHT: return 2;
            case TEN: return 3;
            case QUEEN: return 4;
            case KING: return 5;
            case ACE: return 6;
            case NINE: return 7;
            
            }
            
            return 8;
            
        }
         
         @Override
         public String toString() {
             
             switch(this) {
             
             case SIX: return "6";
             case SEVEN: return "7";
             case EIGHT: return "8";
             case TEN: return "10";
             case QUEEN: return "Q";
             case KING: return "K";
             case ACE: return "A";
             case NINE: return "9";
             
             }
             
             return "J";
             
         }
        
    }
    
    public static Card of(Color c, Rank r) {
        
        Card card = new Card(PackedCard.pack(c, r));
       
        return card;
    
    }
    
    public static Card ofPacked(int packed) {
       if(!PackedCard.isValid(packed)) {
           throw new IllegalArgumentException();
       }
       
       return new Card(packed);
       
        
    }
    
    public int packed() {
        return pkCard;
    }
    
    public Color color() {
        return PackedCard.color(pkCard);
    }
    
    public Rank rank() {
        return PackedCard.rank(pkCard);
    }
    
    public boolean isBetter(Color trump, Card that) {
        return PackedCard.isBetter(trump, pkCard, that.pkCard);
    }
    
    public int points(Color trump) {
        return PackedCard.points(trump, pkCard);
    }
    
    public boolean equals(Object thatO) {
        if(!(thatO instanceof Card)) {
            return false;
        }
        return ((Card)thatO).pkCard == this.pkCard;
    }
    
    public int hashCode() {
        return packed();
    }
    
    public String toString() {
        return PackedCard.toString(pkCard);
    }
    
    
    
    
    
    
    

}
