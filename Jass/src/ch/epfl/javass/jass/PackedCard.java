package ch.epfl.javass.jass;

import ch.epfl.javass.bits.Bits32;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.Card.Rank;

public final class PackedCard {

    private PackedCard() {};
    
    public static int INVALID= 0b111111;
    
    public static boolean isValid(int pkCard) {
        
        
        return Bits32.extract(pkCard, 0, 3)<=8 && Bits32.extract(pkCard , 4, 31)==0 ; //Changed Smth

        
    }
    
    public static int pack(Card.Color c, Card.Rank r) {
        
        return Bits32.pack ( r.ordinal(),4,c.ordinal(),2);  //Changed smth
    }
    
    public static Card.Color color(int pkCard){
        assert isValid(pkCard);
        
        return Color.values()[Bits32.extract(pkCard,4,2)];   //See more about values
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
        
       if(color(pkCard)==trump) return rank(pkCard).trumpOrdinal();
       
       return rank(pkCard).ordinal();
    }
    
    public static String toString(int pkCard) {
        
        return "cette carte est de couleur "+color(pkCard).toString()+" et de rang "+rank(pkCard).toString();
    }
}
