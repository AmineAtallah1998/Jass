package ch.epfl.javass.gui;

import ch.epfl.javass.jass.TeamId;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Mohamed Ali Dhraief (283509) 
 * @author Amine Atallah (284592)


 *
 */
public final class ScoreBean {
    private int turnPoints1;
    private int gamePoints1;
    private int totalPoints1;
    private int turnPoints2;
    private int gamePoints2;
    private int totalPoints2;
    private TeamId winningTeam;

    /**
     * @param team : équipe 
     * @return le nombre de points du tour sous forme de propriété
     */
    public ReadOnlyIntegerProperty turnPointsProperty(TeamId team) {
        return team==TeamId.TEAM_1 ? new SimpleIntegerProperty(turnPoints1) : 
            new SimpleIntegerProperty(turnPoints2);
    }
    
    /**
     * @param team
     * @return le nombre de points du jeu sous forme de propriété
     */
    public  ReadOnlyIntegerProperty gamePointsProperty(TeamId team) {
        return team==TeamId.TEAM_1 ? new SimpleIntegerProperty(gamePoints1) : 
            new SimpleIntegerProperty(gamePoints2);
    }
    /**
     * @param team
     * @return le nombre de points total  sous forme de propriété
     */
    public ReadOnlyIntegerProperty totalPointsProperty(TeamId team) {
        return team==TeamId.TEAM_1 ? new SimpleIntegerProperty(totalPoints1) : 
            new SimpleIntegerProperty(totalPoints2); 
    }
    
    /**
     * @param team
     * @param newTurnPoints
     * affecte newTurnPoints au nombre de points du tour de l'équipe correspendante 
     */
    public void setTurnPoints(TeamId team, int newTurnPoints) {
        if(team==TeamId.TEAM_1) {
            turnPoints1=newTurnPoints;
        }else {
            turnPoints2=newTurnPoints;
        }
    }
    /**
     * @param team
     * @param newGamePoints
     * affecte newGamePoints  au nombre de points du jeu de l'équipe correspendante 
     */
    public void setGamePoints(TeamId team, int newGamePoints) {
        if(team==TeamId.TEAM_1) {
            gamePoints1=newGamePoints;
        }else {
            gamePoints2=newGamePoints;
        }
    }
    /**
     * @param team
     * @param newTotalPoints
     * affecte newTotalPoints au nombre de points point de l'équipe correspendante 
     */
    public void setTotalPoints(TeamId team, int newTotalPoints) {
        if(team==TeamId.TEAM_1) {
            totalPoints1=newTotalPoints;
        }else {
            totalPoints2=newTotalPoints;
        }
    }
    
    
    /**
     * @return la team gagnante sous format de propriété
     */
    public ReadOnlyObjectProperty<TeamId> winningTeamProperty() {
        return new SimpleObjectProperty<>(winningTeam);
    }
    /**
     * @param winningTeam
     * affete winning team à l'attribut correspendant
     */
    public void setWinningTeam(TeamId winningTeam) {
        this.winningTeam=winningTeam;
    }
}




