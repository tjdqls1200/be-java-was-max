package was.container;

import was.common.HttpMethod;
import was.container.mvc.controller.proxy.ControllerProxy;
import was.request.HttpRequest;
import was.response.HttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FrontServlet extends BaseServlet {

    private static final List<Class<? extends RequestMapping>> requestMappings = List.of(
            UserRequestMapping.class
    );

    private final Map<String, List<RequestMapping>> handlers = new ConcurrentHashMap<>();

    @Override
    public void init() {
        for (var requestMapping : requestMappings) {
            for (RequestMapping mapping : requestMapping.getEnumConstants()) {
                handlers.computeIfAbsent(mapping.getPrifix(), prifix -> new ArrayList<>()).add(mapping);
            }
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {

        final RequestMapping requestMapping = findRequestMapping(request)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));

        final ControllerProxy controllerProxy = requestMapping.getController();

        controllerProxy.process(request, response, requestMapping);
    }

    private Optional<RequestMapping> findRequestMapping(HttpRequest request) {
        final String requestUrl = request.getUrl();
        final HttpMethod method = request.getMethod();

        return handlers.get(requestUrl).stream()
                .filter(requestMapping -> requestMapping.isMapped(requestUrl, method))
                .findFirst();
    }
}
