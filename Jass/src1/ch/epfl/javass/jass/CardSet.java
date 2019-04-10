package ch.epfl.javass.jass;

import java.util.List;

import ch.epfl.javass.Preconditions;

public final class CardSet {

    private final long pkCardSet;

    private CardSet(long packed) {
        this.pkCardSet = packed;

    }

    public static final CardSet EMPTY = new CardSet(PackedCardSet.EMPTY);
    public static final CardSet ALL_CARDS = new CardSet(
            PackedCardSet.ALL_CARDS);

    public static CardSet of(List<Card> cards) {
        long res = 0;

        for (int i = 0; i < cards.size(); i++) {
            res = PackedCardSet.add(res, cards.get(i).packed());
        }

        return new CardSet(res);
    }

    public static CardSet ofPacked(long packed) {
        Preconditions.checkArgument(PackedCardSet.isValid(packed));

        return new CardSet(packed);
    }

    public long packed() {
        return pkCardSet;
    }

    public boolean isEmpty() {
        return pkCardSet == 0;
    }

    public int size() {
        return PackedCardSet.size(pkCardSet);
    }

    public Card get(int index) {
        return Card.ofPacked(PackedCardSet.get(pkCardSet, index));
    }

    public CardSet add(Card card) {
        return new CardSet(PackedCardSet.add(pkCardSet, card.packed()));
    }

    public CardSet remove(Card card) {
        return new CardSet(PackedCardSet.remove(pkCardSet, card.packed()));
    }

    public boolean contains(Card card) {
        return PackedCardSet.contains(pkCardSet, card.packed());
    }

    public CardSet complement() {
        return new CardSet(PackedCardSet.complement(pkCardSet));
    }

    public CardSet union(CardSet that) {
        return new CardSet(PackedCardSet.union(pkCardSet, that.pkCardSet));
    }

    public CardSet intersection(CardSet that) {
        return new CardSet(
                PackedCardSet.intersection(pkCardSet, that.pkCardSet));
    }

    public CardSet difference(CardSet that) {
        return new CardSet(PackedCardSet.difference(pkCardSet, that.pkCardSet));
    }

    public CardSet subsetOfColor(Card.Color color) {
        return new CardSet(PackedCardSet.subsetOfColor(pkCardSet, color));
    }

    @Override
    public int hashCode() {
        return Long.hashCode(pkCardSet);
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof CardSet)) {
            return false;
        }
        return ((CardSet) that).pkCardSet == this.pkCardSet;
    }

    @Override
    public String toString() {
        return PackedCardSet.toString(pkCardSet);
    }

}


