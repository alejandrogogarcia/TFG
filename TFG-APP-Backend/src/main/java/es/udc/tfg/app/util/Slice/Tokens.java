package es.udc.tfg.app.util.Slice;

public class Tokens {

    public static String[] getTokens(String keywords) {
        if (keywords == null || keywords.trim().isEmpty()) {
            return new String[0];
        } else {
            return keywords.trim().split("\\s+");
        }
    }
}
