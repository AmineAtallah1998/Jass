package ch.epfl.javass.bits;

import static org.junit.Assert.*;

import org.junit.Test;

public class Bits32Test {

    @Test
    public void testMaskNonTrivial() {
        assertEquals(24,Bits32.mask(3,2));
    }
    @Test
    public void ExtractMaskNonTrivial() {
        assertEquals(3,Bits32.extract(24, 3, 2));
    }
    @Test
    public void PackMaskNonTrivial() {
        assertEquals(18,Bits32.pack( 2, 2,4,3));
    }


}
