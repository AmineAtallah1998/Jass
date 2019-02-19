package ch.epfl.javass.jass;

public final class PackedCard {

    private PackedCard() {}
    
    public static int INVALID= 0b111111;
    
    public static boolean isValid(int pkCard) {
        
        
        return true;
        
    }
    
    public static int pack(Card.Color c, Card.Rank r) {
        
        return 0;
    }
    
    public static Card.Color color(int pkCard){
        return null;
    }
    
    public static Card.Rank rank(int pkCard){
        
        return null;
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
