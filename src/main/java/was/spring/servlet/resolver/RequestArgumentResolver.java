package was.spring.servlet.resolver;

import was.common.HttpMethod;
import was.request.ContentType;
import was.request.HttpRequest;
import was.spring.servlet.mvc.controller.PathVariable;
import was.spring.servlet.mvc.view.Model;
import was.spring.servlet.resolver.converter.HttpMessageConverter;
import was.spring.servlet.resolver.converter.ObjectHttpMessageConverter;
import was.spring.servlet.resolver.converter.StringHttpMessageConverter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class RequestArgumentResolver {
    private final List<MethodArgumentResolver> resolvers = List.of(
            new ObjectArgumentResolver(),
            new StandardTypeArgumentResolver()
    );

    private final List<HttpMessageConverter> messageConverters = List.of(
            new StringHttpMessageConverter(),
            new ObjectHttpMessageConverter()
    );

    public Object[] resolve(HttpRequest request, Method method) {
        List<Object> requiredParams = new ArrayList<>();

        try {
            for (Parameter parameter : method.getParameters()) {
                requiredParams.add(makeParameter(request, parameter));
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return requiredParams.toArray();
    }

    private Object makeParameter(HttpRequest request, Parameter parameter) throws ReflectiveOperationException {
        final Class<?> parameterType = parameter.getType();
        final String parameterName = parameter.getName();

        if (parameter.getType() == Model.class) {
            return new Model();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)) {
        }
        //TODO 예외 처리
        if (request.getMethod() == HttpMethod.POST) {
            return getMessageConverter(parameterType).read(parameterType, parameterName, request.getRequestBody());
        }

        return getArgumentResolver(parameterType).resolve(request, parameterType, parameterName);
    }

    private HttpMessageConverter getMessageConverter(Class<?> parameterType) {
        return messageConverters.stream()
                .filter(httpMessageConverter -> httpMessageConverter.canRead(parameterType, ContentType.FORM_DATA))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("지원하지 않는 파라미터 타입"));
    }


    private MethodArgumentResolver getArgumentResolver(Class<?> parameterType) {
        return resolvers.stream()
                .filter(resolver -> resolver.canResolve(parameterType))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("지원하지 않는 파라미터 타입"));
    }
}
