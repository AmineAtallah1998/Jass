package ch.epfl.javass.jass;

import ch.epfl.javass.bits.Bits32;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.Card.Rank;

public final class PackedCard {

    private PackedCard() {};
    
    public static int INVALID= 0b111111;
    
    public static boolean isValid(int pkCard) {
        
        
        return Bits32.extract(pkCard, 0, 3)<=8;
        
    }
    
    public static int pack(Card.Color c, Card.Rank r) {
        
        return Bits32.pack ( r.trumpOrdinal(),4,c.ordinal(),2);
    }
    
    public static Card.Color color(int pkCard){
        return Color.values()[Bits32.extract(pkCard,4,2)];
    }
    
    public static Card.Rank rank(int pkCard){
        
        return Rank.values()[Bits32.extract(pkCard,0,4)];
    }
    
    public static boolean isBetter(Card.Color trump, int pkCardL, int pkCardR) {
        
        return true;
    }
    
    public static int points(Card.Color trump, int pkCard) {
        
        return 0;
    }
    
    public static String toString(int pkCard) {
        
        return null;
    }
}
