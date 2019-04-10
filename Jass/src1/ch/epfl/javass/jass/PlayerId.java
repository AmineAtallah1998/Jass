package ch.epfl.javass.jass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.epfl.javass.jass.TeamId;

public enum  PlayerId {
    PLAYER_1, PLAYER_2,    PLAYER_3,    PLAYER_4;
    public static final List<PlayerId> ALL =Collections.unmodifiableList(Arrays.asList(values()));
    public static final int COUNT =4;
    public TeamId team() {
        switch (this)
        {
        case PLAYER_1 : return TeamId.TEAM_1;
        case PLAYER_2 : return TeamId.TEAM_2;
        case PLAYER_3 :return TeamId.TEAM_1;
        default : return TeamId.TEAM_2 ;
        }
        
    }
    
}


