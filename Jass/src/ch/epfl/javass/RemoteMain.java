package ch.epfl.javass;

import ch.epfl.javass.jass.MctsPlayer;
import ch.epfl.javass.jass.PacedPlayer;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.net.RemotePlayerServer;
import javafx.application.Application;
import javafx.stage.Stage;

public final class RemoteMain extends Application {

    public static void main(String[] args) {
       launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        RemotePlayerServer server = new RemotePlayerServer(new PacedPlayer(new MctsPlayer(PlayerId.PLAYER_3 , 200, 2000),2));
        Thread remoteThread = new Thread(()-> {
            server.run();
        });
        System.out.println("La partie commencera à la connexion du client…");
        remoteThread.setDaemon(true);
        remoteThread.start();
    }

}
