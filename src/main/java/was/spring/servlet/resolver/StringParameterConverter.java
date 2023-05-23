package was.spring.servlet.resolver;

import was.request.HttpRequest;

import java.lang.reflect.Parameter;
import java.util.NoSuchElementException;

public class StringParameterConverter implements MethodArgumentResolver {
    @Override
    public boolean canResolve(Parameter parameter) {
        return ParameterType.from(parameter.getType()) == ParameterType.STRING;
    }

    @Override
    public Object resolve(HttpRequest request, Parameter parameter) throws NoSuchElementException {
        return request.getParameter(parameter.getName());
    }
}
