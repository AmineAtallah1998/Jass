package ch.epfl.javass.gui;

import java.util.Map;
import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.Card.Rank;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.TeamId;
import ch.epfl.javass.jass.Trick;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private Stage stage;
    private Scene scene;
    private final ObservableMap<Card , Image> observableMap = getMap();
    
    /**
     * @return le mapping entre chaque 
     */
    private ObservableMap<Card,Image  > getMap(){
        ObservableMap<Card , Image> map = FXCollections.observableHashMap();
        for (int i=0; i<9;i++) {
            for (int j=0; j<4;j++) {
                Card c=Card.of(Color.values()[j], Rank.values()[i]);
                map.put(c,new Image(getCardRef(c))  ); 
            }
        }
        return map;
    }
    

    /**
     * @param player
     * @param playerNames
     * @param beanScore
     * @param beanTrick
     * crée les panneaux et les imbirque les uns aux autres
     */
    public GraphicalPlayer(PlayerId player, Map<PlayerId , String> playerNames,
            ScoreBean beanScore, TrickBean beanTrick) {
        this.playerNames = playerNames;
        this.player=player;

        BorderPane pane= new BorderPane();
        pane.setTop(createScorePane(beanScore));
        pane.setCenter(createTrickPane(beanTrick));
        scene= new Scene(pane);
    }

    /**
     * @return le stage principale du stage
     */
    public Stage createStage() {
        stage = new Stage();
        stage.setTitle("Javass - "+playerNames.get(player));
        stage.setScene(scene);
        return stage;
    }

    /**
     * @param sb
     * @return le panneau du score
     */
    private GridPane createScorePane(ScoreBean sb) {
        GridPane scorePane = new GridPane();
        scorePane.setStyle("-fx-font: 16 Optima;-fx-background-color: lightgray;-fx-padding: 5px;-fx-alignment: center;");
            Text names = new Text(playerNames.get(PlayerId.values()[0])+" et "+playerNames.get(PlayerId.values()[2])+" : " );
            names.setTextAlignment(TextAlignment.RIGHT);
            scorePane.add(names, 0, 0); //NAMES ADDED
           // Text turnScore =new Text(Bindings.convert(sb.turnPointsProperty(TeamId.values()[i])).get());
            Text turnScore= new Text();
            turnScore.textProperty().bind(Bindings.convert(sb.turnPointsProperty(TeamId.TEAM_1)));
            turnScore.setTextAlignment(TextAlignment.RIGHT);
            scorePane.add(turnScore, 1, 0); //CURRENT TURNSCORES ADDED

            Text stringTotal = new Text(" /Total :");
            stringTotal.setTextAlignment(TextAlignment.LEFT);
            scorePane.add(stringTotal, 3, 0); //AJOUT DE TOTAL

            Text gamePoints = new Text();
            gamePoints.textProperty().bind( Bindings.convert(sb.gamePointsProperty(TeamId.TEAM_1)));
            gamePoints.setTextAlignment(TextAlignment.RIGHT);
            scorePane.add(gamePoints, 4, 0); //AJOUT DE TOTAL DES POINTS
            
            
            
            Text names1 = new Text(playerNames.get(PlayerId.values()[1])+" et "+playerNames.get(PlayerId.values()[3])+" : " );
            names1.setTextAlignment(TextAlignment.RIGHT);
            scorePane.add(names1, 0, 1); //NAMES ADDED
           // Text turnScore =new Text(Bindings.convert(sb.turnPointsProperty(TeamId.values()[i])).get());
            Text turnScore1= new Text();
            turnScore1.textProperty().bind(Bindings.convert(sb.gamePointsProperty(TeamId.TEAM_2)));
            turnScore1.setTextAlignment(TextAlignment.RIGHT);
            scorePane.add(turnScore1, 1, 1); //CURRENT TURNSCORES ADDED
            Text stringTotal1= new Text(" /Total :");

            stringTotal1.setTextAlignment(TextAlignment.LEFT);
            scorePane.add(stringTotal1, 3, 1); //AJOUT DE TOTAL

            Text gamePoints1 = new Text();
            gamePoints1.textProperty().bind( Bindings.convert(sb.gamePointsProperty(TeamId.TEAM_2)));

            gamePoints1.setTextAlignment(TextAlignment.RIGHT);
            scorePane.add(gamePoints1, 4, 1); //AJOUT DE TOTAL DES POINTS

            //RESTE LA DIFFERENCE

        
        return scorePane;
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
        ImageView imageTrump;
        
        if(tb.trumpProperty().getValue()!=null) {
            imageTrump = new ImageView();
            ObjectProperty<Image> wImageTrump= new SimpleObjectProperty( new Image (getColorRef(tb.trumpProperty().getValue())));
            imageTrump.imageProperty().bind(wImageTrump);
            
            imageTrump.setFitWidth(101);
            imageTrump.setFitHeight(101);
            trickPane.add(imageTrump, 1, 1);
            GridPane.setHalignment(imageTrump, HPos.CENTER);
        }
        
        //HALO
        Rectangle halo = getHalo();
        Rectangle halo2 = getHalo();
        Rectangle halo3 = getHalo();
        Rectangle halo4 = getHalo();
        //HALO
        
        Text leftCard = new Text();
        leftCard.setStyle("-fx-font: 14 Optima;");
        PlayerId p = player.team().equals(TeamId.TEAM_1) ? PlayerId.PLAYER_2 : PlayerId.PLAYER_1;
        leftCard = new Text(playerNames.get(p));
        ImageView imageCardLeft = new ImageView();
        if(tb.trick().get(p)!=null) {
            ObjectProperty<Image> wImageCardLeft= new SimpleObjectProperty( new Image (getCardRef(tb.trick().get(p))));
            imageCardLeft.imageProperty().bind(wImageCardLeft);
            }
        imageCardLeft.setFitWidth(120);
        imageCardLeft.setFitHeight(180);
        
        StackPane paneStack = new StackPane();
        halo.visibleProperty().bind(tb.winningPlayerProperty().isEqualTo(p));
        paneStack.getChildren().addAll(halo, imageCardLeft);
        VBox nodeCardName = new VBox(leftCard , paneStack);
        
        nodeCardName.setAlignment(Pos.CENTER);
        trickPane.add(nodeCardName, 0 , 1); //THE FIRST COLUMN OF THE TRICK PANNEL

        Text rightC = new Text();
        rightC.setStyle("-fx-font: 14 Optima;");
        PlayerId p2 = player.team().equals(TeamId.TEAM_1) ? PlayerId.PLAYER_4 : PlayerId.PLAYER_3;
        rightC = new Text(playerNames.get(p2));
        ImageView imageRight = new ImageView();
        if(tb.trick().get(p2)!=null) {
            ObjectProperty<Image> wImageCenter= new SimpleObjectProperty( new Image (getCardRef(tb.trick().get(p2))));
            imageCardLeft.imageProperty().bind(wImageCenter);
            }
       
        imageRight.setFitWidth(120);
        imageRight.setFitHeight(180);
        StackPane paneStack2 = new StackPane();
        halo2.visibleProperty().bind(tb.winningPlayerProperty().isEqualTo(p2));
        paneStack2.getChildren().addAll(halo2, imageRight);
        VBox nodeCardName2 = new VBox(rightC , paneStack2);
        nodeCardName2.setAlignment(Pos.CENTER);
        trickPane.add(nodeCardName2, 2 , 1); //THE THIRD COLUMN OF THE TRICK PANNEL

        Text upMiddle = new Text();
        upMiddle.setStyle("-fx-font: 14 Optima;");
        PlayerId p3 = player.team().equals(TeamId.TEAM_1) ? PlayerId.PLAYER_3 : PlayerId.PLAYER_4;
        upMiddle = new Text(playerNames.get(p3));
        ImageView  upMiddleImage = new ImageView();;
        if(tb.trick().get(p3)!=null) {
            ObjectProperty<Image> wUpMiddle= new SimpleObjectProperty( new Image (getCardRef(tb.trick().get(p3))));
            upMiddleImage.imageProperty().bind(wUpMiddle);

        }
        upMiddleImage.setFitWidth(120);
        upMiddleImage.setFitHeight(180);
        StackPane paneStack3 = new StackPane();
        halo3.visibleProperty().bind(tb.winningPlayerProperty().isEqualTo(p3));
        paneStack3.getChildren().addAll(halo3, upMiddle);
        VBox nodeCardName3 = new VBox(upMiddle , paneStack3);
        nodeCardName3.setAlignment(Pos.CENTER);
        trickPane.add(nodeCardName3,1,0); //THE SECOND COLUMN OF THE TRICK PANNEL PLAYER EN HAUT

        Text downMiddle = new Text(playerNames.get(player));
        downMiddle.setStyle("-fx-font: 14 Optima;");
        ImageView downMiddleImage=new ImageView();
        if(tb.trick().get(player)!=null) {
            ObjectProperty<Image> wDownMiddleImage= new SimpleObjectProperty( new Image (getCardRef(tb.trick().get(player))));
            downMiddleImage.imageProperty().bind(wDownMiddleImage);

        }
        downMiddleImage.setFitWidth(120);
        downMiddleImage.setFitHeight(180);
        StackPane paneStack4 = new StackPane();
        halo4.visibleProperty().bind(tb.winningPlayerProperty().isEqualTo(player));
        paneStack4.getChildren().addAll(halo4, downMiddleImage);
        VBox nodeCardName4 = new VBox(downMiddle , paneStack4);
        nodeCardName4.setAlignment(Pos.BOTTOM_CENTER);

        trickPane.add(nodeCardName4,1,2);// NODE OF OUR PLAYER

        
        return trickPane;
    }

    // les 2 panneaux de victoire
    private BorderPane createVictoryPane1(ScoreBean sb) {
        BorderPane victoryPane1 = new BorderPane();
        victoryPane1.setStyle("-fx-font: 16 Optima;\n" + 
                "-fx-background-color: white;");
        Text text1 = new Text(Bindings.format("%s et %s ont gagne avec %d points contre %d .",
                playerNames.get(PlayerId.PLAYER_1) , playerNames.get(PlayerId.PLAYER_3) , 
                sb.totalPointsProperty(TeamId.TEAM_1) , sb.totalPointsProperty(TeamId.TEAM_2) ).get());
        victoryPane1.setCenter(text1);
        victoryPane1.visibleProperty().bind(sb.winningTeamProperty().isEqualTo(TeamId.TEAM_1));
        return victoryPane1;
    }

    private BorderPane createVictoryPane2(ScoreBean sb) {
        BorderPane victoryPane2 = new BorderPane();
        victoryPane2.setStyle("-fx-font: 16 Optima;\n" + 
                "-fx-background-color: white;");
        Text text2 = new Text(Bindings.format("%s et %s ont gagne avec %d points contre %d .",
                playerNames.get(PlayerId.PLAYER_2) , playerNames.get(PlayerId.PLAYER_4) , 
                sb.totalPointsProperty(TeamId.TEAM_2) , sb.totalPointsProperty(TeamId.TEAM_1) ).get());
        victoryPane2.setCenter(text2);
        victoryPane2.visibleProperty().bind(sb.winningTeamProperty().isEqualTo(TeamId.TEAM_2));
        return victoryPane2;
    }


    /**
     * @param card
     * @return le string corrependant au URL de la card
     */
    private String getCardRef(Card card) {
        System.out.println(card);
        return "/card_"+card.color().ordinal()+"_"+card.rank().ordinal()+"_"+"240.png"; 
    }
    /**
     * @param color
     * @return le string corrependant au URL de la couleur
     */
    private String getColorRef(Color color) {
        return "/trump_"+color.ordinal()+".png";
    }
    
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
}





