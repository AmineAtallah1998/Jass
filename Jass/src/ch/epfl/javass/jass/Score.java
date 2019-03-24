package ch.epfl.javass.jass;

import ch.epfl.javass.Preconditions;

public final class Score {
    
    private final long pkScore;
    public static Score INITIAL = new Score(0);
    
    private Score(long c) {
        pkScore=c;
    }
    
    public static Score ofPacked(long packed) {
        Preconditions.checkArgument(PackedScore.isValid(packed));
        
        return new Score(packed);
        
        
    }
    
    public long packed() {
        return pkScore;
    }
    
    public int turnTricks(TeamId t) {
        return PackedScore.turnTricks(pkScore, t);
    }
    
    public int turnPoints(TeamId t) {
        return PackedScore.turnPoints(pkScore, t);
    }
    
    public int gamePoints(TeamId t) {
        return PackedScore.gamePoints(pkScore, t);
    }
    
    public int totalPoints(TeamId t) {
        return PackedScore.totalPoints(pkScore, t);
    }
    
    public Score withAdditionalTrick(TeamId winningTeam, int trickPoints) {
        Preconditions.checkArgument(trickPoints>=0);
        
        return new Score(PackedScore.withAdditionalTrick(pkScore, winningTeam, trickPoints));
    }
    
    public Score nextTurn() {
        return new Score(PackedScore.nextTurn(pkScore));
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(pkScore);
    }
    
    @Override
    public String toString() {
        return PackedScore.toString(pkScore);
    }
    
    @Override
    public boolean equals(Object thatO) {
        if(!(thatO instanceof Score)) {
            return false;
        }
        return ((Score)thatO).pkScore == this.pkScore;
    }
    
    
  
    

}


