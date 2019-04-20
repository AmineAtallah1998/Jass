package ch.epfl.javass.gui;

import java.util.Map;
import ch.epfl.javass.jass.Card;
import ch.epfl.javass.jass.Card.Color;
import ch.epfl.javass.jass.PlayerId;
import ch.epfl.javass.jass.TeamId;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GraphicalPlayer {
    private Map<PlayerId , String> playerNames;
    private PlayerId player;
    private Stage stage;
    private Scene scene;
    // Le constructeur de GraphicalPlayer les appelle(les panneaux) et construit
    // le panneau principal en les combinant comme il se doit
    public GraphicalPlayer(PlayerId player, Map<PlayerId , String> playerNames,
            ScoreBean beanScore, TrickBean beanTrick) {
        this.playerNames = playerNames;
        this.player=player;

        //HAVE TO ADD THE VICTORY PANES
        BorderPane pane= new BorderPane();
        pane.setTop(createScorePane(beanScore));
        pane.setCenter( createTrickPane(beanTrick));
        scene= new Scene(pane);

    }

    public Stage createStage() {
        stage = new Stage();
        stage.setTitle("Javass");
        stage.setScene(scene);
        return stage;

    }

    // Panneau des scores
    private GridPane createScorePane(ScoreBean sb) {
        GridPane scorePane = new GridPane();
        scorePane.setStyle("-fx-font: 16 Optima;-fx-background-color: lightgray;-fx-padding: 5px;-fx-alignment: center;");
        for (int i=0 ; i<2 ; i++) {
            Text names = new Text(playerNames.get(PlayerId.values()[i])+" et "+playerNames.get(PlayerId.values()[i+2])+" : " );
            names.setTextAlignment(TextAlignment.RIGHT);
            scorePane.add(names, 0, i); //NAMES ADDED
            Text turnScore =new Text(Bindings.convert(sb.turnPointsProperty(TeamId.values()[i])).get());
            turnScore.setTextAlignment(TextAlignment.RIGHT);
            scorePane.add(turnScore, 1, i); //CURRENT TURNSCORES ADDED

            Text stringTotal = new Text(" /Total :");
            stringTotal.setTextAlignment(TextAlignment.LEFT);
            scorePane.add(stringTotal, 3, i); //AJOUT DE TOTAL

            Text gamePoints = new Text(Bindings.convert(sb.gamePointsProperty(TeamId.values()[i])).get());
            gamePoints.setTextAlignment(TextAlignment.RIGHT);
            scorePane.add(gamePoints, 4, i); //AJOUT DE TOTAL DES POINTS

            //RESTE LA DIFFERENCE

        }
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
        ImageView imageTrump;
        if(tb.trump()!=null) {
            imageTrump = new ImageView(new Image(getColorRef(tb.trump())));
        }else {
            imageTrump = new ImageView();  
        }
        imageTrump.setFitWidth(101);
        imageTrump.setFitHeight(101);
        trickPane.add(imageTrump, 1, 1);


        Text text = new Text();
        text.setStyle("-fx-font: 14 Optima;");
        PlayerId p = player.team().equals(TeamId.TEAM_1) ? PlayerId.PLAYER_2 : PlayerId.PLAYER_1;
        ImageView image;
        if(tb.trick().get(p)!=null) {
            image = new ImageView(new Image(getCardRef(tb.trick().get(p))));
        }else {
            image = new ImageView();
        }
        image.setFitWidth(120);
        image.setFitHeight(180);
        VBox nodeCardName = new VBox(text , image);
        nodeCardName.setAlignment(Pos.CENTER);
        trickPane.addColumn(0, nodeCardName); //THE FIRST COLUMN OF THE TRICK PANNEL

        Text text2 = new Text();
        text2.setStyle("-fx-font: 14 Optima;");
        PlayerId p2 = player.team().equals(TeamId.TEAM_1) ? PlayerId.PLAYER_4 : PlayerId.PLAYER_3;
        ImageView image2;
        if(tb.trick().get(p2)!=null) {
            image2 = new ImageView(new Image(getCardRef(tb.trick().get(p2))));
        }else {
            image2 = new ImageView();
        }
        image2.setFitWidth(120);
        image2.setFitHeight(180);
        VBox nodeCardName2 = new VBox(text2 , image2);
        nodeCardName2.setAlignment(Pos.CENTER);
        trickPane.addColumn(2, nodeCardName2); //THE THIRD COLUMN OF THE TRICK PANNEL

        Text text3 = new Text();
        text3.setStyle("-fx-font: 14 Optima;");
        PlayerId p3 = player.team().equals(TeamId.TEAM_1) ? PlayerId.PLAYER_3 : PlayerId.PLAYER_4;
        ImageView image3;
        if(tb.trick().get(p3)!=null) {
            image3 = new ImageView(new Image(getCardRef(tb.trick().get(p3))));
        }else {
            image3 = new ImageView();
        }
        image3.setFitWidth(120);
        image3.setFitHeight(180);
        VBox nodeCardName3 = new VBox(text3 , image3);
        trickPane.add(nodeCardName3,1,0); //THE SECOND COLUMN OF THE TRICK PANNEL PLAYER EN HAUT

        Text text4 = new Text();
        text4.setStyle("-fx-font: 14 Optima;");
        ImageView image4;
        if(tb.trick().get(player)!=null) {
            image4 = new ImageView(new Image(getCardRef(tb.trick().get(player))));
        }else {
            image4 = new ImageView();
        }
        image4.setFitWidth(120);
        image4.setFitHeight(180);
        VBox nodeCardName4 = new VBox(text4 , image4);
        trickPane.add(nodeCardName4,1,2);// NODE OF OUR PLAYER



        /*  Rectangle halo = new Rectangle(120,180);
        StackPane haloPane = new StackPane();
        haloPane.setStyle("-fx-arc-width: 20;\n" + 
                "-fx-arc-height: 20;\n" + 
                "-fx-fill: transparent;\n" + 
                "-fx-stroke: lightpink;\n" + 
                "-fx-stroke-width: 5;\n" + 
                "-fx-opacity: 0.5;");
        haloPane.setEffect(new GaussianBlur(4));   */ //THE HALO

        return trickPane;
    }

    // les 2 panneaux de victoire
    private BorderPane createVictoryPane1(ScoreBean sb) {
        BorderPane victoryPane1 = new BorderPane();
        victoryPane1.setStyle("-fx-font: 16 Optima;\n" + 
                "-fx-background-color: white;");
        //USE BINDING FORMAT
        Text text1 = new Text(playerNames.get(PlayerId.PLAYER_1)+" et "+playerNames.get(PlayerId.PLAYER_3)
        +" ont gagné avec "+sb.totalPointsProperty(TeamId.TEAM_1).toString()+" points contre "+
        sb.totalPointsProperty(TeamId.TEAM_2).toString());

        victoryPane1.setCenter(text1);
        victoryPane1.setVisible(sb.winningTeamProperty().getValue().equals(TeamId.TEAM_1));
        return victoryPane1;
    }

    private BorderPane createVictoryPane2(ScoreBean sb) {
        BorderPane victoryPane2 = new BorderPane();
        victoryPane2.setStyle("-fx-font: 16 Optima;\n" + 
                "-fx-background-color: white;");

        //USE BINDING FORMAT
        Text text2 = new Text(playerNames.get(PlayerId.PLAYER_2)+" et "+playerNames.get(PlayerId.PLAYER_4)
        +" ont gagné avec "+sb.totalPointsProperty(TeamId.TEAM_2).toString()+" points contre "+
        sb.totalPointsProperty(TeamId.TEAM_1).toString());
        victoryPane2.setCenter(text2);
        victoryPane2.setVisible(sb.winningTeamProperty().getValue().equals(TeamId.TEAM_2));
        return victoryPane2;
    }


    private String getCardRef(Card card) {
        System.out.println(card);
        return "/card_"+card.color().ordinal()+"_"+card.rank().ordinal()+"_"+"240.png"; 
    }
    private String getColorRef(Color color) {
        System.out.println(color);
        return "/trump_"+color.ordinal()+".png";
    }
}
