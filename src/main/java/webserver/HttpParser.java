package webserver;

public final class HttpParser {
    private static final String SPACE_BAR = " ";
    private static final String CRLF = "";

    public static void parse(WebRequest request, String line, final int lineNumber) {
        if (isStartLine(lineNumber)) {
            parseStartLine(request, line);
        }
        if (isHeaderLine(line, lineNumber)) {
            parseHeader(request, line);
        }

    }

    private static void parseHeader(WebRequest request, String line) {
        //TODO 헤더 파싱 구현
    }

    private static boolean isHeaderLine(String line, int lineNumber) {
        return lineNumber > 1 && !line.equals(CRLF);
    }

    private static boolean isStartLine(int lineNumber) {
        return lineNumber == 0;
    }

    private static void parseStartLine(WebRequest request, String line) {
        final String[] elements = line.split(SPACE_BAR);

        request.setMethod(elements[0]);
        request.setUrl(elements[1]);
        request.setVersion(elements[2]);
    }
}
