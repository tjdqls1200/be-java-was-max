package was.container;

import was.common.HttpHeaders;
import was.request.HttpRequest;
import was.request.RequestStartLine;

public class HttpServletRequest extends HttpRequest {
    protected HttpServletRequest(RequestStartLine startLine, HttpHeaders header, String requestBody) {
        super(startLine, header, requestBody);
    }

    public String getParameter(String name) {
        return startLine.getParameter(name);
    }
}
