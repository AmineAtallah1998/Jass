package ch.epfl.javass.jass;

import java.util.SplittableRandom;
import ch.epfl.javass.Preconditions;

public final class MctsPlayer implements Player {

    private final PlayerId ownId;
    private final int iterations;
    private final SplittableRandom seed;

    public MctsPlayer(PlayerId ownId, long rngSeed, int iterations) {

        Preconditions.checkArgument(iterations >= 9);
        this.iterations = iterations;
        this.ownId = ownId;
        SplittableRandom rng = new SplittableRandom(rngSeed);
        this.seed = new SplittableRandom(rng.nextLong());
    }

    private static class Node {

        private TurnState turnStateNode;
        private Node[] childrenNode;
        private CardSet inexistantChildrenNode;
        private int totalPointsSn;
        private int numberOfTurnsNn; 
        
        
        

    }

    @Override
    public Card cardToPlay(TurnState state, CardSet hand) {

        return null;
    }

}
