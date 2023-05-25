package was.spring.servlet.resolver.converter;

import was.request.ContentType;
import was.spring.servlet.resolver.ObjectBinder;
import was.spring.servlet.resolver.ParameterType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectHttpMessageConverter implements HttpMessageConverter {
    private static final int KEY_IDX = 0;
    private static final int VALUE_IDX = 1;

    private final ObjectBinder objectBinder = new ObjectBinder();



    @Override
    public boolean canRead(Class<?> parameterType, ContentType contentType) {
        return !ParameterType.isStandardType(parameterType);
    }

    @Override
    public Object read(Class<?> parameterType, String parameterName, String requestBody) throws ReflectiveOperationException {
        return objectBinder.mapToBind(parameterType, parseArguments(requestBody));
    }

    private Map<String, String> parseArguments(String requestBody) {
        return Arrays.stream(requestBody.split(PARAMETER_SEPERATOR))
                .map(entry -> entry.split(PARAM_ENTRY_SEPERATOR))
                .collect(Collectors.toMap(entry -> entry[KEY_IDX], entry -> entry[VALUE_IDX]));
    }
}
