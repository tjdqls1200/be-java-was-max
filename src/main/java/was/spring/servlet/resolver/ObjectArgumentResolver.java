package was.spring.servlet.resolver;

import was.request.HttpRequest;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectArgumentResolver implements MethodArgumentResolver {
    private final ObjectBinder objectBinder = new ObjectBinder();

    @Override
    public boolean canResolve(Class<?> parameterType) {
        return ParameterType.from(parameterType) == ParameterType.OBJECT;
    }

    @Override
    public Object resolve(HttpRequest request, Class<?> parameterType, String parameterName) throws ReflectiveOperationException {
        return objectBinder.mapToBind(parameterType, parseArguments(request, parameterType));
    }

    private Map<String, String> parseArguments(HttpRequest request, Class<?> parameterType) {
        return Arrays.stream(parameterType.getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, field -> getArgument(request, field)));
    }

    private String getArgument(HttpRequest request, Field field) {
        return request.getParameter(field.getName());
    }
}
