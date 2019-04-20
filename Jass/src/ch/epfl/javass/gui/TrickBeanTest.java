package ch.epfl.javass.gui;

import ch.epfl.javass.jass.*;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.Card.Rank;
import ch.epfl.test.TestRandomizer;
import javafx.collections.ObservableMap;
import org.junit.jupiter.api.Test;

import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TrickBeanTest {

    @Test
    public void initWorks(){
        TrickBean bean = new TrickBean();
        assertNull(bean.trumpProperty().get());
        assertNull(bean.winningPlayerProperty().get());
        ObservableMap<PlayerId, Card> trick = bean.trick();
        assertEquals(0, trick.size());
    }

    @Test
    public void trumpWorks(){
        TrickBean bean = new TrickBean();
        SplittableRandom rand = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
            Card.Color trump = Card.Color.ALL.get(rand.nextInt(Card.Color.COUNT));
            bean.setTrump(trump);
            assertEquals(trump, bean.trumpProperty().get());
        }
    }
     Trick trickk = Trick.firstEmpty(Color.CLUB, PlayerId.PLAYER_1).withAddedCard(Card.of(Color.SPADE, Rank.JACK));
    @Test
    public void trickWorks(){
        TrickBean bean = new TrickBean();
        SplittableRandom rand = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
            Trick trick = trickk;
            bean.setTrick(trick);
            assertEquals(trick.isEmpty() ? null : trick.winningPlayer(), bean.winningPlayerProperty().get());
            for(int j = 0; j < PlayerId.COUNT; ++j){
                PlayerId player = trick.player(j);
                assertEquals(trick.size() > j ? trick.card(j) : null, bean.trick().get(player));
            }
        }
    }
}
