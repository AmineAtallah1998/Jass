package ch.epfl.javass.jass;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Card {
    
    public enum Color{
        SPADE , HEART , DIAMOND , CLUB;
        
        private static final List<Color> ALL =Collections.unmodifiableList(Arrays.asList(values()));
        private static final int COUNT =4;
        
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
        
        private static final List<Rank> ALL =Collections.unmodifiableList(Arrays.asList(values()));
        private static final int COUNT = 9;
        
         int trumpOrdinal() {
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

}
