package ch.epfl.javass.net;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public final class StringSerializer {

    //Constructeur privé
    private StringSerializer() {
    }
    
    public static String serializeInt(int n) {
        return Integer.toUnsignedString(n, 16);
    }
    public static int deserializeInt(String s) {
        return Integer.parseUnsignedInt(s,16);
    }
    
    
    public static String serializeLong(long n) {
        return Long.toUnsignedString(n, 16);
    }
    public static long deserializeLong(String s) {
        return Long.parseUnsignedLong(s,16);
    }
    
    public static String serializeString(String s) {
        Base64.Encoder encoder =Base64.getEncoder();
        return encoder.encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }
    
    public static String deserializeString(String s) {
        Base64.Decoder decoder =Base64.getDecoder();
        return new String(decoder.decode(s));
    }
    
    public static String combine(char ch , String...strings ) {
        return String.join(ch+"", strings);
    }
    
    public static String[] split(char ch , String s) {
        return s.split(ch+"");
    }
    
}
