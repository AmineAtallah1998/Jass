package ch.epfl.javass.gui;

import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.Card.Rank;
import ch.epfl.javass.jass.CardSet;
import ch.epfl.javass.jass.CardSetTest;
import ch.epfl.javass.jass.Jass;
import ch.epfl.test.TestRandomizer;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HandBeanTest {

    @Test
    public void initWorks(){
        HandBean bean = new HandBean();
        assertEquals(Jass.HAND_SIZE, bean.hand().size());
        for(Card c : bean.hand())
            assertNull(c);
        assertEquals(0, bean.playableCards().size());
    }
    CardSet h = CardSet.EMPTY
            .add(Card.of(Color.SPADE, Rank.SIX))
            .add(Card.of(Color.SPADE, Rank.NINE))
            .add(Card.of(Color.SPADE, Rank.JACK))
            .add(Card.of(Color.HEART, Rank.SEVEN))
            .add(Card.of(Color.HEART, Rank.ACE))
            .add(Card.of(Color.DIAMOND, Rank.KING))
            .add(Card.of(Color.DIAMOND, Rank.ACE))
            .add(Card.of(Color.CLUB, Rank.TEN))
            .add(Card.of(Color.CLUB, Rank.QUEEN));


    @Test
    public void handWorks(){
        HandBean bean = new HandBean();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
            List<Card> lastHand = bean.hand();
            CardSet newHand = h;
            bean.setHand(newHand);
            List<Card> hand = bean.hand();
            List<Card> common = new LinkedList<>(hand);
            common.retainAll(lastHand);

            for(Card c : common)
                if(c != null)
                    assertEquals(lastHand.indexOf(c), hand.indexOf(c));

            for(int j = 0; j < newHand.size(); ++j)
                assertTrue(hand.contains(newHand.get(j)));

            for (Card card : hand)
                if(card != null)
                    assertTrue(newHand.contains(card));
        }
    }

  
}
