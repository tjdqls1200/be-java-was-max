package was.spring.servlet.resolver.converter;

import was.request.ContentType;
import was.spring.servlet.resolver.ParameterType;

public class StringHttpMessageConverter implements HttpMessageConverter {
    @Override
    public boolean canRead(Class<?> type, ContentType contentType) {
        return contentType == ContentType.FORM_DATA && ParameterType.isStandardType(type);
    }

    @Override
    public Object read(Class<?> parameterType, String parameterName, String requestBody) throws ReflectiveOperationException {
        final int valueStartIndex = requestBody.indexOf(PARAM_ENTRY_SEPERATOR, requestBody.indexOf(parameterName)) + 1;
        final int valueEndIndex = requestBody.indexOf(PARAMETER_SEPERATOR, valueStartIndex);

        return requestBody.substring(valueStartIndex, valueEndIndex);
    }
}
