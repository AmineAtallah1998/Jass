package ch.epfl.javass;

import java.io.IOError;
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
import ch.epfl.javass.net.RemotePlayerServer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

public final class LocalMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private void checkSeed(String s) {
        try {
                Long.parseLong(s);
        }
        catch (NumberFormatException e) {
            System.out.println("la graine aléatoire n'est pas un long valide ");
            System.exit(1);
        }
    }

    @Override                            
    public void start(Stage primaryStage) throws Exception {
        System.out.println("GO!!");
        String[] defaultNames = { "Aline", "Bastien", "Colette" , "David"};
        Random seed = null;
        //GESTION DES ERREURS
        List<String> arguments = getParameters().getRaw();
        
        if (arguments.size()>5) {
            System.out.println("Error: undefined number of  paramaters");
            System.exit(1);
        }

        
        else if (arguments.size()==5) {
            checkSeed(arguments.get(4));
            seed = new Random(Long.parseLong(arguments.get(4)));     
            for (int i=0 ; i<arguments.size() ; i++) {
                if (!checkNames (arguments.get(i),i).getKey()) {
                    System.out.println(checkNames (arguments.get(i),i).getValue());
                    System.exit(1);
                }
        }
        }
        
        else {
            seed= new Random();
            for (int i=0 ; i<arguments.size() ; i++) {
                if (!checkNames (arguments.get(i),i).getKey()) {
                    System.out.println(checkNames (arguments.get(i),i).getValue());
                    System.exit(1);
                }
            }
            
        }



        long[] seeds = new long[1+numberOfMctsPlayers(arguments)];
        for (int i=0 ; i< seeds.length ; i++) {
            seeds[i]=seed.nextLong();
        }
        
       
        Map<PlayerId, Player> players = new EnumMap<>(PlayerId.class);
        Map<PlayerId, String> names = new EnumMap<>(PlayerId.class);
        
        int comp=0;
        
        for (int i=0 ; i<arguments.size() ; i++) {
            String s = arguments.get(i);
            if(s.charAt(0)=='h') {
                players.put(PlayerId.values()[i], new GraphicalPlayerAdapter());
                String[] tabString = s.split(":");
                if(tabString.length==2) {
                    names.put(PlayerId.values()[i], tabString[1]);
                }else {
                    names.put(PlayerId.values()[i], defaultNames[i]);
                }
            }
            else if(s.charAt(0)=='s') {
              comp++;
              int iterations = s.split(":").length==3 ? Integer.parseInt(s.split(":")[2]) :10_000;
              players.put(PlayerId.values()[i],
                      new PacedPlayer(new MctsPlayer(PlayerId.values()[i],seeds[comp],iterations),2));
              String[] tabString = s.split(":");
              if(tabString.length==2 && tabString[1]!="") {
                  names.put(PlayerId.values()[i], tabString[1]);
              }else {
                  names.put(PlayerId.values()[i], defaultNames[i]);
              }
                
            }else if(s.charAt(0) == 'r') {
                String[] tabString = s.split(":");
                if(tabString.length>1 && tabString[1]!="") {
                    names.put(PlayerId.values()[i], tabString[1]);
                }else {
                    names.put(PlayerId.values()[i], defaultNames[i]);
                }

                String hostName= tabString.length==3? tabString[2]: "localhost";  
                players.put(PlayerId.values()[i], new RemotePlayerClient(hostName));
                
            }
        }

        //FIL D'EXECUTION
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

    private int numberOfArgument (String s) {
        return s.split(":").length;
    }

    private Pair <Boolean, String > checkNames (String s,int i) {
        //TODO replace i with i+1
        char type = s.charAt(0);
        if(type!='s' && type!='h' && type!='r') {
            return new Pair<>(false, "Erreur : type du joueur invalide dans le joueur "+i );
        }
        if (numberOfArgument (s)>3) return new Pair<>(false, "Erreur : nombre d'arguments invalide dans le joueur "+i);
        if (s.charAt(0)=='h') {
            if(numberOfArgument (s)>2) {
                return new Pair<>(false, "Erreur : nombre d'arguments invalide dans le joueur humain "+i);
            }
        } 
        if (s.charAt(0)=='s') {
            String [] tab= s.split(":");
            if (tab.length==3) 
            try {
                int iteration = Integer.parseInt(tab[2]);
                if (iteration <10 ) {
                    return new Pair<>(false, "Erreur : nombre d'itérations trop petit dans le joueur simule "+i);
                }
            }// fin try
            catch (NumberFormatException e) {
                return new Pair<>(false, "Erreur : nombre d'itérations invalide dans le joueur simule "+i);
            }//fin catch

        }// fin if joueur simulé
        if (s.charAt(0)=='r') {
            try {
                //TODO Find how to do dis
                
            }catch(IOError e) {
                return new Pair<>(false, "Erreur : erreur de connexion du joueur distant "+i);
            }
        }
        return new Pair <>(true,"" )  ; 
    }
    
    private int numberOfMctsPlayers(List<String> list) {
        int nb=0;
        for (int i=0 ; i<list.size() ; i++) {
            if(list.get(i).contains("s")) {
                nb++;
            }
        }
        return nb;
    }

}
