package was.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {
    private static final String HEADER_ENTRY_SEPARATOR = ": ";
    private static final String HEADER_VALUE_SEPARATOR = ",";
    private static final String EMPTY = "";
    private static final int HEADER_KEY_INDEX = 0;

    private final Map<String, List<String>> headers;

    public HttpHeaders() {
        headers = new LinkedHashMap<>();
    }

    protected HttpHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void addHeader(String key, String value) {
        headers.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    public static HttpHeaders parse(final List<String> headerLines) {
        final Map<String, List<String>> headers = new LinkedHashMap<>();

        for (String headerLine : headerLines) {
            String key = headerLine.split(HEADER_ENTRY_SEPARATOR)[HEADER_KEY_INDEX];
            List<String> values = parseHeaderValues(headerLine.replaceFirst(key + HEADER_ENTRY_SEPARATOR, EMPTY));

            headers.put(HeaderType.keyOf(key), values);
        }

        return new HttpHeaders(headers);
    }

    public List<String> getHeader(String header) {
        return new ArrayList<>(headers.getOrDefault(header, Collections.emptyList()));
    }

    public int getContentLength() {
        List<String> contentLength = headers.getOrDefault(HeaderType.CONTENT_LENGTH.getKey(), Collections.emptyList());

        if (contentLength.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(contentLength.get(0));
    }

    private static List<String> parseHeaderValues(String headerValues) {
        return Arrays.stream(headerValues.split(HEADER_VALUE_SEPARATOR))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public String toHeadersOutputFormat() {
        return headers.entrySet().stream()
                .map(this::toHeaderOutputFormat)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String toHeaderOutputFormat(Map.Entry<String, List<String>> entry) {
        return entry.getKey() + HEADER_ENTRY_SEPARATOR + String.join(HEADER_VALUE_SEPARATOR, entry.getValue());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HttpHeaders { ");

        for (var entry : headers.entrySet()) {
            sb.append("\n").append('\t').append(entry.getKey()).append(": ").append(entry.getValue());
        }

        sb.append("\n").append("}");

        return sb.toString();
    }
}
