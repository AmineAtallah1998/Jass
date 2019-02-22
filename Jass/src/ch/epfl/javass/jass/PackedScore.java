package ch.epfl.javass.jass;

public final class PackedScore {
    private PackedScore() {  }
    public static final long  INITIAL=0;
    /* vrai si  valeur donnée est un score empaqueté valid
     * i les six composantes contiennent des valeurs comprises
     *  dans les bornes données plus haut,
     *  et les bits inutilisés valent tous 0,
     */
    public static boolean  isValid (long Score) {
        return false;
    }
    /* empaquète les six composantes des scores dans un entier de type long
     *  turnTricks1 étant le nombre de plis 
     *  remportés par l'équipe 1 dans le tour couran 
     */
    public static long pack(int turnTricks1, int turnPoints1, int gamePoints1,
            int turnTricks2, int turnPoints2, int gamePoints2) 
    {
        return 0;
    }
    // retourne le nombre de plis remportés par l'équipe 
    //donnée dans le tour courant des scores empaquetés donnés,
    public static int turnTricks(long pkScore, TeamId t) {
        return 0;
    } 
    /*retourne le nombre de points remportés par l'équipe
     *  donnée dans le tour courant des scores empaquetés donnés,
     *  */
     
    public static int turnPoints(long pkScore, TeamId t) {
        return 0;
    }
    /*
     * retourne le nombre de points reportés par 
     * l'équipe donnée dans les tours précédents 
     * (sans inclure le tour courant) des scores empaquetés donnés,
     */
    public static int gamePoints(long pkScore, TeamId t) {
        return 0;
    }
    /* retourne le nombre total de points remportés par l'équipe 
     * donnée dans la partie courante des scores
     *  empaquetés donnés, c-à-d la somme des points remportés dans les tours précédents et ceux remportés dans le tour courant,
     */
    public static int totalPoints(long pkScore, TeamId t) {
        return 0;
    }
    /*etourne les scores empaquetés donnés mis à jour pour
     *  tenir compte du fait que l'équipe winningTeam a remporté un pli valant
     *   trickPoints points ; notez que seuls le nombre de plis et le nombre de points du tour courant doivent être 
     *   mis à jour, et que si cette mise à jour fait que l'équipe gagnante a remporté tous les plis du tour, alors son score
     *    doit être augmenté de 100 points additionels étant donné qu'elle a fait match (voir la constante MATCH_ADDITIONAL_POINTS 
     *    définie dans l'étape précédente) ; par contre, les 5 points attribués au dernier pli ne doivent 
     * pas être gérés par cette méthode, ils seront gérés ailleurs,
     */
    public static long withAdditionalTrick(long pkScore, TeamId winningTeam, int trickPoints) {
        return 0;
    }
    /*
     * retourne les scores empaquetés donnés mis à jour pour le tour prochain, c-à-d avec les points
     *  obtenus par chaque équipe dans le tour courant ajoutés à leur nombre de points remportés lors de la partie,
     *   et les deux autres composantes remises à 0,
     */
    public static long nextTurn(long pkScore) {
        return 0;
    }
    /*retourne la représentation textuelle des scores, que vous êtes libres de choisir, cette méthode servant uniquement 
     * au déboguage ; néanmoins, la méthode toString doit au moins inclure le nombre de points total de chacune 
     * des deux équipes, p.ex. séparés par une barre oblique.
     * */
     
    public static String toString(long pkScore) {
        return "";
    }
    
    
    
    

}
