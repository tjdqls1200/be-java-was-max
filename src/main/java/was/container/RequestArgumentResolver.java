package was.container;

import was.request.HttpRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;

public class RequestArgumentResolver {
    public Object[] resolve(HttpRequest request, Method method) throws Exception {
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
}
