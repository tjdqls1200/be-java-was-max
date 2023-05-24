package was.spring.servlet.resolver.converter;

import was.request.ContentType;
import was.spring.servlet.mvc.controller.RequestBody;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectHttpMessageConverter implements HttpMessageConverter {
    private static final String SETTER_PREFIX = "set";

    @Override
    public boolean canRead(Parameter parameter, ContentType contentType) {
        if (parameter.isAnnotationPresent(RequestBody.class) || contentType == ContentType.FORM_DATA) {
            return true;
        }

        return false;
    }

    @Override
    public Object read(Parameter parameter, String bodyMessage) throws Exception {
        final Class<?> classType = parameter.getType();
        final Object object = classType.getDeclaredConstructor().newInstance();
        final var args = parseArguments(bodyMessage);

        for (Field field : classType.getDeclaredFields()) {
            String fieldName = field.getName();
            String setterName = formatSetterMethodName(fieldName);
            Object argumentValue = getArgumentValue(field.getType(), args.get(fieldName));

            classType.getDeclaredMethod(setterName, field.getType()).invoke(object, argumentValue);
        }

        return object;
    }

    private Object getArgumentValue(Class<?> fieldType, String value) {
        if (fieldType == Integer.class) {
            return Integer.parseInt(value);
        }
        if (fieldType == Long.class) {
            return Long.parseLong(value);
        }
        if (fieldType == Boolean.class) {
            return Boolean.valueOf(value);
        }

        return value;
    }

    private String formatSetterMethodName(String fieldName) {
        return SETTER_PREFIX + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }


    private Map<String, String> parseArguments(String bodyMessage) {
        return Arrays.stream(bodyMessage.split("&"))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
    }
}
