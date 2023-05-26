package was.spring.servlet.resolver;

import was.request.HttpRequest;

public interface MethodArgumentResolver {
    boolean canResolve(Class<?> parameterType);

    Object resolve(HttpRequest request, Class<?> parameterType, String parameterName) throws ReflectiveOperationException;
}
