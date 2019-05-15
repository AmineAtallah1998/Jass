package ch.epfl.javass.gui;

import java.util.HashMap;
import java.util.Map;
import ch.epfl.javass.jass.TeamId;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
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
    
    private Map<TeamId , IntegerProperty> turnPointsProperty = getMap();
    private Map<TeamId , IntegerProperty> gamePointsProperty = getMap();
    private Map<TeamId , IntegerProperty> totalPointsProperty = getMap();
    private ObjectProperty<TeamId> winningTeamProperty = new SimpleObjectProperty<>();
    
    /**
     * @param team
     *            : équipe
     * @return le nombre de points du tour sous forme de propriété
     */
    public ReadOnlyIntegerProperty turnPointsProperty(TeamId team) {
        return turnPointsProperty.get(team);
    }
    /**
     * @param team
     * @return le nombre de points du jeu sous forme de propriété
     */
    public ReadOnlyIntegerProperty gamePointsProperty(TeamId team) {
        return gamePointsProperty.get(team);
    }
    /**
     * @param team
     * @return le nombre de points total sous forme de propriété
     */
    public ReadOnlyIntegerProperty totalPointsProperty(TeamId team) {
        return totalPointsProperty.get(team);
    }
    /**
     * @param team
     * @param newTurnPoints
     *            affecte newTurnPoints au nombre de points du tour de l'équipe
     *            correspendante
     */
    public void setTurnPoints(TeamId team, int newTurnPoints) {
        turnPointsProperty.get(team).set(newTurnPoints);
        turnPointsProperty.put(team, turnPointsProperty.get(team));
    }
    /**
     * @param team
     * @param newGamePoints
     *            affecte newGamePoints au nombre de points du jeu de l'équipe
     *            correspendante
     */
    public void setGamePoints(TeamId team, int newGamePoints) {
        gamePointsProperty.get(team).set(newGamePoints);
        gamePointsProperty.put(team, gamePointsProperty.get(team));
    }
    /**
     * @param team
     * @param newTotalPoints
     *            affecte newTotalPoints au nombre de points point de l'équipe
     *            correspendante
     */
    public void setTotalPoints(TeamId team, int newTotalPoints) {
        totalPointsProperty.get(team).set(newTotalPoints);
        totalPointsProperty.put(team, totalPointsProperty.get(team));
    }
    /**
     * @return la team gagnante sous format de propriété
     */
    public ReadOnlyObjectProperty<TeamId> winningTeamProperty() {
        return winningTeamProperty;
    }
    /**
     * @param winningTeam
     *            affete winning team à l'attribut correspendant
     */
    public void setWinningTeam(TeamId winningTeam) {
        winningTeamProperty.set(winningTeam);
    }
    //Initialize la map 
    private  Map<TeamId , IntegerProperty>  getMap () {
        Map<TeamId , IntegerProperty>  res= new HashMap<>();
        for ( int i =0; i<2;i++) {
            res.put(TeamId.values()[i], new SimpleIntegerProperty());
        }
        return res;
    }
}