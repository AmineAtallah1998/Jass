package ch.epfl.javass;

/**
 * @author Mohamed Ali Dhraief & Amine Atallah 
 *
 */
public final class Preconditions {
    
    private Preconditions() {}
    
    /**
     * @param b
     */
    public static void checkArgument(boolean b) {
        if(b==false) {
            throw new IllegalArgumentException();
        }
        
    }
    
    
    /**
     * @param index
     * @param size
     * @return
     */
    public static int checkIndex(int index , int size) {
        if(index<0 || index>=size) {
            throw new IndexOutOfBoundsException();
        }
        
        return index;
        
    }

}
