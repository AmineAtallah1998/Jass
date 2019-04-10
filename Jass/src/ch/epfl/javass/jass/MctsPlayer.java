package ch.epfl.javass.jass;

import java.util.SplittableRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        private List<Node> childrenNode; // list of all the possible Children

        private CardSet nextPlayableCards;
        private float Sn; // S(n)
        private int Nn; // N(n)

        private int c = 40; // constante c
        private double Vn;
        private CardSet hand;
        private PlayerId ownId;
        private SplittableRandom seed;
        private List<Node> path = new ArrayList<>(); // alias path
        
        private CardSet nonPlayedChildren;

        public Node(TurnState turnStateNode, CardSet hand, PlayerId ownId,
                SplittableRandom seed) {
            this.ownId = ownId;
            this.seed = seed;
            this.turnStateNode = turnStateNode;
            nextPlayableCards = this.playableCards(turnStateNode, hand);
            childrenNode = new ArrayList<>();
            this.hand = hand;
            nonPlayedChildren= this.playableCards(turnStateNode, hand);
        }

        @Override
        public String toString() {
            String res = turnStateNode.trick().toString() + "Ceci est le pli  "
                    + Float.toString(getSn()) + "Sn   " + Integer.toString(Nn)
                    + "Nn";
            return res;
        }

        private float getSn() {
            return Sn;
        }

        private void setNn(int a) {
            this.Nn = a;
        }

        private void setSn(float a) {
            this.Sn = a;
        }

        private int getNn() {
            return Nn;
        }

        private double getVn() {
            return Vn;
        }

        private void setVn(double Vn) {
            this.Vn = Vn;
        }

        private PlayerId nextPlayer(TurnState state) {
            if (!PackedTrick.isValid(state.packedTrick())) {
                System.out.println(state.trick());
            }
            return state.trick().isFull()
                    ? state.withTrickCollected().nextPlayer()
                    : state.nextPlayer();
        }

        private CardSet playableCards(TurnState state, CardSet hand) {

            if (!PackedTrick.isValid(state.packedTrick())) {
                System.out.println("invalide at first dans Playable Cards");
            }
            TurnState other = TurnState.ofPackedComponents(state.packedScore(),
                    state.packedUnplayedCards(), state.packedTrick());

            if (other.trick().isLast() && other.trick().isFull()) {
                return null;
            }
            PlayerId nextPlayer = nextPlayer(other);

            other = other.trick().isFull() ? other.withTrickCollected() : other;
            nextPlayer = nextPlayer(other);

            if (this.ownId == nextPlayer) {
                return other.trick().playableCards(
                        other.unplayedCards().intersection(hand));
            }
            return other.trick()
                    .playableCards(other.unplayedCards().difference(hand));
        }

        private Node childToExpand() {
            int maxIndice = 0;
            for (int i = 1; i < childrenNode.size(); i++) {
                if (childrenNode.get(i).getVn() > childrenNode.get(maxIndice)
                        .getVn()) {
                    maxIndice = i;
                }
            }
            return childrenNode.get(maxIndice);
        }

        // Appel forc�ment par le parent
        private void setVnOfChildren() {

            for (int i = 0; i < this.nextPlayableCards.size(); i++) {
                Node childNode = childrenNode.get(i);
                if (childNode.Nn == 0) {
                    childNode.setVn(Double.POSITIVE_INFINITY);
                } else {
                    double toBeSet;
                    toBeSet = ((double) childNode.Sn) / childNode.Nn;
                    toBeSet += c * Math
                            .sqrt(2 * (Math.log(this.getNn())) / childNode.Nn);

                    childNode.setVn(toBeSet);
                }
            }
        }

        private boolean sature() {
            return childrenNode.size() == nextPlayableCards.size();
        }

        // retourne la carte qui a fait gagn� le plus de point � l'�quipe
        private double averageScore() {
            return ((double) Sn) / Nn;
        }

        private Card chosenCard() {

            if (turnStateNode.isTerminal())
                return null;

            Node maxNode = childrenNode.get(0);

            for (int i = 1; i < childrenNode.size(); i++) {
                Node currentNode = childrenNode.get(i);
                if (currentNode.averageScore() > maxNode.averageScore()) {
                    maxNode = currentNode;
                }

            }
            Trick selectedTrick = maxNode.turnStateNode.trick();

            return selectedTrick.card(selectedTrick.size() - 1);
        }

        // appel� par un noeud
        // retourne une carte jouable possible � partir de ce noeud
        private Card randomCard(TurnState state) {
            if (state.isTerminal())
                return null;
            
            TurnState other = TurnState.ofPackedComponents(state.packedScore(),
                    state.packedUnplayedCards(), state.packedTrick());
            
            int random = seed.nextInt(playableCards(state, hand).size());
            Card randomCard = playableCards(other, hand).get(random);
            return randomCard;
        }

        // retourne le score d'une simulation
        public Score finalScoreOfTurn(TurnState state, CardSet hand) {
            TurnState other = TurnState.ofPackedComponents(state.packedScore(),
                    state.packedUnplayedCards(), state.packedTrick());

            while (!other.isTerminal()) {
                while (!other.trick().isFull()) {

                    Card addedCard = randomCard(other);
                    other = other.withNewCardPlayed(addedCard);

                }
                other = other.withTrickCollected();
            }
            return other.score();
        }

        // appel� par le noeud o� la simulation aura lieu
        // mets � jour les Sn et les Nn
        private void MCSimulation() {
            setSn(getSn() + finalScoreOfTurn(turnStateNode, hand)
                    .turnPoints(ownId.team()));
            setNn(getNn() + 1);
        }

        // appel� par le noeud o� on veut ajouter un fils
        // retour une liste des noeuds parents du plus �lev�s au plus bas
        private List<Node> bonEndroit() {
            List<Node> list = new LinkedList<>();
            list.add(this);

            if (this.isLeaf()) {
                return list;
                
            }

            Node theChild;
            if (!this.sature()) {
                return list;
            }
            if (this.sature()) {
            	
                this.setVnOfChildren();
                theChild = this.childToExpand();
                list.addAll(theChild.bonEndroit());
                return list;
            }

            return null;
        }

        // appel� par le noeud o� la modification a eu lieu
        // update tous ses parents
        private void updateScores(List<Node> path) {

            for (int i = 0; i < path.size(); i++) {
                path.get(i).setSn(getSn() + path.get(i).getSn());
                path.get(i).setNn(path.get(i).getNn() + 1);
            }
        }

       
        private Node addNode() {
            if (this.isLeaf()) {
                return this;
            }
            TurnState other = TurnState.ofPackedComponents(
                    this.turnStateNode.packedScore(),
                    turnStateNode.packedUnplayedCards(),
                    turnStateNode.packedTrick());

            Node n;
            TurnState m;
            other = other.trick().isFull() ? other.withTrickCollected() : other;
            m = other.withNewCardPlayed(nonPlayedChildren.get(0));
            nonPlayedChildren= nonPlayedChildren.remove(nonPlayedChildren.get(0));
            n = new Node(m, hand, ownId, seed);
            childrenNode.add(n);

            n.path.add(this);
            n.path.addAll(this.path);

            return n;
        }

        private boolean isLeaf() {
            return (turnStateNode.trick().isLast()
                    && turnStateNode.trick().isFull());
        }

    }// end node

    @Override
    public Card cardToPlay(TurnState state, CardSet hand) {

        if (hand.size() == 1) {
            return hand.get(0);
        }

        Node bigRoot = new Node(state, hand, ownId, seed);
        Node root = new Node(state, hand, ownId, seed);
        int s = 0;
        while (!bigRoot.sature()) {
            root = bigRoot.addNode();
            root.MCSimulation();
            root.updateScores(root.path);
            s++;
        }
        

        
        while (s < iterations) {
        	bigRoot.setVnOfChildren();
            root = bigRoot.childToExpand();
            Node added = root.bonEndroit().get(root.bonEndroit().size() - 1).addNode();
            added.MCSimulation();
            added.updateScores(added.path);
           
            s++;
        }

        return bigRoot.chosenCard();
    }

}
