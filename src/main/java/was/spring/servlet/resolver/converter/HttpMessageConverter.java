package was.spring.servlet.resolver.converter;

import was.http.enums.ContentType;

public interface HttpMessageConverter {
    String PARAMETER_SEPERATOR = "&";
    String PARAM_ENTRY_SEPERATOR = "=";

    boolean canRead(Class<?> parameterType, ContentType contentType);

    Object read(Class<?> parameterType, String parameterName, String requestBody) throws ReflectiveOperationException;
}
