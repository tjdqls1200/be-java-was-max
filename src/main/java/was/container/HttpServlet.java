package was.container;

import was.request.HttpRequest;
import was.response.HttpResponse;

public abstract class HttpServlet extends BaseServlet {
    @Override
    public abstract void service(HttpRequest request, HttpResponse response);
}
