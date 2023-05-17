package was.container;

import was.request.HttpRequest;
import was.response.HttpResponse;

public abstract class BaseServlet {
    public abstract void init();

    public abstract void service(HttpRequest request, HttpResponse response);
}
