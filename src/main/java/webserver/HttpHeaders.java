package webserver;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {
    private static final String COLON = ":";
    private static final String COMMA = ",";

    private final Map<String, List<String>> headers;

    protected HttpHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public static HttpHeaders parse(final List<String> headerLines) {
        final Map<String, List<String>> headers = new LinkedHashMap<>();

        for (String headerLine : headerLines) {
            String[] headerEntry = headerLine.split(COLON);
            headers.put(HeaderType.keyOf(headerEntry[0]), parseHeaderValues(headerEntry[1]));
        }

        return new HttpHeaders(headers);
    }

    private static List<String> parseHeaderValues(String headerValues) {
        return Arrays.stream(headerValues.split(COMMA))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HttpHeaders { ");

        for (var entry : headers.entrySet()) {
            sb.append("\n").append('\t').append(entry.getKey()).append(": ").append(entry.getValue());
        }

        sb.append("}");

        return sb.toString();
    }
}
