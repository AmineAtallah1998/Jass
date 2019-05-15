package ch.epfl.javass.gui;

import java.util.EnumMap;
import java.util.Map;

import ch.epfl.javass.debug.RandomPlayer;
import ch.epfl.javass.jass.JassGame;
import ch.epfl.javass.jass.MctsPlayer;
import ch.epfl.javass.jass.PacedPlayer;
import ch.epfl.javass.jass.Player;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.TeamId;
import javafx.application.Application;
import javafx.stage.Stage;

public final class Step10_Test extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
      Map<PlayerId, Player> ps = new EnumMap<>(PlayerId.class);
      ps.put(PlayerId.PLAYER_1, new GraphicalPlayerAdapter());
      ps.put(PlayerId.PLAYER_2, new RandomPlayer(2000));
      ps.put(PlayerId.PLAYER_3, new RandomPlayer(2000));
      ps.put(PlayerId.PLAYER_4, new RandomPlayer(2000));

      Map<PlayerId, String> ns = new EnumMap<>(PlayerId.class);
      PlayerId.ALL.forEach(i -> ns.put(i, i.name()));

      new Thread(() -> {
      JassGame g = new JassGame(0, ps, ns);
      while (! g.isGameOver()) {
        g.advanceToEndOfNextTrick();
      }
      }).start();
    }
  }