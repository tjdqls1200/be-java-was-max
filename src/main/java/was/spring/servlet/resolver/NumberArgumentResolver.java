package was.spring.servlet.resolver;

import was.request.HttpRequest;

import java.lang.reflect.Parameter;
import java.sql.Wrapper;
import java.util.NoSuchElementException;

public class NumberArgumentResolver implements MethodArgumentResolver {
    @Override
    public boolean canResolve(Parameter parameter) {
        final ParameterType parameterType = ParameterType.from(parameter.getType());

        return parameterType == ParameterType.INTEGER || parameterType == ParameterType.LONG;
    }

    @Override
    public Object resolve(HttpRequest request, Parameter parameter) throws NoSuchElementException {
        return null;
    }
}
