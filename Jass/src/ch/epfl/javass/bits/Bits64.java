package ch.epfl.javass.bits;

import ch.epfl.javass.Preconditions;

public final class Bits64 {
 private Bits64() {}
 private static boolean validPlage(long start, long size) {
     return ((start >= 0) && (size >= 0) && (start + size < Integer.SIZE));
 }
 
 public static long mask(long start, long size)
         throws IllegalArgumentException {
     Preconditions.checkArgument(validPlage(start, size));

     return ((1L<<size) -1)<<start;
 }
 public static long extract(long bits, int start, int size) throws IllegalArgumentException 
 {  
 Preconditions.checkArgument(validPlage(start, size));
    return (bits>>>(start)) & ((1<<size)-1);
 }
 private static long sizeBin (long a )
 {  
     return (long) (Math.floor( ( Math.log(a)/Math.log(2))) +1);
 }
 private static boolean  checkPack(long v,int s)
 {
     return (s>=1)&& (s<=31)&& (sizeBin(v)<=s);
 }
 
 public static long pack (long v1,int s1, long v2,int s2) throws IllegalArgumentException 
 {  Preconditions.checkArgument(checkPack(v1,s1)&&checkPack(v2,s2));
     
  return (v2<<s1) |v1;
 }
}
