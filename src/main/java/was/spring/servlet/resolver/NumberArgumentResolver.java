package was.spring.servlet.resolver;

import was.request.HttpRequest;

import java.lang.reflect.Parameter;
import java.util.NoSuchElementException;

public class NumberArgumentResolver implements MethodArgumentResolver {
    @Override
    public boolean canResolve(Parameter parameter) {
        final ParameterType parameterType = ParameterType.from(parameter.getType());

        return parameterType == ParameterType.INTEGER || parameterType == ParameterType.LONG;
    }

    @Override
    public Object resolve(HttpRequest request, Parameter parameter) throws NoSuchElementException {
        final ParameterType parameterType = ParameterType.from(parameter.getType());
        final String value = request.getParameter(parameter.getName());

        if (parameterType == ParameterType.INTEGER) {
            return Integer.parseInt(value);
        }

        return Long.parseLong(value);
    }
}
