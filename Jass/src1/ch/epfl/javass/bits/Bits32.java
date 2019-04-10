/**
 * 
 */
package ch.epfl.javass.bits;

import ch.epfl.javass.Preconditions;

/**
 * @author Mohamed Ali
 *
 */
public final class Bits32 {
    private Bits32() {
    }

    public static int mask(int start, int size) {
        Preconditions.checkArgument(start>=0 && size>=0 && start+size<=Integer.SIZE);
       
        if(size==Integer.SIZE) return -1;
        return ((1 << size) - 1) << (start); 
    }

    public static int extract(int bits, int start, int size) {
      
        Preconditions.checkArgument(start>=0 && size>=0 && start+size<=Integer.SIZE);
        
        return (bits & Bits32.mask(start, size)) >>> (start);
                
    }

    
    private static boolean check (int v1, int s1) {
        return s1>=1 && s1<=(Integer.SIZE-1)  && Integer.toBinaryString(v1).length()<=s1;
    }
    
    public static int pack(int v1, int s1, int v2, int s2) {
       
        Preconditions.checkArgument( check (v1,s1)&& check(v2,s2) && s1+s2<=Integer.SIZE );
        return (v2 << s1) | v1;
    }

    public static int pack(int v1, int s1, int v2, int s2, int v3, int s3) {
        return pack(pack(v1, s1, v2, s2), s2 + s1, v3, s3);
    }

    public static int pack(int v1, int s1, int v2, int s2, int v3, int s3,
            int v4, int s4, int v5, int s5, int v6, int s6, int v7, int s7) {
        return pack(
                pack(v1, s1, v2, s2, v3, s3), s2 + s1 + s3,
                pack(v4, s4, v5, s5, v6, s6), s4 + s5 + s6, 
                v7, s7);
    }
}
