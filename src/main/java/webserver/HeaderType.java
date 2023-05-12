package webserver;

import java.util.Arrays;

public enum HeaderType {
    ACCEPT("accept"),
    ACCEPT_ENCODING("accept-encoding"),
    ACCEPT_LANGUAGE("accept-language"),
    AUTHORIZATION("authorization"),
    CACHE_CONTROL("cache-control"),
    CONNECTION("connection"),
    CONTENT_ENCODING("content-encoding"),
    CONTENT_LANGUAGE("content-language"),
    CONTENT_LENGTH("content-length"),
    CONTENT_LOCATION("content-location"),
    CONTENT_TYPE("content-type"),
    COOKIE("cookie"),
    HOST("host"),
    KEEP_ALIVE("keep-alive");

    private final String key;

    HeaderType(String key) {
        this.key = key;
    }

    public static String keyOf(String keyName) {
        String key = keyName.trim().toLowerCase();

        return Arrays.stream(values())
                .filter(type -> type.key.equals(key))
                .map(type -> type.key)
                .findFirst()
                .orElse(key);
    }
}
