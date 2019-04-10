package ch.epfl.javass.jass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.Card.Rank;

public final class JassGame {

    private final Map<PlayerId, Player> players;
    private final Map<PlayerId, String> playerNames;
    private final Random shuffleRng, trumpRng;

    private Map<PlayerId, CardSet> hands = new HashMap<>();
    private List<Card> deck = deck();
    private TurnState tour;
    private int nbTurn = 0;
    private boolean firstPli = true;
    private boolean firstTrickInGame = true;
    private PlayerId winningPlayer;
    private final PlayerId firstPlayerOfTheTurn;
    private TeamId winningTeam;

    public JassGame(long rngSeed, Map<PlayerId, Player> players,
            Map<PlayerId, String> playerNames) {

        Random rng = new Random(rngSeed);
        this.shuffleRng = new Random(rng.nextLong());
        this.trumpRng = new Random(rng.nextLong());
        this.players = Collections.unmodifiableMap(new EnumMap<>(players));
        this.playerNames = Collections
                .unmodifiableMap(new EnumMap<>(playerNames));

        DealsTheCard();
        firstPlayerOfTheTurn = firstPlayerOfGame();
        tour = TurnState.initial(trumpChoose(), Score.INITIAL,
                firstPlayerOfGame());
        Set<Map.Entry<PlayerId, Player>> vu = players.entrySet();
        for (Map.Entry<PlayerId, Player> e : vu) {
            e.getValue().setPlayers(PlayerId.PLAYER_1, playerNames);
        }

    }

    private void afficheTrump() {
     
            Set<Map.Entry<PlayerId, Player>> vu = players.entrySet();
            for (Map.Entry<PlayerId, Player> e : vu) {
                e.getValue().setTrump(tour.trick().trump());
                break;
            }
       
    }
    private void affichePli() {
        Set<Map.Entry<PlayerId, Player>> vu = players.entrySet();
        for (Map.Entry<PlayerId, Player> e : vu) {
            e.getValue().updateTrick(tour.trick());
            break;
        }
    }

    private void afficheMain() {
        Set<Map.Entry<PlayerId, Player>> vu = players.entrySet();
        for (Map.Entry<PlayerId, Player> e : vu) {
            e.getValue().updateHand(hands.get(PlayerId.PLAYER_1));
            break;
        }
    }

    private void afficheScore() {
        Set<Map.Entry<PlayerId, Player>> vu = players.entrySet();
        for (Map.Entry<PlayerId, Player> e : vu) {
            e.getValue().updateScore(tour.score());
            break;
        }
    }
    
    private void afficheWinningTeam() {
        Set<Map.Entry<PlayerId, Player>> vu = players.entrySet();
        for (Map.Entry<PlayerId, Player> e : vu) {
            e.getValue().setWinningTeam(winningTeam);
            break;
        }
    }

    // Le déroulement d'un pli lambda
    private void PlaysATrick(PlayerId player) {

        Player p = players.get(player);
        CardSet hand = hands.get(player);
        Card playedCard = p.cardToPlay(tour, hand);
        tour = tour.withNewCardPlayed(playedCard);
        affichePli();
        
        hands.put(player, hand.remove(playedCard));
        hand = hands.get(player);
        PlayerId playerWillPlay;
        for (int i = 0; i < 3; i++) {
            playerWillPlay = tour.nextPlayer();
            p = players.get(playerWillPlay);
            hand = hands.get(playerWillPlay);
            playedCard = p.cardToPlay(tour, hand);
            tour = tour.withNewCardPlayed(playedCard);
            affichePli();
            hand = hand.remove(playedCard);
            hands.put(playerWillPlay, hand.remove(playedCard));
            hand = hands.get(player);
           
        }

        winningPlayer = tour.trick().winningPlayer();

    }

    private PlayerId firstPlayerinTrick() {

        if (firstPli) {
            return PlayerId.values()[(firstPlayerOfTheTurn.ordinal() + nbTurn)% 4];
        }

        return winningPlayer;
    }

    public boolean isGameOver() {
        Set<Map.Entry<PlayerId, Player>> vue = players.entrySet();
        for (Map.Entry<PlayerId, Player> e : vue) {
            if (PackedScore.totalPoints(tour.packedScore(),
                    e.getKey().team()) >= Jass.WINNING_POINTS) {
                winningTeam=e.getKey().team();
                return true;
            }
        }
        return false;
    }

    // choix de l'atout au début par hasard
    private Color trumpChoose() {
        return Color.ALL.get(trumpRng.nextInt(4));
    }

    // Toutes les cards non melangees
    private List<Card> deck() {
        List<Card> res = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                res.add(Card.of(Color.values()[i], Rank.values()[j]));
            }
        }
        return res;
    }

    // Premier joueur de toute la partie
    private PlayerId firstPlayerOfGame() {
        int s = 0;
        for (int i = 0; i < 36; i++) {
            if (deck.get(i).equals(Card.of(Color.DIAMOND, Rank.SEVEN))) {
                s = i;
                break;
            }
        }
        return PlayerId.values()[s / 9];
    }

    // Melange et Distribution des cartes
    private void DealsTheCard() {
        Collections.shuffle(deck, shuffleRng);
        hands.put(PlayerId.PLAYER_1, CardSet.of(deck.subList(0, 9)));
        hands.put(PlayerId.PLAYER_2, CardSet.of(deck.subList(9, 18)));
        hands.put(PlayerId.PLAYER_3, CardSet.of(deck.subList(18, 27)));
        hands.put(PlayerId.PLAYER_4, CardSet.of(deck.subList(27, 36)));

    }

    public void advanceToEndOfNextTrick() {

        if (!isGameOver()) {
           
            if (firstPli && firstTrickInGame) {
                System.out.println( "ce pli a ete commencé par " + firstPlayerinTrick());
                afficheTrump();
                affichePli();
                afficheScore();
                afficheMain();
                PlaysATrick(firstPlayerOfTheTurn);
                firstPli = false;
                firstTrickInGame = false;
            } else {
                // premier pli du tour
                if (tour.trick().isLast()) {
                    System.out.println("new Tour (si personne n'a gagne)");
                    firstPli = true;
                    nbTurn++;
                    tour = tour.withTrickCollected();
                    deck = deck();
                    DealsTheCard();
                    tour = TurnState.initial(trumpChoose(),
                            tour.score().nextTurn(), firstPlayerinTrick());
                    
                    if (isGameOver()) {
                        System.out.println(
                                "NOUS AVONS UNE EQUIPE GAGNANTE AVEC LES SCORES: "
                                        + tour.score());
                        afficheWinningTeam();
                    } else {
                        System.out.println( "ce pli a ete commencé par " + firstPlayerinTrick());
                        afficheTrump();
                        affichePli();
                        afficheScore();
                        afficheMain();
                        PlaysATrick(firstPlayerinTrick());
                        firstPli = false;
                    }
                } else {
                    tour = tour.withTrickCollected();
                    if (isGameOver()) {
                        System.out.println(
                                "NOUS AVONS UNE EQUIPE GAGNANTE AVEC LES SCORES: "
                                        + tour.score());
                        afficheWinningTeam();
                    }else {
                    System.out.println("On commence un nouveau pli");
                    System.out.println( "ce pli a ete commencé par " + firstPlayerinTrick());
                    affichePli();
                    afficheScore();
                    afficheMain();
                    PlaysATrick(firstPlayerinTrick());
                    }
                }
            }
        }
    } // fin méthode

}
