package ch.epfl.javass;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import ch.epfl.javass.gui.GraphicalPlayerAdapter;
import ch.epfl.javass.jass.JassGame;
import ch.epfl.javass.jass.MctsPlayer;
import ch.epfl.javass.jass.PacedPlayer;
import ch.epfl.javass.jass.Player;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.net.RemotePlayerClient;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * LocalMain : programme principal permettant de jouer une partie locale
 * 
 * @author Amine Atallah(284592)
 * @author Mohamed Ali Dhraief (283509)
 *
 */
public final class LocalMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /*
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override                            
    public void start(Stage primaryStage) throws Exception {
        System.out.println("GO!!");
        String[] defaultNames = { "Aline", "Bastien", "Colette" , "David"};
        int defaultIterations = 10_000;
        String defaultHost = "localhost";
        int minTime = 2;
        Random seed = null;
        List<String> arguments = getParameters().getRaw();
        
        // TODO show utilisation
        // GESTION DES ERREURS 
        if (arguments.size() > 5 || arguments.size() < 4) {
            showHelpMessage();
            System.exit(1);
        } else if (arguments.size() == 5) {
            checkSeed(arguments.get(4));
            seed = new Random(Long.parseLong(arguments.get(4)));
            for (int i = 0; i < arguments.size(); i++) {
                if (!checkArgument(arguments.get(i), i).getKey()) {
                    System.err.println(
                            checkArgument(arguments.get(i), i).getValue());
                    System.exit(1);
                }
            }
        } else {
            seed = new Random();
            for (int i = 0; i < arguments.size(); i++) {
                if (!checkArgument(arguments.get(i), i).getKey()) {
                    System.err.println(
                            checkArgument(arguments.get(i), i).getValue());
                    System.exit(1);
                }
            }
        }

        //TODO change to always 5 seeds
        // GENERATION DES GRAINES ALEATOIRES
        long[] seeds = new long[1+numberOfMctsPlayers(arguments)];
        for (int i=0 ; i< seeds.length ; i++) {
            seeds[i]=seed.nextLong();
        }


        // CREATION DES JOUEURS
        Map<PlayerId, Player> players = new EnumMap<>(PlayerId.class);
        Map<PlayerId, String> names = new EnumMap<>(PlayerId.class);

        int counterOfMcts=0;
        for (int i=0 ; i<arguments.size() ; i++) {
            String argument = arguments.get(i);
            String[] argSeparator = argument.split(":");

            if(argument.charAt(0)=='h') {
                players.put(PlayerId.values()[i], new GraphicalPlayerAdapter());
                setName(argSeparator , i , names , defaultNames);
            }

            else if(argument.charAt(0)=='s') {
                counterOfMcts++;
                int iterations = argSeparator.length==3 ? Integer.parseInt(argSeparator[2]) : defaultIterations;
                players.put(PlayerId.values()[i],
                        new PacedPlayer(new MctsPlayer(PlayerId.values()[i],seeds[counterOfMcts],iterations), minTime));
                setName(argSeparator , i , names , defaultNames);
            } 

            else if(argument.charAt(0) == 'r') {
                setName(argSeparator , i , names , defaultNames);
                String hostName= argSeparator.length==3? argSeparator[2]: defaultHost;  
                try {
                players.put(PlayerId.values()[i], new RemotePlayerClient(hostName));   
                } catch (IOException e) {
                    System.out.println("Erreur de connexion pour le joueur distant "+(i+1));
                    System.exit(1);
                }
            }
        }

        // FIL D'EXECUTION
        Thread gameThread = new Thread(() -> {
            JassGame g = new JassGame(seeds[0], players, names);
            while (! g.isGameOver()) {
                g.advanceToEndOfNextTrick();
                try { Thread.sleep(1000); } catch (Exception e) {}
            }
        });
        gameThread.setDaemon(true);
        gameThread.start();
    }

    //Retourne vrai si aucune erreur n'est signalée sinon retourne faux avec
    //le message correspondant
    private Pair <Boolean, String > checkArgument (String s,int i) {
        char playerType = s.charAt(0);
        String[] argSeparator = s.split(":");

        if(playerType!='s' && playerType!='h' && playerType!='r') {
            return new Pair<>(false, "Erreur : type du joueur invalide dans le joueur "+(i+1) );
        }
        if (argSeparator.length>3) return new Pair<>(false, "Erreur : nombre d'arguments invalide dans le joueur "+(i+1));

        if (playerType=='h') {
            if(argSeparator.length>2) {
                return new Pair<>(false, "Erreur : nombre d'arguments invalide dans le joueur humain "+(i+1));
            }
        } 

        if (playerType=='s') {
            if (argSeparator.length==3) 
                try {
                    int iterations = Integer.parseInt(argSeparator[2]);
                    if (iterations <10 ) {
                        return new Pair<>(false, "Erreur : nombre d'itérations trop petit dans le joueur simule "+(i+1));
                    }
                }
            catch (NumberFormatException e) {
                return new Pair<>(false, "Erreur : nombre d'itérations invalide dans le joueur simule "+(i+1));
            }

        }
        
        return new Pair <>(true,"")  ; 
    }
    // Set the name of the player 
    private void setName(String[] argSeparator , int counter ,  Map<PlayerId, String> names ,String[] defaultNames ) {
        if(argSeparator.length==2 && argSeparator[1]!="") {
            names.put(PlayerId.values()[counter], argSeparator[1]);
        }else {
            names.put(PlayerId.values()[counter], defaultNames[counter]);
        }
    }
    //Retourne le nombre de joueurs simulés
    private int numberOfMctsPlayers(List<String> list) {
        int nb=0;
        for (int i=0 ; i<list.size() ; i++) {
            if(list.get(i).contains("s")) {
                nb++;
            }
        }
        return nb;
    }
    //Vérifie que la graine donnée est valide
    private void checkSeed(String s) {
        try {
            Long.parseLong(s);
        }
        catch (NumberFormatException e) {
            System.err.println("Erreur : la graine aléatoire n'est pas un long valide ");
            System.exit(1);
        }
    }
    private void showHelpMessage() {
        System.err.println("Utilisation: java ch.epfl.javass.LocalMain <j1>…<j4> [<graine>]\n" + 
                "où :\n" + 
                "<jn> spécifie le joueur n, ainsi:\n" + 
                "  h:<nom>  un joueur humain nommé <nom>\n" + 
                "…");
    }
}