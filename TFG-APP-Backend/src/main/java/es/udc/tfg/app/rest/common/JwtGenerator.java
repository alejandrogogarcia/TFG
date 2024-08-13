package es.udc.tfg.app.rest.common;

public interface JwtGenerator {

    String generate(JwtInfo info);

    JwtInfo getInfo(String token);
}
