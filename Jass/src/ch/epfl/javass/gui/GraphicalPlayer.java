package ch.epfl.javass.gui;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.Card.Rank;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.TeamId;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Mohamed Ali Dhraief (283509) 
 * @author Amine Atallah (284592)
 *
 */
public final class GraphicalPlayer {
    private final Map<PlayerId , String> playerNames;
    private final PlayerId player;
    private Scene scene;

    private final ObservableMap<Card , Image> observableMap = getMapCard();
    private final ObservableMap<Color , Image> observableMapTrump = getMapTrump();

    // TODO TEST VICTORYPANES
    /**
     * @param player
     * @param playerNames
     * @param beanScore
     * @param beanTrick
     * crée les panneaux et les imbrique les uns aux autres
     */
    public GraphicalPlayer(PlayerId player, Map<PlayerId , String> playerNames,
            ScoreBean beanScore, TrickBean beanTrick, HandBean beanHand, BlockingQueue<Card> queue) {
        this.playerNames = playerNames;
        this.player=player;
        
        GridPane scorePane = createScorePane(beanScore);
        GridPane trickPane = createTrickPane(beanTrick);
        HBox handPane = createHandPane(beanHand, queue);
        BorderPane pane= new BorderPane(trickPane , scorePane , null , handPane , null);
        StackPane mainPane = new StackPane();
        
        mainPane.getChildren().add(pane);
        mainPane.getChildren().addAll(createVictoryPane1(beanScore), createVictoryPane2(beanScore));
        scene= new Scene(mainPane);
    }

    /**
     * @return le stage principale du stage
     */
    public Stage createStage() {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Javass - "+playerNames.get(player));
        stage.setMaxHeight(900); //TODO need to delete this
        return stage;
    }

    private HBox createHandPane(HandBean hb, BlockingQueue<Card> queue) {
        HBox handPane = new HBox();
        handPane.setStyle("-fx-background-color: lightgray;\n" + 
                "-fx-spacing: 5px;\n" + 
                "-fx-padding: 5px;");
     
        for (int i=0 ; i<9 ; i++) {
            int s=i; // crée uniquement pour utilisation dans l'expression lambda
            
            ImageView card = new ImageView();
            card.setFitWidth(80);
            card.setFitHeight(120);
            card.imageProperty().bind( Bindings.valueAt(observableMap, Bindings.valueAt(hb.hand(), i)) );
            
            card.setOnMouseClicked( (e) ->{
                try {
                    queue.put(hb.hand().get(s));
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            });
                        
            BooleanBinding isPlayable = Bindings.createBooleanBinding( ()->{
                return hb.playableCards().contains(hb.hand().get(s));
            }, hb.playableCards() , hb.hand() );
            
            card.opacityProperty().bind(Bindings.when(isPlayable).then(1).otherwise(0.2));
            card.disableProperty().bind(isPlayable.not());
            
            handPane.getChildren().add(card);
        }
        
        return handPane;
    }
    private Text nameCreator(int p1, int p2) {
        Text res= new Text (playerNames.get(PlayerId.values()[p1])+" et "+playerNames.get(PlayerId.values()[p2])+" : ");
        res.setTextAlignment(TextAlignment.RIGHT);
        return res;
    }
    private Text turnScoreCreator (TeamId t, ScoreBean sb) {
        Text turnScore= new Text();
        turnScore.textProperty().bind(Bindings.convert(sb.turnPointsProperty(t)));
        turnScore.setTextAlignment(TextAlignment.RIGHT);
        return turnScore;
    }
    
    private Text gamePointsCreator (TeamId t, ScoreBean sb ){
        Text gamePoints = new Text();
        gamePoints.setTextAlignment(TextAlignment.RIGHT);
        gamePoints.textProperty().bind( Bindings.convert(sb.gamePointsProperty(t)));
        return gamePoints; 
    }
    
    private Text diffCreator (TeamId t, ScoreBean sb) {
        Text textDiff = new Text();
        StringProperty difference = new SimpleStringProperty();
        textDiff.textProperty().bind(difference);
        sb.turnPointsProperty(t).addListener((o,oV,nV)->{ 
            
            if(nV.intValue()!=0) {
            int diff = nV.intValue()-oV.intValue();
            difference.set("(+"+Integer.toString(diff)+")");
            }else {
           
            difference.set("");  
            }
        });
        return textDiff;
    }   
    //Panneau du score
    private GridPane createScorePane(ScoreBean sb) {
        GridPane scorePane = new GridPane();
        scorePane.setStyle("-fx-font: 16 Optima;-fx-background-color: lightgray;-fx-padding: 5px;-fx-alignment: center;");
        
        //NAMES x2
        Text names= nameCreator (0,2);
        Text names1= nameCreator(1,3);
        scorePane.add(names, 0, 0);
        scorePane.add(names1, 0, 1);
        
        //STRING TOTAL x2
        Text stringTotal = new Text(" /Total :");
        stringTotal.setTextAlignment(TextAlignment.LEFT);
        scorePane.add(stringTotal, 3, 0);
        
        Text stringTotal1 = new Text(" /Total :");
        stringTotal1.setTextAlignment(TextAlignment.LEFT);
        scorePane.add(stringTotal1, 3, 1);
    
        
        //TURNSCORES
        Text turnScore= turnScoreCreator(TeamId.TEAM_1,sb);
        scorePane.add(turnScore, 1, 0);
        
        Text turnScore1 = turnScoreCreator(TeamId.TEAM_2,sb);
        scorePane.add(turnScore1, 1, 1);
        

        //GAMEPOINTS
        Text gamePoints= gamePointsCreator(TeamId.TEAM_1, sb);
        scorePane.add(gamePoints, 4, 0);
        
        Text gamePoints1= gamePointsCreator(TeamId.TEAM_2, sb);        
          scorePane.add(gamePoints1, 4, 1);
        

        //DIFFERENCE
        Text textDiff= diffCreator(TeamId.TEAM_1, sb);
        scorePane.add(textDiff, 2, 0);
        
        Text textDiff1= diffCreator(TeamId.TEAM_2, sb);
        scorePane.add(textDiff1, 2, 1);

        return scorePane;
    }
    private ImageView createTrump (TrickBean tb) {
        ImageView imageTrump = new ImageView();
        imageTrump.imageProperty().bind(Bindings.valueAt(observableMapTrump, tb.trumpProperty()));
        imageTrump.setFitWidth(101);
        imageTrump.setFitHeight(101);
        return imageTrump;       
    }
    
    private VBox createCard(TrickBean tb, Rectangle halo, PlayerId p, Pos value  ) {
        
        Text leftCard = new Text();
        leftCard.setStyle("-fx-font: 14 Optima;-fx-padding: 5px; -fx-alignment: center;");
        leftCard = new Text(playerNames.get(p));
        
        ImageView imageCardLeft = new ImageView();
        imageCardLeft.imageProperty().bind(Bindings.valueAt(observableMap, Bindings.valueAt(tb.trick(), p) ));
        imageCardLeft.setFitWidth(120);
        imageCardLeft.setFitHeight(180);
        
        StackPane paneStack = new StackPane();
        halo.visibleProperty().bind(tb.winningPlayerProperty().isEqualTo(p));
        paneStack.getChildren().addAll(halo, imageCardLeft);
      
        VBox nodeCardName = new VBox(leftCard , paneStack);
        nodeCardName.setAlignment(value);
        return nodeCardName;
    }
    // Panneau du pli
    private GridPane createTrickPane(TrickBean tb) {

        GridPane trickPane = new GridPane();
        trickPane.setStyle("-fx-background-color: whitesmoke;\n" + 
                "-fx-padding: 5px;\n" + 
                "-fx-border-width: 3px 0px;\n" + 
                "-fx-border-style: solid;\n" + 
                "-fx-border-color: gray;\n" + 
                "-fx-alignment: center;");

        //TRUMP
        ImageView imageTrump = createTrump(tb);
        trickPane.add(imageTrump, 1, 1);
        GridPane.setHalignment(imageTrump, HPos.CENTER);

        //HALO
        Rectangle halo = getHalo();
        Rectangle halo2 = getHalo();
        Rectangle halo3 = getHalo();
        Rectangle halo4 = getHalo();

        //CARD LEFT
        PlayerId p = player==PlayerId.PLAYER_1 ? PlayerId.PLAYER_4 : PlayerId.values()[player.ordinal()-1];
        VBox nodeCardName = createCard(tb, halo, p, Pos.CENTER);    
        trickPane.add(nodeCardName, 0 , 1);
      

        //CARD RIGHT
        PlayerId p2 = PlayerId.values()[(player.ordinal()+1)%4];        
        VBox nodeCardName2 = createCard(tb, halo2, p2,Pos.CENTER);
        trickPane.add(nodeCardName2, 2 , 1);

        PlayerId p3 = PlayerId.values()[(p2.ordinal()+1)%4];
        VBox nodeCardName3 =createCard(tb, halo3, p3,Pos.CENTER);
        trickPane.add(nodeCardName3,1,0);
  
        //CARD DOWN (GraphicalPlayer)
        VBox nodeCardName4= createCard(tb, halo4, player, Pos.BOTTOM_CENTER);
        trickPane.add(nodeCardName4,1,2);

        return trickPane;
    }

    // PANNEAU DE VICTOIRE DE L'EQUIPE 1
    private BorderPane createVictoryPane1(ScoreBean sb) {
        BorderPane victoryPane1 = new BorderPane();
        victoryPane1.setStyle("-fx-font: 16 Optima;\n" + 
                "-fx-background-color: white;");
        Text text1 = new Text();
        text1.textProperty().bind(Bindings.format("%s et %s ont gagne avec %d points contre %d .",
                playerNames.get(PlayerId.PLAYER_1) , playerNames.get(PlayerId.PLAYER_3) , 
                sb.totalPointsProperty(TeamId.TEAM_1) , sb.totalPointsProperty(TeamId.TEAM_2) ));
        victoryPane1.setCenter(text1);
        victoryPane1.visibleProperty().bind(sb.winningTeamProperty().isEqualTo(TeamId.TEAM_1));
        return victoryPane1;
    }

    //PANNEAU DE VICTOIRE DE L'EQUIPE 2
    private BorderPane createVictoryPane2(ScoreBean sb) {
        BorderPane victoryPane2 = new BorderPane();
        victoryPane2.setStyle("-fx-font: 16 Optima;\n" + 
                "-fx-background-color: white;");
        Text text2 = new Text();
        text2.textProperty().bind(Bindings.format("%s et %s ont gagne avec %d points contre %d .",
                playerNames.get(PlayerId.PLAYER_2) , playerNames.get(PlayerId.PLAYER_4) , 
                sb.totalPointsProperty(TeamId.TEAM_2) , sb.totalPointsProperty(TeamId.TEAM_1) ));
        victoryPane2.setCenter(text2);
        victoryPane2.visibleProperty().bind(sb.winningTeamProperty().isEqualTo(TeamId.TEAM_2));
        return victoryPane2;
    }

    //retourne un halo crée
    private Rectangle getHalo() {
        Rectangle halo = new Rectangle(120,180);
        halo.setStyle("-fx-arc-width: 20;\n" + 
                "-fx-arc-height: 20;\n" + 
                "-fx-fill: transparent;\n" + 
                "-fx-stroke: lightpink;\n" + 
                "-fx-stroke-width: 5;\n" + 
                "-fx-opacity: 0.5;");
        halo.setEffect(new GaussianBlur(4));
        return halo;
    }

    //Associe chaque carte a son image
    private ObservableMap<Card,Image  > getMapCard(){
        ObservableMap<Card , Image> map = FXCollections.observableHashMap();
        for (int i=0; i<9;i++) {
            for (int j=0; j<4;j++) {
                Card c=Card.of(Color.values()[j], Rank.values()[i]);
                map.put(c,new Image(getCardRef(c))  ); 
            }
        }
        return map;
    }

    //Associe chaque couleur atout a son image
    private ObservableMap<Color , Image> getMapTrump(){
        ObservableMap<Color , Image> mapTrump = FXCollections.observableHashMap();
        for (int i=0 ; i<4 ; i++) {
            mapTrump.put(Color.values()[i], new Image(getColorRef(Color.values()[i])));
        }
        return mapTrump;
    }

    //retourne le string correspondant à l'URL de la carte donnée
    private String getCardRef(Card card) {
        return "/card_"+card.color().ordinal()+"_"+card.rank().ordinal()+"_"+"240.png"; 
    }
    //retourne le string correspondant à l'URL de la couleur donnée
    private String getColorRef(Color color) {
        return "/trump_"+color.ordinal()+".png";
    }
}