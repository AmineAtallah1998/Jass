package ch.epfl.javass.jass;

import ch.epfl.javass.bits.Bits64;

public final class PackedScore {
    private PackedScore() { }
    public static final long  INITIAL= pack(0,0,0,0,0,0);
    
    /* vrai si  valeur donnée est un score empaqueté valid
     * i les six composantes contiennent des valeurs comprises
     *  dans les bornes données plus haut,
     *  et les bits inutilisés valent tous 0,
     */
    public static boolean  isValid (long Score) {
        return Bits64.extract(Score, 0, 4)>=0 && Bits64.extract(Score, 0, 4)<=9 
                && Bits64.extract(Score, 4, 9)>=0 && Bits64.extract(Score, 4, 9)<=257
                && Bits64.extract(Score, 13, 11)>=0 && Bits64.extract(Score, 13, 11)<=2000
                && Bits64.extract(Score, 24, 8)==0 
                && Bits64.extract(Score, 32, 4)>=0 && Bits64.extract(Score, 32, 4)<=9
                && Bits64.extract(Score, 36, 9)>=0 && Bits64.extract(Score, 36, 9)<=257
                && Bits64.extract(Score, 45, 11)>=0 && Bits64.extract(Score, 45, 11)<=2000
                && Bits64.extract(Score, 56, 8)==0;
    }
    /* empaquète les six composantes des scores dans un entier de type long
     *  turnTricks1 étant le nombre de plis 
     *  remportés par l'équipe 1 dans le tour courant
     */
    public static long pack(int turnTricks1, int turnPoints1, int gamePoints1,
            int turnTricks2, int turnPoints2, int gamePoints2)
    {
      assert  ( (turnTricks1>=0 && turnTricks1<=9 && turnTricks2>=0 && turnTricks2<=9 
               && turnPoints1>=0 && turnPoints2<=257 && turnPoints2>=0 && turnPoints2<=257
               && gamePoints1>=0 && gamePoints1<=2000 && gamePoints2>=0 && gamePoints2<=2000));
           
       
        
        
        return Bits64.pack(Bits64.pack(Bits64.pack(turnTricks1, 4, turnPoints1, 9),13, gamePoints1, 11 ),32,
                Bits64.pack(Bits64.pack(turnTricks2, 4, turnPoints2, 9),13, gamePoints2, 11),32 );
    }
    // retourne le nombre de plis remportés par l'équipe 
    //donnée dans le tour courant des scores empaquetés donnés,
    public static int turnTricks(long pkScore, TeamId t) {
        assert isValid(pkScore);
        
        if(t==TeamId.TEAM_1) {
            return (int)Bits64.extract(pkScore, 0, 4);
        }
        
        return (int)Bits64.extract(pkScore, 32, 4);
    } 
    /*retourne le nombre de points remportés par l'équipe
     *  donnée dans le tour courant des scores empaquetés donnés,
     *  */
     
    public static int turnPoints(long pkScore, TeamId t) {
        assert isValid(pkScore);
        if(t==TeamId.TEAM_1) {
            return (int)Bits64.extract(pkScore, 4, 9);
        }
        
        return (int)Bits64.extract(pkScore, 36, 9);
    }
    /*
     * retourne le nombre de points reportés par 
     * l'équipe donnée dans les tours précédents 
     * (sans inclure le tour courant) des scores empaquetés donnés,
     */
    public static int gamePoints(long pkScore, TeamId t) {
        assert isValid(pkScore);
        if(t==TeamId.TEAM_1) {
            return (int)Bits64.extract(pkScore, 13, 11);
        }
        
        return (int)Bits64.extract(pkScore, 45, 11);
    }
    /* retourne le nombre total de points remportés par l'équipe 
     * donnée dans la partie courante des scores
     *  empaquetés donnés, c-à-d la somme des points remportés dans les tours précédents et ceux remportés dans le tour courant,
     */
    public static int totalPoints(long pkScore, TeamId t) {
        assert isValid(pkScore);
        return gamePoints(pkScore,t)+turnPoints(pkScore,t); 
    }
    /*retourne les scores empaquetés donnés mis à jour pour
     *  tenir compte du fait que l'équipe winningTeam a remporté un pli valant
     *   trickPoints points ; notez que seuls le nombre de plis et le nombre de points du tour courant doivent être 
     *   mis à jour, et que si cette mise à jour fait que l'équipe gagnante a remporté tous les plis du tour, alors son score
     *    doit être augmenté de 100 points additionels étant donné qu'elle a fait match (voir la constante MATCH_ADDITIONAL_POINTS 
     *    définie dans l'étape précédente) ; par contre, les 5 points attribués au dernier pli ne doivent 
     * pas être gérés par cette méthode, ils seront gérés ailleurs,
     */
    public static long withAdditionalTrick(long pkScore, TeamId winningTeam, int trickPoints) {
 
        if(winningTeam==TeamId.TEAM_1) {
            if(turnTricks(pkScore,winningTeam)==8) {
            return pack(turnTricks(pkScore,TeamId.TEAM_1)+1 , turnPoints(pkScore,TeamId.TEAM_1)+trickPoints+Jass.MATCH_ADDITIONAL_POINTS ,gamePoints(pkScore, TeamId.TEAM_1),
                      
                    turnTricks(pkScore,TeamId.TEAM_2) ,turnPoints(pkScore,TeamId.TEAM_2) ,gamePoints(pkScore, TeamId.TEAM_2));
            } 
            
            return pack(turnTricks(pkScore,TeamId.TEAM_1)+1 , turnPoints(pkScore,TeamId.TEAM_1)+trickPoints ,gamePoints(pkScore, TeamId.TEAM_1),
                    
                    turnTricks(pkScore,TeamId.TEAM_2) ,turnPoints(pkScore,TeamId.TEAM_2) ,gamePoints(pkScore, TeamId.TEAM_2));
            
            }
        
        if(turnTricks(pkScore,winningTeam)==8) {
            return pack(turnTricks(pkScore,TeamId.TEAM_1) , turnPoints(pkScore,TeamId.TEAM_1) ,gamePoints(pkScore, TeamId.TEAM_1),
                    
                    turnTricks(pkScore,TeamId.TEAM_2)+1 ,turnPoints(pkScore,TeamId.TEAM_2)+trickPoints+Jass.MATCH_ADDITIONAL_POINTS ,gamePoints(pkScore, TeamId.TEAM_2));
            
        }
        
        
        
        return pack(turnTricks(pkScore,TeamId.TEAM_1) , turnPoints(pkScore,TeamId.TEAM_1) ,gamePoints(pkScore, TeamId.TEAM_1),
                
                turnTricks(pkScore,TeamId.TEAM_2)+1 ,turnPoints(pkScore,TeamId.TEAM_2)+trickPoints ,gamePoints(pkScore, TeamId.TEAM_2));
        
        
        
    }
    /*
     * retourne les scores empaquetés donnés mis à jour pour le tour prochain, c-à-d avec les points
     *  obtenus par chaque équipe dans le tour courant ajoutés à leur nombre de points remportés lors de la partie,
     *   et les deux autres composantes remises à 0,
     */
    public static long nextTurn(long pkScore) {
        assert isValid(pkScore);
        return pack(0,0,(int)(Bits64.extract(pkScore, 4, 9)+Bits64.extract(pkScore, 13, 11)) , 0 ,0,
                (int)(Bits64.extract(pkScore, 36, 9)+Bits64.extract(pkScore, 45, 11)) );
    }
    /*retourne la représentation textuelle des scores, que vous êtes libres de choisir, cette méthode servant uniquement 
     * au déboguage ; néanmoins, la méthode toString doit au moins inclure le nombre de points total de chacune 
     * des deux équipes, p.ex. séparés par une barre oblique.
     * */
     

    
    public static String toString(long pkScore) {
        return"(" + turnTricks( pkScore, TeamId.TEAM_1) + ","+ turnPoints(pkScore, TeamId.TEAM_1) + ","+
                gamePoints(pkScore, TeamId.TEAM_1) + ")/("+  turnTricks( pkScore, TeamId.TEAM_2) + ","+ 
                turnPoints(pkScore, TeamId.TEAM_2) + ","+ gamePoints(pkScore, TeamId.TEAM_2) + ")" ;
    }
    
    
    
    

}


