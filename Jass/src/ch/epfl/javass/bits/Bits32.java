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
    private Bits32() {    };
    private static boolean validPlage(int start, int size) {
        return ((start >= 0) && (size >= 0) && (start + size < Integer.SIZE));
    }
    
    public static int mask(int start, int size)
            throws IllegalArgumentException {
        Preconditions.checkArgument(validPlage(start, size));

        return ((1<<size) -1)<<start;
    }
    public static int extract(int bits, int start, int size) throws IllegalArgumentException 
    {  
    Preconditions.checkArgument(validPlage(start, size));
       return (bits>>>(start)) & ((1<<size)-1);
    }
    private static int sizeBin (int a )
    {  
        return (int) (Math.floor( ( Math.log(a)/Math.log(2))) +1);
    }
    private static boolean  checkPack(int v,int s)
    {
        return (s>=1)&& (s<=31)&& (sizeBin(v)<=s);
    }
    
    public static int pack (int v1,int s1, int v2,int s2) throws IllegalArgumentException 
    {  Preconditions.checkArgument(checkPack(v1,s1)&&checkPack(v2,s2));
        
     return (v2<<s1) |v1;
    }
    
    public static int pack (int v1,int s1, int v2,int s2,int v3, int s3) 
    {
     return pack (pack( v1, s1,  v2, s2),s2+s1,  v3, s3);
    }
    public static int pack (int v1,int s1, int v2,int s2,int v3, int s3,int s4, int v4, int s5, int v5 , int s6, int v6 , int s7, int v7) 
    {
        return pack ( pack ( v1, s1,  v2, s2,v3,s3) , s2+s1+s3, pack (v4,s4,v5,s5,v6,s6), v4+v5+v6, s7,v7);
       }
}
    

   /* private static boolean validPlage(int start, int size) {
        return ((start >= 0) && (size >= 1) && (start + size < Integer.SIZE));
    }

    private static int[] repBin (int a )
    { 
       int[] tab = new int[Integer.SIZE] ;
       
    for (int i=Integer.SIZE-1;i>=00;i--)
    {   if ((a % 2) ==0) {
            tab[i]=0;            
        }
        else {
          tab[i]=1;            
        }
        a=a/2;

    }
    return tab;  
    }
    private static int sizeBin (int a )
    {   System.out.println(( Math.log(a)/Math.log(2)));
        return (int) (Math.floor( ( Math.log(a)/Math.log(2))) +1);
    }

    public static int mask(int start, int size)
            throws IllegalArgumentException {
        if (!validPlage(start, size)) {
            throw new IllegalArgumentException();
        }
        int res = 0;
        for (int i = start; i < (start + size); i++) {
            res += Math.pow(2, i);
        }
        return res;

    }

    public static int extract(int bits, int start, int size) {
        if (!validPlage(start, size)) {
            throw new IllegalArgumentException();
        }
        int s=0;
        for (int i=0; i<size; i++)
        {
            if (repBin(bits)[start+i]==1)
            {
                s+=Math.pow(2,i);
            }
        }
        return s;
    }
    private static boolean  checkPack(int v,int s)
    {
        return (s>=1)&& (s<=31)&& (sizeBin(v)<=s);
    }
    public static int pack (int v1,int s1, int v2,int s2) throws IllegalArgumentException 
    { if (!(checkPack(v1,s1)&&checkPack(v2,s2)))
        {
          throw new IllegalArgumentException() ;
        }
      int [] tab= new int[s1+s2];
      for (int i=s2;i<=s1+s2-1;i++)
      {
          tab[i]=repBin(v1)[Integer.SIZE-s1+1+i-s2];
      }
      for (int i=0;i<=s2-1;i++)
      {
          tab[i]=repBin(v2)[Integer.SIZE-s1+1+i-s2];
      }
      int res=0;
      for (int i=0; i<s1+s2;i++)
      { if (tab[i]==1) 
          {
          res+=Math.pow(2, s1+s2-i);
          }
      }
      return res;    
    }
    public static int pack (int v1,int s1, int v2,int s2,int v3, int s3) 
    {
     return pack (pack( v1, s1,  v2, s2),s2+s1,  v3, s3);
    }
    public static int pack (int v1,int s1, int v2,int s2,int v3, int s3,int s4, int v4, int s5, int v5 , int s6, int v6 , int s7, int v7) 
    {
        return pack ( pack ( v1, s1,  v2, s2,v3,s3) , s2+s1+s3, pack (v4,s4,v5,s5,v6,s6), v4+v5+v6, s7,v7);
       }
*/
  


