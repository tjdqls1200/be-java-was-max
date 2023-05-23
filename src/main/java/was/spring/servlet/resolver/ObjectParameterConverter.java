package was.spring.servlet.resolver;

import was.request.HttpRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ObjectParameterConverter implements MethodArgumentResolver {
    @Override
    public boolean canResolve(Parameter parameter) {
        return ParameterType.from(parameter.getType()) == ParameterType.OBJECT;
    }

    @Override
    public Object resolve(HttpRequest request, Parameter parameter) throws NoSuchElementException {
        final Class<?> parameterType = parameter.getType();
        final Object[] arguments = getArguments(request, parameterType);

        try {
            return findConstructor(parameterType, arguments.length).newInstance(arguments);
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
    }

    private Object[] getArguments(HttpRequest request, Class<?> parameterType) {
        return Arrays.stream(parameterType.getDeclaredFields())
                .map(field -> request.getParameter(field.getName()))
                .toArray();
    }

    private Constructor<?> findConstructor(Class<?> parameterType, int argumentCount) throws NoSuchMethodException {
        return Arrays.stream(parameterType.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == argumentCount)
                .findFirst()
                .orElseThrow(NoSuchMethodException::new);
    }
}
