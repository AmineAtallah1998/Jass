package ch.epfl.javass.gui;

import ch.epfl.javass.jass.TeamId;
import ch.epfl.test.TestRandomizer;
import org.junit.jupiter.api.Test;

import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ScoreBeanTest {

    @Test
    public void initWorks(){
        ScoreBean bean = new ScoreBean();
        assertEquals(0, bean.turnPointsProperty(TeamId.TEAM_1).get());
        assertEquals(0, bean.turnPointsProperty(TeamId.TEAM_2).get());
        assertEquals(0, bean.gamePointsProperty(TeamId.TEAM_1).get());
        assertEquals(0, bean.gamePointsProperty(TeamId.TEAM_2).get());
        assertEquals(0, bean.totalPointsProperty(TeamId.TEAM_1).get());
        assertEquals(0, bean.totalPointsProperty(TeamId.TEAM_2).get());
        assertNull(bean.winningTeamProperty().get());
    }

    @Test
    public void turnWorks(){
        ScoreBean bean = new ScoreBean();
        SplittableRandom rand = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
            TeamId team = TeamId.ALL.get(rand.nextInt(TeamId.COUNT));
            int points = rand.nextInt();
            bean.setTurnPoints(team, points);
            assertEquals(points, bean.turnPointsProperty(team).get());
        }
    }

    @Test
    public void gameWorks(){
        ScoreBean bean = new ScoreBean();
        SplittableRandom rand = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
            TeamId team = TeamId.ALL.get(rand.nextInt(TeamId.COUNT));
            int points = rand.nextInt();
            bean.setGamePoints(team, points);
            assertEquals(points, bean.gamePointsProperty(team).get());
        }
    }

    @Test
    public void totalWorks(){
        ScoreBean bean = new ScoreBean();
        SplittableRandom rand = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
            TeamId team = TeamId.ALL.get(rand.nextInt(TeamId.COUNT));
            int points = rand.nextInt();
            bean.setTotalPoints(team, points);
            assertEquals(points, bean.totalPointsProperty(team).get());
        }
    }

    @Test
    public void winningTeamWorks(){
        ScoreBean bean = new ScoreBean();
        SplittableRandom rand = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
            TeamId team = TeamId.ALL.get(rand.nextInt(TeamId.COUNT));
            bean.setWinningTeam(team);
            assertEquals(team, bean.winningTeamProperty().get());
        }
    }
}
