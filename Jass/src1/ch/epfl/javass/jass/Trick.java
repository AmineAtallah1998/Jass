package ch.epfl.javass.jass;


import ch.epfl.javass.Preconditions;
import ch.epfl.javass.jass.Card.Color;

public final class Trick {
    
    private final int pkTrick;
    
    private Trick(int pkTrick) {
        this.pkTrick=pkTrick;
    }
    
    public static final Trick INVALID = new Trick(-1);
    
    public static Trick firstEmpty(Color trump, PlayerId firstPlayer) {
        return new Trick(PackedTrick.firstEmpty(trump, firstPlayer));
    }

    public static Trick ofPacked(int packed) {
        Preconditions.checkArgument(PackedTrick.isValid(packed));
        
        return new Trick(packed);
    }
    
    public int packed() {
        return pkTrick;
    }
    
    public Trick nextEmpty() {
        if(!PackedTrick.isFull(pkTrick)) {
            throw new IllegalStateException();
        }
        
        return new Trick(PackedTrick.nextEmpty(pkTrick));
    }
    
    public boolean isEmpty() {
        return PackedTrick.isEmpty(pkTrick);
    }
    
    public boolean isFull() {
        return PackedTrick.isFull(pkTrick);
    }
    
    public boolean isLast() {
        return PackedTrick.isLast(pkTrick);
    }
    
    public int size() {
        return PackedTrick.size(pkTrick);
    }
    
    public Color trump() {
        return PackedTrick.trump(pkTrick);
    }
    
    public int index() {
        return PackedTrick.index(pkTrick);
    }
    
    public PlayerId player(int index) {
        Preconditions.checkIndex(index, 4);
        return PackedTrick.player(pkTrick, index);
    }
    
    public Card card(int index) {
        Preconditions.checkIndex(index, size());
        return Card.ofPacked(PackedTrick.card(pkTrick, index));
    }
    
    public Trick withAddedCard(Card c) {
        if(isFull()) {
            throw new IllegalStateException();
        }
        
        return new Trick(PackedTrick.withAddedCard(pkTrick, c.packed()));
    }
    
    public Color baseColor() {
        if(isEmpty()) {
            throw new IllegalStateException();
        }
        return PackedTrick.baseColor(pkTrick);
    }
    
    public CardSet playableCards(CardSet hand) {
        if(isFull()) {
            throw new IllegalStateException();
        }
        
        return CardSet.ofPacked(PackedTrick.playableCards(pkTrick, hand.packed()));
    }
    
    public int points() {
        return PackedTrick.points(pkTrick);
    }
    
    public PlayerId winningPlayer() {
        if(isEmpty()) {
            throw new IllegalStateException();
        }
        return PackedTrick.winningPlayer(pkTrick);
    }
    
    @Override
    public int hashCode() {
        return pkTrick;
    }
    
    @Override
    public boolean equals(Object that) {
        if(!(that instanceof Trick)) {
            return false;
        }
        return ((Trick)that).pkTrick == this.pkTrick;
    }
    
    @Override
    public String toString() {
        return PackedTrick.toString(pkTrick);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}



