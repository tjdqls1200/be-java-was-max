package was.spring.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.http.enums.HttpMethod;
import was.spring.servlet.common.exception.ControllerHandleMethodNotFoundException;
import was.spring.servlet.common.exception.ControllerNotFoundException;
import was.spring.servlet.mvc.controller.Controller;
import was.spring.servlet.mvc.controller.annotation.RequestMapping;
import was.spring.servlet.mvc.view.Model;
import was.spring.servlet.mvc.view.ModelAndView;
import was.spring.servlet.resolver.RequestArgumentResolver;
import was.spring.servlet.resolver.StringReturnValueHandler;

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
    private final StringReturnValueHandler returnValueHandler = new StringReturnValueHandler();
    private final Map<String, Controller> controllers;

    public ControllerAdapter(List<Controller> initControllers) {
        Map<String, Controller> controllers = new HashMap<>();

        for (Controller initController : initControllers) {
            for (String mappingUrl : findAllMappingUrl(initController.getClass())) {
                controllers.put(mappingUrl, initController);
            }
        }

        this.controllers = controllers;
    }

    public ModelAndView handle(HttpRequest request, HttpResponse response) {
        Controller controller = findController(request).orElseThrow(ControllerNotFoundException::new);
        ModelAndView mv = null;

        try {
            Method method = findControllerMethod(request, controller).orElseThrow(ControllerHandleMethodNotFoundException::new);
            Object[] args = argumentResolver.resolve(request, method);
            Object returnValue = method.invoke(controller, args);
            mv = returnValueHandler.handle(method, returnValue, getModel(args));
            response.setStatus(mv.getHttpStatus());

        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }

        return mv;
    }

    private Optional<Controller> findController(HttpRequest request) {
        final String mappingUrl = toMappingUrl(request.getUrl());

        return Optional.ofNullable(controllers.get(mappingUrl));
    }

    private Model getModel(Object[] args) {
        for (Object argument : args) {
            if (argument.getClass() == Model.class) {
                return (Model) argument;
            }
        }

        return new Model();
    }


    private Optional<Method> findControllerMethod(HttpRequest request, Controller controller) {
        return Arrays.stream(controller.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .filter(method -> isMatchedRequestMapping(method, request.getUrl(), request.getMethod()))
                .findFirst();
    }

    private boolean isMatchedRequestMapping(Method method, String requestUrl, HttpMethod httpMethod) {
        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);

        return requestMapping.url().equals(requestUrl) && requestMapping.method() == httpMethod;
    }

    private List<String> findAllMappingUrl(Class<? extends Controller> classType) {
        return Arrays.stream(classType.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .map(method -> method.getDeclaredAnnotation(RequestMapping.class))
                .map(requestMapping -> toMappingUrl(requestMapping.url()))
                .collect(Collectors.toList());
    }

    private String toMappingUrl(String url) {
        return url.replaceAll(PATH_VARIABLE_PATTERN, NUMBER_FORMAT);
    }
}
