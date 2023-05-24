package was.spring.servlet.resolver.converter;

import was.request.ContentType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.NoSuchElementException;

public interface HttpMessageConverter {
    boolean canRead(Parameter parameter, ContentType contentType);

    Object read(Parameter parameter, String bodyMessage) throws Exception;
}
