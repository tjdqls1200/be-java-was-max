package was.request;

import was.common.HttpMethod;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestStartLine {
    private static final String SP = " ";
    private static final String URL_SEPARATOR = "\\?";
    private static final String QUERY_PARAMETERS_SEPARATOR = "&";
    private static final String QUERY_PARAM_ENTRY_SEPARATOR = "=";
    private static final int ELEMENTS_COUNT = 3;

    private final HttpMethod method;

    private final String url;

    public final Map<String, String> requestParams;

    private final String version;

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getRequestParams() {
        return new LinkedHashMap<>(requestParams);
    }

    public String getVersion() {
        return version;
    }

    protected RequestStartLine(HttpMethod method, String url, Map<String, String> requestParams, String version) {
        this.method = method;
        this.url = url;
        this.requestParams = requestParams;
        this.version = version;
    }

    public static RequestStartLine parse(String startLine) {
        final String[] elements = toElements(startLine);

        HttpMethod method = HttpMethod.from(elements[0]);
        String[] urlElements = elements[1].split(URL_SEPARATOR);
        String url = urlElements[0];
        Map<String, String> requestParams = parseRequestParams(urlElements);
        String version = elements[2];

        return new RequestStartLine(method, url, requestParams, version);
    }

    private static String[] toElements(String startLine) {
        if (startLine == null || startLine.isBlank()) {
            throw new IllegalArgumentException("Start line is not exist");
        }

        String[] elements = startLine.split(SP);

        if (elements.length != ELEMENTS_COUNT) {
            throw new IllegalArgumentException("Invalid Request start Line");
        }

        return elements;
    }

    private static Map<String, String> parseRequestParams(String[] urlElements) {
        Map<String, String> requestParams = new LinkedHashMap<>();
        if (urlElements.length < 2) {
            return Collections.emptyMap();
        }

        for (String queryParam : urlElements[1].split(QUERY_PARAMETERS_SEPARATOR)) {
            String[] queryParamEntry = queryParam.split(QUERY_PARAM_ENTRY_SEPARATOR);
            requestParams.put(queryParamEntry[0], queryParamEntry[1]);
        }

        return requestParams;
    }

    @Override
    public String toString() {
        return "RequestStartLine {" + '\n' +
                '\t' + "Method: " + method + '\n'+
                '\t' + "Url: " + url + '\n' +
                '\t' + "RequestParams: " + requestParams + '\n' +
                '\t' + "Version: " + version + '\n' +
                '}';
    }
}
