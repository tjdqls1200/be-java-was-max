package was.spring.servlet.resolver;

import was.request.HttpRequest;

public class StandardTypeArgumentResolver implements MethodArgumentResolver {
    @Override
    public boolean canResolve(Class<?> parameterType) {
        return ParameterType.isStandardType(parameterType);
    }

    @Override
    public Object resolve(HttpRequest request, Class<?> parameterType, String parameterName) throws ReflectiveOperationException {
        return ParameterType.from(parameterType).convert(request.getParameter(parameterName));
    }
}
