package was.spring.servlet.resolver;

import was.request.HttpRequest;

import java.lang.reflect.Parameter;
import java.util.NoSuchElementException;

public interface MethodArgumentResolver {
    boolean canResolve(Parameter parameter);

    Object resolve(HttpRequest request, Parameter parameter) throws NoSuchElementException;
}
