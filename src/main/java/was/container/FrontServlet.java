package was.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.container.mvc.controller.UserController;
import was.container.mvc.controller.proxy.UserControllerProxy;
import was.container.mvc.repository.UserRepositoryImpl;
import was.container.mvc.service.UserService;
import was.container.mvc.controller.proxy.ControllerProxy;
import was.request.HttpRequest;
import was.response.HttpResponse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FrontServlet extends BaseServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontServlet.class);

    //TODO 초기화, 관리 어떻게 할 것인지
    private final List<ControllerProxy> controllers = List.of(
            new UserControllerProxy(new UserController(new UserService(new UserRepositoryImpl())))
    );

    private final Map<String, ControllerProxy> handlers = new ConcurrentHashMap<>();

    @Override
    public void init() {
        LOGGER.info("FRONT SERVLET INIT");
        for (var controller : controllers) {
            for (String mappingUrl : ControllerProxy.findAllMappingUrl(controller)) {
                handlers.put(mappingUrl, controller);
            }
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        LOGGER.info("FRONT SERVLET SERVICE");
        // "users/1" -> "users/%d" format
        final String requestFormat = ControllerProxy.converRequestFormat(request.getUrl());

        final ControllerProxy controller = handlers.get(requestFormat);

        try {
            LOGGER.info("CONTROLLER PROCESS START");
            controller.process(request, response);
        } catch (Exception ex) {
            LOGGER.info(ex.getClass().getName());
        }
        LOGGER.info("CONTROLLER PROCESS COMPLETE");
    }

    private String convertUrlFormat(String url) {
        return url.replaceAll(ControllerProxy.PATH_VARIABLE_PATTERN, ControllerProxy.NUMBER_FORMAT);
    }
}
