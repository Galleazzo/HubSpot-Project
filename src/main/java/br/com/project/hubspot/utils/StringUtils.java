package br.com.project.hubspot.utils;

public class StringUtils {

    public static String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new IllegalArgumentException("Authorization header inválido");
    }
}
