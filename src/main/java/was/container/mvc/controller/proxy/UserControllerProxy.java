package was.container.mvc.controller.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.common.HttpMethod;
import was.container.mvc.controller.UserController;
import was.request.HttpRequest;
import was.response.HttpResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;

public class UserControllerProxy implements ControllerProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerProxy.class);

    private final UserController userController;

    public UserControllerProxy(UserController userController) {
        this.userController = userController;
    }

    @Override
    public Class<UserController> getTarget() {
        return UserController.class;
    }

    @Override
    public void process(HttpRequest request, HttpResponse response) throws Exception {
        final String url = request.getUrl();
        final HttpMethod httpMethod = request.getMethod();

        Method mappingMethod = findMappingMethod(url, httpMethod).orElseThrow(IllegalArgumentException::new);

        Object[] requiredParameters = findRequiredParameters(request, mappingMethod);

        mappingMethod.invoke(userController, requiredParameters);
    }

    private Object[] findRequiredParameters(HttpRequest request, Method method) throws Exception {
        final Parameter[] parameters = method.getParameters();
        final int parameterCount = method.getParameterCount();
        final Object[] requiredParameters = new Object[parameterCount];

        for (int i = 0; i < parameterCount; i++) {
            requiredParameters[i] = findParameter(request, parameters[i]);
        }

        return requiredParameters;
    }

    private Object findParameter(HttpRequest request, Parameter parameter) throws Exception {
        final Class<?> parameterType = parameter.getType();
        if (parameterType.isAssignableFrom(String.class) || parameterType.isPrimitive()) {
            return request.getParameter(parameter.getName());
        }

        final Object[] fields = Arrays.stream(parameterType.getDeclaredFields())
                .map(field -> request.getParameter(field.getName()))
                .toArray();

        Constructor<?> constructor = findConstructor(parameterType, fields).orElseThrow(NoSuchMethodException::new);

        return constructor.newInstance(fields);
    }

    private Optional<Constructor<?>> findConstructor(Class<?> parameterType, Object[] fields) {
        for (var constructor : parameterType.getDeclaredConstructors()) {
            if (isSameParameterCount(fields, constructor)) {
                return Optional.of(constructor);
            }
        }
        return Optional.empty();
    }

    private boolean isSameParameterCount(Object[] fields, Constructor<?> constructor) {
        return constructor.getParameterTypes().length == fields.length;
    }

    private Optional<Method> findMappingMethod(String requestUrl, HttpMethod requestMethod) {
        for (Method method : userController.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            if (isMatchedRequestMapping(requestUrl, requestMethod, method.getDeclaredAnnotation(RequestMapping.class))) {
                return Optional.of(method);
            }
        }

        return Optional.empty();
    }

    private boolean isMatchedRequestMapping(String requestUrl, HttpMethod requestMethod, RequestMapping requestMapping) {
        return requestUrl.equals(requestMapping.url()) && requestMethod == requestMapping.method();
    }
}
