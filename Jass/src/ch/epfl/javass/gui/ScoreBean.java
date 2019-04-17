package ch.epfl.javass.gui;

import ch.epfl.javass.jass.TeamId;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public final class ScoreBean {
    private int turnPoints1;
    private int gamePoints1;
    private int totalPoints1;
    private int turnPoints2;
    private int gamePoints2;
    private int totalPoints2;
    private TeamId winningTeam;

    ReadOnlyIntegerProperty turnPointsProperty(TeamId team) {
        return team==TeamId.TEAM_1 ? new SimpleIntegerProperty(turnPoints1) : 
            new SimpleIntegerProperty(turnPoints2);
    }
    
    ReadOnlyIntegerProperty gamePointsProperty(TeamId team) {
        return team==TeamId.TEAM_1 ? new SimpleIntegerProperty(gamePoints1) : 
            new SimpleIntegerProperty(gamePoints2);
    }
    ReadOnlyIntegerProperty totalPointsProperty(TeamId team) {
        return team==TeamId.TEAM_1 ? new SimpleIntegerProperty(totalPoints1) : 
            new SimpleIntegerProperty(totalPoints2); 
    }
    
    public void setTurnPoints(TeamId team, int newTurnPoints) {
        if(team==TeamId.TEAM_1) {
            turnPoints1=newTurnPoints;
        }else {
            turnPoints2=newTurnPoints;
        }
    }
    public void setGamePoints(TeamId team, int newGamePoints) {
        if(team==TeamId.TEAM_1) {
            gamePoints1=newGamePoints;
        }else {
            gamePoints2=newGamePoints;
        }
    }
    public void setTotalPoints(TeamId team, int newTotalPoints) {
        if(team==TeamId.TEAM_1) {
            totalPoints1=newTotalPoints;
        }else {
            totalPoints2=newTotalPoints;
        }
    }
    
    
    public ReadOnlyObjectProperty<TeamId> winningTeamProperty() {
        return new SimpleObjectProperty<>(winningTeam);
    }
    public void setWinningTeam(TeamId winningTeam) {
        this.winningTeam=winningTeam;
    }
}
