package was.container.mvc.controller.proxy;

import was.request.HttpRequest;
import was.response.HttpResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface ControllerProxy {
    String PATH_VARIABLE_PATTERN = "\\{[^}]+}";
    String NUMBER_FORMAT = "%d";

    void process(HttpRequest request, HttpResponse response) throws Exception;

    Class<?> getTarget();

    static List<String> findAllMappingUrl(ControllerProxy controllerProxy) {
        return Arrays.stream(controllerProxy.getTarget().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .map(method -> method.getDeclaredAnnotation(RequestMapping.class))
                .map(RequestMapping::url)
                .map(ControllerProxy::converRequestFormat)
                .collect(Collectors.toList());
    }

    static String converRequestFormat(String url) {
        return url.replaceAll(PATH_VARIABLE_PATTERN, NUMBER_FORMAT);
    }
}
