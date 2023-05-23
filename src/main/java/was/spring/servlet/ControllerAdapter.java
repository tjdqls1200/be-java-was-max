package was.spring.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.common.HttpMethod;
import was.spring.servlet.mvc.controller.RequestMapping;
import was.spring.servlet.mvc.controller.Controller;
import was.request.HttpRequest;
import was.response.HttpResponse;
import was.spring.servlet.resolver.RequestArgumentResolver;

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

    private final RequestArgumentResolver argumentResolver = new RequestArgumentResolver();
    private final Map<String, Controller> controllers;

    public ControllerAdapter(List<Controller> initControllers) {
        Map<String, Controller> controllers = new HashMap<>();

        for (Controller initController : initControllers) {
            for (String mappingUrl : findAllMappingUrl(initController)) {
                controllers.put(mappingUrl, initController);
            }
        }

        this.controllers = controllers;
    }

    public void handle(HttpRequest request, HttpResponse response) {
        final String mappingUrl = toMappingUrl(request.getUrl());
        final Controller controller = controllers.get(mappingUrl);

        try {
            Method method = findHandleMethod(request, controller).orElseThrow(IllegalArgumentException::new);
            Object[] requiredArguments = argumentResolver.resolve(request, method);

            method.invoke(controller, requiredArguments);

        } catch (Exception ex) {
            LOGGER.info(ex.getClass().getName());
        }
    }


    private Optional<Method> findHandleMethod(HttpRequest request, Controller controller) {
        final String requestUrl = request.getUrl();
        final HttpMethod httpMethod = request.getMethod();

        return Arrays.stream(controller.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .filter(method -> isMatchedRequestMapping(method, requestUrl, httpMethod))
                .findFirst();
    }

    private boolean isMatchedRequestMapping(Method method, String requestUrl, HttpMethod httpMethod) {
        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);

        return requestMapping.url().equals(requestUrl) && requestMapping.method() == httpMethod;
    }

    private List<String> findAllMappingUrl(Controller controller) {
        return Arrays.stream(controller.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .map(method -> method.getDeclaredAnnotation(RequestMapping.class))
                .map(requestMapping -> toMappingUrl(requestMapping.url()))
                .collect(Collectors.toList());
    }

    private String toMappingUrl(String url) {
        return url.replaceAll(PATH_VARIABLE_PATTERN, NUMBER_FORMAT);
    }
}
