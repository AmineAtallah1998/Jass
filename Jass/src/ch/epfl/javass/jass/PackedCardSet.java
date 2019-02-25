package ch.epfl.javass.jass;

import ch.epfl.javass.bits.Bits32;

public final  class PackedCardSet {
    private PackedCardSet() {}
    public static final long EMPTY;
    public static final long  ALL_CARDS;
    
    public boolean isValid(long pkCardSet) {
        return ((pkCardSet & ( Bits32.mask(9, 6)| Bits32.mask(25, 6)|Bits32.mask(47, 6)|Bits32.mask(48, 6)))==0);
                
    }
    /* retourne l'ensemble des cartes strictement plus fortes que la carte
    empaquetée donnée, sachant qu'il s'agit d'une carte d'atout ; par exemple,
    appliquée à l'as de cœur, elle doit retourner un ensemble contenant deux
     éléments, le neuf et le valet de cœur, car ces deux cartes sont les seules
      à être strictement plus fortes que l'as de cœur lorsque cœur est atout ;
       attention, cette méthode doit s'exécuter rapidement 
       (voir les conseils de programmation plus bas)*/
    public long trumpAbove(int pkCard) {
        return 0;
    }
    //qui retourne l'ensemble de cartes empaqueté contenant uniquement la carte empaquetée donnée
    public long  singleton(int pkCard) {
       return 0;
    }
    //retourne vrai ssi l'ensemble de cartes empaqueté donné est vide,
    public  boolean isEmpty(long pkCardSet)
    {
        
    }
   // retourne la taille de l'ensemble de cartes empaqueté donné, c-à-d le nombre de cartes qu'il contient,
    public int size(long pkCardSet) {
        return Long.bitCount(pkCardSet);        
           }
    
         
    
    
    


}
