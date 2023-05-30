package was.spring.servlet.resolver;

import was.http.HttpRequest;

public interface MethodArgumentResolver {
    boolean canResolve(Class<?> parameterType);

    Object resolve(HttpRequest request, Class<?> parameterType, String parameterName) throws ReflectiveOperationException;
}
