package ch.epfl.javass.jass;

import java.util.StringJoiner;

import ch.epfl.javass.bits.Bits64;


public final class PackedCardSet {

    private PackedCardSet() {
    }

    public static final long EMPTY = 0;
    public static final long ALL_CARDS = Bits64.mask(0, 9) | Bits64.mask(16, 9)
            | Bits64.mask(32, 9) | Bits64.mask(48, 9);

    public static boolean isValid(long pkCardSet) {
        return (((pkCardSet & (~ALL_CARDS)) == 0));
    }
    /*
     * retourne l'ensemble des cartes strictement plus fortes que la carte
     * empaquetée donnée, sachant qu'il s'agit d'une carte d'atout ; par
     * exemple, appliquée à l'as de cœur, elle doit retourner un ensemble
     * contenant deux éléments, le neuf et le valet de cœur, car ces deux cartes
     * sont les seules à être strictement plus fortes que l'as de cœur lorsque
     * cœur est atout ; attention, cette méthode doit s'exécuter rapidement
     * (voir les conseils de programmation plus bas)
     */

    public static long trumpAbove(int pkCard) {
        long s = 0L;
        switch (Card.ofPacked(pkCard).rank()) {
        case SIX:
            s = 0b1111_1_1110;
            break;
        case SEVEN:
            s = 0b1111_1_1100;
            break;

        case EIGHT:
            s = 0b1111_1_1000;
            break;

        case NINE:
            s = 0b0001_0_0000;        
            break;

        case TEN:
            s = 0b1111_0_1000;
            break;

        case JACK:
            s = 0b0;
            break;

        case QUEEN:
            s = 0b1101_0_1000;
            break;

        case KING:
            s = 0b1001_0_1000; break;
        default:
            s = 0b0001_0_1000;
        }
        
        return s << 16*Card.ofPacked(pkCard).color().ordinal();
    }
  

    // qui retourne l'ensemble de cartes empaqueté contenant uniquement la carte
    // empaquetée donnée
    public static long singleton(int pkCard) {
        
        return (1L <<Card.ofPacked(pkCard).rank().ordinal()) << (16*Card.ofPacked(pkCard).color().ordinal()) ; 
             
            
    }

    // retourne vrai ssi l'ensemble de cartes empaqueté donné est vide,
    public static boolean isEmpty(long pkCardSet) {
        return pkCardSet == EMPTY;
    }

    // retourne la taille de l'ensemble de cartes empaqueté donné, c-à-d le
    // nombre de cartes qu'il contient,
    public static int size(long pkCardSet) {
        return Long.bitCount(pkCardSet);
    }

  
        
        public static int get(long pkCardSet, int index) {
          
            int pos = Long.numberOfTrailingZeros(pkCardSet);
            for (int i=0; i<index;i++) {
                pkCardSet= pkCardSet & 
                        Bits64.mask(pos+1, 63-pos);
                pos= Long.numberOfTrailingZeros(pkCardSet);
            }
           
            return PackedCard.pack(
                    Card.Color.values()[(int)(pos/16)],
                    Card.Rank.values()[pos%16]);
         }
      
     
    
    public static long add(long pkCardSet, int pkCard) {
        return pkCardSet | singleton(pkCard);  
    }

    public static long remove(long pkCardSet, int pkCard) {

        return pkCardSet & (~singleton(pkCard)); 
    }

    public static boolean contains(long pkCardSet, int pkCard) {
        return (pkCardSet | singleton(pkCard)) == pkCardSet;
    }

    public static long complement(long pkCardSet) { 
       
        return (pkCardSet ^ ALL_CARDS);
    }

    public static long union(long pkCardSet1, long pkCardSet2) {
        return pkCardSet1 | pkCardSet2;
    }

    public static long intersection(long pkCardSet1, long pkCardSet2) {
        return pkCardSet1 & pkCardSet2;
    }

    public static long difference(long pkCardSet1, long pkCardSet2) {
        return (pkCardSet1 & ~pkCardSet2);
    }

    public static long subsetOfColor(long pkCardSet, Card.Color color) {
        return pkCardSet & (Bits64.mask(color.ordinal() * 16, 9));
    }
    
    
    public static String toString(long pkCardSet) {
        StringJoiner j = new StringJoiner(",", "{", "}");
       
        for (int i=0 ; i<size(pkCardSet) ; i++) {
           Card c = Card.ofPacked(get(pkCardSet, i)) ;
           j.add(c.color().toString()+c.rank().toString());
        }
        
        
        
        
        
            return j.toString();
    }
    
 
   

}

