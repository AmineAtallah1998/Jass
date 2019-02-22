package ch.epfl.javass.bits;

import ch.epfl.javass.Preconditions;

public final class Bits64 {
    
    private Bits64() {
    }

    private static boolean validPlage(long start, long size) {
        return ((start >= 0) && (size >= 0) && (start + size <= Integer.SIZE));
    }

    public static long mask(long start, long size){
        Preconditions.checkArgument(validPlage(start, size));

        if(size==Long.SIZE) return -1L;
        return ((1L << size) - 1L) << start;       // 1 en 1L
    }

    public static long extract(long bits, int start, int size){
        
        Preconditions.checkArgument(validPlage(start, size));
        return (bits & Bits64.mask(start, size)) >>> (start); 
    }

    

    private static boolean checkPack(long v, int s) {
        return s>=1 && s<=(Long.SIZE-1)  && Long.toBinaryString(v).length()<=s;
    }

    public static long pack(long v1, int s1, long v2, int s2){
        
        Preconditions.checkArgument( checkPack (v1,s1)&& checkPack(v2,s2) && s1+s2<=Integer.SIZE );
        return (v2 << s1) | v1;
    }
}
