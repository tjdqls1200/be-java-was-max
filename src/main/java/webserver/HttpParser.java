package webserver;

public final class HttpParser {
    private static final String SPACE_BAR = " ";
    private static final String BLANK = "";
    private static final String COLON = ":";
    private static final String REQUEST_URL_SEPARATOR = "\\?";
    private static final String QUERY_PARAM_SEPARATOR = "&";
    private static final String ENTRY_SEPARATOR = "=";

    public static void parse(WebRequest request, String line, final int lineNumber) {
        if (isStartLine(lineNumber)) {
            parseStartLine(request, line);
        }

        parseHeader(request, line);
    }

    private static boolean isStartLine(int lineNumber) {
        return lineNumber == 0;
    }

    private static void parseHeader(WebRequest request, String line) {
        String key = line.split(COLON)[0];
        String value = line.replaceFirst(key + COLON, BLANK).trim();

        request.setHeader(key, value);
    }

    private static void parseStartLine(WebRequest request, String line) {
        final String[] elements = line.split(SPACE_BAR);

        request.setMethod(elements[0]);
        parseRequestURL(request, elements[1]);
        request.setVersion(elements[2]);
    }

    private static void parseRequestURL(WebRequest request, String url) {
        final String[] params = url.split(REQUEST_URL_SEPARATOR);
        request.setUrl(url);
        request.setPath(params[0]);

        if (hasQueryParams(params)) {
            for (String queryParam : params[1].split(QUERY_PARAM_SEPARATOR)) {
                String[] queryParamEntry = queryParam.split(ENTRY_SEPARATOR);
                request.setRequestParam(queryParamEntry[0], queryParamEntry[1]);
            }
        }
    }

    private static boolean hasQueryParams(String[] params) {
        return params.length > 1;
    }
}
