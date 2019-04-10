package ch.epfl.javass.jass;

import ch.epfl.javass.bits.Bits32;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.Card.Rank;

public final class PackedCard {

    private PackedCard() {};
    
    public static int INVALID= 0b111111;
    
    public static boolean isValid(int pkCard) {
        
        
        return Bits32.extract(pkCard, 0, 4)<=8 && Bits32.extract(pkCard , 6, 26)==0 ; 

        
    }
    
    public static int pack(Card.Color c, Card.Rank r) {
        
        return Bits32.pack ( r.ordinal(),4,c.ordinal(),2); 
    }
    
    public static Card.Color color(int pkCard){
        assert isValid(pkCard);
        
        return Color.values()[Bits32.extract(pkCard,4,2)]; 
    }
    
    public static Card.Rank rank(int pkCard){
        
        return Rank.values()[Bits32.extract(pkCard,0,4)];
    }
    
    public static boolean isBetter(Card.Color trump, int pkCardL, int pkCardR) {
        if( (color(pkCardL) != color(pkCardR)) && ((color(pkCardL)== trump) || (color(pkCardR)== trump)) ) {
            return color(pkCardL)== trump; 
        }
        
        if(color(pkCardL)==color(pkCardR)) {
            if(color(pkCardL)==trump) {
                return (rank(pkCardL).trumpOrdinal()>rank(pkCardR).trumpOrdinal());
            }else {
                return (rank(pkCardL).ordinal()>rank(pkCardR).ordinal());
            }
        }
        
        return false;
       
        
    }
    
    public static int points(Card.Color trump, int pkCard) {
       
        
       if(color(pkCard)==trump) {
           switch(rank(pkCard).trumpOrdinal()) {
           case 0 : return 0;
           case 1: return 0;
           case 2: return 0;
           case 3: return 10;
           case 4: return 3;
           case 5: return 4;
           case 6: return 11;
           case 7: return 14;
           
           
           }
           return 20;
       }
       
       switch(rank(pkCard).ordinal()) {
       case 0 : return 0;
       case 1: return 0;
       case 2: return 0;
       case 3: return 0;
       case 4: return 10;
       case 5: return 2;
       case 6: return 3;
       case 7: return 4;
       
       
       }
       return 11;
       
    }
    
    public static String toString(int pkCard) {
        
        return color(pkCard).toString()+rank(pkCard).toString();
    }
}
