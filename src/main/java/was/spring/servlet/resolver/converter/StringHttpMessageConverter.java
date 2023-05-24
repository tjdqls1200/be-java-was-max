package was.spring.servlet.resolver.converter;

import was.request.ContentType;
import was.spring.servlet.mvc.controller.RequestBody;
import was.spring.servlet.resolver.converter.HttpMessageConverter;

import java.lang.reflect.Parameter;

public class StringHttpMessageConverter implements HttpMessageConverter {
    @Override
    public boolean canRead(Parameter parameter, ContentType contentType) {
        if (parameter.isAnnotationPresent(RequestBody.class) || contentType == ContentType.FORM_DATA) {
            Class<?> classType = parameter.getType();

            return classType == Integer.class || classType == Long.class || classType == String.class;
        }

        return false;
    }

    @Override
    public Object read(Parameter parameter, String bodyMessage) {
        int startIndex = bodyMessage.indexOf(parameter.getName()) + parameter.getName().length();

        return null;
    }
}
