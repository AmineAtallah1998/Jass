package ch.epfl.javass.gui;

import ch.epfl.javass.jass.TeamId;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Mohamed Ali Dhraief (283509)
 * @author Amine Atallah (284592)
 *
 * 
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
    
    private IntegerProperty turnPointsProperty1 = new SimpleIntegerProperty(
            turnPoints1);
    private IntegerProperty gamePointsProperty1 = new SimpleIntegerProperty(
            gamePoints1);
    private IntegerProperty totalPointsProperty1 = new SimpleIntegerProperty(
            totalPoints1);

    private IntegerProperty turnPointsProperty2 = new SimpleIntegerProperty(
            turnPoints2);
    private IntegerProperty gamePointsProperty2 = new SimpleIntegerProperty(
            gamePoints2);
    private IntegerProperty totalPointsProperty2 = new SimpleIntegerProperty(
            totalPoints2);

    private ObjectProperty<TeamId> winningTeamProperty = new SimpleObjectProperty<>(
            winningTeam);

    /**
     * @param team
     *            : équipe
     * @return le nombre de points du tour sous forme de propriété
     */

    public IntegerProperty turnPointsProperty(TeamId team) {
        return team == TeamId.TEAM_1 ? turnPointsProperty1
                : turnPointsProperty2;
    }

    /**
     * @param team
     * @return le nombre de points du jeu sous forme de propriété
     */
    public IntegerProperty gamePointsProperty(TeamId team) {
        return team == TeamId.TEAM_1 ? gamePointsProperty1
                : gamePointsProperty2;
    }

    /**
     * @param team
     * @return le nombre de points total sous forme de propriété
     */
    public IntegerProperty totalPointsProperty(TeamId team) {
        return team == TeamId.TEAM_1 ? totalPointsProperty1
                : totalPointsProperty2;
    }

    /**
     * @param team
     * @param newTurnPoints
     *            affecte newTurnPoints au nombre de points du tour de l'équipe
     *            correspendante
     */
    public void setTurnPoints(TeamId team, int newTurnPoints) {
        if (team == TeamId.TEAM_1) {
            turnPointsProperty1.set(newTurnPoints);
        } else {
            turnPointsProperty2.set(newTurnPoints);
       }
    }

    /**
     * @param team
     * @param newGamePoints
     *            affecte newGamePoints au nombre de points du jeu de l'équipe
     *            correspendante
     */
    public void setGamePoints(TeamId team, int newGamePoints) {
        if (team == TeamId.TEAM_1) {
            gamePointsProperty1.set(newGamePoints);
        } else {
            gamePointsProperty2.set(newGamePoints);
        }
    }

    /**
     * @param team
     * @param newTotalPoints
     *            affecte newTotalPoints au nombre de points point de l'équipe
     *            correspendante
     */
    public void setTotalPoints(TeamId team, int newTotalPoints) {
        if (team == TeamId.TEAM_1) {
            totalPointsProperty1.set(newTotalPoints);
        } else {
            totalPointsProperty1.set(newTotalPoints);
        }
    }

    /**
     * @return la team gagnante sous format de propriété
     */
    public ObjectProperty<TeamId> winningTeamProperty() {
        return winningTeamProperty;
}

    /**
     * @param winningTeam
     *            affete winning team à l'attribut correspendant
     */
    public void setWinningTeam(TeamId winningTeam) {
        winningTeamProperty.set(winningTeam);
    }
}



