package was.spring.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.common.HttpMethod;
import was.spring.servlet.annotation.RequestMapping;
import was.spring.servlet.mvc.controller.Controller;
import was.request.HttpRequest;
import was.response.HttpResponse;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControllerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdapter.class);
    private static final String PATH_VARIABLE_PATTERN = "\\{[^}]+}";
    private static final String NUMBER_FORMAT = "%d";

    private final Map<String, Controller> handlers;
    private final RequestArgumentResolver argumentResolver = new RequestArgumentResolver();

    public ControllerAdapter(List<Controller> controllers) {
        Map<String, Controller> handlers = new HashMap<>();

        for (Controller controller : controllers) {
            for (String mappingUrl : findAllMappingUrl(controller)) {
                handlers.put(mappingUrl, controller);
            }
        }

        this.handlers = handlers;
    }

    public void handle(HttpRequest request, HttpResponse response) {
        final String mappingUrl = toMappingUrl(request.getUrl());
        final Controller controller = handlers.get(mappingUrl);

        try {
            Method method = findHandleMethod(request, controller).orElseThrow(IllegalArgumentException::new);
            Object[] requiredArguments = argumentResolver.resolve(request, method);

            method.invoke(controller, requiredArguments);

        } catch (Exception ex) {
            LOGGER.info(ex.getClass().getName());
        }
    }


    private Optional<Method> findHandleMethod(HttpRequest request, Controller controller) {
        final String url = request.getUrl();
        final HttpMethod httpMethod = request.getMethod();

        for (Method method : controller.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            if (isMatchedRequestMapping(url, httpMethod, method.getDeclaredAnnotation(RequestMapping.class))) {
                return Optional.of(method);
            }
        }

        return Optional.empty();
    }

    private boolean isMatchedRequestMapping(String requestUrl, HttpMethod requestMethod, RequestMapping requestMapping) {
        return requestUrl.equals(requestMapping.url()) && requestMethod == requestMapping.method();
    }

    private List<String> findAllMappingUrl(Controller controller) {
        return Arrays.stream(controller.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .map(method -> method.getDeclaredAnnotation(RequestMapping.class))
                .map(RequestMapping::url)
                .map(this::toMappingUrl)
                .collect(Collectors.toList());
    }

    private String toMappingUrl(String url) {
        return url.replaceAll(PATH_VARIABLE_PATTERN, NUMBER_FORMAT);
    }
}
