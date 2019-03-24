package ch.epfl.javass.jass;

import java.util.StringJoiner;

import ch.epfl.javass.bits.Bits32;
import ch.epfl.javass.jass.Card.Color;

public final class PackedTrick {

    private PackedTrick() {

    }
    public static final int INVALID = -1;

    public static boolean isValid(int pkTrick) {
        return (Bits32.extract(pkTrick, 24, 4) >= 0
                && Bits32.extract(pkTrick, 24, 4) <= 8)
                && (   (PackedCard.isValid(Bits32.extract(pkTrick, 0, 6))
                        && PackedCard.isValid(Bits32.extract(pkTrick, 6, 6))
                        && PackedCard.isValid(Bits32.extract(pkTrick, 12, 6))
                        && PackedCard.isValid(Bits32.extract(pkTrick, 18, 6)))
                        || (PackedCard.isValid(Bits32.extract(pkTrick, 0, 6))
                                && PackedCard
                                        .isValid(Bits32.extract(pkTrick, 6, 6))
                                && PackedCard
                                        .isValid(Bits32.extract(pkTrick, 12, 6))
                                && (
                                     Bits32.extract(pkTrick, 18, 6)==PackedCard.INVALID ) )
                        || (PackedCard.isValid(Bits32.extract(pkTrick, 0, 6))
                                && PackedCard
                                        .isValid(Bits32.extract(pkTrick, 6, 6))
                                && ( Bits32.extract(pkTrick, 12, 6)==PackedCard.INVALID)
                                && ( Bits32.extract(pkTrick, 18, 6)==PackedCard.INVALID) )
                        
                        || (PackedCard.isValid(Bits32.extract(pkTrick, 0, 6))
                                && ( Bits32.extract(pkTrick, 6, 6)==PackedCard.INVALID)
                                && ( Bits32.extract(pkTrick, 12, 6)==PackedCard.INVALID)
                                && ( Bits32.extract(pkTrick, 18, 6)==PackedCard.INVALID) )
                        || (( Bits32.extract(pkTrick, 0, 6)==PackedCard.INVALID)
                                && ( Bits32.extract(pkTrick, 6, 6)==PackedCard.INVALID)
                                && ( Bits32.extract(pkTrick, 12, 6)==PackedCard.INVALID)
                                && ( Bits32.extract(pkTrick, 18, 6)==PackedCard.INVALID))

                );

    }

    public static int size(int pkTrick) {
        int nb = 0;
        
        if (PackedCard.isValid(Bits32.extract(pkTrick, 0, 6))) {
            nb++;
        }
        if (PackedCard.isValid(Bits32.extract(pkTrick, 6, 6))) {
            nb++;
        }
        if (PackedCard.isValid(Bits32.extract(pkTrick, 12, 6))) {
            nb++;
        }
        if (PackedCard.isValid(Bits32.extract(pkTrick, 18, 6))) {
            nb++;
        }

        return nb;
    }

    public static Color trump(int pkTrick) {
        return Card.Color.values()[Bits32.extract(pkTrick, 30, 2)];
    }

    public static int index(int pkTrick) {
        return Bits32.extract(pkTrick, 24, 4);
    }

    public static int card(int pkTrick, int index) {

        return (pkTrick >>> (6 * index)) & 0b111111;

    }

    public static String toString(int pkTrick) {
        StringJoiner j = new StringJoiner(",", "{", "}");
        
        for (int i=0 ; i<size(pkTrick) ; i++) {
           Card c = Card.ofPacked(card(pkTrick, i)) ;
           j.add(c.color().toString()+c.rank().toString());
        }
        
        
        
            return j.toString();
    }

    public static int firstEmpty(Color trump, PlayerId firstPlayer) {
      
        return (trump.ordinal() << 30 | firstPlayer.ordinal() << 28)
                | (INVALID >>> 8);
    }

    public static boolean isLast(int pkTrick) {

        return Bits32.extract(pkTrick, 24, 4) == 8;
    }

    public static boolean isEmpty(int pkTrick) {
        return (Bits32.extract(pkTrick, 0, 6) == PackedCard.INVALID)
                && (Bits32.extract(pkTrick, 6, 6) == PackedCard.INVALID)
                && (Bits32.extract(pkTrick, 12, 6) == PackedCard.INVALID)
                && (Bits32.extract(pkTrick, 18, 6) == PackedCard.INVALID);
    }

    public static boolean isFull(int pkTrick) {
        return PackedCard.isValid(Bits32.extract(pkTrick, 18, 6));
    }

    public static PlayerId player(int pkTrick, int index) {
        return PlayerId.values()[(Bits32.extract(pkTrick, 28, 2) + index) % 4];
    }

    public static Color baseColor(int pkTrick) {
        return PackedCard.color(Bits32.extract(pkTrick, 0, 6));
    }

    public static int points(int pkTrick) {
        int res = 0;

        for (int i = 0; i < 4; i++) {
           if(PackedCard.isValid(Bits32.extract(pkTrick, i * 6, 6))) {
               res += PackedCard.points(trump(pkTrick),  Bits32.extract(pkTrick, i * 6, 6));
           }
                  
        }
        if (isLast(pkTrick))
            res += 5;

        return res;
    }

    public static int withAddedCard(int pkTrick, int pkCard) {
        for (int i = 0; i < 3; i++) {
            if (Bits32.extract(pkTrick, i * 6, 6) == PackedCard.INVALID) {

                int t = ~Bits32.mask(i * 6, 6);
                return (pkTrick & t) | (pkCard << i * 6);
            }

        }

        int l = ~Bits32.mask(18, 6);

        return (pkTrick & l) | (pkCard << 18);
    }

    public static PlayerId winningPlayer(int pkTrick) {

        Card cardMax, card;
        int cardIndexMax, cardIndex;
        int maxIndice = 0;

        for (int i = 1; i <= 3; i++) {

            cardIndexMax = Bits32.extract(pkTrick, maxIndice * 6, 6);
            cardIndex = Bits32.extract(pkTrick, i * 6, 6);

            if (!PackedCard.isValid(cardIndex)) {
                break;
            }
            cardMax = Card.ofPacked(cardIndexMax);
            card = Card.ofPacked(cardIndex);
            if (card.isBetter(trump(pkTrick), cardMax)) {             
                maxIndice = i;
            }

        }

        return player(pkTrick, maxIndice);
    }

    public static int nextEmpty(int pkTrick) {
        if (isLast(pkTrick)) {
            return INVALID;
        }

        return (trump(pkTrick).ordinal() << 30)
                | (winningPlayer(pkTrick).ordinal() << 28)
                | ((index(pkTrick) + 1) << 24) | (Bits32.mask(0, 24));

    }

    private static boolean rightToSousCouper(int pkTrick, long pkHand) {
        // //On doit verifier que toutes les cartes en main sont ATOUT
        for (int i = 0; i < PackedCardSet.size(pkHand); i++) {
            if (!(PackedCard
                    .color(PackedCardSet.get(pkHand, i)) == trump(pkTrick))) {
                return false;
            }
        }
        // //On a verifié que toutes les cartes en main sont ATOUT

        // On doit verifier que toutes les cartes en main < plus fort atout
        // posee
        for (int i = 0; i < PackedCardSet.size(pkHand); i++) {
            if (PackedCard.isBetter(trump(pkTrick),
                    PackedCardSet.get(pkHand, i), maxAtoutPose(pkTrick))) {
                return false;
            }
        }

        return true;

    }
    //Retourne la carte d'atout la plus forte dans le pli sinon -1
    private static int maxAtoutPose(int pkTrick) {
        int maxAtoutPose = -1;
        for (int i = 0; i < size(pkTrick); i++) {
            if (PackedCard.color(card(pkTrick, i)) == trump(pkTrick)) {
                maxAtoutPose = card(pkTrick, i);
                break;
            }
        }
        if (maxAtoutPose == -1) {
            return -1;
        }

        for (int i = 0; i < size(pkTrick); i++) {
            if (PackedCard.isBetter(trump(pkTrick), card(pkTrick, i),
                    maxAtoutPose)) {
                maxAtoutPose = card(pkTrick, i);
            }
        }

        return maxAtoutPose;
    }
 

    public static long playableCards(int pkTrick, long pkHand) {

        // Le premier joueur peut jouer n'importe quelle carte
        if (isEmpty(pkTrick)) {
  
            return pkHand;
        }

        Card.Color trump = trump(pkTrick);
        Card.Color couleurDeBase = baseColor(pkTrick);

     // CAS : Atout == couleur de base & Main: Jack atout
        if (couleurDeBase == trump && PackedCardSet.subsetOfColor(pkHand,
                trump) == (0b100000L << trump.ordinal() * 16)) {
 
            return pkHand;
        }

        // CAS : Main: Pas de couleur de base et pas de Trump
        if (PackedCardSet.subsetOfColor(pkHand, couleurDeBase) == 0L
                && PackedCardSet.subsetOfColor(pkHand, trump) == 0L) {
      
            return pkHand;
        }

        // CAS : Main: Pas de couleur de base
        if (PackedCardSet.subsetOfColor(pkHand, couleurDeBase) == 0L) {
            if (maxAtoutPose(pkTrick) == -1) {
        
                return pkHand;
            }
            long res = PackedCardSet.EMPTY;
            //itérer sur ses cartes
            for (int i = 0; i < PackedCardSet.size(pkHand); i++) {
                // voir si la carte itérée en question est un atout
                if (PackedCard.color(PackedCardSet.get(pkHand, i)) == trump) {
                    // voir si la carte dans sa main est plus forte que la carte qui a déjà coupé 
                    if (PackedCard.isBetter(trump, PackedCardSet.get(pkHand, i),
                            maxAtoutPose(pkTrick))) {
                        res = PackedCardSet.add(res,
                                PackedCardSet.get(pkHand, i));
                        // voir s'il a le droit de sous couper vu que les  atouts en sa possession sont moins
                        // forts que la carte qui a déjà coupé 
                    } else if (rightToSousCouper(pkTrick, pkHand)) {
                        return pkHand;
                       /* res = PackedCardSet.add(res,
                                PackedCardSet.get(pkHand, i));
                                */
                    }
                }
                else {
                    res = PackedCardSet.add(res, PackedCardSet.get(pkHand, i));
                }
            }
          
            return res;

        }

        // CAS : Atout =/= couleur de base et on a un peu de tout (cas normale)
        long res1  = PackedCardSet.EMPTY;
        for (int i = 0; i < PackedCardSet.size(pkHand); i++) {
            if (PackedCard
                    .color(PackedCardSet.get(pkHand, i)) == couleurDeBase) {
                res1 = PackedCardSet.add(res1, PackedCardSet.get(pkHand, i));
            }

            else if (PackedCard.color(PackedCardSet.get(pkHand, i)) == trump) {

                boolean verif = true;
                for (int j = 0; j < size(pkTrick); j++) {
                    if (PackedCard.color(card(pkTrick, j)) == trump
                            && PackedCard.isBetter(trump, card(pkTrick, j),
                                    PackedCardSet.get(pkHand, i))) {
                        verif = false;
                        break;
                    }
                }
                if (verif) {

                    res1 = PackedCardSet.add(res1, PackedCardSet.get(pkHand, i));
                }

            }
        }
 
        return res1;

    }

}



