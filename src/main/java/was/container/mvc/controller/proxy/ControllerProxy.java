package was.container.mvc.controller.proxy;

import was.container.RequestMapping;
import was.request.HttpRequest;
import was.response.HttpResponse;

public interface ControllerProxy {
    void process(HttpRequest request, HttpResponse response, RequestMapping requestMapping);
}
