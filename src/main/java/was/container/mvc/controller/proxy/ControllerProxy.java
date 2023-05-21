package was.container.mvc.controller.proxy;

import was.container.HttpServletRequest;
import was.container.RequestMapping;
import was.response.HttpResponse;

public interface ControllerProxy {
    void process(HttpServletRequest request, HttpResponse response, RequestMapping requestMapping);
}
