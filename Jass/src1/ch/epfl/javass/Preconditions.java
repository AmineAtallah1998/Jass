package ch.epfl.javass;

public final class Preconditions {
    
    private Preconditions() {}
    
    public static void checkArgument(boolean b) {
        if(b==false) {
            throw new IllegalArgumentException();
        }
        
    }
    
    public static int checkIndex(int index , int size) {
        if(index<0 || index>=size) {
            throw new IndexOutOfBoundsException();
        }
        
        return index;
        
    }

}
