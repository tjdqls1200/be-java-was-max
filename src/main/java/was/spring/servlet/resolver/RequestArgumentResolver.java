package was.spring.servlet.resolver;

import was.common.HttpMethod;
import was.request.ContentType;
import was.request.HttpRequest;
import was.spring.servlet.resolver.converter.HttpMessageConverter;
import was.spring.servlet.resolver.converter.ObjectHttpMessageConverter;
import was.spring.servlet.resolver.converter.StringHttpMessageConverter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class RequestArgumentResolver {
    private final List<MethodArgumentResolver> resolvers = List.of(
            new ObjectArgumentResolver(),
            new StringArgumentResolver()
    );

    private final List<HttpMessageConverter> messageConverters = List.of(
            new StringHttpMessageConverter(),
            new ObjectHttpMessageConverter()
    );

    public Object[] resolve(HttpRequest request, Method method) {
        return Arrays.stream(method.getParameters())
                .map(requireParameter -> makeParameter(request, requireParameter))
                .toArray();
    }

    private Object makeParameter(HttpRequest request, Parameter parameter) {
        //TODO 예외 처리
        try {
            if (request.getMethod() == HttpMethod.POST) {
                return getMessageConverter(parameter).read(parameter, request.getRequestBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getArgumentResolver(parameter).resolve(request, parameter);
    }

    private HttpMessageConverter getMessageConverter(Parameter parameter) {
        return messageConverters.stream()
                .filter(httpMessageConverter -> httpMessageConverter.canRead(parameter, ContentType.FORM_DATA))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("지원하지 않는 파라미터 타입"));
    }


    private MethodArgumentResolver getArgumentResolver(Parameter parameter) {
        return resolvers.stream()
                .filter(resolver -> resolver.canResolve(parameter))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("지원하지 않는 파라미터 타입"));
    }
}
