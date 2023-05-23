package was.spring.servlet.resolver;

import was.request.HttpRequest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class RequestArgumentResolver {
    private final List<MethodArgumentResolver> resolvers = List.of(
            new ObjectParameterConverter(),
            new StringParameterConverter()
    );

    //TODO 예외 처리
    public Object[] resolve(HttpRequest request, Method method) {
        return Arrays.stream(method.getParameters())
                .map(requireParameter -> makeParameter(request, requireParameter))
                .toArray();
    }

    private Object makeParameter(HttpRequest request, Parameter parameter) throws NoSuchElementException {
        return getArgumentResolver(parameter).resolve(request, parameter);
    }

    private MethodArgumentResolver getArgumentResolver(Parameter parameter) {
        return resolvers.stream()
                .filter(resolver -> resolver.canResolve(parameter))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("지원하지 않는 파라미터 타입"));
    }
}
